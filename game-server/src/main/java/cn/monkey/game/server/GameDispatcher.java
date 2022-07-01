package cn.monkey.game.server;

import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.game.core.PlayerManager;
import cn.monkey.game.repository.UserRepository;
import cn.monkey.game.utils.GameCmdUtil;
import cn.monkey.proto.CmdType;
import cn.monkey.proto.Command;
import cn.monkey.proto.Game;
import cn.monkey.server.Dispatcher;
import cn.monkey.server.Session;
import cn.monkey.state.scheduler.SchedulerManager;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.util.AttributeKey;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.locks.ReentrantLock;

public class GameDispatcher implements Dispatcher {

    static final AttributeKey<cn.monkey.data.User> USER_KEY = AttributeKey.newInstance("user");
    private final LoadingCache<String, ReentrantLock> lockCache;
    private final SchedulerManager<PlayerCmdPair> schedulerManager;
    private final PlayerManager playerManager;

    private final UserRepository userRepository;

    private final Scheduler scheduler;

    private final Scheduler loginScheduler;

    public GameDispatcher(SchedulerManager<PlayerCmdPair> schedulerManager,
                          PlayerManager playerManager,
                          UserRepository userRepository) {
        this.schedulerManager = schedulerManager;
        this.playerManager = playerManager;
        this.userRepository = userRepository;
        this.lockCache = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(2))
                .build(new CacheLoader<>() {
                    @Override
                    @NonNull
                    public ReentrantLock load(@NonNull String key) throws Exception {
                        return new ReentrantLock();
                    }
                });
        this.scheduler = Schedulers.newSingle("dispatcher");
        this.loginScheduler = Schedulers.newParallel("login", 5);
    }


    @Override
    public void dispatch(Session session, Command.Package pkg) {
        int cmdType = pkg.getCmdType();
        ReentrantLock reentrantLock = this.lockCache.getUnchecked(session.id());
        if (!reentrantLock.tryLock()) {
            return;
        }
        try {
            if (cmdType == CmdType.LOGIN) {
                Mono.just(pkg)
                        .map(p -> {
                            try {
                                return Game.Session.parseFrom(p.getContent());
                            } catch (InvalidProtocolBufferException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .flatMap(userSession -> this.userRepository.get(userSession.getToken()))
                        .doOnNext(user -> this.playerManager.findOrCreate(session, user))
                        .doOnNext(user -> session.setAttribute(USER_KEY, user))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("session is not exists")))
                        .doOnError(e -> session.write(GameCmdUtil.error(e)))
                        .subscribeOn(this.loginScheduler)
                        .subscribe();
            } else {
                Mono.just(pkg)
                        .flatMap(p -> {
                            String groupId = p.getGroupId();
                            cn.monkey.data.User user = session.getAttribute(USER_KEY);
                            if (user == null) {
                                return Mono.empty();
                            }
                            return Mono.just(Tuples.of((this.playerManager.findOrCreate(session, user)), groupId));
                        })
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("user is not login")))
                        .doOnNext(t -> this.schedulerManager.addEvent(t.getT2(), new PlayerCmdPair(t.getT1(), pkg)))
                        .doOnError(e -> session.write(GameCmdUtil.error(e)))
                        .subscribeOn(this.scheduler)
                        .subscribe();
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}

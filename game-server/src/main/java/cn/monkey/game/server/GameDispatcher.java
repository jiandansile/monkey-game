package cn.monkey.game.server;

import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.game.core.PlayerManager;
import cn.monkey.proto.Command;
import cn.monkey.server.Dispatcher;
import cn.monkey.server.Session;
import cn.monkey.state.scheduler.SchedulerManager;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.lang.NonNull;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.locks.ReentrantLock;

public class GameDispatcher implements Dispatcher {

    private final LoadingCache<String, ReentrantLock> lockCache;
    private final SchedulerManager<PlayerCmdPair> schedulerManager;
    private final PlayerManager playerManager;
    private final Scheduler scheduler;
    public GameDispatcher(SchedulerManager<PlayerCmdPair> schedulerManager,
                          PlayerManager playerManager) {
        this.schedulerManager = schedulerManager;
        this.playerManager = playerManager;
        this.lockCache = CacheBuilder.newBuilder()
                .expireAfterAccess(Duration.ofSeconds(2))
                .build(new CacheLoader<String, ReentrantLock>() {
                    @Override
                    @NonNull
                    public ReentrantLock load(@NonNull String key) throws Exception {
                        return new ReentrantLock();
                    }
                });
        this.scheduler = Schedulers.newSingle("dispatcher");
    }


    @Override
    public void dispatch(Session session, Command.Package pkg) {
    }
}

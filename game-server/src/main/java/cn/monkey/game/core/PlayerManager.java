package cn.monkey.game.core;

import cn.monkey.commons.bean.Refreshable;
import cn.monkey.commons.utils.Timer;
import cn.monkey.data.User;
import cn.monkey.proto.Command;
import cn.monkey.server.Session;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager implements Refreshable {

    private volatile ConcurrentHashMap<String, Player> playerMap;

    private final Timer timer;

    public PlayerManager(Timer timer) {
        this.timer = timer;
        this.playerMap = new ConcurrentHashMap<>();
    }

    protected User wrapper(Command.Package pkg){
        try {
            cn.monkey.proto.User.Login login = cn.monkey.proto.User.Login.parseFrom(pkg.getContent());
            String username = login.getUsername();
            User user = new User();
            user.setUsername(username);
            user.setUid(login.getUid());
            return user;
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    public Player findOrCreate(Session session, Command.Package pkg) {
        User user = this.wrapper(pkg);
        final ConcurrentHashMap<String, Player> playerMap = this.playerMap;
        Player p = playerMap.compute(user.getUid(), (s, player) -> {
            if (null == player) {
                return new Player(session, user, timer);
            }
            player.setSession(session);
            return player;
        });
        this.playerMap = playerMap;
        return p;
    }

    @Override
    public void refresh() {

    }
}

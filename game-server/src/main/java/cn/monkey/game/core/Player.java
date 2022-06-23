package cn.monkey.game.core;

import cn.monkey.data.User;
import cn.monkey.proto.Command;
import cn.monkey.server.Session;

public class Player {
    private Session session;

    private final User user;

    public Player(Session session,
                  User user) {
        this.session = session;
        this.user = user;
    }

    public String getId() {
        return this.user.getUid();
    }

    public String getUsername(){
        return this.user.getUsername();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void write(Command.PackageGroup packageGroup) {
        this.session.write(packageGroup);
    }
}

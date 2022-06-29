package cn.monkey.game.core;

import cn.monkey.commons.utils.Timer;
import cn.monkey.data.User;
import cn.monkey.proto.Command;
import cn.monkey.server.Session;

public class Player {
    private Session session;

    private final User user;

    private final Timer timer;

    private volatile long lastOperateTime;

    public Player(Session session,
                  User user,
                  Timer timer) {
        this.user = user;
        this.session = session;
        this.timer = timer;
        this.lastOperateTime = timer.getCurrentTimeMs();
    }

    public String getId() {
        return this.session.id();
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public String getUid() {
        return this.user.getUid();
    }

    public void setSession(Session session) {
        if (this.session.id().equals(session.id())) {
            return;
        }
        this.session = session;
    }

    public boolean isActive() {
        return this.session.isActive();
    }

    public void refreshLastOperateTime() {
        this.lastOperateTime = this.timer.getCurrentTimeMs();
    }

    public void write(Command.PackageGroup packageGroup) {
        this.session.write(packageGroup);
    }
}

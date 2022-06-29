package cn.monkey.game.core;

import cn.monkey.proto.Command;
import com.google.common.base.Preconditions;

public class PlayerCmdPair {

    private final Player player;
    private final Command.Package pkg;

    public PlayerCmdPair(Player player, Command.Package pkg) {
        Preconditions.checkNotNull(pkg);
        Preconditions.checkNotNull(player);
        this.player = player;
        this.pkg = pkg;
    }

    public Command.Package getPkg() {
        return pkg;
    }

    public Player getPlayer() {
        return player;
    }
}

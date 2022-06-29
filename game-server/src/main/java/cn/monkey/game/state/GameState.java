package cn.monkey.game.state;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.core.Player;
import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.proto.CmdType;
import cn.monkey.proto.Command;
import cn.monkey.state.core.OncePerInitState;
import cn.monkey.state.core.StateContext;
import cn.monkey.state.core.StateGroup;

import java.util.List;

public abstract class GameState extends OncePerInitState<PlayerCmdPair> {

    public GameState(Timer timer, StateGroup<PlayerCmdPair> stateGroup) {
        super(timer, stateGroup);
    }

    @Override
    public void fireEvent(PlayerCmdPair playerCmdPair) throws Exception {
        Command.Package pkg = playerCmdPair.getPkg();
        int cmdType = pkg.getCmdType();
        if (cmdType == CmdType.ENTER) {
            this.enter(playerCmdPair.getPlayer(), pkg);
            return;
        }
        this.handleCmd(playerCmdPair.getPlayer(), pkg);
    }

    @Override
    public GameStateContext getStateContext() {
        return (GameStateContext) super.getStateContext();
    }

    protected void enter(Player player, Command.Package pkg) {

    }

    protected final boolean tryAddPlayer(Player player){
        return true;
    }


    protected void handleCmd(Player player, Command.Package pkg) {

    }
}

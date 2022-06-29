package cn.monkey.game.state;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.core.Player;
import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.proto.Command;
import cn.monkey.state.core.StateContext;
import cn.monkey.state.core.StateGroup;

public class StartState extends GameState {

    public static final String CODE = "start";

    public StartState(Timer timer, StateGroup<PlayerCmdPair> stateGroup) {
        super(timer, stateGroup);
    }

    @Override
    public String code() {
        return CODE;
    }

    @Override
    protected void enter(Player player, Command.Package pkg) {
        StateContext stateContext = this.getStateContext();

    }

    @Override
    public String finish() throws Exception {
        return null;
    }
}

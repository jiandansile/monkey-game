package cn.monkey.game.state;

import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.state.core.SimpleStateGroup;

public class GameStateGroup extends SimpleStateGroup<PlayerCmdPair> {
    public GameStateGroup(String id, GameStateContext stateContext, boolean canAutoUpdate) {
        super(id, stateContext, canAutoUpdate);
    }

    @Override
    public GameStateContext getStateContext() {
        return (GameStateContext)super.getStateContext();
    }
}

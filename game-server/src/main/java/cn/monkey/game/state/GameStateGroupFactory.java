package cn.monkey.game.state;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.state.core.SimpleStateGroupFactory;
import cn.monkey.state.core.StateGroup;

public class GameStateGroupFactory extends SimpleStateGroupFactory<PlayerCmdPair> {
    public GameStateGroupFactory(Timer timer) {
        super(timer);
    }

    @Override
    public StateGroup<PlayerCmdPair> create(String id) {
        return super.create(id);
    }
}

package cn.monkey.game.state;

import cn.monkey.commons.utils.AttributeKey;
public interface StateGroupConstants {
    AttributeKey<GameStateContext> GAME_STATE_CONTEXT = AttributeKey.newInstance("gameStateContext");
}

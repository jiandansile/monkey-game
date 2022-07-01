package cn.monkey.game.state;

import cn.monkey.game.core.Player;
import cn.monkey.state.core.StateContext;

import java.util.HashMap;
import java.util.Map;

public class GameStateContext implements StateContext {

    private final int playerSize;

    private final Map<String, Player> players;

    private String password;

    public GameStateContext(int playerSize) {
        this.players = new HashMap<>(playerSize);
        this.playerSize = playerSize;
    }

    public boolean tryAddPlayer(Player player) {
        if (this.players.containsKey(player.getId())) {
            this.players.put(player.getId(), player);
            return true;
        }
        if (this.players.size() >= this.playerSize) {
            return false;
        }
        this.players.put(player.getId(), player);
        return true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

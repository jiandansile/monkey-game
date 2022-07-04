package cn.monkey.game.state;

import cn.monkey.commons.utils.Timer;
import cn.monkey.game.core.Player;
import cn.monkey.game.core.PlayerCmdPair;
import cn.monkey.proto.CmdType;
import cn.monkey.proto.Command;
import cn.monkey.proto.CommandUtil;
import cn.monkey.proto.Game;
import cn.monkey.state.core.OncePerInitState;
import cn.monkey.state.core.StateContext;
import cn.monkey.state.core.StateGroup;
import com.google.common.base.Strings;
import com.google.protobuf.InvalidProtocolBufferException;

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
    protected void onInit() {
        // do nothing here
    }

    @Override
    public GameStateContext getStateContext() {
        return (GameStateContext) super.getStateContext();
    }

    protected void enter(Player player, Command.Package pkg) {
        GameStateContext stateContext = this.getStateContext();
        String password = stateContext.getPassword();
        if (!Strings.isNullOrEmpty(password)) {
            try {
                Game.Enter enter = Game.Enter.parseFrom(pkg.getContent());
                String enterPassword = enter.getPassword();
                if (!enterPassword.equals(password)) {
                    log.error("invalid password enter");
                    player.write(CommandUtil.packageGroup());
                }
            } catch (InvalidProtocolBufferException e) {

            }
        }
    }


    protected void handleCmd(Player player, Command.Package pkg) {

    }
}

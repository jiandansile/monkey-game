package cn.monkey.game.utils;

import cn.monkey.data.vo.ResultCode;
import cn.monkey.game.core.Player;
import cn.monkey.game.state.GameStateContext;
import cn.monkey.proto.CmdType;
import cn.monkey.proto.Command;
import cn.monkey.proto.CommandUtil;
import cn.monkey.proto.Game;

import java.util.Collection;

public abstract class GameCmdUtil {

    public static Command.PackageGroup error(Throwable error){
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(ResultCode.ERROR, error.getMessage());
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        return CommandUtil.packageGroup(builder.build());
    }

    public static Command.PackageGroup  enterSuccess(GameStateContext stateContext){
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(ResultCode.OK, null);
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        Collection<Player> players = stateContext.getPlayers();
        if(players.size() > 0){
            Game.EnterResult.Builder enterResultBuilder = Game.EnterResult.newBuilder();
            for(Player player: players){
                enterResultBuilder.addPlayers(build(player));
            }
            builder.setContent(enterResultBuilder.build().toByteString());
        }
        return CommandUtil.packageGroup(builder.build());
    }

    public  static Game.Player build(Player player){
        String uid = player.getUid();
        String username = player.getUsername();
        Game.Player.Builder builder = Game.Player.newBuilder();
        return builder.setId(uid).setName(username).build();
    }

    public static Command.PackageGroup enterFail(String msg){
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(ResultCode.ERROR, msg);
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        builder.setCmdType(CmdType.ENTER_RESULT);
        return CommandUtil.packageGroup(builder.build());
    }
}

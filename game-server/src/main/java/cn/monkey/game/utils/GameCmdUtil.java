package cn.monkey.game.utils;

import cn.monkey.data.vo.ResultCode;
import cn.monkey.proto.Command;
import cn.monkey.proto.CommandUtil;

public abstract class GameCmdUtil {

    public static Command.PackageGroup error(Throwable error){
        Command.ResultMessage resultMessage = CommandUtil.resultMessage(ResultCode.ERROR, error.getMessage());
        Command.Package.Builder builder = Command.Package.newBuilder();
        builder.setResultMsg(resultMessage);
        return CommandUtil.packageGroup(builder.build());
    }
}

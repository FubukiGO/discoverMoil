package com.yk.util;

import com.yk.commons.constant.Cmd;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * Created by hhg on 2018/8/20.
 */
public class CommandExecUtil {

    public static String cmdExe(CommandLine cmdLine) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        DefaultExecutor exec=new DefaultExecutor();
        exec.setExitValues(null);
        //设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(10 * 1000);
        exec.setWatchdog(watchdog);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(cmdLine);
        //不同操作系统注意编码，否则结果乱码
        String out = outputStream.toString("GBK");
        String error = errorStream.toString("GBK");
        return out + error;
    }

    public static Process exec(String cmd,Runtime runtime){
        StringBuffer sb=new StringBuffer();
        Process process=null;
        try {
            process = runtime.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return process;
    }

    public static Runtime getRuntime(){
        return Runtime.getRuntime();
    }



    public static String linkRedis(String ip,String cmd) throws Exception{
        return cmdExe(CommandLine.parse(Cmd.linkCmd.replaceAll("#host",ip)+cmd));
    }



}

package com.yk.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by hhg on 2018/8/20.
 */
public class CmdExeUtil {

    public static String cmdExe(String cmdLin) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //接收异常结果流
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

        CommandLine cmdLine = CommandLine.parse(cmdLin);
        DefaultExecutor exec = new DefaultExecutor();
        exec.setExitValues(null);
        //设置一分钟超时
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
        exec.setWatchdog(watchdog);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
        exec.setStreamHandler(streamHandler);
        exec.execute(cmdLine);
        //不同操作系统注意编码，否则结果乱码
        String out = outputStream.toString("GBK");
        String error = errorStream.toString("GBK");
        return out + error;
    }

    public static String exec(String [] cmd){
        Runtime runtime = Runtime.getRuntime();
        StringBuffer sb=new StringBuffer();
        try {
            Process process = runtime.exec(cmd);
            String line = null;
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}

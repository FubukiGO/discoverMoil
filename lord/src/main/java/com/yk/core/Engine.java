package com.yk.core;

import com.yk.service.impl.Raider;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: akhan
 * @Description:
 * @Date: 上午10:30 2018/8/20
 */

import com.yk.util.CmdExeUtil;
import com.yk.util.ReadFromFile;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.List;

public class Engine {

    public static Queue<String> addressQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws  Exception{
//        new Thread(new Raider()).run();
        addressQueue.add("localhost");

        System.out.print("0======{}===================>");
        String ip=addressQueue.poll();
        CmdExeUtil.exec("redis-cli -h "+ip+" -p 6379");



//        ReadFromFile.WriteStringToFile("C:\\Users\\hhg\\Desktop\\ip.txt","192.168.1.173");



    }
}

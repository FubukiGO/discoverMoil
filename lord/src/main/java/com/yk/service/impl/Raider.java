package com.yk.service.impl;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @Author: akhan
 * @Description:
 * @Date: 下午5:03 2018/8/17
 */
public class Raider implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("---------开始嗅探--------");
        //CommandLine line = new CommandLine("nmap -v -n -Pn -p 6379 -sV --script redis-info 192.168.56.1/24");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[]{"nmap", "-p", "6379", "--script", "6379", "redis-info", "192.168.0.1/24"});
            String line = null;
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

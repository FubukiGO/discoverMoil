package com.yk.service.impl;

import com.yk.commons.constant.PatternConstant;
import com.yk.core.Engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

/**
 * @Author: akhan
 * @Description:
 * @Date: 下午5:03 2018/8/17
 */
public class Raider implements Runnable {

    private String router;

    private Raider() {}

    public Raider(String router){
        this.router = router;
    }

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
        System.out.println("---------scanning--------");
        //CommandLine line = new CommandLine("nmap -v -n -Pn -p 6379 -sV --script redis-info 59.192.0.0/8 | grep Discovered | awk '{print $6}'");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(new String[]{"nmap", "-v", "-n", "-Pn", "-p", "6379", "-sV", "--script", "redis-info", router, "|", "grep", "Discovered", "|", "awk", "'{print $6}'"});
            String line = null;
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("Discovered")) {
                    Matcher matcher = PatternConstant.IP.matcher(line);
                    if (matcher.find()) Engine.routeQueue.offer(matcher.group());
                    matcher = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

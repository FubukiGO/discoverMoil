package com.yk.service.impl;

import com.google.common.collect.Lists;
import com.yk.commons.constant.PatternConstant;
import com.yk.core.Engine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @Author: akhan
 * @Description:
 * @Date: 下午5:03 2018/8/17
 */
public class Raider implements Runnable {

    private static final Logger logger = LogManager.getLogger();

    private String router;

    private Raider() {
    }

    public Raider(String router) {
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
        logger.info("---------scanning[" + router + "]start--------");
        //CommandLine line = new CommandLine("nmap -v -n -Pn -p 6379 -sV --script redis-info 192.168.0.1/24 | grep Discovered | awk '{print $6}'");
        //nmap -v -n -Pn -p 6379 -sV --script redis-info 192.168.0.1/24 | awk '{if ($1 == "6379/tcp" && $2 == "open"){print a}}'
        Runtime runtime = Runtime.getRuntime();
        List<String> results = Lists.newArrayList();
        try {
            Process process = runtime.exec(new String[]{"nmap", "-v", "-n", "-Pn", "-p", "6379", "-sV", "--script", "redis-info", router, "|", "grep", "Discovered", "|", "awk", "'{print $6}'"});
            String line = null;
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String foo = line;
                results.add(foo);
                logger.info(line);
                if (line.startsWith("| redis-info:")) {
                    foo = results.get(results.size() - 6);
                    Matcher matcher = PatternConstant.IP.matcher(foo);
                    if (matcher.find()) Engine.routeQueue.offer(matcher.group());
                    matcher = null;
                }
            }
            logger.info("---------scanning[" + router + "]end--------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
    }
}

package com.yk.service.handler;

import com.yk.commons.constant.PatternConstant;
import com.yk.core.Engine;
import com.yk.service.impl.Raider;
import com.yk.util.ThreadPoolBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;

import static com.yk.core.Engine.pool1;

/**
 * @Author: akhan
 * @Description:
 * @Date: 下午6:51 2018/8/20
 */
public class ScannerHandler {
    private static final Logger logger = LogManager.getLogger();


    private String[] urls = null;

    private ScannerHandler() {
    }

    public ScannerHandler(String[] urls) {
        this.urls = urls;
    }

    public void __START__() {

        for (String url : urls) {
            try {

                URL foo = new URL(url);

                URLConnection connection = foo.openConnection();

                connection.setConnectTimeout(500);
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
                connection.addRequestProperty("Accept", "*/*");
                connection.addRequestProperty("Connection", "keep-alive");

                connection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = null;
                while ((line = br.readLine()) != null) {
                    String route = null;
                    Matcher matcher = PatternConstant.ROUTE.matcher(line);
                    if (matcher.find()) route = matcher.group();
                    else {
                        String ip = null;
                        matcher = PatternConstant.IP.matcher(line);
                        if (matcher.find()) {
                            ip = matcher.group();
                            matcher = PatternConstant.MASK.matcher(line);
                            if (matcher.find() && StringUtils.isNotBlank(ip)) {
                                route = ip + "/" + StringUtils.countMatches(matcher.group(), "255") * 8;
                            }
                        }
                    }
                    matcher = null;
                    if (StringUtils.isNotBlank(route)){
                        pool1.execute(new Raider(route));
                    }
                    logger.info(route);
                }
            } catch (Exception e) {

            }
        }
    }
}

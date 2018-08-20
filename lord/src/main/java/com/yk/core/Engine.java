package com.yk.core;

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

    public static void main(String[] args) {
        new Thread(new Raider()).run();

    }
}

package com.py7hon.scanner.normal;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/30 12:09
 */
class MyThreadFactory implements ThreadFactory {
    /**
     * 线程编号
     */
    private int threadNum;

    public MyThreadFactory() {
        threadNum = 0;
    }

    @Override
    public Thread newThread(Runnable r) {
        threadNum++;
        return new Thread(r, String.format("scanner-thread-%d", threadNum));
    }
}

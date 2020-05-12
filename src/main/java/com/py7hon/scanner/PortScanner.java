package com.py7hon.scanner;

import com.py7hon.properties.ScannerProperties;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 10:18
 */
public class PortScanner {
    /**
     * 主程序的版本
     */
    private static final String MAIN_VERSION = "1.1";

    /**
     * 全扫描方式版本
     */
    private static final String FULL_SCAN_VERSION = "1.1";

    /**
     * 扫描的配置
     */
    protected ScannerProperties properties;

    /**
     * 当前正在被扫描的端口号
     */
    protected int currentPort;

    /**
     * 总共要扫描的端口号数目
     */
    protected int totalPortNum;

    /**
     * 已扫描的端口号数目
     */
    protected int scannedPortNum = 0;

    Queue<Integer> queue = new LinkedList<>();

    /**
     * 已开启的端口
     */
    List<Integer> openedPorts = new ArrayList<>();

    public PortScanner(ScannerProperties properties) {
        this.properties = properties;
        // 如果是扫描一定范围的
        if (this.properties.isScanRangePort()) {
            this.currentPort = this.properties.getStartPort();
            this.totalPortNum = this.properties.getEndPort() - this.properties.getStartPort() + 1;
        } else {
            // 如果扫描的是不特定范围的
            for (int port : properties.getPorts()) {
                queue.add(port);
            }
            this.totalPortNum = queue.size();
        }
    }

    /**
     * 打印程序信息
     */
    public static void printHelloMsg() {
        System.out.println("主程序版本：" + MAIN_VERSION);
        System.out.println("全扫描程序版本：" + FULL_SCAN_VERSION);
    }

    public void scan() {
        ExecutorService pool = new ThreadPoolExecutor(properties.getThreadNumber(), properties.getThreadNumber(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                new MyThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        //线程池
        int threadNumber = properties.getThreadNumber();
        for (int i = 0; i < threadNumber; i++) {
            pool.submit(Objects.requireNonNull(ScanTaskFactory.createTask(this)));
            System.out.printf("\r正在创建线程...(%d/%d)", i + 1, threadNumber);
        }
        System.out.println();

        pool.shutdown();

        while (true) {
            if (pool.isTerminated()) {
                System.out.println("\n\n扫描结束");
                System.out.println("开放的端口：");
                int length = openedPorts.size();
                for (int i = 0; i < length; i++) {
                    System.out.printf("(%d)\t%5d\n", i + 1, openedPorts.get(i));
                }
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Queue<Integer> getQueue() {
        return queue;
    }

    /**
     * 出队
     *
     * @return 出队的值
     */
    public Integer queueRemoveFirst() {
        return queue.remove();
    }

    public int getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(int currentPort) {
        this.currentPort = currentPort;
    }

    public ScannerProperties getProperties() {
        return properties;
    }

    public int getTotalPortNum() {
        return totalPortNum;
    }

    public int getScannedPortNum() {
        return scannedPortNum;
    }

    public void setScannedPortNum(int scannedPortNum) {
        this.scannedPortNum = scannedPortNum;
    }
}

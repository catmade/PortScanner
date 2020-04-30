package com.py7hon.scanner.normal;

import com.py7hon.properties.ScannerProperties;
import com.py7hon.scanner.BaseScanner;
import com.py7hon.scanner.IScanner;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

import java.util.*;
import java.util.concurrent.*;

/**
 * 普通的扫描方式
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 10:06
 */
public class NormalScanner extends BaseScanner implements IScanner {

    Queue<Integer> queue = new LinkedList<Integer>();

    /**
     * 已开启的端口
     */
    List<Integer> openedPorts = new ArrayList<Integer>();

    public NormalScanner(ScannerProperties properties) {
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

    @Override
    public void scan() {

        ExecutorService pool = new ThreadPoolExecutor(properties.getThreadNumber(), properties.getThreadNumber(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                new MyThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        //线程池
        int threadNumber = properties.getThreadNumber();
        for (int i = 0; i < threadNumber; i++) {
            pool.submit(new NormalScanTask(this));
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
}
















package com.py7hon.scanner.normal;

import com.py7hon.properties.ScannerProperties;
import com.py7hon.scanner.BaseScanner;
import com.py7hon.scanner.IScanner;

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

    private static final Integer[] PORTS = new Integer[]{21, 22, 23, 25, 26, 53, 69, 80, 110, 143,
            443, 465, 69, 161, 162, 135, 995, 1080, 1158, 1433, 1521, 2100, 3128, 3306, 3389,
            7001, 8080, 8081, 9080, 9090, 43958, 135, 445, 1025, 1026, 1027, 1028, 1055, 5357};
    Queue<Integer> queue = new LinkedList<Integer>(Arrays.asList(PORTS));

    /**
     * 已开启的端口
     */
    List<Integer> openedPorts = new ArrayList<Integer>();

    public NormalScanner(ScannerProperties properties) {
        this.properties = properties;
        // 如果是范围扫描
        if (this.properties.isScanRangePort()) {
            this.currentPort = this.properties.getStartPort();
            this.totalPortNum = this.properties.getEndPort() - this.properties.getStartPort() + 1;
        } else {
            // 如果是常规扫描
            this.totalPortNum = NormalScanner.PORTS.length;
        }
    }

    @Override
    public void scan() {
        ExecutorService pool = new ThreadPoolExecutor(1, properties.getThreadNumber(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        //线程池
        for (int i = 0; i < properties.getThreadNumber(); i++) {
            pool.execute(new NormalScanTask(this));
        }
        pool.shutdown();

        while (true) {
            if (pool.isTerminated()) {
                System.out.println("扫描结束");
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
















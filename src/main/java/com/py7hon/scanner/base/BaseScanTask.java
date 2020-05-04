package com.py7hon.scanner.base;

import com.py7hon.scanner.PortScanner;

import java.net.InetAddress;

/**
 * 扫描任务
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/30 14:36
 */
public abstract class BaseScanTask implements Runnable {

    /**
     * 开始端口
     */
    private final int startPort;

    /**
     * 结束端口
     */
    private final int endPort;

    /**
     * 扫描器
     */
    protected final PortScanner scanner;

    /**
     * 连接超时时间，单位毫秒
     */
    protected final int timeout;

    /**
     * 是否显示每个端口的扫描过程
     */
    private final boolean printProcess;

    /**
     * 主机地址
     */
    protected final InetAddress inetAddress;

    /**
     * 当前计算任务的进度
     */
    private String progress;

    public BaseScanTask(PortScanner scanner) {
        this.scanner = scanner;
        this.timeout = scanner.getProperties().getTimeOut();
        this.startPort = scanner.getProperties().getStartPort();
        this.endPort = scanner.getProperties().getEndPort();
        this.printProcess = scanner.getProperties().isPrintProcess();
        this.inetAddress = scanner.getProperties().getTarget();
    }

    @Override
    public void run() {
        // 执行前先等一下，等大概所有线程都创建了再运行
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (scanner.getProperties().isScanRangePort()) {
            this.scanRange();
        } else {
            this.scanCommon();
        }
    }

    /**
     * 扫描一定范围的端口
     */
    private void scanRange() {

        while (true) {
            int port;

            // 获取端口
            synchronized (scanner) {
                port = scanner.getCurrentPort();
                boolean outOfRange = (port > endPort || port < startPort);
                if (outOfRange) {
                    return;
                }

                int scannedPortNum = scanner.getScannedPortNum();
                scannedPortNum++;
                setProgress(String.format("(%d/%d)", scannedPortNum, scanner.getTotalPortNum()));

                scanner.setCurrentPort(port + 1);
                scanner.setScannedPortNum(scannedPortNum);
            }

            scan(port);
        }
    }

    /**
     * 扫描部分端口
     */
    private void scanCommon() {
        int port;
        int timeout = scanner.getProperties().getTimeOut();
        while (true) {

            // 获取端口
            synchronized (scanner) {
                if (scanner.getQueue().size() == 0) {
                    return;
                }
                port = scanner.queueRemoveFirst();

                int scannedPortNum = scanner.getScannedPortNum();
                scannedPortNum++;
                setProgress(String.format("(%d/%d)", scannedPortNum, scanner.getTotalPortNum()));

                scanner.setScannedPortNum(scannedPortNum);
            }

            scan(port);
        }
    }

    private void setProgress(String progress) {
        this.progress = progress;
        if (!printProcess) {
            System.out.print("\r线程全部开启，正在扫描..." + progress);
        }
    }

    /**
     * 扫描
     *
     * @param port 端口
     */
    protected abstract void scan(int port);

    /**
     * 输出扫描结果
     *
     * @param port   端口
     * @param opened 是否开发
     */
    protected void print(int port, boolean opened) {
        // 如果不打印
        if (!printProcess) {
            return;
        }

        if (opened) {
            System.out.printf("port: %5d opened. %s. [%s]\n", port, progress, Thread.currentThread().getName());
        } else {
            System.err.printf("port: %5d closed. %s. [%s]\n", port, progress, Thread.currentThread().getName());
        }
    }
}

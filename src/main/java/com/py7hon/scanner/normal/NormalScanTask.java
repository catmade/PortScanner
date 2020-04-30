package com.py7hon.scanner.normal;

import java.io.IOException;
import java.net.*;

/**
 * 普通扫描任务
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 10:06
 */
class NormalScanTask implements Runnable {

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
    private final NormalScanner scanner;

    /**
     * 连接超时时间，单位毫秒
     */
    private final int timeout;

    /**
     * 是否显示每个端口的扫描过程
     */
    private final boolean printProcess;

    /**
     * 主机地址
     */
    private final InetAddress inetAddress;

    /**
     * 当前计算任务的进度
     */
    private String progress;

    public NormalScanTask(NormalScanner normalScanner) {
        this.scanner = normalScanner;
        this.timeout = scanner.getProperties().getTimeOut();
        this.startPort = scanner.getProperties().getStartPort();
        this.endPort = scanner.getProperties().getEndPort();
        this.printProcess = scanner.getProperties().isPrintProcess();
        this.inetAddress = scanner.getProperties().getTarget();
    }

    @Override
    public void run() {
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
            synchronized (NormalScanTask.class) {
                port = scanner.getCurrentPort();
                boolean outOfRange = (port > endPort || port < startPort);
                if (outOfRange) {
                    return;
                }

                int scannedPortNum = scanner.getScannedPortNum();
                scannedPortNum++;
                progress = String.format("(%d/%d)", scannedPortNum, scanner.getTotalPortNum());

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
            synchronized (NormalScanTask.class) {
                if (scanner.queue.size() == 0) {
                    return;
                }
                port = scanner.queue.remove();

                int scannedPortNum = scanner.getScannedPortNum();
                scannedPortNum++;
                progress = String.format("(%d/%d)", scannedPortNum, scanner.getTotalPortNum());

                scanner.setScannedPortNum(scannedPortNum);
            }

            scan(port);
        }
    }

    /**
     * 扫描
     *
     * @param port 端口
     */
    private void scan(int port) {
        // 套接字
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
        try {
            // 对目标主机的指定端口进行连接，超时后连接失败
            socket.connect(socketAddress, timeout);

            // 关闭端口
            socket.close();
            this.print(port, true);
        } catch (IOException e) {
            this.print(port, false);
        }
    }

    /**
     * 输出扫描结果
     *
     * @param port   端口
     * @param opened 是否开发
     */
    private void print(int port, boolean opened) {
        // 如果不打印
        if (!printProcess) {
            return;
        }

        if (opened) {
            System.out.printf("port: %5d opened. %s. thread:%s\n", port, progress, Thread.currentThread().getName());
        } else {
            System.err.printf("port: %5d closed. %s. thread:%s\n", port, progress, Thread.currentThread().getName());
        }
    }
}
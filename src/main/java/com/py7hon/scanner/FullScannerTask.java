package com.py7hon.scanner;

import com.py7hon.scanner.base.BaseScanTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 全扫描
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/30 14:40
 */
public class FullScannerTask extends BaseScanTask {

    public FullScannerTask(PortScanner scanner) {
        super(scanner);
    }

    @Override
    protected void scan(int port) {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
            // 对目标主机的指定端口进行连接，超时后连接失败
            socket.connect(socketAddress, timeout);
            scanner.openedPorts.add(port);
            this.print(port, true);
        } catch (IOException e) {
            this.print(port, false);
        }
    }
}

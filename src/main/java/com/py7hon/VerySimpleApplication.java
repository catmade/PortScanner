package com.py7hon;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * 非常简单的端口扫描器
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/29 16:45
 */
public class VerySimpleApplication {
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            printHelp();
        }

        String target = args[0];
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(target);
            String[] ports = Arrays.copyOfRange(args, 1, args.length);
            for (String p : ports) {
                try {
                    int port = Integer.parseInt(p);
                    scan(inetAddress, port);
                } catch (NumberFormatException e) {
                    System.err.printf("“%s”不符合端口的格式\n", p);
                }
            }
        } catch (UnknownHostException e) {
            System.err.printf("错误的主机名：“%s”\n", e.getMessage());
        }
    }

    public static void scan(InetAddress inetAddress, int port) {
        try {
            Socket socket = new Socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, port);
            socket.connect(inetSocketAddress, 1000);
            socket.close();
            System.out.printf("%s:%5d is opened\n", inetAddress, port);
        } catch (IOException e) {
            System.err.printf("%s:%5d is closed\n", inetAddress, port);
        } catch (IllegalArgumentException e) {
            System.err.printf("错误的端口号：“%d”", port);
        }
    }

    private static void printHelp() {
        System.out.println("usage: name [target] [port1 port2 ...]\n" +
                "example: \n" +
                "\tname www.domain.com 22 80 8080 3306");
    }
}

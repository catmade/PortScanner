package com.py7hon.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;

/**
 * 扫描器的参数
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 10:14
 */
@Data
@NoArgsConstructor
public class ScannerProperties {
    /**
     * 目标。IP 地址或域名
     */
    private InetAddress target;

    /**
     * 连接超时，单位：毫秒
     */
    private int timeOut = 2000;

    /**
     * 线程数
     */
    private int threadNumber = 5;

    /**
     * 是否扫描一定范围内的端口
     */
    private boolean scanRangePort = false;

    /**
     * 开始端口
     */
    private int startPort = 0;

    /**
     * 结束端口
     */
    private int endPort = 65535;

    /**
     * 常用端口
     */
    private int[] ports = new int[]{21, 22, 23, 25, 26, 53, 69, 80, 110, 143,
            443, 465, 69, 161, 162, 135, 995, 1080, 1158, 1433, 1521, 2100, 3128, 3306, 3389,
            7001, 8080, 8081, 9080, 9090, 43958, 135, 445, 1025, 1026, 1027, 1028, 1055, 5357};

    /**
     * 是否输出所有扫描过程
     */
    private boolean printProcess = true;

    /**
     * 扫描方式
     */
    private int type = 1;
}

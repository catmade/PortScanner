package com.py7hon.properties;

import com.sun.istack.internal.NotNull;
import lombok.Builder;
import lombok.Data;

import java.net.InetAddress;

/**
 * 扫描器的参数
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 10:14
 */
@Builder
@Data
public class ScannerProperties {
    /**
     * 目标。IP 地址或域名
     */
    @NotNull
    private InetAddress target;

    /**
     * 连接超时，单位：毫秒
     */
    private int timeOut = 3000;

    /**
     * 线程数
     */
    private int threadNumber = 10;

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
     * 是否输出所有扫描过程
     */
    private boolean printProcess = true;
}

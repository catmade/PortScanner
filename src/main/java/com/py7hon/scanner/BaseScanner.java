package com.py7hon.scanner;

import com.py7hon.properties.ScannerProperties;
import sun.misc.Version;

/**
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 10:18
 */
public abstract class BaseScanner {
    /**
     * 扫描器的版本
     */
    protected static String version;

    /**
     * 扫描器的名称
     */
    protected static String name;

    /**
     * 扫描的配置
     */
    protected ScannerProperties properties;

    /**
     * 当前的端口号
     */
    protected int currentPort;

    /**
     * 总共要扫描的端口号
     */
    protected int totalPortNum;

    /**
     * 已扫描的端口号
     */
    protected int scannedPortNum = 0;

    public int getCurrentPort() {
        return currentPort;
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

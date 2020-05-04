package com.py7hon.scanner;

import com.py7hon.scanner.base.BaseScanTask;

/**
 * 创建一个计算任务线程
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/30 14:49
 */
public class ScanTaskFactory {
    static BaseScanTask createTask(PortScanner scanner) {
        switch (scanner.properties.getType()) {
            default:
                return new FullScannerTask(scanner);
        }
    }
}

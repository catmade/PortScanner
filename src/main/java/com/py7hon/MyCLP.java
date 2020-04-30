package com.py7hon;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * 程序的总配置，单例模式
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 9:51
 */
enum MyCLP {
    /**
     * 实例
     */
    out;

    /**
     * 打印帮助信息
     *
     * @param options 定义的要解析哪些参数
     */
    public void printHelp(Options options) {
        new HelpFormatter().printHelp("scan", options, true);
    }
}

package com.py7hon;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * 定义的解析哪些参数
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/30 9:51
 */

public enum MyCLPOptions {
    /**
     * 实例
     */
    INSTANCE;

    private Options options;

    /**
     * 版本
     */
    private final Option versionOpt = new org.apache.commons.cli.Option("v", "version", false,
            "display the version");

    /**
     * 是否扫描所有端口
     */
    private final Option allOpt = new org.apache.commons.cli.Option("a", "all", false,
            "scan all port. default: scan common port");

    /**
     * 帮助
     */
    private final Option helpOpt = new Option("h", "help", false,
            "display the help");

    /**
     * 目标
     */
    private final Option targetOpt = new Option("t", "target", true,
            "target host. ip address of domain");

    private MyCLPOptions() {
        versionOpt.setRequired(false);
        helpOpt.setRequired(false);
        allOpt.setRequired(false);
    }

    Options getOptions() {
        return options;
    }

    Option getVersionOpt() {
        return versionOpt;
    }

    Option getAllOpt() {
        return allOpt;
    }

    Option getHelpOpt() {
        return helpOpt;
    }

    Option getTARGET() {
        return targetOpt;
    }
}

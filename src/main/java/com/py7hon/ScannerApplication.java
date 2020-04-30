package com.py7hon;

import lombok.SneakyThrows;
import org.apache.commons.cli.*;

import static java.lang.System.exit;

/**
 * 扫描程序入口
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 9:32
 */
public class ScannerApplication {

    /**
     * 自己定义的有哪些参数
     */
    private static Options options;

    /**
     * 解析出来的命令
     */
    private static CommandLine cmd;

    /**
     * 主程序的版本
     */
    private static final String VERSION = "1.0";

    @SneakyThrows
    public static void main(String[] args) {
        options = MyCLPOptions.INSTANCE.getOptions();
        cmd = parseArgs(args);
        executeArgs(cmd);
    }

    /**
     * 执行参数
     *
     * @param cmd 解析出来的命令行参数
     */
    private static void executeArgs(CommandLine cmd) {
        if (cmd.getArgList().size() == 0) {
            MyCLP.out.printHelp(options);
            return;
        }

        if (cmd.hasOption('h')) {
            MyCLP.out.printHelp(options);
            return;
        }

        if (cmd.hasOption('v')) {
            System.out.println("version: PortScanner " + VERSION);
            return;
        }
    }

    /**
     * 解析参数
     *
     * @param args 参数
     * @return 解析出来的命令行参数
     */
    private static CommandLine parseArgs(String[] args) {
        // 解析 args
        DefaultParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("options error!");
            MyCLP.out.printHelp(options);
            exit(-1);
        }
        return cmd;
    }

}

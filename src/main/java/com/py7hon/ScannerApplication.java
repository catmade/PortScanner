package com.py7hon;

import com.py7hon.properties.ScannerProperties;
import com.py7hon.scanner.PortScanner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * 扫描程序入口
 *
 * @author Seven
 * @version 1.0
 * @date 2020/4/24 9:32
 */
public class ScannerApplication {

    /**
     * 获取控制台输入
     */
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PortScanner.printHelloMsg();

        ScannerProperties properties = new ScannerProperties();

        setTarget(properties);
        setScanPort(properties);
        setThreadNumber(properties);
        setTimeOut(properties);
        setPrintStyle(properties);

        new PortScanner(properties).scan();
    }

    /**
     * 设置打印方式
     */
    private static void setPrintStyle(ScannerProperties properties) {
        System.out.printf("是否打印每个扫描结果(y/n)[%s]：", properties.isPrintProcess() ? 'y' : 'n');
        while (true) {
            String s = scanner.nextLine();
            if (isNullOrEmpty(s)) {
                break;
            }
            boolean yes = "y".equals(s) || "Y".equals(s);
            boolean no = "n".equals(s) || "N".equals(s);
            if (yes) {
                return;
            } else if (no) {
                properties.setPrintProcess(false);
                return;
            } else {
                System.err.print("请输入[y/Y/n/N]：");
            }
        }
    }

    private static void setThreadNumber(ScannerProperties properties) {
        System.out.printf("请输入线程数[%d]：", properties.getThreadNumber());
        while (true) {
            String s = scanner.nextLine();
            try {
                if (!isNullOrEmpty(s)) {
                    properties.setThreadNumber(Integer.parseInt(s));
                }
                return;
            } catch (NumberFormatException e) {
                System.err.print("请输入正整数：");
            }
        }
    }

    /**
     * 设置扫描端口
     */
    private static void setScanPort(ScannerProperties properties) {
        System.out.println("1) 0-65535\t2) 常用端口\t3) 指定端口\t4)指定范围");
        System.out.print("请根据上诉选项选择端口扫描范围[2]：");
        int choose = 2;
        while (true) {
            String s = scanner.nextLine();
            try {
                if (!isNullOrEmpty(s)) {
                    choose = Integer.parseInt(s);
                }
                boolean outOfRange = choose > 4 || choose < 1;
                if (outOfRange) {
                    System.err.print("没有指定的操作请重新输入：");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.print("请输入正确的整数：");
            }
        }
        // 扫描全部
        if (choose == 1) {
            properties.setScanRangePort(true);
            return;
        } else if (choose == 2) {
            return;
        } else if (choose == 4) {// 指定范围
            properties.setScanRangePort(true);
            System.out.print("请输入起始端口和结束端口，用空格分开：");
            while (true) {
                String str = scanner.nextLine();
                String[] s = str.split(" ");
                try {
                    int startPort = Integer.parseInt(s[0]);
                    int endPort = Integer.parseInt(s[1]);
                    boolean rangError = startPort > endPort || startPort < 0 || endPort > 65535;
                    if (rangError) {
                        throw new NumberFormatException();
                    }
                    properties.setStartPort(startPort);
                    properties.setEndPort(endPort);
                    return;
                } catch (NumberFormatException e) {
                    System.err.print("格式错误，请重新输入起点和终点：");
                }
            }
        }

        // 扫描指定端口
        System.out.print("请输入要扫描的端口，用空格分开：");
        again:
        while (true) {
            String str = scanner.nextLine();
            String[] s = str.split(" ");
            int[] ints = new int[s.length];

            for (int i = 0; i < s.length; i++) {
                try {
                    ints[i] = Integer.parseInt(s[i]);
                } catch (NumberFormatException e) {
                    System.err.print("请输入正确的正整数格式：");
                    break again;
                }
            }
            properties.setPorts(ints);
            break;
        }
    }

    /**
     * 设置超时时间
     */
    private static void setTimeOut(ScannerProperties properties) {
        System.out.printf("请输入超时时间（毫秒）[%d]：", properties.getTimeOut());
        while (true) {
            String s = scanner.nextLine();
            try {
                if (!isNullOrEmpty(s)) {
                    properties.setTimeOut(Integer.parseInt(s));
                }
                return;
            } catch (NumberFormatException e) {
                System.err.print("请输入正整数：");
            }
        }
    }

    /**
     * 设置扫描目标
     */
    private static void setTarget(ScannerProperties properties) {
        System.out.print("请输入要扫描的主机[localhost]：");
        while (true) {
            String target = scanner.nextLine();
            try {
                if (!isNullOrEmpty(target)) {
                    properties.setTarget(InetAddress.getByName(target));
                } else {
                    properties.setTarget(InetAddress.getByName("localhost"));
                }
                return;
            } catch (UnknownHostException e) {
                System.err.print("主机名错误，请重新输入：");
            }
        }
    }

    /**
     * 判断字符串是否为 null 或全是空白符
     *
     * @param str 字符
     * @return 结果
     */
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

}

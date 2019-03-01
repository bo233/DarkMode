package com.bo233.darkmode.util;


import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.bo233.darkmode.util.AppHelper.getAllPkgNames;

/**
 * 执行root功能
 */
public class AppKiller {

    private static Process process;

    /**
     * 结束进程,执行操作调用即可
     */
    public static void kill(String packageName) {
        initProcess();
        killProcess(packageName);
        close();
    }

    public static void killApps(List<String> packageNames){
        initProcess();
        killProcesses(packageNames);
        close();
    }

    public static void killAllApps(){
        killApps(getAllPkgNames());
    }

    public static void killSelectedApps(){

    }

    /**
     * 初始化进程
     */
    private static void initProcess() {
        if (process == null)
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * 结束进程
     */
    private static void killProcess(String packageName) {
        OutputStream out = process.getOutputStream();
        String cmd = "am force-stop " + packageName + " \n";
        try {
            out.write(cmd.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void killProcesses(List<String> packageNames){
        OutputStream out = process.getOutputStream();
        String cmd;
        for(String pkgName : packageNames) {
            cmd = "am force-stop " + pkgName + " \n";
            try {
                out.write(cmd.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     */
    private static void close() {
        if (process != null)
            try {
                process.getOutputStream().close();
                process = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}

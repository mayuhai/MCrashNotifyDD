package com.hunter.tracelog.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Description:通用log管理器
 * 开发阶段LOGLEVEL = 6
 * 发布阶段LOGLEVEL = -1
 *
 * @author: mayuhai
 * @date: 2016/4/22 18:01
 */
public class LogUtil {

    private static int sLoglevel = -1;
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;

    /**
     * 是否开启日志
     *
     * @param flag 开发阶段为true,上线时一定要为false
     */
    public static void setDevelopMode(boolean flag) {
        if (flag) {
            sLoglevel = 6;
        } else {
            sLoglevel = -1;
        }
    }

    /**
     * 日志
     *
     * @param tag 标记
     * @param msg 信息
     */
    public static void v(String tag, String msg) {
        if (sLoglevel > VERBOSE && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    /**
     * 日志
     *
     * @param tag 标记
     * @param msg 信息
     */
    public static void d(String tag, String msg) {
        if (sLoglevel > DEBUG && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    /**
     * 日志
     *
     * @param tag 标记
     * @param msg 信息
     */
    public static void i(String tag, String msg) {
        if (sLoglevel > INFO && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    /**
     * 日志
     *
     * @param tag 标记
     * @param msg 信息
     */
    public static void w(String tag, String msg) {
        if (sLoglevel > WARN && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    /**
     * 日志
     *
     * @param tag 标记
     * @param msg 信息
     */
    public static void e(String tag, String msg) {
        if (sLoglevel > ERROR && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }
}

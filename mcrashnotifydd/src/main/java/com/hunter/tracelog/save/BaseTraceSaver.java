package com.hunter.tracelog.save;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.hunter.tracelog.TraceLog;
import com.hunter.tracelog.crash.PerformanceReportManager;
import com.hunter.tracelog.encryption.IEncryption;
import com.hunter.tracelog.util.FileUtil;
import com.hunter.tracelog.util.LogUtil;
import com.hunter.tracelog.util.NetUtil;
import com.hunter.tracelog.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 提供通用的保存操作log的日志和设备信息的方法
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public abstract class BaseTraceSaver implements ITraceSave {

    private final static String TAG = "BaseTraceSaver";

    /**
     * 使用线程池对异步的日志写入做管理，提高性能
     */
    public ExecutorService mThreadPool = Executors.newFixedThreadPool(2);

    /**
     * 根据日期创建文件夹,文件夹的名称以日期命名,下面是日期的格式
     */
    public final static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    /**
     * 在每一条log前面增加一个时间戳
     */
    public final static SimpleDateFormat yyyy_MM_dd_HH_mm_ss_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS", Locale.getDefault());

    /**
     * 日志的保存的类型
     */
    public static final String SAVE_FILE_TYPE = ".log";

    /**
     * 日志命名的其中一部分：时间戳
     */
    public final static String LOG_CREATE_TIME = yyyy_MM_dd.format(new Date(System.currentTimeMillis()));

    public static String TimeLogFolder;


    public static String logDir = "logs/";

    /**
     * 操作日志全名拼接
     */
    public final static String LOG_FILE_NAME_MONITOR = FileUtil.INFO + LOG_CREATE_TIME + SAVE_FILE_TYPE;

    public Context context;

    /**
     * 加密方式
     */
    public static IEncryption mEncryption;

    /**
     * 是否解密已加密的数据
     */
    public boolean decode;

    public BaseTraceSaver(Context context) {
        this.context = context;
    }

    /**
     * 用于在每条log前面，增加更多的文本信息，包括时间，线程名字等等
     */
    public static String formatTraceLogMsg(Activity currentActivity, String tag, String tips, String userInfo) {
        String timeStr = yyyy_MM_dd_HH_mm_ss_SS.format(Calendar.getInstance().getTime());
        String currentActivityName = null;
        String netWork = null;
        if (currentActivity != null) {
            currentActivityName = currentActivity.getClass().getName();
            netWork = NetUtil.getNetWorkStr(currentActivity);
        } else {
            currentActivityName = Utils.getRunningActivityName(PerformanceReportManager.getInstance().getContext());
            netWork = NetUtil.getNetWorkStr(PerformanceReportManager.getInstance().getContext());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Activity: ")
                .append(currentActivityName)
                .append("| Network: ")
                .append(netWork)
                .append("| Time: ")
                .append(timeStr)
                .append("| Class: ")
                .append(tag)
                .append("| user: ")
                .append(userInfo)
                .append(" >> ")
                .append(tips);
//        LogUtil.d(TAG, "添加的内容是:\n" + sb.toString());
        return sb.toString();
    }

    /**
     * 写入设备的各种参数信息之前，请确保File文件以及他的父路径是存在的
     *
     * @param file 需要创建的文件
     */
    public File createFile(File file, Context context) {
        String deviceInfo = Utils.deviceInfo(context);

        LogUtil.e(TAG, "创建的设备信息（加密前） = \n" + deviceInfo);
        //加密信息
        deviceInfo = encodeString(deviceInfo);
        LogUtil.e(TAG, "创建的设备信息（加密后） = \n" + deviceInfo);
        try {
            if (!file.exists()) {
                boolean successCreate = false;
                try{
                    successCreate = file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                if (!successCreate) {
                    return null;
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(deviceInfo.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void setEncodeType(IEncryption encodeType) {
        mEncryption = encodeType;
    }

    @Override
    public void setDecode(boolean decode) {
        this.decode = decode;
    }

    public String encodeString(String content) {
        if (mEncryption != null && !decode) {
            try {
                return mEncryption.encrypt(content);
            } catch (Exception e) {
                LogUtil.e(TAG, e.toString());
                e.printStackTrace();
                return content;
            }
        }

        return content;

    }

    public String decodeString(String content) {
        if (mEncryption != null) {
            try {
                return mEncryption.decrypt(content);
            } catch (Exception e) {
                LogUtil.e(TAG, e.toString());
                e.printStackTrace();
                return content;
            }
        }
        return content;
    }

    /**
     * 异步操作，务必加锁
     *
     * @param tag     Log的标签
     * @param content Log的内容
     */
    @Override
    public void writeTraceLog(final String tag, final String content) {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (BaseTraceSaver.class) {
                    TimeLogFolder = TraceLog.getInstance().getROOT() + logDir + yyyy_MM_dd.format(new Date(System.currentTimeMillis())) + File.separator;
                    final File logsDir = new File(TimeLogFolder);
                    final File logFile = new File(logsDir, LOG_FILE_NAME_MONITOR);
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        LogUtil.e(TAG, "SDcard 不可用");
                        return;
                    }
                    if (!logsDir.exists()) {
                        LogUtil.e(TAG, "logsDir.mkdirs() =  +　" + logsDir.mkdirs());
                    }
                    if (!logFile.exists()) {
                        createFile(logFile, context);
                    }
                    //long startTime = System.nanoTime();
                    //long endTime = System.nanoTime();
                    //Logs.d(TAG, "解密耗时为 = ： " + String.valueOf((double) (endTime - startTime) / 1000000) + "ms");
                    //Logs.d(TAG, "读取本地的Log文件，并且解密 = \n" + preContent.toString());
                    //Logs.d(TAG, "即将保存的Log文件内容 = \n" + preContent.toString());
                    writeText(logFile, decodeString(FileUtil.getText(logFile)) + formatTraceLogMsg(null, tag, content, null) + "\n");
                }

            }
        });
    }

    public void writeText(final File logFile, final String content) {
        FileOutputStream outputStream = null;
        try {
            String encoderesult = encodeString(content);
//            LogUtil.d(TAG, "最终写到文本的Log：\n" + content);
            outputStream = new FileOutputStream(logFile);
            outputStream.write(encoderesult.getBytes("UTF-8"));
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //每次写入的最后检测sd卡空间是否够用
            TraceLog.getInstance().checkCacheSizeAndDelOldestFile(new File(TraceLog.getInstance().getROOT()));
        }
    }
}

package com.hunter.tracelog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hunter.tracelog.crash.CrashHandler;
import com.hunter.tracelog.encryption.IEncryption;
import com.hunter.tracelog.save.ITraceSave;
import com.hunter.tracelog.save.imp.TraceLogWriter;
import com.hunter.tracelog.upload.ITraceLogUpload;
import com.hunter.tracelog.upload.UploadService;
import com.hunter.tracelog.util.FileUtil;
import com.hunter.tracelog.util.LogUtil;
import com.hunter.tracelog.util.NetUtil;

import java.io.File;

/**
 * 日志管理框架
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public class TraceLog {

    private static String TAG = "TraceLog";
    private static TraceLog traceLog;
    public static final String LOG_DRI = "tracelog";
    /**
     * 设置上传的方式
     */
    public ITraceLogUpload logUpload;
    /**
     * 设置缓存文件夹的大小,默认是50MB
     */
    private long cacheSize = 50 * 1024 * 1024;

    /**
     * 设置日志保存的路径
     */
    private String rootPath;

    /**
     * 设置加密方式
     */
    private IEncryption encryption;

    /**
     * 是否解密已经加密的文件
     */
    private boolean decode;

    /**
     * 设置日志的保存方式
     */
    private ITraceSave traceSave;

    /**
     * 设置在哪种网络状态下上传，true为只在wifi模式下上传，false是wifi和移动网络都上传
     */
    private boolean wifiOnly = true;

    /**
     * 设置上传日志信息为所有
     */
    public static final int LOG_LEVEL_INFO = 1;

    /**
     * 设置上传日志信息为crash
     */
    public static final int LOG_LEVEL_ERROR = 0;

    /**
     * 设置默认为只记录crash日志
     */
    private int traceLogLevel = LOG_LEVEL_ERROR;

    /**
     * 设置info和crash日志是否合并
     * 默认合并
     */
    private boolean combineInfoCrash = true;

    /**
     * 设置上传日志内容为
     */
    private int traceLogContent;

    private TraceLog() {
    }

    public static TraceLog getInstance() {
        if (traceLog == null) {
            synchronized (TraceLog.class) {
                if (traceLog == null) {
                    traceLog = new TraceLog();
                }
            }
        }
        return traceLog;
    }

    public TraceLog setCacheSize(long cacheSize) {
        this.cacheSize = cacheSize;
        return this;
    }

    public TraceLog setEncryption(IEncryption encryption) {
        this.encryption = encryption;
        return this;
    }

    public TraceLog setUploadType(ITraceLogUpload logUpload) {
        this.logUpload = logUpload;
        return this;
    }

    public TraceLog setWifiOnly(boolean wifiOnly) {
        this.wifiOnly = wifiOnly;
        return this;
    }

    public TraceLog setDecode(boolean decode) {
        this.decode = decode;
        return this;
    }

    public TraceLog setLogLevel(int logLevel){
        if (logLevel == LOG_LEVEL_INFO) {
            traceLogLevel = LOG_LEVEL_INFO;
        }else {
            traceLogLevel = LOG_LEVEL_ERROR;
        }
        return this;
    }

    public int getLogLevel(){
        return traceLogLevel;
    }

    public TraceLog setCombineInfoCrash(boolean combineInfoCrash) {
        this.combineInfoCrash = combineInfoCrash;
        return this;
    }

    public boolean isCombineInfoCrash() {
        return combineInfoCrash;
    }

    public TraceLog setLogDir(Context context, String logDir) {
        if (TextUtils.isEmpty(logDir)) {
            rootPath = context.getFilesDir() + File.separator + TraceLog.LOG_DRI + File.separator;
        } else {
            rootPath = logDir;
        }
        return this;
    }

    public TraceLog setLogSaver(ITraceSave logSaver) {
        this.traceSave = logSaver;
        return this;
    }

    public TraceLog setLogContent(int logContent){
        this.traceLogContent = logContent;
        return this;
    }

    public int getLogContent(){
        return traceLogContent;
    }

    public TraceLog setLogDebugModel(boolean isDebug){
//        Logs.isDebug = isDebug;
        return this;
    }


    public String getROOT() {
        return rootPath;
    }

    public void init(Context context) {
        if (TextUtils.isEmpty(rootPath)) {
            rootPath = context.getFilesDir() + File.separator + TraceLog.LOG_DRI + File.separator;
        }
        if (encryption != null) {
            traceSave.setEncodeType(encryption);
        }
        traceSave.setDecode(decode);
        CrashHandler.getInstance().init(traceSave, context);
        TraceLogWriter.getInstance().init(traceSave);
        LogUtil.d(TAG, "init");
//        checkVersion(context);
    }

//    /**
//     * 检查APP versionCode 如果是新版本就清空log日志
//     * @param context
//     */
//    private void checkVersion(Context context) {
//        int oldVersionCode = SavePreferences.getInt(SPreferencesConst.APP_VERSIONCODE, -1);
//        ApplicationInfo ai = context.getApplicationInfo();
//        PackageManager pm = context.getPackageManager();
//        try {
//            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
//            int currentVersionCode = pi.versionCode;
//            LogUtil.d(TAG, "oldVersionCode = " + oldVersionCode + " currentVersionCode = " + currentVersionCode);
//            if (oldVersionCode > 0) {//存储过versionCode
//                if (currentVersionCode > oldVersionCode) {//新版本 清除tracelog
//                    File logFolder = new File(rootPath);
//                    if (logFolder.exists()) {
//                        FileUtil.deleteDir(logFolder);
//                        LogUtil.d(TAG, "新版本 删除旧版本的日志");
//                    }
//                }
//            } else {
//                SavePreferences.setData(SPreferencesConst.APP_VERSIONCODE, currentVersionCode);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public ITraceLogUpload getUpload() {
        return logUpload;
    }

    public long getCacheSize() {
        return cacheSize;
    }

    /**
     * 检查文件夹是否超出缓存大小，超出则会删除该目录下的所有文件
     *
     * @param dir 需要检查大小的文件夹
     * @return 返回是否超过大小，true为是，false为否
     */

    public synchronized boolean checkCacheSizeAndDelOldestFile(File dir) {
        long dirSize = FileUtil.folderSize(dir);
        return dirSize >= TraceLog.getInstance().getCacheSize() && FileUtil.deleteOldestFile(new File(TraceLog.getInstance().getROOT()));
    }

    /**
     * 调用此方法，上传日志信息
     *
     * @param applicationContext 全局的application context，避免内存泄露
     */
    public void upload(Context applicationContext, String collectId) {
        //如果没有设置上传，则不执行
        if (logUpload == null) {
            return;
        }
        //如果网络可用，而且是移动网络，但是用户设置了只在wifi下上传，返回
        if (NetUtil.isConnected(applicationContext) && !NetUtil.isWifi(applicationContext) && wifiOnly) {
            return;
        }
        Intent intent = new Intent(applicationContext, UploadService.class);
        intent.putExtra("collectId", collectId);
        applicationContext.startService(intent);
    }
}

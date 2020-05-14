package com.hunter.mcrashnotifyddtest;

import android.app.Application;

import com.hunter.tracelog.TraceLog;
import com.hunter.tracelog.crash.PerformanceReportManager;
import com.hunter.tracelog.encryption.imp.Base64Encode;
import com.hunter.tracelog.save.imp.CrashWriter;
import com.hunter.tracelog.upload.http.RetrofitUploadTrace;

import java.io.File;

/**
 * Description:AppApplication
 *
 * @author mayuhai
 * date: 2020/5/14 11:19 AM
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCrash();
        initReport();
    }

    private void initCrash() {
        TraceLog.getInstance()
//                .setCacheSize(10 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(this,
                getFilesDir() + File.separator + TraceLog.LOG_DRI + File.separator)
                .setWifiOnly(false)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setWifiOnly(false)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(this))//支持自定义保存崩溃信息的样式
                .setLogDebugModel(true) //设置是否显示日志信息
                .setLogContent(TraceLog.LOG_LEVEL_INFO)
//                .setEncryption(new Base64Encode()) //支持日志到AES加密、DES加密和Base64编码，默认不开启
//                .setDecode(true)//解密已加密的数据
                .setCombineInfoCrash(false)//是否合并info和crash
                .setUploadType(new RetrofitUploadTrace(this))
                .init(this);
    }

    private void initReport() {
        PerformanceReportManager.getInstance()
                .setContext(this)
                .setReportOfflineUrl("https://oapi.dingtalk.com/robot/send?access_token=fe747815e640beba0f24408e2c68d1f503c14258c4741146b4e0bdca54fbad78");

    }
}

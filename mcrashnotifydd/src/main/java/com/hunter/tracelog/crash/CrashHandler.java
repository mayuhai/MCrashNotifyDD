package com.hunter.tracelog.crash;

import android.content.Context;

import com.hunter.tracelog.save.BaseTraceSaver;
import com.hunter.tracelog.save.ITraceSave;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


/**
 * 自定义的崩溃捕获Handler
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static CrashHandler INSTANCE = new CrashHandler();

    private Context context;
    /**
     * 设置日志的保存方式
     */
    private ITraceSave traceSave;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化,，设置此CrashHandler来响应崩溃事件
     *
     * @param logSaver 保存的方式
     * @param context
     */
    public void init(ITraceSave logSaver, Context context) {
        traceSave = logSaver;
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        StringBuilder sb = new StringBuilder();
        sb.append("↓↓↓↓exception↓↓↓↓\n")
                .append(" >> ")
                .append(writer.toString());
        traceSave.writeCrash(thread, ex, TAG, sb.toString());

        PerformanceReportManager.getInstance().sendCrashTDD(BaseTraceSaver.formatTraceLogMsg(null, "crash", sb.toString(), null));
        // 如果处理了，让主程序继续运行3秒再退出，保证异步的写操作能及时完成
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}



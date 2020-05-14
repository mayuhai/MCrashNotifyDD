package com.hunter.tracelog.save.imp;

import com.hunter.tracelog.TraceLog;
import com.hunter.tracelog.save.ITraceSave;
import com.hunter.tracelog.util.LogUtil;

/**
 * 用于写入Log到本地
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public class TraceLogWriter {
    private static TraceLogWriter traceLogWriter;
    private static ITraceSave save;

    private TraceLogWriter() {
    }

    public static TraceLogWriter getInstance() {
        if (traceLogWriter == null) {
            synchronized (TraceLog.class) {
                if (traceLogWriter == null) {
                    traceLogWriter = new TraceLogWriter();
                }
            }
        }
        return traceLogWriter;
    }

    public TraceLogWriter init(ITraceSave save) {
        TraceLogWriter.save = save;
        return this;
    }

    public static void writeLog(String tag, String content) {
        LogUtil.d(tag,  content);
        if (save != null) {
            save.writeTraceLog(tag, content);
        }

    }
}
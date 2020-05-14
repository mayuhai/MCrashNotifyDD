package com.hunter.tracelog.save;

import com.hunter.tracelog.encryption.IEncryption;

/**
 * 保存日志与崩溃信息的接口
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public interface ITraceSave {

    void writeTraceLog(String tag, String content);

    void writeCrash(Thread thread, Throwable ex, String tag, String content);

    void setEncodeType(IEncryption encodeType);

    void setDecode(boolean decode);

}

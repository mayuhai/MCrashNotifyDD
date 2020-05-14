package com.hunter.tracelog.crash;

import android.content.Context;
import com.hunter.net.TCHttpURLClient;
import com.hunter.tracelog.TraceLog;
import com.hunter.tracelog.util.LogUtil;
import com.hunter.tracelog.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 性能标准通知
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public class PerformanceReportManager {
    private static PerformanceReportManager INSTANCE = new PerformanceReportManager();
    private Context context;
    private String reportOnlineUrl = "https://oapi.dingtalk.com/robot/send?access_token=fe747815e640beba0f24408e2c68d1f503c14258c4741146b4e0bdca54fbad78";
    private String reportOfflineUrl = "https://oapi.dingtalk.com/robot/send?access_token=fe747815e640beba0f24408e2c68d1f503c14258c4741146b4e0bdca54fbad78";

    /**
     * 保证只有一个CrashHandler实例
     */
    private PerformanceReportManager() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static PerformanceReportManager getInstance() {
        if (INSTANCE == null) {
            synchronized (TraceLog.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PerformanceReportManager();
                }
            }
        }
        return INSTANCE;
    }

    public PerformanceReportManager setContext(Context context) {
        this.context = context;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public PerformanceReportManager setReportOnlineUrl(String reportOnlineUrl) {
        this.reportOnlineUrl = reportOnlineUrl;
        return this;
    }

    public PerformanceReportManager setReportOfflineUrl(String reportOfflineUrl) {
        this.reportOfflineUrl = reportOfflineUrl;
        return this;
    }

    /**
     * 发送crash 信息到钉钉群
     * @param exceptionStr
     */
    public void sendCrashTDD(String exceptionStr) {
        String body = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msgtype", "text");

            JSONObject jsonObjectContent = new JSONObject();
            jsonObjectContent.put("content", "Tech:" + Utils.deviceInfo(context) + exceptionStr);
            jsonObject.put("text", jsonObjectContent.toString());

            body = jsonObject.toString();

            TCHttpURLClient.getInstance().postJson(reportOfflineUrl, body, new TCHttpURLClient.OnHttpCallback() {
                @Override
                public void onSuccess(String result) {
                    LogUtil.d("sendCrashTDD", "succes");
                }

                @Override
                public void onError() {
                    LogUtil.d("sendCrashTDD", "onError");
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}



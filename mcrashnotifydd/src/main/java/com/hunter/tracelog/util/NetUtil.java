package com.hunter.tracelog.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 与网络相关的工具类
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public class NetUtil {
    private NetUtil()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 获取网络类型(2g、3g、4g、wifi)
     * @param context
     * @return String
     */
    public static String getNetWorkStr(Context context) {
        String netWorkStr = "";
        int getNetworkType = NetUtil.getNetWorkType(context);// 获取网络类型
        if (getNetworkType == -1 || getNetworkType == 0) {
            netWorkStr = "unknown";
        } else if (getNetworkType == 1) {
            netWorkStr = "2G";
        } else if (getNetworkType == 2) {
            netWorkStr = "3G";
        } else if (getNetworkType == 3) {
            netWorkStr = "4G";
        } else if (getNetworkType == 4) {
            netWorkStr = "wifi";
        }

        return netWorkStr;
    }

    /**
     * 获取网络类型(2g、3g、4g、wifi)
     *
     * @param context
     * @return
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (!isNetworkAvailable(context)) {
            info = null;
        } else {
            if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                return DataType.NETWORK_CLASS_WIFI;
            } else if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case DataType.NETWORK_TYPE_GPRS:
                    case DataType.NETWORK_TYPE_EDGE:
                    case DataType.NETWORK_TYPE_CDMA:
                    case DataType.NETWORK_TYPE_1XRTT:
                    case DataType.NETWORK_TYPE_IDEN:
                        return DataType.NETWORK_CLASS_2_G;

                    case DataType.NETWORK_TYPE_UMTS:
                    case DataType.NETWORK_TYPE_EVDO_0:
                    case DataType.NETWORK_TYPE_EVDO_A:
                    case DataType.NETWORK_TYPE_HSDPA:
                    case DataType.NETWORK_TYPE_HSUPA:
                    case DataType.NETWORK_TYPE_HSPA:
                    case DataType.NETWORK_TYPE_EVDO_B:
                    case DataType.NETWORK_TYPE_EHRPD:
                    case DataType.NETWORK_TYPE_HSPAP:
                    case DataType.NETWORK_TYPE_TDS_HSPAP:
                    case DataType.NETWORK_TYPE_TDSCDMA:
                        return DataType.NETWORK_CLASS_3_G;

                    case DataType.NETWORK_TYPE_LTE:
                        return DataType.NETWORK_CLASS_4_G;

                    default:
                        return DataType.NETWORK_CLASS_UNKNOWN;
                }
            }
        }
        return DataType.NETWORK_CLASS_NOT;
    }

    /**
     * 判断网络是否链接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return !(networkinfo == null || !networkinfo.isAvailable());
    }
}

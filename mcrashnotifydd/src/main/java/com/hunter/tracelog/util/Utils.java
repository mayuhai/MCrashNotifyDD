package com.hunter.tracelog.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import static android.text.TextUtils.isEmpty;

/**
 * Description:
 * author: mayuhai
 * created on: 2019-06-21 14:03
 */
public class Utils {
    /**
     * SDCard是否有剩余空间
     */
    public static boolean isAvailableSpace() {
        if (!isSDCardAvailable()) {
            return false;
        }
        boolean flag = false;
        int bytelength = 1024;
        File pathFile = android.os.Environment.getExternalStorageDirectory();
        android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
        // 获取SDCard上每个block的SIZE
        long m_nBlocSize = statfs.getBlockSize();
        // 获取可供程序使用的Block的数量
        long m_nAvailaBlock = statfs.getAvailableBlocks();
        long total = m_nAvailaBlock * m_nBlocSize / bytelength;
        if (total > 5120) {
            flag = true;
        }
        return flag;
    }

    /**
     * sd卡是否可用
     */
    public static boolean isSDCardAvailable() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 当前activity名字
     * @param context
     * @return
     */
    public static String getRunningActivityName(Context context){
        ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    /**
     * 获取设备信息
     * @param context
     * @return
     */
    public static String deviceInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = context.getApplicationInfo();
        sb.append("App Name : ").append(pm.getApplicationLabel(ai)).append('\n');
        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            sb.append("Version Code: ").append(pi.versionCode).append('\n');
            sb.append("Version Name: ").append(pi.versionName).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        sb.append("CHANNEL: ").append('\n');
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        sb.append("BRAND: ").append(Build.BRAND).append('\n');
        sb.append("DEVICE: ").append(Build.DEVICE).append('\n');
        sb.append("VERSION: ").append(Build.VERSION.RELEASE).append('\n');
        sb.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
        return sb.toString();
    }
}

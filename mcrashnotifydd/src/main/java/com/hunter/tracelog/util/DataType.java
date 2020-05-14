package com.hunter.tracelog.util;

/**
 * @author
 */
public class DataType {
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B */
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0 */
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A */
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT */
	public static final int NETWORK_TYPE_1XRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B */
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;
	/** Current network is TDS_HSPAP */
	public static final int NETWORK_TYPE_TDSCDMA = 17;
	/** Current network is TDS_HSPAP */
	public static final int NETWORK_TYPE_TDS_HSPAP = 18;

    /** 网络类型：无网络. {@hide} */
    public static final int NETWORK_CLASS_NOT = -1;
    /** 网络类型：未知. {@hide} */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /** 网络类型：2G. {@hide} */
    public static final int NETWORK_CLASS_2_G = 1;
    /** 网络类型：3G. {@hide} */
    public static final int NETWORK_CLASS_3_G = 2;
    /** 网络类型：4G. {@hide} */
    public static final int NETWORK_CLASS_4_G = 3;
    /** 网络类型：WIFI. {@hide} */
    public static final int NETWORK_CLASS_WIFI = 4;

    /** 网络运营商：未知.*/
    public static final int NETWORK_IMSI_UNKNOWN = 0;
    /** 网络运营商：中国移动 */
    public static final int NETWORK_IMSI_MOBILE= 1;
    /** 网络运营商：中国联通 */
    public static final int NETWORK_IMSI_UNICOM = 2;
    /** 网络运营商：中国电信 */
    public static final int NETWORK_IMSI_TELECOM = 3;
}

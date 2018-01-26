package cn.v1.kanglewanjia.network;

/**
 * Created by lawrence on 14/11/25.
 */
public class NetContract {

    public static final String NEW_VERSION = "45";// cici 2017-06-09
    //康乐万家
    public static final String KANGLE_HOST = "http://111.198.169.114:8082";
    //融云
    public static final String RONGYUN_HOST = "https://api.cn.ronghub.com/user";
    //文件上传
    public static final String UPLOAD_HOST = "http://tup.yihu365.com/";
    //医护到家
    public static String YIHU_HOST = "http://app.yihu365.com";


    public static final String ENCODING = "utf-8";
    public static final String SOURCE = "2";//1:用户版  2:医生版
    //    public static final String SOURCE = "android_health_101";
    public static final String IDENTITY_ANDROID = "201";
    public static final String MAPP_KEY = "HCO2O";

    //加密key
    public static final String ALLPARA_SECRET_KEY = "afaaGn_A2bytbd10";
    public static final String md5Key = "signkey@aiclient";// MD5 key
    public static final String aesKey = "ai@cellphone.com";
    public static final String TokenAesKey = "afaaGn_A2bytbd10";

}

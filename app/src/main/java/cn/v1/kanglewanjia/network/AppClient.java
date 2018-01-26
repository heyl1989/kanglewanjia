package cn.v1.kanglewanjia.network;

import android.os.Build;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import cn.v1.kanglewanjia.App;
import cn.v1.kanglewanjia.BuildConfig;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.ActiveFamlilyFileData;
import cn.v1.kanglewanjia.model.AddressListData;
import cn.v1.kanglewanjia.model.AuthCodeData;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.DeletepatientInfoData;
import cn.v1.kanglewanjia.model.DepartmentData;
import cn.v1.kanglewanjia.model.FamilyDoctorDetailData;
import cn.v1.kanglewanjia.model.HospitalListData;
import cn.v1.kanglewanjia.model.InquiryLineData;
import cn.v1.kanglewanjia.model.InquiryOrderDetailData;
import cn.v1.kanglewanjia.model.InquiryOrderListData;
import cn.v1.kanglewanjia.model.LoginData;
import cn.v1.kanglewanjia.model.PatientCaseData;
import cn.v1.kanglewanjia.model.PatientDrugData;
import cn.v1.kanglewanjia.model.PatientInfoData;
import cn.v1.kanglewanjia.model.PatientListData;
import cn.v1.kanglewanjia.model.PayInfoData;
import cn.v1.kanglewanjia.model.SaveOrderData;
import cn.v1.kanglewanjia.model.SavePatientInfoData;
import cn.v1.kanglewanjia.model.UploadPicData;
import cn.v1.kanglewanjia.model.YiHuNurseData;
import cn.v1.kanglewanjia.network.exception.ConversionThrowable;
import cn.v1.kanglewanjia.network.exception.NetErrorThrowable;
import cn.v1.kanglewanjia.network.exception.ServerErrorThrowable;
import cn.v1.kanglewanjia.util.DefaultEncryption;
import cn.v1.kanglewanjia.util.ExclusionField;
import cn.v1.kanglewanjia.util.MD5Util;
import cn.v1.kanglewanjia.util.SHA1Utils;
import cn.v1.kanglewanjia.util.Util;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;
import rx.Observable;


/**
 * Created by lawrence on 15/6/11.
 * <p>
 * Updated by qy on 18/01/01
 */
public class AppClient {

    private String TAG = AppClient.class.getSimpleName();
    private KangLeService mKangLeService;
    private YiHuService mYiHuService;
    private OkHttpClient mOkHttpClient;
    private RongGetTokenService mRongGetTokenService;
    private UploadService mUploadService;
    private OkClient client = new OkClient(getOkHttpClient());
    private int timeSpan = 600000;
    private RestAdapter mKangLeRestAdapter = new RestAdapter.Builder()
            .setClient(client)
            .setEndpoint(NetContract.KANGLE_HOST)
            .setLogLevel(getLogLevel())
            .setConverter(new KangLeConverter())
            .setErrorHandler(new CustomErrorHandler())
            .build();
    private RestAdapter mRongGetTokenRestAdapter = new RestAdapter.Builder()
            .setClient(client)
            .setEndpoint(NetContract.RONGYUN_HOST)
            .setLogLevel(getLogLevel())
            .setConverter(new KangLeConverter())
            .setErrorHandler(new CustomErrorHandler())
            .build();

    private RestAdapter mUploadRestAdapter = new RestAdapter.Builder()
            .setClient(client)
            .setEndpoint(NetContract.UPLOAD_HOST)
            .setLogLevel(getLogLevel())
            .setErrorHandler(new CustomErrorHandler())
            .build();

    private RestAdapter mYiHuRestAdapter = new RestAdapter.Builder()
            .setClient(client)
            .setEndpoint(NetContract.YIHU_HOST)
            .setLogLevel(getLogLevel())
            .setConverter(new KangLeConverter())
            .setErrorHandler(new CustomErrorHandler())
            .build();

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient.setRetryOnConnectionFailure(true);
            mOkHttpClient.setConnectTimeout(30 * 1000, TimeUnit.SECONDS);
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_NONE);
            mOkHttpClient.setCookieHandler(cookieManager);
        }
        return mOkHttpClient;
    }

    private class CustomErrorHandler implements ErrorHandler {

        @Override
        public Throwable handleError(RetrofitError cause) {
            if (cause.getKind().equals(RetrofitError.Kind.NETWORK)) {
                return new NetErrorThrowable(cause.getMessage());
            } else if (cause.getKind().equals(RetrofitError.Kind.HTTP)) {
                return new ServerErrorThrowable(cause.getMessage());
            } else if (cause.getKind().equals(RetrofitError.Kind.CONVERSION)) {
                return new ConversionThrowable(cause.getMessage());
            }
            return cause;
        }
    }

    private RestAdapter.LogLevel getLogLevel() {
        return BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE;
    }

    private KangLeService getKangLeService() {
        if (mKangLeService == null)
            mKangLeService = mKangLeRestAdapter.create(KangLeService.class);
        return mKangLeService;
    }

    public YiHuService getYiHuService() {
        if (mYiHuService == null)
            mYiHuService = mYiHuRestAdapter.create(YiHuService.class);
        return mYiHuService;
    }

    public RongGetTokenService getmRongGetTokenService() {
        if (mRongGetTokenService == null)
            mRongGetTokenService = mRongGetTokenRestAdapter.create(RongGetTokenService.class);
        return mRongGetTokenService;
    }

    public UploadService getUploadService() {
        if (mUploadService == null)
            mUploadService = mUploadRestAdapter.create(UploadService.class);
        return mUploadService;
    }

    /**
     * 康乐万家联网公共方法
     *
     * @param parameter 传递的参数（Map类型）
     * @param function  方法名
     * @param mType     数据类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> getKangLeObserable(Hashtable<String, Object> parameter, String function, Type mType) {
        return Observable.create(new BaseKangLeObservable<T>(getKangLeData(parameter, function), mType));
    }

    /**
     * 医护公共方法
     *
     * @param parameter
     * @param function
     * @param mType
     * @param <T>
     * @return
     */
    public <T> Observable<T> getYiHuObserable(Hashtable<String, Object> parameter, String function, Type mType) {
        if (parameter == null)
            parameter = new Hashtable<>();
        setCommonParam(parameter);
        if (!parameter.containsKey("source")) {
            parameter.put("source", "android_health_102");
        }
        if (!parameter.containsKey("version")) {
            parameter.put("version", Util.getVersionName());
        }
        if (!parameter.containsKey("token")) {
            parameter.put("token", "");
        }
        parameter.put("login_userId_base", "");
        parameter.put("_cityCode", "131");
        parameter.put("_lan", "40.01507");
        parameter.put("_lon", "116.492932");
        parameter.put("newversion", NetContract.NEW_VERSION);
        parameter.put("function", function);
        Date now = new Date();
        int timeStamp = (int) (now.getTime() / timeSpan) - 0;
        String stampStr = String.valueOf(timeStamp);
        parameter.put("timeStamp", stampStr);
        String accessSecret = getAccessSecretData(parameter);
        String md5Data = DefaultEncryption.parseByte2HexStr(MD5Util.encode(accessSecret)).toLowerCase();
        String sign = DefaultEncryption.parseByte2HexStr(MD5Util.encode(md5Data + stampStr)).toLowerCase();
        String sessionStr = "";
        String sessionId = (String) SPUtil.get(App.getInstance(), Common.SESSION_ID, "");
        if (sessionId != null && sessionId.length() > 0) {
            sessionStr = String.format(";jsessionid=%s", sessionId);
        }
        sessionStr += "?debugStr=" + now.toLocaleString() + "-" + 0 + "-" + timeSpan;
        return Observable.create(new BaseKangLeObservable<T>(
                getYiHuService().getYiHuResponse(sessionStr, accessSecret, sign),
                mType));
    }

    /**
     * kangle接口公共方法
     *
     * @param hashtable 参数集合
     * @param function  方法名
     */
    public Observable<TypedString> getKangLeData(Hashtable<String, Object> hashtable, String function) {
        if (hashtable == null)
            hashtable = new Hashtable<>();
        setCommonParam(hashtable);
        hashtable.put("function", function);
        String accessSecret = getAccessSecretData(hashtable);
        String md5Data = DefaultEncryption.parseByte2HexStr(MD5Util.encode(accessSecret)).toLowerCase();
        String sign = DefaultEncryption.parseByte2HexStr(MD5Util.encode(md5Data)).toLowerCase();
        String sessionStr = "";
        String sessionId = (String) SPUtil.get(App.getInstance(), Common.SESSION_ID, "");
        if (sessionId != null && sessionId.length() > 0) {
            sessionStr = String.format(";jsessionid=%s", sessionId);
        }
        return getKangLeService().getKangLeResponse(sessionStr, accessSecret, sign);
    }


    /**
     * 设置kangle接口默认参数
     * 所有的请求接口中必传：
     * ct:0012-安卓医生客户端；0002-IOS客户端；0003-H5
     * sid:渠道号
     * token：登录令牌，登录成功返回，无时传空字符串
     * ver：版本号 1.0.0
     * mac:地址信息参数,android默认取IMSI取不到换IMEI；IOS 传UID    加前缀IMSI/IMEI/UID+”_”
     * lat:经度
     * lon:维度
     * cc:用户登录所在城市编码
     * ip:
     * cn:城市名称
     * pn:省份名称
     *
     * @param param
     */
    public void setCommonParam(Hashtable<String, Object> param) {
        if (!param.containsKey("ct")) {
            param.put("ct", "0011");
        }
        if (!param.containsKey("token")) {
            param.put("token", (String) SPUtil.get(App.getInstance(), Common.USER_TOKEN, ""));
        }
//        if (!param.containsKey("source")) {
//            param.put("source", NetContract.SOURCE);
//        }
        if (!param.containsKey("mac")) {
            param.put("mac", Util.getMacCode());
        }
        if (!param.containsKey("ver")) {
            param.put("ver", Util.getVersionName());
        }

        param.put("cc", "131");
        param.put("cn", "北京");
        param.put("pn", "北京");
        param.put("lat", "40.01507");
        param.put("lon", "116.492932");
        param.put("sid", "80001100000");
        param.put("ip", Util.getLocalHostIp());
        param.put("newver", NetContract.NEW_VERSION);
    }

    /**
     * 全部加密
     *
     * @param parameters
     * @return
     */
    public String getAccessSecretData(Hashtable<String, Object> parameters) {
        String encodenewvalue = "";
        if (parameters != null) {
            StringBuilder newvalue = new StringBuilder();
            newvalue.append(new GsonBuilder().setExclusionStrategies(new ExclusionField()).create().toJson(parameters));
            try {
                Log.d(TAG, "NEWVALUE " + newvalue);
                encodenewvalue = EncryptPara(newvalue.toString(), NetContract.ALLPARA_SECRET_KEY);
                Log.d(TAG, "encryptnewvalue " + encodenewvalue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //String ret = percentEncode(encodenewvalue);
        return encodenewvalue;
    }

    /**
     * 加密参数
     *
     * @param param
     * @param secretKey
     * @return
     */
    public static String EncryptPara(String param, String secretKey) {
        String Encryptvalue = null;
        try {
            Encryptvalue = DefaultEncryption.Encrypt(param.getBytes("utf-8"), secretKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Encryptvalue;
    }

    /*========================融云相关接口======================================*/

    public Observable<TypedString> getRongToken(String userId, String name, String portraitUri) {
//        String key = "n19jmcy59zoy9";
//        String secrt = "jznvw2cWJw7xf9";
        String key = "bmdehs6pd4l6s";
        String secrt = "Le0JtAEachAWxh";
        String ran = (int) (Math.random() * 10000) + "";
        String Timestamp = System.currentTimeMillis() + "";
        String sign = SHA1Utils.hex_sha1(secrt + ran + Timestamp);
        return getmRongGetTokenService().getRongToken(key, ran, Timestamp, sign, userId, name, portraitUri);
    }


    //=====================================语音视频接口相关start==========================================//

    /**
     * 4.1.4 语音、视频通话记录操作接口接口
     *
     * @param optType      1保存，2更新
     * @param orderId
     * @param calledUserId
     * @param type         0语音，1视频
     * @param recordId
     * @param status       1呼叫中，2被叫接通（正在通话中）,3呼叫失败，4结束通话，5异常中断，6系统中断
     * @return
     */
//    public Observable<OptPsyCallRecordData> getOptPsyCallRecord(String optType, String orderId, String calledUserId, String callingUserId, String type, String recordId,
//                                                                String status) {
//        Hashtable<String, Object> params = new Hashtable<>();
//        params.put("optType", optType);
//        params.put("orderId", orderId);
//        params.put("calledUserId", calledUserId);
//        if (optType.equals("1")) {
//            params.put("callingUserId", callingUserId);
//            params.put("type", type);
//        } else if (optType.equals("2")) {
//            params.put("recordId", recordId);
//            params.put("status", status);
//        }
//        return getCaiboObserable(params, "optPsyCallRecord", OptPsyCallRecordData.class);
//    }


    /**
     * 上传图片 待用
     *
     * @param uploadFile
     * @param service_name
     * @param service_type
     * @return
     */
    public Observable<UploadPicData> uploadImage(File uploadFile, String service_name, String service_type) {
        Hashtable<String, Object> hashtable = new Hashtable<>();
        TypedFile file = new TypedFile("multipart/form-data", uploadFile);
        String stampStr = String.valueOf(new Date().getTime() / timeSpan);
        hashtable.put("timeStamp", stampStr);
        hashtable.put("service_name", service_name);
        hashtable.put("service_type", service_type);
        hashtable.put("get_access", "011");//001 医护 002 彩票 003 邻讯 006 蜻蜓心理 007 租琴 011 互联网医院
        hashtable.put("phone_model", Build.MODEL);
        hashtable.put("storagetime", "0");//存储时间（0：永久，1：1年，2：六个月;3三个月;4:一个月）
        hashtable.put("souce_id", "");//渠道号
        hashtable.put("source", NetContract.SOURCE);
        hashtable.put("remark", "");
        String accessSecret = getAccessSecretData(hashtable);
        String md5Data = DefaultEncryption.parseByte2HexStr(MD5Util.encode(accessSecret)).toLowerCase();
        String sign = DefaultEncryption.parseByte2HexStr(MD5Util.encode(md5Data + stampStr)).toLowerCase();
        return getUploadService().uploadPic(file, accessSecret, sign, true);
    }


/************************************************************康乐万家接口****************************************************************************/
    /**
     * 4.1.1.	获取手机验证码(对接人:盖伟伟)
     *
     * @param mobile
     * @return
     */
    public Observable<AuthCodeData> getAuthCode(String mobile) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("mobile", mobile);
        return getKangLeObserable(parameter, "getAuthCode", AuthCodeData.class);
    }

    /**
     * 4.1.2.	用户登录接口(对接人:盖伟伟)
     *
     * @param mobile   手机号
     * @param codeId   验证码唯一标识
     * @param authCode 验证码
     * @return
     */
    public Observable<LoginData> login(String mobile, String codeId, String authCode) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("mobile", mobile);
        parameter.put("codeId", codeId);
        parameter.put("authCode", authCode);
        parameter.put("userType", "0");
        return getKangLeObserable(parameter, "login", LoginData.class);
    }

    /**
     * 4.1.3.	退出登录接口(对接人:盖伟伟)
     *
     * @param token
     * @return
     */
    public Observable<BaseData> loginOut(String token) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("token", token);
        parameter.put("userType", "0");
        return getKangLeObserable(parameter, "loginOut", BaseData.class);
    }

    /**
     * 4.1.4.	保存患者档案信息(对接人:张志强)
     *
     * @param userId
     * @param realName
     * @param idCardNo
     * @param idCardUrl
     * @param securityNo
     * @param securityUrl
     * @param relationShip
     * @return
     */
    public Observable<SavePatientInfoData> savePatientInfo(String userId, String realName, String idCardNo,
                                                           String idCardUrl, String securityNo, String securityUrl,
                                                           String relationShip) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("userId", userId);
        parameter.put("realName", realName);
        parameter.put("idCardNo", idCardNo);
        parameter.put("idCardUrl", idCardUrl);
        parameter.put("securityNo", securityNo);
        parameter.put("securityUrl", securityUrl);
        parameter.put("district", "0");
        parameter.put("relationShip", relationShip);
        return getKangLeObserable(parameter, "savePatientInfo", SavePatientInfoData.class);
    }


    /**
     * 修改患者档案信息(对接人:张志强)
     *
     * @param patientId
     * @return
     */
    public Observable<SavePatientInfoData> updatePatientInfoByUserId(String patientId, String idCardNo,
                                                                     String idCardUrl, String securityNo,
                                                                     String securityUrl, String flag) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("id", patientId);
        parameter.put("idCardNo", idCardNo);
        parameter.put("idCardUrl", idCardUrl);
        parameter.put("securityNo", securityNo);
        parameter.put("securityUrl", securityUrl);
        parameter.put("flag", flag);
        return getKangLeObserable(parameter, "updatePatientInfoByUserId", SavePatientInfoData.class);
    }

    /**
     * 4.1.5.	患者信息查询接口（对接人：张志强）
     *
     * @param userId
     * @param queryFlag 请求来源 0: 家庭医生 1：问诊医生， 2 个人中心
     * @return
     */
    public Observable<PatientListData> selectPatientInfoById(String userId, String queryFlag) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("userId", userId);
        parameter.put("queryFlag", queryFlag);
        return getKangLeObserable(parameter, "selectPatientInfoById", PatientListData.class);
    }

    /**
     * 患者详细信息查询接口（对接人：张志强）
     *
     * @param patientId
     * @return
     */
    public Observable<PatientInfoData> selectPatientInfo(String patientId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("id", patientId);
        return getKangLeObserable(parameter, "selectPatientInfo", PatientInfoData.class);
    }

    /**
     * 4.1.6.	患者信息删除接口（对接人：张志强）
     *
     * @param id
     * @return
     */
    public Observable<DeletepatientInfoData> deletePatientInfoById(String id) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("id", id);
        return getKangLeObserable(parameter, "deletePatientInfoById", DeletepatientInfoData.class);
    }

    /**
     * 4.1.7.	获取医院列表接口(对接人:盖伟伟)
     */
    public Observable<HospitalListData> getHospitalList() {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("pid", "");
        parameter.put("cid", "");
        parameter.put("dcid", "");
        return getKangLeObserable(parameter, "getHospitalList", HospitalListData.class);
    }


    /**
     * 4.1.12.	获取医院科室接口(对接人:盖伟伟)
     *
     * @param hospitalId
     * @return
     */
    public Observable<DepartmentData> getHospitalDepart(String hospitalId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("hospitalId", hospitalId);
        parameter.put("pageNo", "1");
        parameter.put("pageSize", "100");
        parameter.put("type", "0");
        return getKangLeObserable(parameter, "getHospitalDepart", DepartmentData.class);
    }


    /**
     * 4.1.17.	保存问诊订单接口(对接人:盖伟伟)
     *
     * @param orderType
     * @param patientArchivesId
     * @param hospitalDepartId
     * @param doctorId
     * @return
     */
    public Observable<SaveOrderData> saveWzOrder(String orderType, String patientArchivesId,
                                                 String hospitalDepartId, String doctorId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderType", orderType);
        parameter.put("patientArchivesId", patientArchivesId);
        parameter.put("hospitalDepartId", hospitalDepartId);
        parameter.put("doctorId", doctorId);
        return getKangLeObserable(parameter, "saveWzOrder", SaveOrderData.class);
    }


    /**
     * 4.1.28.	保存收货地址(对接人:张志强)
     *
     * @param userRealName
     * @param mobile
     * @param detailAddress
     * @return
     */
    public Observable<BaseData> saveConsigneeAddress(String userRealName, String mobile,
                                                     String detailAddress) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("userId", "");
        parameter.put("userRealName", userRealName);
        parameter.put("mobile", mobile);
        parameter.put("province", "");
        parameter.put("city", "");
        parameter.put("district", "");
        parameter.put("doorPlate", "");
        parameter.put("detailAddress", detailAddress);
        parameter.put("cityCode", "");
        parameter.put("longitude", "116.492932");
        parameter.put("latitude", "40.01507");
        return getKangLeObserable(parameter, "saveConsigneeAddress", BaseData.class);
    }

    /**
     * 4.1.29.	获取收货地址列表(对接人:张志强)
     *
     * @return
     */
    public Observable<AddressListData> getConsigneeAddressList() {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("userId", "");
        return getKangLeObserable(parameter, "getConsigneeAddressList", AddressListData.class);
    }


    /**
     * 4.1.33.	修改收货地址(对接人:系统志强)
     *
     * @param caId
     * @param userRealName
     * @param mobile
     * @param detailAddress
     * @return
     */
    public Observable<BaseData> updateConsigeeAddress(String caId, String userRealName, String mobile,
                                                      String detailAddress) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("caId", caId);
        parameter.put("userId", "");
        parameter.put("userRealName", userRealName);
        parameter.put("mobile", mobile);
        parameter.put("province", "");
        parameter.put("city", "");
        parameter.put("district", "");
        parameter.put("doorPlate", "");
        parameter.put("detailAddress", detailAddress);
        parameter.put("cityCode", "");
        parameter.put("longitude", "116.492932");
        parameter.put("latitude", "40.01507");
        return getKangLeObserable(parameter, "updateConsigeeAddress", BaseData.class);
    }

    /**
     * 4.1.25.	取消订单接口(对接人:盖伟伟)
     *
     * @return
     */
    public Observable<BaseData> cancelOrder(String orderType, String orderId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderType", orderType);
        parameter.put("orderId", orderId);
        return getKangLeObserable(parameter, "cancelOrder", BaseData.class);
    }

    /**
     * 4.1.26.	完成订单接口(对接人:盖伟伟)
     *
     * @param orderType
     * @param orderId
     * @param talkTime
     * @return
     */
    public Observable<BaseData> completeOrder(String orderType, String orderId, String talkTime) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderType", orderType);
        parameter.put("orderId", orderId);
        parameter.put("talkTime", talkTime);
        return getKangLeObserable(parameter, "completeOrder", BaseData.class);
    }

    /**
     * 4.1.36.	激活家庭医生签约记录接口(对接人:盖伟伟)
     *
     * @param patientArchiveId
     * @return
     */
    public Observable<ActiveFamlilyFileData> activeFamilyDoctorSign(String patientArchiveId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("patientArchiveId", patientArchiveId);
        return getKangLeObserable(parameter, "activeFamilyDoctorSign", ActiveFamlilyFileData.class);
    }

    /**
     * 4.1.37.	获取医生信息接口(对接人:盖伟伟)
     *
     * @param doctorId
     * @return
     */
    public Observable<FamilyDoctorDetailData> getDoctorDetail(String doctorId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("doctorId", doctorId);
        return getKangLeObserable(parameter, "getDoctorDetail", FamilyDoctorDetailData.class);
    }

    /**
     * 4.1.38.	获取订单列表接口(对接人:盖伟伟)
     *
     * @param status
     * @return
     */
    public Observable<InquiryOrderListData> getOrderList(String status) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("userType", "0");
        parameter.put("orderType", "0,1");
        parameter.put("pageNo", "1");
        parameter.put("pageSize", "100");
        parameter.put("orderStatus", status);
        return getKangLeObserable(parameter, "getOrderList", InquiryOrderListData.class);
    }

    /**
     * 获取患者订单列表接口
     *
     * @param patientInfoId
     * @return
     */
    public Observable<InquiryOrderListData> getPatientOrderList(String patientInfoId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("userType", "0");
        parameter.put("orderType", "0,1");
        parameter.put("pageNo", "1");
        parameter.put("pageSize", "100");
        parameter.put("orderStatus", "-1");
        parameter.put("patientInfoId", patientInfoId);
        return getKangLeObserable(parameter, "getOrderList", InquiryOrderListData.class);
    }

    /**
     * 4.1.18.	获取订单二维码支付信息接口(对接人:盖伟伟)
     *
     * @param orderId
     * @param orderType
     * @return
     */
    public Observable<PayInfoData> getPayInfo(String orderId, String orderType) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderId", orderId);
        parameter.put("orderType", orderType);
        return getKangLeObserable(parameter, "getPayInfo", PayInfoData.class);
    }


    /**
     * 4.1.46.	获取订单支付状态接口(对接人:盖伟伟)
     *
     * @param orderId
     * @return
     */
    public Observable<BaseData> getOrderPayStatus(String orderId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderId", orderId);
        parameter.put("orderType", "0");
        return getKangLeObserable(parameter, "getOrderPayStatus", BaseData.class);
    }

    /**
     * 4.1.15.	二维码生成接口(对接人:盖伟伟)
     *
     * @param data
     * @return
     */
    public Observable<TypedInput> getPayQrCode(String data) {
        return getKangLeService().getPayQrCode("1", data);
    }


    /**
     * 4.1.47.	获取订单详情接口(对接人:盖伟伟)
     *
     * @param orderId
     * @param orderType
     * @return
     */
    public Observable<InquiryOrderDetailData> getOrderDetail(String orderId, String orderType) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderId", orderId);
        parameter.put("orderType", orderType);
        return getKangLeObserable(parameter, "getOrderDetail", InquiryOrderDetailData.class);
    }


    /**
     * 4.1.23.	查询病历 (对接人:张志强)
     *
     * @param orderId
     * @return
     */
    public Observable<PatientCaseData> selectUcUserCase(String orderId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderId", orderId);
        return getKangLeObserable(parameter, "selectUcUserCase", PatientCaseData.class);
    }

    /**
     * 4.1.56.	获取处方信息（对接人:张志强）
     *
     * @param orderId
     * @return
     */
    public Observable<PatientDrugData> getPrescriptionInfo(String orderId, String doctorId) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("orderId", orderId);
        parameter.put("doctorId", doctorId);
        return getKangLeObserable(parameter, "getPrescriptionInfo", PatientDrugData.class);
    }

    /**
     * 4.1.52.	订单排队查询(对接人:张志强)
     *
     * @param doctorsId
     * @param orderId
     * @param orderType 订单类型：0-普通问诊；1-家医问诊
     * @return
     */
    public Observable<InquiryLineData> getLineDoctorsWzOrders(String doctorsId, String orderId, String orderType) {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("doctorsId", doctorsId);
        parameter.put("orderId", orderId);
        parameter.put("orderType", orderType);
        return getKangLeObserable(parameter, "getLineDoctorsWzOrders", InquiryLineData.class);
    }


    /**
     * 获取医护护士上门服务项列表
     *
     * @return
     */
    public Observable<YiHuNurseData> getYiHuNurseItemData() {
        Hashtable<String, Object> parameter = new Hashtable<>();
        parameter.put("role_code", "002");
        parameter.put("service_code", "");
        parameter.put("needLogin", false);
        return getYiHuObserable(parameter, "item", YiHuNurseData.class);
    }

}

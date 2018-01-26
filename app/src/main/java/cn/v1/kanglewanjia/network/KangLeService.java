package cn.v1.kanglewanjia.network;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * Created by lawrence on 14-7-8.
 */
public interface KangLeService {
    @FormUrlEncoded
    @POST("/SesameHospitalControl.action{SESSIONID}")
    Observable<TypedString> getKangLeResponse(@Path(value = "SESSIONID", encode = false) String sessionId, @Field("data") String param, @Query("encryption") boolean encryption);

    @FormUrlEncoded
    @POST("/SesameHospitalControl.action")
    Observable<TypedString> getServerConfig(@Field("data") String param, @Query("encryption") boolean encryption);


    @FormUrlEncoded
    @POST("/KlwjApiControl.action{SESSIONID}")
    Observable<TypedString> getKangLeResponse(@Path(value = "SESSIONID", encode = false) String sessionId, @Field("data") String param, @Field("sign") String sign);

    @GET("/qRCodeImage")
    Observable<TypedInput> getQrcode(@Query("userid") String userid, @Query("mobile") String mobile, @Query("sid") String sid);

    @GET("/QRCodeImage.action")
    Observable<TypedInput> getQrCode(@Query("type") String type, @Query("userId") String userId);

    @GET("/QRCodeImage.action")
    Observable<TypedInput> getPayQrCode(@Query("type") String type, @Query("data") String data);
}

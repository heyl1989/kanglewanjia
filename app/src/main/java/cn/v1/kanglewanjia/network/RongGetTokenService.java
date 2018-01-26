package cn.v1.kanglewanjia.network;


import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * Created by An4 on 2016/1/22.
 */
public interface RongGetTokenService {
    @FormUrlEncoded
    @POST("/getToken.json")
    Observable<TypedString> getRongToken(@Header("App-Key") String key, @Header("Nonce") String Nonce, @Header("Timestamp") String Timestamp, @Header("Signature") String Signature, @Field("userId") String userId, @Field("name") String name, @Field("portraitUri") String portraitUri);
}

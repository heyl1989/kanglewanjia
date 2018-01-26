package cn.v1.kanglewanjia.network;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * Created by qy on 2018/1/12.
 */

public interface YiHuService {

    @FormUrlEncoded
    @POST("/NurseHome_Control.action{SESSIONID}")
    Observable<TypedString> getYiHuResponse(@Path(value="SESSIONID",encode=false) String sessionId, @Field("data") String param, @Field("sign") String sign);

}

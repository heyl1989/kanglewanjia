package cn.v1.kanglewanjia.network;


import cn.v1.kanglewanjia.model.UploadPicData;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;

/**
 * Created by lawrence on 14-9-11.
 */
public interface UploadService {
    @Multipart
    @POST("/nurseUpload")
    Observable<UploadPicData> uploadPic(@Part("myfile") TypedFile typedFile, @Part("data") String param, @Part("sign") String sign, @Part("encryption") boolean encryption);
}

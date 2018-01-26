package cn.v1.kanglewanjia.ui.family_file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.model.UploadPicData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.CompressUtil;
import news.jaywei.com.compresslib.CompressTools;
import rx.functions.Action1;

import static com.baidu.ocr.sdk.model.IDCardParams.ID_CARD_SIDE_FRONT;


public class FamilyFileCardInfoActivity extends BaseActivity {

    @Bind(R.id.et_card_num)
    EditText etCardNum;
    @Bind(R.id.img_camera)
    ImageView imgCamera;
    @Bind(R.id.tv_camera)
    TextView tvCamera;
    @Bind(R.id.ll_camera)
    LinearLayout llCamera;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.tv_title)
    TextView tvTitle;


    private String imgPath;
    private String addFamilyFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_file_card_info);
        ButterKnife.bind(this);
        initData();
        initOCR();
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initData() {
        if (getIntent().hasExtra("addFamilyFile")) {
            addFamilyFile = getIntent().getStringExtra("addFamilyFile");
        }
        if (getIntent().hasExtra("for")) {
            String forCamera = getIntent().getStringExtra("for");
            if (TextUtils.equals("idCard", forCamera)) {
                tvTitle.setText("身份证");
            }
            if (TextUtils.equals("medicalCard", forCamera)) {
                tvTitle.setText("社保卡");
            }
        }
    }

    @OnClick({R.id.ll_camera, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_camera:
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent,1);
                Intent intent1 = new Intent(context, FamilyFileCameraActivity.class);
                startActivityForResult(intent1, 100);
                break;
            case R.id.tv_next:
                Intent intent2 = new Intent(context, FamilyFileDetailActivity.class);
                intent2.putExtra("to", "look");
                intent2.putExtra("imgPath", imgPath);
                intent2.putExtra("addFamilyFile", addFamilyFile);
                startActivity(intent2);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (null != data) {
                imgPath = data.getStringExtra("filePath");
                Log.e("☆", imgPath);
                tvCamera.setVisibility(View.GONE);
                imgCamera.setVisibility(View.VISIBLE);
                Glide.with(context).load("file://" + imgPath).into(imgCamera);
                uploadPic();
            }

        }

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                showTost("文件路径："+uri.getPath().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPic() {
        showDialog();
        CompressUtil.getCompressTool(context).compressToFileJni(new File(imgPath), new CompressTools.OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                bindObservable(mAppClient.uploadImage(file, "", ""), new Action1<UploadPicData>() {
                    @Override
                    public void call(UploadPicData uploadPicData) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
                // 身份证识别参数设置
                IDCardParams param = new IDCardParams();
                param.setImageFile(file);
                param.setIdCardSide(ID_CARD_SIDE_FRONT);
                // 调用身份证识别服务
                OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
                    @Override
                    public void onResult(IDCardResult result) {
                        // 调用成功，返回IDCardResult对象
//                        Log.e("☆",result.toString());
                    }

                    @Override
                    public void onError(OCRError error) {
                        // 调用失败，返回OCRError对象
//                        Log.e("☆",error.toString());
//                        showTost(error.getMessage()+"");
                        closeDialog();
                    }
                });
            }
        });
    }

    private void initOCR() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
            }
        }, getApplicationContext(), Common.OCR_AK, Common.OCR_SK);
    }
}

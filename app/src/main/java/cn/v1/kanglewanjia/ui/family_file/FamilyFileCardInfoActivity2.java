package cn.v1.kanglewanjia.ui.family_file;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.baidu.ocr.sdk.model.Word;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.model.RelationShipData;
import cn.v1.kanglewanjia.model.SavePatientInfoData;
import cn.v1.kanglewanjia.model.UploadPicData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DialogActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.CompressUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import cn.v1.kanglewanjia.util.GlideUtil;
import cn.v1.kanglewanjia.util.RegularUtil;
import cn.v1.kanglewanjia.view.RLoopRecyclerView;
import news.jaywei.com.compresslib.CompressTools;
import rx.functions.Action1;

import static com.baidu.ocr.sdk.model.IDCardParams.ID_CARD_SIDE_FRONT;


public class FamilyFileCardInfoActivity2 extends BaseActivity {


    @Bind(R.id.recyclerView_role)
    RLoopRecyclerView recyclerViewRole;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_card_num)
    EditText etCardNum;
    @Bind(R.id.img_camera)
    ImageView imgCamera;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.img_bg)
    ImageView imgBg;

    private String addFamilyFile;
    private String forCamera;
    private String filePath;
    private String cardUrl;
    private String relationShip = "";
    private String patientId = "";
    private String for_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_file_card_info2);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initOCR();
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(this);
        imgBg.setImageBitmap(bmp);
        Intent intent = new Intent(context, DialogActivity.class);
        intent.putExtra("from", "cancelAddFile");
        startActivity(intent);
//        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgBg.setImageBitmap(null);
    }

    @OnClick(R.id.tv_next)
    public void onClick() {
        String realName = etName.getText().toString().trim();
        String cardNumber = etCardNum.getText().toString().trim();
        if (TextUtils.isEmpty(realName)) {
            showTost("请填写持卡人姓名");
            return;
        }
        if (TextUtils.isEmpty(cardNumber)) {
            showTost("请填写证件号码");
            return;
        }
        if (!RegularUtil.chechIdNum(cardNumber)) {
            showTost("请填写正确的证件号码");
            return;
        }
        String idCardNumber = "";
        String idCardUrl = "";
        String securityNo = "";
        String securityUrl = "";
        String flag = "";
        if (TextUtils.equals(forCamera, "idCard")) {
            idCardNumber = cardNumber;
            idCardUrl = cardUrl;
            flag = "0";
        }
        if (TextUtils.equals(forCamera, "medicalCard")) {
            securityNo = cardNumber;
            securityUrl = cardUrl;
            flag = "1";
        }
        if (TextUtils.equals("edit", for_edit)) {
            updatePatientInfoByUserId(idCardNumber, idCardUrl, securityNo, securityUrl, flag);
        } else {
            savePatientInfo(realName, idCardNumber, idCardUrl, securityNo, securityUrl);
        }
    }

    /**
     * 保存患者信息，提交服务器
     *
     * @param realName
     * @param idCardNo
     * @param idCardUrl
     * @param securityNo
     * @param securityUrl
     */
    private void savePatientInfo(String realName, String idCardNo,
                                 String idCardUrl, String securityNo, String securityUrl
    ) {
        showDialog();
        bindObservable(mAppClient.savePatientInfo("", realName, idCardNo, idCardUrl,
                securityNo, securityUrl, relationShip), new Action1<SavePatientInfoData>() {
            @Override
            public void call(SavePatientInfoData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    Intent intent2 = new Intent(context, FamilyFileDetailActivity.class);
                    intent2.putExtra("to", "look");
                    intent2.putExtra("imgPath", filePath);
                    intent2.putExtra("for",forCamera);
                    intent2.putExtra("addFamilyFile", addFamilyFile);
                    intent2.putExtra("id", data.getData() + "");
                    startActivity(intent2);
                }
                closeDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    /**
     * 修改患者信息，提交服务器
     *
     * @param idCardNo
     * @param idCardUrl
     * @param securityNo
     * @param securityUrl
     */
    private void updatePatientInfoByUserId(String idCardNo,
                                           String idCardUrl, String securityNo, String securityUrl,
                                           String flag) {
        showDialog();
        bindObservable(mAppClient.updatePatientInfoByUserId(patientId, idCardNo, idCardUrl,
                securityNo, securityUrl, flag), new Action1<SavePatientInfoData>() {
            @Override
            public void call(SavePatientInfoData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    Intent intent2 = new Intent(context, FamilyFileDetailActivity.class);
                    intent2.putExtra("to", "look");
                    intent2.putExtra("imgPath", filePath);
                    intent2.putExtra("for",forCamera);
                    intent2.putExtra("addFamilyFile", addFamilyFile);
                    intent2.putExtra("id", patientId);
                    startActivity(intent2);
                }
                closeDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });

    }

    private void initData() {
        if (getIntent().hasExtra("addFamilyFile")) {
            addFamilyFile = getIntent().getStringExtra("addFamilyFile");
        }
        if (getIntent().hasExtra("for")) {
            forCamera = getIntent().getStringExtra("for");
        }
        if (getIntent().hasExtra("for_edit")) {
            for_edit = getIntent().getStringExtra("for_edit");
            if (TextUtils.equals("edit", getIntent().getStringExtra("for_edit"))) {
                llTop.setVisibility(View.GONE);
            }
        }
        if (getIntent().hasExtra("id")) {
            patientId = getIntent().getStringExtra("id");
        }
        if (getIntent().hasExtra("filePath")) {
            filePath = getIntent().getStringExtra("filePath");
//            Glide.with(context).load("file://" + filePath).into(imgCamera);
            GlideUtil.setImagRadius(context,"file://" + filePath,16,imgCamera);
            uploadPic();
        }

    }


    private void initView() {
        recyclerViewRole.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRole.setFocusable(false);
        ArrayList<RelationShipData> relationShipList = new ArrayList<>();
        relationShipList.add(new RelationShipData("爷爷", R.drawable.icon_yeye));
        relationShipList.add(new RelationShipData("奶奶", R.drawable.icon_nainai));
        relationShipList.add(new RelationShipData("爸爸", R.drawable.icon_dad));
        relationShipList.add(new RelationShipData("妈妈", R.drawable.icon_mother));
        relationShipList.add(new RelationShipData("儿子", R.drawable.icon_son));
        relationShipList.add(new RelationShipData("女儿", R.drawable.icon_doctor));
        FamilyFileRoleAdapter familyFileRoleAdapter = new FamilyFileRoleAdapter();
        familyFileRoleAdapter.setDatas(relationShipList);
        recyclerViewRole.setAdapter(familyFileRoleAdapter);

    }

    class FamilyFileRoleAdapter extends RLoopRecyclerView.LoopAdapter<FamilyFileRoleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_family_file_role, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindLoopViewHolder(ViewHolder holder, final int position) {
            holder.tvRole.setText(datas.get(position).getRoleName());
            holder.imgRole.setImageResource(datas.get(position).getRoleResourse());
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        relationShip = datas.get(position).getRoleName();
                    }
                }
            });
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_role)
            ImageView imgRole;
            @Bind(R.id.tv_role)
            TextView tvRole;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    /**
     * 初始化OCR
     */
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

    /**
     * 上传以及身份证识别
     */
    private void uploadPic() {
        showDialog();
        CompressUtil.getCompressTool(context).compressToFileJni(new File(filePath), new CompressTools.OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                bindObservable(mAppClient.uploadImage(file, "", ""), new Action1<UploadPicData>() {
                    @Override
                    public void call(UploadPicData uploadPicData) {
                        if (TextUtils.equals("0000", uploadPicData.getCode())) {
                            cardUrl = uploadPicData.getUrl();
                        }
                        closeDialog();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        closeDialog();
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
                        Log.e("OCR", result.toString());
                        closeDialog();
                        Word realName = result.getName();
                        Word idCardNumber = result.getIdNumber();
                        etName.setText(realName.getWords());
                        etCardNum.setText(idCardNumber.getWords());
                    }

                    @Override
                    public void onError(OCRError error) {
                        // 调用失败，返回OCRError对象
                        Log.e("OCR", error.toString());
//                        showTost(error.getMessage()+"");
                        closeDialog();
                    }
                });
            }
        });
    }


}

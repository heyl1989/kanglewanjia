package cn.v1.kanglewanjia.ui.family_file;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.CameraProviderUtil;


public class FamilyFileBindInfoActivity extends BaseActivity {

    @Bind(R.id.img_id_card)
    ImageView imgIdCard;
    @Bind(R.id.img_medical_card)
    ImageView imgMedicalCard;
    @Bind(R.id.ll_card_partent)
    LinearLayout llCardPartent;
    @Bind(R.id.tv_next)
    TextView tvNext;


    private String addFamilyFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_file_bind_info);
        ButterKnife.bind(this);
        initView();
        initData();
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initData() {
        if(getIntent().hasExtra("addFamilyFile")){
            addFamilyFile = getIntent().getStringExtra("addFamilyFile");
        }
    }

    private void initView() {
        BorderView border = new BorderView(this);
        border.attachTo(llCardPartent);
    }

    @OnClick({R.id.img_id_card, R.id.img_medical_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_id_card:
                if(CameraProviderUtil.hasCamera()){
                    Intent intent1 = new Intent(context,FamilyFileCameraActivity.class);
                    intent1.putExtra("addFamilyFile",addFamilyFile);
                    intent1.putExtra("for","idCard");
                    startActivity(intent1);
                }else{
                    showTost("请安装摄像头");
                }
                break;
            case R.id.img_medical_card:
                if(CameraProviderUtil.hasCamera()){
                    Intent intent2 = new Intent(context,FamilyFileCameraActivity.class);
                    intent2.putExtra("addFamilyFile",addFamilyFile);
                    intent2.putExtra("for","medicalCard");
                    startActivity(intent2);
                }else{
                    showTost("请安装摄像头");
                }
                break;
        }
    }

}

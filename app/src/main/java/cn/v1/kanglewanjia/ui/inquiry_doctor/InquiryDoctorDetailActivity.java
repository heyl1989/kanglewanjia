package cn.v1.kanglewanjia.ui.inquiry_doctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.LoginActivity;
import cn.v1.kanglewanjia.ui.SelectFamilyFileActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;

public class InquiryDoctorDetailActivity extends BaseActivity {

    @Bind(R.id.img_doctor_avatar)
    ImageView imgDoctorAvatar;
    @Bind(R.id.tv_doctor_state)
    TextView tvDoctorState;
    @Bind(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @Bind(R.id.tv_inquiry)
    TextView tvInquiry;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.tv_price_unit)
    TextView tvPriceUnit;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_doctor_inquiry_state)
    TextView tvDoctorInquiryState;
    @Bind(R.id.partent)
    RelativeLayout partent;
    @Bind(R.id.tv_doctor_summary_title)
    TextView tvDoctorSummaryTitle;
    @Bind(R.id.tv_doctor_summary_content)
    TextView tvDoctorSummaryContent;
    @Bind(R.id.tv_doctor_declaration_title)
    TextView tvDoctorDeclarationTitle;
    @Bind(R.id.tv_doctor_declaration_content)
    TextView tvDoctorDeclarationContent;
    @Bind(R.id.tv_confirm_inquiry)
    TextView tvConfirmInquiry;
    @Bind(R.id.img_bg)
    ImageView imgBg;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_doctor_detail);
        ButterKnife.bind(this);
        initView();
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
    }

    @OnClick(R.id.tv_confirm_inquiry)
    public void onClick() {
//        setBlurBg();
        if(isLogin()){
            Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(InquiryDoctorDetailActivity.this);
            imgBg.setImageBitmap(bmp);
            goNewActivity(SelectFamilyFileActivity.class);
        }else{
            Intent intent = new Intent(context,LoginActivity.class);
            intent.putExtra("from","noMain");
            startActivity(intent);
        }
//        showTost("确认排队问诊");
    }


    private void initView() {
        tvPrice.setVisibility(View.GONE);
        tvPriceUnit.setVisibility(View.GONE);
        partent.setFocusable(false);
        tvConfirmInquiry.setFocusable(true);
    }

    /**
     * 设置高斯模糊背景
     */
    private void setBlurBg() {
        new Thread(){
            private Bitmap bmp;
            @Override
            public void run() {
                super.run();
                bmp = FastBlurUtility.getBlurBackgroundDrawer(InquiryDoctorDetailActivity.this);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imgBg.setImageBitmap(bmp);
                    }
                });
            }
        }.start();
    }
}

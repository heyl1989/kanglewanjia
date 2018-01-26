package cn.v1.kanglewanjia.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import io.rong.callkit.RongCallKit;


public class OneKeyConsultationActivity extends BaseActivity {

    @Bind(R.id.img_doctor_avatar)
    ImageView imgDoctorAvatar;
    @Bind(R.id.tv_doctor_state)
    TextView tvDoctorState;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @Bind(R.id.tv_inquiry)
    TextView tvInquiry;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.tv_doctor_inquiry_state)
    TextView tvDoctorInquiryState;
    @Bind(R.id.tv_price_unit)
    TextView tvPriceUnit;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.partent)
    RelativeLayout partent;
    @Bind(R.id.tv_doctor_summary_title)
    TextView tvDoctorSummaryTitle;
    @Bind(R.id.tv_doctor_summary_content)
    TextView tvDoctorSummaryContent;
    @Bind(R.id.tv_confirm_inquiry)
    TextView tvConfirmInquiry;
    @Bind(R.id.img_bg)
    ImageView imgBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_consultation);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.tv_confirm_inquiry)
    public void onClick() {
//        RongCallKit.startSingleCall(context, "receive", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
        finish();
    }

    private void initView() {
        tvPrice.setVisibility(View.GONE);
        tvPriceUnit.setVisibility(View.GONE);
        partent.setFocusable(false);
        tvConfirmInquiry.setFocusable(true);
    }
}

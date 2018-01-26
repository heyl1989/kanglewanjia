package cn.v1.kanglewanjia.ui.family_doctor_order;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;

public class PersonalMessageRecordActivity extends BaseActivity {


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
    @Bind(R.id.partent)
    RelativeLayout partent;
    @Bind(R.id.tv_doctor_message_title)
    TextView tvDoctorMessageTitle;
    @Bind(R.id.tv_doctor_message_content)
    TextView tvDoctorMessageContent;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    @Bind(R.id.tv_patient)
    TextView tvPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_message_record);
        ButterKnife.bind(this);
        partent.setFocusable(false);
        initData();
    }

    private void initData() {
        if(getIntent().hasExtra("focusPatient")){
            String focusPatient = getIntent().getStringExtra("focusPatient");
            tvPatient.setText("签约用户:" + focusPatient);
        }
    }

}

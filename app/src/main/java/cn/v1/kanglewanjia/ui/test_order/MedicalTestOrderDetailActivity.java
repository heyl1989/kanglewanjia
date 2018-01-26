package cn.v1.kanglewanjia.ui.test_order;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.PayMentActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderListActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class MedicalTestOrderDetailActivity extends BaseActivity {

    @Bind(R.id.tv_look_medical_test)
    TextView tvLookMedicalTest;
    @Bind(R.id.tv_look_inquiry_order)
    TextView tvLookInquiryOrder;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_select_address)
    TextView tvSelectAddress;
    @Bind(R.id.tv_patient)
    TextView tvPatient;
    @Bind(R.id.tv_service)
    TextView tvService;
    @Bind(R.id.tv_pay_time)
    TextView tvPayTime;
    @Bind(R.id.tv_fee)
    TextView tvFee;

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_test_order_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.equals("medicalTest", from)) {
            ActivityManagerUtil.getInstance().finishActivityclass(PayMentActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(MedicalTestActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(OrderDetailActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(OrderListActivity.class);
        }
        super.onBackPressed();
    }

    @OnClick({R.id.tv_look_medical_test, R.id.tv_look_inquiry_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_medical_test:
                goNewActivity(MedicalTestResultActivity.class);
                break;
            case R.id.tv_look_inquiry_order:
                goNewActivity(OrderDetailActivity.class);
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }
    }
}

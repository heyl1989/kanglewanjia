package cn.v1.kanglewanjia.ui.drug_order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.PatientDrugData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DrugSelectShopActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryDepartmentActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryDoctorActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryDoctorDetailActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryHospitalActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryPayMentActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class DrugActivity extends BaseActivity {

    @Bind(R.id.tv_btn)
    TextView tvBtn;

    private String from;
    private String orderType = "";
    private String orderId = "";
    private List<PatientDrugData.DataData.MedicineListData> drugList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
        if (TextUtils.equals("call", from)) {
            Intent intent = new Intent(context,OrderDetailActivity.class);
            intent.putExtra("orderId",orderId);
            intent.putExtra("orderType",orderType);
            startActivity(intent);
        }
    }

    private void initData() {
        int number = (int) (Math.random() * 10);
        if (number > 5) {
            tvBtn.setText("查看购物订单");
        } else {
            tvBtn.setText("送药购药");
        }
        tvBtn.setText("送药购药");
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
            if (TextUtils.equals("call", from)) {
                tvBtn.setText("送药购药");
            }
        }
        if(getIntent().hasExtra("orderType")){
            orderType = getIntent().getStringExtra("orderType");
        }
        if(getIntent().hasExtra("orderId")){
            orderId = getIntent().getStringExtra("orderId");
        }
        if(getIntent().hasExtra("drugList")){
            drugList = (List<PatientDrugData.DataData.MedicineListData>) getIntent().getSerializableExtra("drugList");
        }
    }




    @OnClick(R.id.tv_btn)
    public void onClick() {
        if (TextUtils.equals("查看购物订单", tvBtn.getText().toString().trim())) {
            goNewActivity(DrugOrderDetailActivity.class);
        }
        if (TextUtils.equals("送药购药", tvBtn.getText().toString().trim())) {
            Intent intent2 = new Intent(context, DrugFillInfoActivity.class);
            intent2.putExtra("orderId",orderId);
            intent2.putExtra("orderType",orderType);
            intent2.putExtra("drugList", (Serializable) drugList);
            startActivity(intent2);
        }
    }
}

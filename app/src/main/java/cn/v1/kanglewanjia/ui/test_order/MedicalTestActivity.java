package cn.v1.kanglewanjia.ui.test_order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.AddressListData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.PayMentActivity;
import cn.v1.kanglewanjia.ui.address_manage.AdressListActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class MedicalTestActivity extends BaseActivity {


    @Bind(R.id.tv_confirm_pay)
    TextView tvConfirmPay;
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
    @Bind(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    @Bind(R.id.tv_patient)
    TextView tvPatient;
    @Bind(R.id.tv_service)
    TextView tvService;
    @Bind(R.id.tv_pay_time)
    TextView tvPayTime;
    @Bind(R.id.tv_fee)
    TextView tvFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_test);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick({R.id.tv_confirm_pay,R.id.ll_select_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_address:
                Intent intent1 = new Intent(context, AdressListActivity.class);
                startActivityForResult(intent1, 100);
                break;
            case R.id.tv_confirm_pay:
                Intent intent = new Intent(context, PayMentActivity.class);
                intent.putExtra("from", "medicalTest");
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 100) {
            if (data.hasExtra("address")) {
                AddressListData.DataData addressData = (AddressListData.DataData) data.getSerializableExtra("address");
                tvName.setText("联  系  人        " + addressData.getUserRealName() + "");
                tvPhone.setText("联系电话        " + addressData.getMobile() + "");
                tvAddress.setText("收货地址        " + addressData.getDetailAddress() + "");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

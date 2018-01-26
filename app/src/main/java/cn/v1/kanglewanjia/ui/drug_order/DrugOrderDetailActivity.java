package cn.v1.kanglewanjia.ui.drug_order;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DrugSelectShopActivity;
import cn.v1.kanglewanjia.ui.PayMentActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderListActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class DrugOrderDetailActivity extends BaseActivity {

    @Bind(R.id.tv_confirm_receipt)
    TextView tvConfirmReceipt;
    @Bind(R.id.tv_look_inquiry_order)
    TextView tvLookInquiryOrder;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.tv_receiver)
    TextView tvReceiver;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_pay_fee)
    TextView tvPayFee;
    @Bind(R.id.tv_tip_fee)
    TextView tvTipFee;
    @Bind(R.id.tv_total_fee)
    TextView tvTotalFee;
    @Bind(R.id.tv_medical_fee)
    TextView tvMedicalFee;
    @Bind(R.id.tv_cancel_order)
    TextView tvCancelOrder;


    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_order_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.equals("drug", from)) {
            ActivityManagerUtil.getInstance().finishActivityclass(PayMentActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(DrugSelectShopActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(DrugFillInfoActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(DrugActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(OrderDetailActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(OrderListActivity.class);
        }
        super.onBackPressed();
    }

    @OnClick({R.id.tv_confirm_receipt, R.id.tv_look_inquiry_order,R.id.tv_cancel_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_receipt:
                showTost("已确认收货");
                tvConfirmReceipt.setVisibility(View.GONE);
                tvCancelOrder.setVisibility(View.GONE);
                tvOrderState.setText("已完成");
                break;
            case R.id.tv_look_inquiry_order:
                goNewActivity(OrderDetailActivity.class);
                break;
            case R.id.tv_cancel_order:
                showTost("已取消");
                tvConfirmReceipt.setVisibility(View.GONE);
                tvCancelOrder.setVisibility(View.GONE);
                tvOrderState.setText("已取消");
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }
    }

}

package cn.v1.kanglewanjia.ui.nurse_order;

import android.os.Bundle;
import android.text.TextUtils;

import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.PayMentActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderListActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class NurseComeOrderDetailActivity extends BaseActivity {

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_come_order_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        if (TextUtils.equals("nurseCome", from)) {
            ActivityManagerUtil.getInstance().finishActivityclass(PayMentActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(NurseComeFillInfoActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(MoreServiceActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(NurseComeServiceIntroActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(NurseComeActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(OrderDetailActivity.class);
            ActivityManagerUtil.getInstance().finishActivityclass(OrderListActivity.class);
        }
        super.onBackPressed();
    }


    private void initData() {
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }
    }


}

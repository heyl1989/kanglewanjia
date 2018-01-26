package cn.v1.kanglewanjia.ui.family_doctor;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import io.rong.callkit.RongCallKit;

public class FamilyDoctorPayMentActivity extends BaseActivity {

    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;
    @Bind(R.id.tv_medical_paied)
    TextView tvMedicalPaied;
    @Bind(R.id.tv_need_pay)
    TextView tvNeedPay;
    @Bind(R.id.tv_reminder)
    TextView tvReminder;

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initData() {
        if(getIntent().hasExtra("from")){
            from = getIntent().getStringExtra("from");
        }
    }

    private void initView() {
        tvOrderPrice.setText("订单金额：10000元");
        tvMedicalPaied.setText("服务时间：365天");
        String needPay = "实际支付：<font color='#F22509'>10000元</font>";
        tvNeedPay.setText(Html.fromHtml(needPay));
        String reminder = "温馨提示：家庭医生购买后，将立刻生效。如果对家庭医生的服务有任何意见或建议，均可致电4000-123-789。取消支付请按遥控器‘返回’键。";
        tvReminder.setText(Html.fromHtml(reminder));


        tvReminder.setFocusable(true);
        tvReminder.requestFocus();
        tvReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTost("签约成功");
                finish();
            }
        });
    }
}

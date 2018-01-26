package cn.v1.kanglewanjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.drug_order.DrugOrderDetailActivity;
import cn.v1.kanglewanjia.ui.nurse_order.NurseComeOrderDetailActivity;
import cn.v1.kanglewanjia.ui.test_order.MedicalTestOrderDetailActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class PayMentActivity extends BaseActivity {

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
        setContentView(R.layout.activity_pay_ment2);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        tvReminder.setFocusable(true);
        tvReminder.requestFocus();
        initData();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick(R.id.tv_reminder)
    public void onClick() {
        if(TextUtils.equals("drug",from)){
            Intent intent1 = new Intent(context,DrugOrderDetailActivity.class);
            intent1.putExtra("from",from);
            startActivity(intent1);
        }
        if(TextUtils.equals("medicalTest",from)){
            Intent intent1 = new Intent(context,MedicalTestOrderDetailActivity.class);
            intent1.putExtra("from",from);
            startActivity(intent1);
        }
        if(TextUtils.equals("nurseCome",from)){
            Intent intent1 = new Intent(context,NurseComeOrderDetailActivity.class);
            intent1.putExtra("from",from);
            startActivity(intent1);
        }

    }

    private void initData() {
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }
        if(TextUtils.equals("drug",from)){
            tvOrderPrice.setText("订单金额：100元");
            tvMedicalPaied.setText("医保支付：60元");
            String needPay = "实际支付：<font color='#F22509'>40元</font>";
            tvNeedPay.setText(Html.fromHtml(needPay));
            String reminder = "温馨提示：护士上门前会跟您确认送药地址，请保持手机开机。如您有任何关于购药订单的问题，请致电4000-123-789。";
            tvReminder.setText(Html.fromHtml(reminder));
        }
        if(TextUtils.equals("medicalTest",from)){
            tvOrderPrice.setText("订单金额：100元");
            tvMedicalPaied.setVisibility(View.GONE);
            tvNeedPay.setVisibility(View.GONE);
            String reminder = "温馨提示：护士上门前会跟您上门服务地址，请保持手机开机。如您有任何关于购药订单的问题，请致电4000-123-789。";
            tvReminder.setText(Html.fromHtml(reminder));
        }
        if(TextUtils.equals("nurseCome",from)){
            tvOrderPrice.setText("订单金额：100元");
            tvMedicalPaied.setVisibility(View.GONE);
            tvNeedPay.setVisibility(View.GONE);
            String reminder = "温馨提示：护士上门前会跟您上门服务地址，请保持手机开机。如您有任何关于购药订单的问题，请致电4000-123-789。";
            tvReminder.setText(Html.fromHtml(reminder));
        }
    }


}

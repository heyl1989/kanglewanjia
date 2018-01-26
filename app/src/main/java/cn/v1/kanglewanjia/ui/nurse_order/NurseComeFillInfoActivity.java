package cn.v1.kanglewanjia.ui.nurse_order;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.PayMentActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class NurseComeFillInfoActivity extends BaseActivity {

    @Bind(R.id.tv_btn)
    TextView tvBtn;
    @Bind(R.id.scrollView)
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_come_fill_info);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick(R.id.tv_btn)
    public void onClick() {
        Intent intent = new Intent(context,PayMentActivity.class);
        intent.putExtra("from","nurseCome");
        startActivity(intent);
    }
}

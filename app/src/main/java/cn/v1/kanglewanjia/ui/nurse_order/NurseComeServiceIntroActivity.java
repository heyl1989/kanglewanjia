package cn.v1.kanglewanjia.ui.nurse_order;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class NurseComeServiceIntroActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_btn)
    TextView tvBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_come_service_intro);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick(R.id.tv_btn)
    public void onClick() {
        goNewActivity(NurseComeFillInfoActivity.class);
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title + "");
    }


}

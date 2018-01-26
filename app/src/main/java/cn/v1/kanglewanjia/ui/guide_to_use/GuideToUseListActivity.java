package cn.v1.kanglewanjia.ui.guide_to_use;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;

public class GuideToUseListActivity extends BaseActivity {

    @Bind(R.id.ll_sign)
    LinearLayout llSign;
    @Bind(R.id.ll_family_file)
    LinearLayout llFamilyFile;
    @Bind(R.id.ll_inquiry)
    LinearLayout llInquiry;
    @Bind(R.id.ll_family_doctor)
    LinearLayout llFamilyDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_to_use_list);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_sign, R.id.ll_family_file, R.id.ll_inquiry, R.id.ll_family_doctor})
    public void onClick(View view) {
        Intent intent = new Intent(context,GuideToUseDetailActivity.class);
        switch (view.getId()) {
            case R.id.ll_sign:
                intent.putExtra("guide",1);
                break;
            case R.id.ll_family_file:
                intent.putExtra("guide",2);
                break;
            case R.id.ll_inquiry:
                intent.putExtra("guide",3);
                break;
            case R.id.ll_family_doctor:
                intent.putExtra("guide",4);
                break;
        }
        startActivity(intent);

    }
}

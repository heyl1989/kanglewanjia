package cn.v1.kanglewanjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.drug_order.DrugFillInfoActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class DrugSelectShopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_select_shop);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick({R.id.ll_yihu, R.id.ll_dingdang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_yihu:
//                Intent intent1 = new Intent(context,PayMentActivity.class);
//                intent1.putExtra("from","drug");
//                startActivity(intent1);
                goNewActivity(DrugFillInfoActivity.class);
                break;
            case R.id.ll_dingdang:
                Intent intent2 = new Intent(context,PayMentActivity.class);
                intent2.putExtra("from","drug");
                startActivity(intent2);
                break;
        }
    }
}

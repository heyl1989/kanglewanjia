package cn.v1.kanglewanjia.ui.family_doctor_order;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DrugQRCodeActivity;
import cn.v1.kanglewanjia.ui.LookDrugOrderActivity;
import cn.v1.kanglewanjia.util.FastBlurUtility;

public class PersonalVideoRecordActivity extends BaseActivity {

    @Bind(R.id.tv_drug_code)
    TextView tvDrugCode;
    @Bind(R.id.tv_look_drug)
    TextView tvLookDrug;
    @Bind(R.id.img_bg)
    ImageView imgBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_video_record);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
    }

    @OnClick({R.id.tv_drug_code, R.id.tv_look_drug})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_drug_code:
                Bitmap bmp1 = FastBlurUtility.getBlurBackgroundDrawer(PersonalVideoRecordActivity.this);
                imgBg.setImageBitmap(bmp1);
                goNewActivity(DrugQRCodeActivity.class);
                break;
            case R.id.tv_look_drug:
                Bitmap bmp2 = FastBlurUtility.getBlurBackgroundDrawer(PersonalVideoRecordActivity.this);
                imgBg.setImageBitmap(bmp2);
                goNewActivity(LookDrugOrderActivity.class);
                break;
        }
    }
}

package cn.v1.kanglewanjia.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.util.ZXingUtils;

public class DrugQRCodeActivity extends BaseActivity {

    @Bind(R.id.img_drug_code)
    ImageView imgDrugCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_qrcode);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Bitmap bitmap = ZXingUtils.createQRImage("http://www.yihu365.com/kangle/images/chufangok.jpg", 480, 480);
        imgDrugCode.setImageBitmap(bitmap);
    }
}

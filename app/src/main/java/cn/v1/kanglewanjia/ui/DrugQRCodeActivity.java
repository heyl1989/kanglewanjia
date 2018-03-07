package cn.v1.kanglewanjia.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
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
        String orderId = "";
        String doctorId = "";
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getStringExtra("orderId");
        }
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
        String baseURL = "http://111.198.169.114:8088/page/klwj/prescription.jsp";
        String url = baseURL + "?orderId=" + orderId + "&doctorId="  + doctorId + "&token=" +
                (String) SPUtil.get(context, Common.USER_TOKEN,"");
        Bitmap bitmap = ZXingUtils.createQRImage(url, 480, 480);
        imgDrugCode.setImageBitmap(bitmap);
    }
}

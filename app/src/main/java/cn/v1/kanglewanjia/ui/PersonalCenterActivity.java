package cn.v1.kanglewanjia.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.ui.address_manage.AdressListActivity;
import cn.v1.kanglewanjia.ui.drug_order.DrugOrderListActivity;
import cn.v1.kanglewanjia.ui.family_doctor_order.PersonalFamilyDoctorRecordActivity;
import cn.v1.kanglewanjia.ui.guide_to_use.AboutUsActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderListActivity;
import cn.v1.kanglewanjia.ui.nurse_order.NurseComeOrderListActivity;
import cn.v1.kanglewanjia.ui.test_order.MedicalTestOrderListActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;

public class PersonalCenterActivity extends BaseActivity {


    @Bind(R.id.tv_sign_out)
    TextView tvSignOut;
    @Bind(R.id.rl_order)
    RelativeLayout rlOrder;
    @Bind(R.id.rl_medical_order)
    RelativeLayout rlMedicalOrder;
    @Bind(R.id.rl_manage_adress)
    RelativeLayout rlManageAdress;
    @Bind(R.id.rl_medical_test_order)
    RelativeLayout rlMedicalTestOrder;
    @Bind(R.id.rl_doctor)
    RelativeLayout rlDoctor;
    @Bind(R.id.img_about_us)
    ImageView imgAboutUs;
    @Bind(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @Bind(R.id.rl_nurse_come)
    RelativeLayout rlNurseCome;
    @Bind(R.id.main)
    RelativeLayout main;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    @Bind(R.id.tv_user_phone)
    TextView tvUserPhone;

    private BorderView border;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
        border.setBackgroundResource(R.drawable.border_highlight);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initView() {
        tvUserPhone.setText((String)SPUtil.get(context, Common.USER_PHONE_NUMBER,""));
        border = new BorderView(this);
        border.setBackgroundResource(R.drawable.border_highlight);
        border.attachTo(main);
    }

    @OnClick({R.id.tv_sign_out, R.id.rl_order, R.id.rl_doctor, R.id.rl_about_us, R.id.rl_medical_order, R.id.rl_manage_adress, R.id.rl_nurse_come, R.id.rl_medical_test_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                goNewActivity(OrderListActivity.class);
//                goNewActivity(EvaluateActivity.class);
//                showTost("问诊订单");
                break;
            case R.id.rl_doctor:
//                showTost("家庭医生");
                goNewActivity(PersonalFamilyDoctorRecordActivity.class);
                break;
            case R.id.rl_about_us:
//                showTost("关于我们");
                goNewActivity(AboutUsActivity.class);
                break;
            case R.id.tv_sign_out:
                //退出登录
                border.setBackgroundResource(0);
                Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(PersonalCenterActivity.this);
                imgBg.setImageBitmap(bmp);
                Intent intent = new Intent(context, DialogActivity.class);
                intent.putExtra("from", "logOut");
                startActivity(intent);
                break;
            case R.id.rl_manage_adress:
                //地址管理
                goNewActivity(AdressListActivity.class);
                break;
            case R.id.rl_medical_order:
                goNewActivity(DrugOrderListActivity.class);
                //医药订单
                break;
            case R.id.rl_nurse_come:
                goNewActivity(NurseComeOrderListActivity.class);
                //护士上门
                break;
            case R.id.rl_medical_test_order:
                goNewActivity(MedicalTestOrderListActivity.class);
                //检验订单
                break;
        }
    }

}

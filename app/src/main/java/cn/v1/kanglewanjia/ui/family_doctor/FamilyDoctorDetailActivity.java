package cn.v1.kanglewanjia.ui.family_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.FamilyDoctorDetailData;
import cn.v1.kanglewanjia.model.InquiryLineData;
import cn.v1.kanglewanjia.model.SaveOrderData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DialogActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.CameraProviderUtil;
import cn.v1.kanglewanjia.view.CircleImageView;
import io.rong.callkit.RongCallKit;
import rx.functions.Action1;

public class FamilyDoctorDetailActivity extends BaseActivity {


    @Bind(R.id.img_doctor_avatar)
    ImageView imgDoctorAvatar;
    @Bind(R.id.tv_doctor_state)
    TextView tvDoctorState;
    @Bind(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.tv_sign_date)
    TextView tvSignDate;
    @Bind(R.id.tv_patient)
    TextView tvPatient;
    @Bind(R.id.tv_doctor_inquiry_state)
    TextView tvDoctorInquiryState;
    @Bind(R.id.tv_days)
    TextView tvDays;
    @Bind(R.id.partent)
    RelativeLayout partent;
    @Bind(R.id.tv_doctor_price_content)
    TextView tvDoctorPriceContent;
    @Bind(R.id.tv_doctor_summary_content)
    TextView tvDoctorSummaryContent;
    @Bind(R.id.tv_doctor_declaration_content)
    TextView tvDoctorDeclarationContent;
    @Bind(R.id.tv_confirm_inquiry)
    TextView tvConfirmInquiry;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    //排队布局
    @Bind(R.id.img_doctor_avator_line)
    CircleImageView imgDoctorAvatorLine;
    @Bind(R.id.tv_doctor_name_line)
    TextView tvDoctorNameLine;
    @Bind(R.id.tv_line_number)
    TextView tvLineNumber;
    @Bind(R.id.rl_line)
    RelativeLayout rlLine;

    private String doctorId = "";
    private String patientId = "";
    private String hosDepId = "";
    private Handler handler = new Handler();
    private String orderId = "";
    private boolean isLine = false;
    private String doctorstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_doctor_detail);
        ButterKnife.bind(this);
        tvConfirmInquiry.requestFocus();
        ActivityManagerUtil.getInstance().addActivity(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        if(TextUtils.equals("0",doctorstate)){
            ActivityManagerUtil.getInstance().finishActivity(this);
        }else{
            if (isLine) {
                imgBg.setImageResource(R.drawable.bg);
                Intent intent = new Intent(context, DialogActivity.class);
                intent.putExtra("from", "exitInquiryFamilyDoctor");
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }else{
                ActivityManagerUtil.getInstance().finishActivity(this);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageResource(0);
    }

    @OnClick({R.id.tv_confirm_inquiry, R.id.rl_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_inquiry:
                if(CameraProviderUtil.hasCamera()){
                    saveInquiryOrder();
                }else{
                    showTost("请安装摄像头");
                }
                break;
            case R.id.rl_line:
                //退出队列
                imgBg.setImageResource(R.drawable.bg);
                Intent intent = new Intent(context, DialogActivity.class);
                intent.putExtra("from", "outInquiryFamilyDoctorLine");
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                break;
        }
    }


    /**
     * 下单接口
     */
    private void saveInquiryOrder() {
        showDialog();
        bindObservable(mAppClient.saveWzOrder("1", patientId, hosDepId, doctorId), new Action1<SaveOrderData>() {
            @Override
            public void call(SaveOrderData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    orderId = data.getData().getOrderId() + "";
                    getRongToken(orderId);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            closeDialog();
                            isLine = true;
                            getLineDoctorsWzOrders();
                        }
                    }, 2000);
                } else {
                    closeDialog();
                    showTost(data.getMessage() + "");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    /**
     * 查询问诊队列
     */
    private void getLineDoctorsWzOrders() {
        bindObservable(mAppClient.getLineDoctorsWzOrders(doctorId, orderId, "1"), new Action1<InquiryLineData>() {
            @Override
            public void call(InquiryLineData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    if (0 == data.getData().getCount()) {
                        SPUtil.put(context, Common.INQUIRY_ORDER_TYPE, "1");
                        RongCallKit.startSingleCall(context, orderId, doctorId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                        finish();
                    } else {
                        rlLine.setVisibility(View.VISIBLE);
                        rlLine.requestFocus();
                        tvLineNumber.setText("当前有" + data.getData().getCount() + "人在排队，请稍后。。。");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getLineDoctorsWzOrders();
                            }
                        }, 10000);
                    }
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getLineDoctorsWzOrders();
                        }
                    }, 10000);
                    showTost(data.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private void initData() {
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
            getDoctorDetail();
        }
        if (getIntent().hasExtra("patientId")) {
            patientId = getIntent().getStringExtra("patientId");
        }
    }

    /**
     * 获取医生信息
     */
    private void getDoctorDetail() {
        showDialog();
        bindObservable(mAppClient.getDoctorDetail(doctorId), new Action1<FamilyDoctorDetailData>() {
            @Override
            public void call(FamilyDoctorDetailData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    initView(data.getData());
                } else {
                    showTost(data.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    private void initView(FamilyDoctorDetailData.DataData data) {
        hosDepId = data.getHospitalDepartId() + "";
        Glide.with(context).load(data.getDoctorHeadPic()).into(imgDoctorAvatar);
        Glide.with(context).load(data.getDoctorHeadPic()).into(imgDoctorAvatorLine);
        //"医生在线状态：0-离线；1-在线
        doctorstate = data.getOnlineStatus() + "";
        if (TextUtils.equals("0", data.getOnlineStatus() + "")) {
            tvDoctorState.setText("离线");
            tvDoctorState.setBackgroundResource(R.drawable.family_file_button_white_bg);
            tvDoctorInquiryState.setVisibility(View.GONE);
            tvConfirmInquiry.setVisibility(View.GONE);
        }
        if (TextUtils.equals("1", data.getOnlineStatus() + "")) {
            tvDoctorState.setText("在线");
            tvDoctorState.setBackgroundResource(R.drawable.family_file_doctor_state_on);
            tvDoctorInquiryState.setVisibility(View.VISIBLE);
            tvConfirmInquiry.setVisibility(View.VISIBLE);
            if (data.getLineUpCount() == 0) {
                tvDoctorInquiryState.setText("可以接诊");
                tvDoctorInquiryState.setBackgroundResource(R.drawable.family_doctor_item_bottom_bg);
                tvConfirmInquiry.setText("立即问诊");
            } else {
                tvDoctorInquiryState.setText("正在接诊，排队" + data.getLineUpCount() + "人");
                tvDoctorInquiryState.setBackgroundResource(R.drawable.family_doctor_item_bottom_busy_bg);
                tvConfirmInquiry.setText("排队问诊");
            }
        }
        tvDoctorName.setText(data.getDoctorName() + "");
        tvDoctorNameLine.setText(data.getDoctorName() + "");
        //医生级别：0-主任；1-副主任；2-主治；3-住院
        if (TextUtils.equals("0", data.getDoctorLevel() + "")) {
            tvDepartment.setText(data.getDepartName() + "    主任医师");
        }
        if (TextUtils.equals("1", data.getDoctorLevel() + "")) {
            tvDepartment.setText(data.getDepartName() + "    副主任医师");
        }
        if (TextUtils.equals("2", data.getDoctorLevel() + "")) {
            tvDepartment.setText(data.getDepartName() + "    主治医师");
        }
        if (TextUtils.equals("3", data.getDoctorLevel() + "")) {
            tvDepartment.setText(data.getDepartName() + "    住院医师");
        }
        tvHospital.setText(data.getHospitalName() + "");
        tvSignDate.setText("签约日" + data.getSignDate() + "");
        tvPatient.setText("签约用户：" + data.getSignUserName() + "");
        tvDays.setText(data.getSignEnableDays() + "天");
        tvDoctorDeclarationContent.setText(data.getFdsDesc() + "");
        tvDoctorSummaryContent.setText(data.getDoctorDesc() + "");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

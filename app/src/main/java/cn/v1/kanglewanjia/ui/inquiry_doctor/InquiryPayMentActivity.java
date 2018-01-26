package cn.v1.kanglewanjia.ui.inquiry_doctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cn.v1.kanglewanjia.model.InquiryLineData;
import cn.v1.kanglewanjia.model.PayInfoData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DialogActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.ZXingUtils;
import cn.v1.kanglewanjia.view.CircleImageView;
import io.rong.callkit.RongCallKit;
import rx.functions.Action1;

public class InquiryPayMentActivity extends BaseActivity {

    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;
    @Bind(R.id.tv_medical_paied)
    TextView tvMedicalPaied;
    @Bind(R.id.tv_need_pay)
    TextView tvNeedPay;
    @Bind(R.id.tv_reminder)
    TextView tvReminder;
    @Bind(R.id.img_doctor_avator)
    CircleImageView imgDoctorAvator;
    @Bind(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @Bind(R.id.tv_doctor_position)
    TextView tvDoctorPosition;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.img_wx)
    ImageView imgWx;
    @Bind(R.id.ll_wx)
    LinearLayout llWx;
    @Bind(R.id.img_ali)
    ImageView imgAli;
    @Bind(R.id.ll_ali)
    LinearLayout llAli;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    //付款成功
    @Bind(R.id.ll_pay_success)
    LinearLayout llPaySuccess;
    //排队
    @Bind(R.id.img_doctor_avator_line)
    CircleImageView imgDoctorAvatorLine;
    @Bind(R.id.tv_doctor_name_line)
    TextView tvDoctorNameLine;
    @Bind(R.id.tv_line_number)
    TextView tvLineNumber;
    @Bind(R.id.rl_line)
    RelativeLayout rlLine;

    private String from;
    private String orderId = "";
    private String doctorId = "";
    private Handler handler = new Handler();
    private boolean isLine = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageResource(0);
    }

    @Override
    public void onBackPressed() {
        if(isLine){
            return;
        }else{
            imgBg.setImageResource(R.drawable.bg);
            Intent intent = new Intent(context, DialogActivity.class);
            intent.putExtra("from", "exitInquiryPay");
            intent.putExtra("orderId", orderId);
            startActivity(intent);
        }

    }


    @OnClick(R.id.rl_line)
    public void onClick() {
        imgBg.setImageResource(R.drawable.bg);
        Intent intent = new Intent(context, DialogActivity.class);
        intent.putExtra("from", "exitInquiryLine");
        intent.putExtra("orderId", orderId);
        startActivity(intent);
    }

    private void initData() {
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getStringExtra("orderId");
            getPayInfo();
        }
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
    }

    /**
     * 获取支付细信息
     */
    private void getPayInfo() {
        showDialog();
        bindObservable(mAppClient.getPayInfo(orderId, "0"), new Action1<PayInfoData>() {
            @Override
            public void call(PayInfoData data) {
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

    private void initView(PayInfoData.DataData data) {
        PayInfoData.DataData.OrderInfoData orderInfo = data.getOrderInfo();
        PayInfoData.DataData.WeixinData wxData = data.getWeixin();
        getWXQrcode(wxData.getCodeUrl());
        PayInfoData.DataData.AlipayData alData = data.getAlipay();
        getALQrcode(alData.getQrCode());

        Glide.with(context).load(orderInfo.getDoctorPic()).into(imgDoctorAvator);
        Glide.with(context).load(orderInfo.getDoctorPic()).into(imgDoctorAvatorLine);
        tvDoctorName.setText(orderInfo.getDoctorName() + "大夫");
        tvDoctorNameLine.setText(orderInfo.getDoctorName() + "");
        //职级：0-主任；1-副主任；2-主治；3-住院
        if (TextUtils.equals("0", orderInfo.getDoctorPosition())) {
            tvDoctorPosition.setText("主任医师");
        }
        if (TextUtils.equals("1", orderInfo.getDoctorPosition())) {
            tvDoctorPosition.setText("副主任医师");
        }
        if (TextUtils.equals("2", orderInfo.getDoctorPosition())) {
            tvDoctorPosition.setText("主治医师");
        }
        if (TextUtils.equals("3", orderInfo.getDoctorPosition())) {
            tvDoctorPosition.setText("住院医师");
        }
        tvOrderPrice.setText("订单金额：" + orderInfo.getPrice() + "元");
        tvMedicalPaied.setText("医保支付：" + orderInfo.getYbPrice() + "元");
        String needPay = "实际支付：<font color='#F22509'>" + orderInfo.getRealPrice() + "元</font>";
        tvNeedPay.setText(Html.fromHtml(needPay));
        String reminder = "温馨提示：您只需支付<font color='#F22509'>" + orderInfo.getRealPrice() + "元</font>即可与医生视频问诊，如未接通或取消问诊，支付金额将在1-5个工作日原路返还。取消支付请按遥控器‘返回’键。";
        tvReminder.setText(Html.fromHtml(reminder));

        if (null == wxData) {
            llWx.setVisibility(View.GONE);
        }
        if (null == alData) {
            llAli.setVisibility(View.GONE);
        }
        getRongToken(orderId);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderPayStatus();
            }
        }, 8000);
    }

    /**
     * 获取阿里二维码
     *
     * @param qrCode
     */
    private void getALQrcode(String qrCode) {
        Bitmap bitmap = ZXingUtils.createQRImage(qrCode, 120, 120);
        imgAli.setImageBitmap(bitmap);
    }

    /**
     * 获取微信二维码
     *
     * @param codeUrl
     */
    private void getWXQrcode(String codeUrl) {
        Bitmap bitmap = ZXingUtils.createQRImage(codeUrl, 120, 120);
        imgWx.setImageBitmap(bitmap);
    }


    /**
     * 查询付款状态
     */
    private void getOrderPayStatus() {
        showDialog();
        bindObservable(mAppClient.getOrderPayStatus(orderId), new Action1<BaseData>() {
            @Override
            public void call(BaseData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    llPaySuccess.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isLine = true;
                            getLineDoctorsWzOrders();
                        }
                    }, 500);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getOrderPayStatus();
                        }
                    }, 2000);
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
        bindObservable(mAppClient.getLineDoctorsWzOrders(doctorId, orderId, "0"), new Action1<InquiryLineData>() {
            @Override
            public void call(InquiryLineData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    if (0 == data.getData().getCount()) {
                        SPUtil.put(context, Common.INQUIRY_ORDER_TYPE, "0");
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
                    showTost(data.getMessage());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getLineDoctorsWzOrders();
                        }
                    }, 10000);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}

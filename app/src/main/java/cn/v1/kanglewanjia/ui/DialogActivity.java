package cn.v1.kanglewanjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.DeletepatientInfoData;
import cn.v1.kanglewanjia.model.LoginData;
import cn.v1.kanglewanjia.ui.family_doctor.FamilyDoctorDetailActivity;
import cn.v1.kanglewanjia.ui.family_file.FamilyFileCardInfoActivity2;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryPayMentActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import rx.functions.Action1;

public class DialogActivity extends BaseActivity {

    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;

    private String from;
    private String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        tvCancel.requestFocus();
        initData();
    }


    private void initData() {
        from = getIntent().getStringExtra("from");
        if (TextUtils.equals("singleCall", from)) {
            tvContent.setText("您是否要结束当前会话？");
            tvConfirm.setText("继续询问");
            tvCancel.setText("结束会话");
        }
        if (TextUtils.equals("logOut", from)) {
            tvContent.setText("您是否要退出康乐万家？");
            tvConfirm.setText("是，退出");
            tvCancel.setText("否，点错了");
        }
        if (TextUtils.equals("toLogin", from)) {
            if (getIntent().hasExtra("title")) {
                tvContent.setText(getIntent().getStringExtra("title"));
            }
            tvConfirm.setText("去登录");
            tvCancel.setText("暂不登录");
        }

        if (TextUtils.equals("selectDoctorGrade", from)) {
            tvContent.setText("请选择您想要找的医生级别");
            tvConfirm.setText("普通医师");
            tvCancel.setText("高级医师");
        }
        if (TextUtils.equals("exit", from)) {
            tvContent.setText("您是否要退出康乐万家？");
            tvConfirm.setText("是，退出");
            tvCancel.setText("否，点错了");
        }
        if (TextUtils.equals("deletePatientInfo", from)) {
            tvContent.setText("档案删除后，无法恢复，只能重新添加。您是否要删除此档案？");
            tvConfirm.setText("是，删除");
            tvCancel.setText("否，点错了");
        }
        if (TextUtils.equals("cancelAddFile", from)) {
            tvContent.setText("放弃编辑？");
            tvConfirm.setText("是，退出");
            tvCancel.setText("否，点错了");
        }
        if (TextUtils.equals("exitInquiryPay", from)) {
            orderId = getIntent().getStringExtra("orderId");
            tvContent.setText("您是否要取消支付，放弃本次问诊？");
            tvConfirm.setText("是，取消支付");
            tvCancel.setText("否，继续支付");
        }
        if (TextUtils.equals("exitInquiryFamilyDoctor", from)) {
            orderId = getIntent().getStringExtra("orderId");
            tvContent.setText("您是否要放弃本次问诊？");
            tvConfirm.setText("是，放弃问诊");
            tvCancel.setText("否，继续问诊");
        }
        if (TextUtils.equals("outInquiryFamilyDoctorLine", from)) {
            orderId = getIntent().getStringExtra("orderId");
            tvContent.setText("您是否要取消排队等待？\n如果您取消订单，支付金额会在1~5个工作日内原路退回，请注意查收，有疑问请咨询客服400-123-789");
            tvConfirm.setText("取消排队");
            tvCancel.setText("继续等待");
        }
        if (TextUtils.equals("exitInquiryLine", from)) {
            orderId = getIntent().getStringExtra("orderId");
            tvContent.setText("您是否要取消排队等待？\n如果您取消订单，支付金额会在1~5个工作日内原路退回，请注意查收，有疑问请咨询客服400-123-789");
            tvConfirm.setText("取消排队");
            tvCancel.setText("继续等待");
        }
    }

    @OnClick({R.id.tv_confirm, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (TextUtils.equals("logOut", from)) {
                    signOut();
                }
                if (TextUtils.equals("toLogin", from)) {
                    goNewActivity(LoginActivity.class);
                    finish();
                }
                if (TextUtils.equals("selectDoctorGrade", from)) {
                    //已废弃
                    goNewActivity(SelectFamilyFileActivity.class);
                    finish();
                }
                if (TextUtils.equals("exit", from)) {
                    ActivityManagerUtil.getInstance().finishActivityclass(MainActivity.class);
                    System.exit(0);
                    finish();
                }
                if (TextUtils.equals("deletePatientInfo", from)) {
                    String id = getIntent().getStringExtra("id");
                    deletePatientInfo(id);
                }
                if (TextUtils.equals("cancelAddFile", from)) {
                    ActivityManagerUtil.getInstance().finishActivityclass(FamilyFileCardInfoActivity2.class);
                    finish();
                }
                if (TextUtils.equals("exitInquiryPay", from)) {
                    cancelOrder("0");
                }
                if (TextUtils.equals("exitInquiryFamilyDoctor", from)) {
                    if (TextUtils.isEmpty(orderId)) {
                        ActivityManagerUtil.getInstance().finishActivityclass(FamilyDoctorDetailActivity.class);
                        finish();
                    } else {
                        cancelOrder("1");
                    }
                }
                if (TextUtils.equals("outInquiryFamilyDoctorLine", from)) {
                    cancelOrder("1");
                }
                if (TextUtils.equals("exitInquiryLine", from)) {
                    cancelOrder("0");
                }
                break;


            case R.id.tv_cancel:
                if (TextUtils.equals("singleCall", from)) {
                    setResult(100, new Intent().putExtra("endSession", true));
                }
                if (TextUtils.equals("selectDoctorGrade", from)) {
                    goNewActivity(SelectFamilyFileActivity.class);
                }
                finish();
                break;
        }
    }

    /**
     * 删除患者档案
     *
     * @param id
     */
    private void deletePatientInfo(String id) {
        showDialog();
        bindObservable(mAppClient.deletePatientInfoById(id), new Action1<DeletepatientInfoData>() {
            @Override
            public void call(DeletepatientInfoData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    finish();
                } else {
                    showTost(data.getMessage());
                }
                closeDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    /**
     * 退出登录
     */
    private void signOut() {
        showDialog();
        bindObservable(mAppClient.loginOut((String) SPUtil.get(context, Common.USER_TOKEN, "")), new Action1<BaseData>() {
            @Override
            public void call(BaseData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    logOut();
                    goNewActivity(LoginActivity.class);
                    ActivityManagerUtil.getInstance().finishActivityclass(PersonalCenterActivity.class);
                    finish();
                } else {
                    showTost(data.getMessage() + "");
                }
                closeDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    /**
     * 取消订单
     *
     * @param orderType 订单类型：0-普通问诊；1-家医问诊
     */
    private void cancelOrder(String orderType) {
        showDialog();
        bindObservable(mAppClient.cancelOrder(orderType, orderId), new Action1<BaseData>() {
            @Override
            public void call(BaseData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    ActivityManagerUtil.getInstance().finishActivityclass(InquiryPayMentActivity.class);
                    ActivityManagerUtil.getInstance().finishActivityclass(FamilyDoctorDetailActivity.class);
                    finish();
                } else {
                    showTost(data.getMessage());
                }
                closeDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }
}
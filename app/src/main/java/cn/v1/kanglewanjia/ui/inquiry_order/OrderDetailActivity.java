package cn.v1.kanglewanjia.ui.inquiry_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.InquiryOrderDetailData;
import cn.v1.kanglewanjia.model.PatientCaseData;
import cn.v1.kanglewanjia.model.PatientDrugData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DrugQRCodeActivity;
import cn.v1.kanglewanjia.ui.LookDrugOrderActivity;
import cn.v1.kanglewanjia.ui.drug_order.DrugActivity;
import cn.v1.kanglewanjia.ui.nurse_order.MoreServiceActivity;
import cn.v1.kanglewanjia.ui.nurse_order.NurseComeActivity;
import cn.v1.kanglewanjia.ui.test_order.MedicalTestActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import rx.functions.Action1;

public class OrderDetailActivity extends BaseActivity {

    //底部按钮
    @Bind(R.id.img_bg)
    ImageView imgBg;
    @Bind(R.id.tv_medical_test)
    TextView tvMedicalTest;
    @Bind(R.id.tv_drug)
    TextView tvDrug;
    @Bind(R.id.tv_nurse_come)
    TextView tvNurseCome;
    @Bind(R.id.img_look_drug)
    ImageView imgLookDrug;
    @Bind(R.id.img_drug_code)
    ImageView imgDrugCode;
    //订单信息
    @Bind(R.id.tv_order_id)
    TextView tvOrderId;
    @Bind(R.id.tv_order_state)
    TextView tvOrderState;
    @Bind(R.id.tv_order_type)
    TextView tvOrderType;
    @Bind(R.id.tv_patient)
    TextView tvPatient;
    @Bind(R.id.tv_doctor)
    TextView tvDoctor;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;
    @Bind(R.id.tv_medical_price)
    TextView tvMedicalPrice;
    @Bind(R.id.tv_need_pay)
    TextView tvNeedPay;
    @Bind(R.id.tv_pay_way)
    TextView tvPayWay;
    @Bind(R.id.tv_pay_id)
    TextView tvPayId;
    //处方
    @Bind(R.id.tv_drug_number)
    TextView tvDrugNumber;
    @Bind(R.id.tv_patient_info)
    TextView tvPatientInfo;
    @Bind(R.id.tv_symptom)
    TextView tvSymptom;
    @Bind(R.id.tv_drug1)
    TextView tvDrug1;
    @Bind(R.id.tv_drug1_price)
    TextView tvDrug1Price;
    @Bind(R.id.tv_drug1_spec)
    TextView tvDrug1Spec;
    @Bind(R.id.tv_drug1_use)
    TextView tvDrug1Use;
    @Bind(R.id.tv_drug2)
    TextView tvDrug2;
    @Bind(R.id.tv_drug2_price)
    TextView tvDrug2Price;
    @Bind(R.id.tv_drug2_spec)
    TextView tvDrug2Spec;
    @Bind(R.id.tv_drug2_use)
    TextView tvDrug2Use;
    //病例
    @Bind(R.id.tv_ill_name)
    TextView tvIllName;
    @Bind(R.id.tv_ill_sex)
    TextView tvIllSex;
    @Bind(R.id.tv_ill_history_department)
    TextView tvIllHistoryDepartment;
    @Bind(R.id.tv_ill_age)
    TextView tvIllAge;
    @Bind(R.id.tv_ill_history)
    TextView tvIllHistory;
    @Bind(R.id.tv_ill_self_history)
    TextView tvIllSelfHistory;
    @Bind(R.id.tv_ill_family_history)
    TextView tvIllFamilyHistory;
    @Bind(R.id.tv_ill_marry_history)
    TextView tvIllMarryHistory;
    @Bind(R.id.tv_ill_allergy)
    TextView tvIllAllergy;
    @Bind(R.id.tv_ill_check)
    TextView tvIllCheck;
    @Bind(R.id.tv_ill_assist_check)
    TextView tvIllAssistCheck;
    @Bind(R.id.tv_ill_diagnosis)
    TextView tvIllDiagnosis;
    @Bind(R.id.tv_ill_date)
    TextView tvIllDate;
    @Bind(R.id.tv_ill_suit)
    TextView tvIllSuit;
    @Bind(R.id.tv_ill_latest_history)
    TextView tvIllLatestHistory;
    @Bind(R.id.tv_ill_view)
    TextView tvIllView;
    @Bind(R.id.tv_doctor_sign)
    TextView tvDoctorSign;
    //空页面
    @Bind(R.id.tv_null_drug)
    TextView tvNullDrug;
    @Bind(R.id.tv_null_ill)
    TextView tvNullIll;

    private String orderId = "";
    private String orderType = "";
    private String patientName = "";
    private String patientAge = "";
    private String patientSex = "";
    private String createTime = "";
    private String departMent = "";
    private List<PatientDrugData.DataData.MedicineListData> drugDatas = new ArrayList<>();
    private String doctorId = "";
    private String doctorName = "";
    private PatientDrugData.DataData drugInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ActivityManagerUtil.getInstance().addActivity(this);
        ButterKnife.bind(this);
        imgLookDrug.requestFocus();
        initData();
        initView();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
    }


    @OnClick({R.id.tv_medical_test, R.id.tv_drug, R.id.tv_nurse_come, R.id.img_look_drug, R.id.img_drug_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_medical_test:
                goNewActivity(MedicalTestActivity.class);
                break;
            case R.id.tv_drug:
                Intent intent2 = new Intent(context, DrugActivity.class);
                intent2.putExtra("orderId",orderId);
                intent2.putExtra("orderType",orderType);
                intent2.putExtra("drugList", (Serializable) drugDatas);
                startActivity(intent2);
                break;
            case R.id.tv_nurse_come:
                goNewActivity(MoreServiceActivity.class);
                break;
            case R.id.img_look_drug:
                Bitmap bmp2 = FastBlurUtility.getBlurBackgroundDrawer(OrderDetailActivity.this);
                imgBg.setImageBitmap(bmp2);
                Intent intent = new Intent(context, LookDrugOrderActivity.class);
                intent.putExtra("drugInfo", (Serializable) drugInfo);
                startActivity(intent);
                break;
            case R.id.img_drug_code:
                Bitmap bmp1 = FastBlurUtility.getBlurBackgroundDrawer(OrderDetailActivity.this);
                imgBg.setImageBitmap(bmp1);
                goNewActivity(DrugQRCodeActivity.class);
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getStringExtra("orderId");
        }
        if (getIntent().hasExtra("orderType")) {
            orderType = getIntent().getStringExtra("orderType");
        }
        getOrderDetail();

    }

    private void initView() {
        imgLookDrug.setVisibility(View.GONE);
        imgDrugCode.setVisibility(View.GONE);
        tvMedicalTest.setVisibility(View.GONE);
        tvDrug.setVisibility(View.GONE);
    }


    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        showDialog();
        bindObservable(mAppClient.getOrderDetail(orderId, orderType), new Action1<InquiryOrderDetailData>() {
            @Override
            public void call(InquiryOrderDetailData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    initOrderView(data.getData());
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

    /**
     * 获取病例信息
     */
    private void selectUseCase() {
        showDialog();
        bindObservable(mAppClient.selectUcUserCase(orderId), new Action1<PatientCaseData>() {
            @Override
            public void call(PatientCaseData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    if(null != data.getData()){
                        initCaseView(data.getData());
                    }
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

    /**
     * 获取处方信息
     */
    private void getPrescriptionInfo(String doctorId) {
        bindObservable(mAppClient.getPrescriptionInfo(orderId, doctorId), new Action1<PatientDrugData>() {
            @Override
            public void call(PatientDrugData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    if(null != data.getData()){
                        if(null != data.getData().getMedicineList()){
                            if(data.getData().getMedicineList().size()!= 0){
                                initPrescription(data.getData());
                            }
                        }
                    }
                } else {
                    showTost(data.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    /**
     * 订单布局
     *
     * @param data
     */
    @SuppressLint("SetTextI18n")
    private void initOrderView(InquiryOrderDetailData.DataData data) {

        doctorId = data.getDoctorId() + "";
        departMent = data.getDepartName() + "";
        patientName = data.getPatientRealName() + "";
        patientAge = data.getPatientAge() + "";
        createTime = data.getCreateTime();
        doctorName = data.getDoctorName()+"";
        //患者性别(0:男,1:女)
        if (TextUtils.equals("0", data.getPatientSex() + "")) {
            patientSex = "男";
        }
        if (TextUtils.equals("1", data.getPatientSex() + "")) {
            patientSex = "女";
        }

        tvOrderId.setText("订单ID：" + data.getOrderId());
        //订单状态：0-初始；1-待服务；2-已服务；3-已取消
        String orderState = data.getOrderStatus() + "";
        if (TextUtils.equals("0", orderState)) {
            tvOrderState.setText("状态：待付款");
        }
        if (TextUtils.equals("1", orderState)) {
            tvOrderState.setText("状态：待服务");
        }
        if (TextUtils.equals("2", orderState)) {
            tvOrderState.setText("状态：已服务");
            selectUseCase();
            getPrescriptionInfo(doctorId);

        }
        if (TextUtils.equals("3", orderState)) {
            tvOrderState.setText("状态：已取消");
        }
        if (TextUtils.equals("4", orderState)) {
            tvOrderState.setText("状态：服务中");
        }
        //TODO 测试
//        selectUseCase();
//        getPrescriptionInfo(doctorId);

        tvPatient.setText("患者：" + data.getPatientRealName());
        tvDoctor.setText("医生：" + data.getDoctorName());
        tvDepartment.setText("科室：" + data.getDepartName());
        tvHospital.setText("医院：" + data.getHospitalName());
        tvDate.setText("时间：" + data.getCreateTime());
        //"订单类型：0-普通问诊；1-家医问诊",
        if (TextUtils.equals("0", data.getOrderType() + "")) {
            tvOrderType.setText("类型：普通问诊");
            tvOrderPrice.setText("订单金额：" + data.getPrice());
            tvMedicalPrice.setText("医保金额：" + data.getMedicalInsuranceDeductibleAmount());
            tvNeedPay.setText("实付金额：" + data.getRealPrice());
            tvOrderPrice.setVisibility(View.VISIBLE);
            tvMedicalPrice.setVisibility(View.VISIBLE);
            tvNeedPay.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals("1", data.getOrderType() + "")) {
            tvOrderType.setText("类型：家医问诊");
            tvOrderPrice.setVisibility(View.GONE);
            tvMedicalPrice.setVisibility(View.GONE);
            tvNeedPay.setVisibility(View.GONE);
        }
        //"payStatus":"支付状态：0-未支付；1-已支付；2-已退款"
        if (TextUtils.equals("0", data.getPayStatus() + "")) {
            tvPayWay.setVisibility(View.GONE);
            tvPayId.setVisibility(View.GONE);
        } else {
            //支付方式：1-微信扫码；2-支付宝扫码"
            if (TextUtils.equals("1", data.getPayType())) {
                tvPayWay.setText("支付方式：微信扫码");
            }
            if (TextUtils.equals("2", data.getPayType())) {
                tvPayWay.setText("支付方式：支付宝扫码");
            }
            tvPayId.setText("支付ID：" + data.getTradeNo() + "");
        }
    }

    /**
     * 病例布局
     *
     * @param data
     */
    @SuppressLint("SetTextI18n")
    private void initCaseView(PatientCaseData.DataData data) {
        tvNullIll.setVisibility(View.GONE);
        tvMedicalTest.setVisibility(View.VISIBLE);
        tvIllName.setText("姓名：" + patientName);
        tvIllSex.setText("性别：" + patientSex);
        tvIllAge.setText("年龄：" + patientAge);
        tvIllHistoryDepartment.setText("科室：" + data.getDepartments());
        tvIllHistory.setText("既往病史：" + data.getAnamnesis());
        tvIllSelfHistory.setText("个人历史：" + data.getPersonage());
        tvIllFamilyHistory.setText("家族史：" + data.getFamily());
        tvIllMarryHistory.setText("婚育史：" + data.getMarital());
        tvIllAllergy.setText("过敏史：" + data.getAllergy());
        tvIllCheck.setText("体格检查：" + data.getPhysicalExamination());
        tvIllAssistCheck.setText("辅助检查：" + data.getAuxiliaryExamination());
        tvIllDiagnosis.setText("初步诊断：" + data.getPrimaryDiagnosis());
        tvIllDate.setText("就诊时间：" + createTime);
        tvIllSuit.setText("主诉：" + data.getCause());
        tvIllLatestHistory.setText("现病史：" + data.getMedicalHistory());
        tvIllView.setText("处理意见：" + data.getTreatmentSuggestion());
        tvDoctorSign.setText("医师签字：" + data.getRealName());
    }

    /**
     * 处方布局
     *
     * @param data
     */
    @SuppressLint("SetTextI18n")
    private void initPrescription(PatientDrugData.DataData data) {
        tvNullDrug.setVisibility(View.GONE);
        imgLookDrug.setVisibility(View.VISIBLE);
        imgDrugCode.setVisibility(View.VISIBLE);
        tvDrug.setVisibility(View.VISIBLE);

        data.setDepartMent(departMent);
        data.setPatientAge(patientAge);
        data.setPatientName(patientName);
        data.setPatientSex(patientSex);
        data.setDoctorName(doctorName);
        drugInfo = data;

        tvDrugNumber.setText("NO." + data.getPrescriptionId() + "        科室：" + departMent);
        tvPatientInfo.setText("姓名：" + patientName + "    性别：" + patientSex + "    年龄：" + patientAge);
        tvSymptom.setText("临床诊断：" + data.getDiagnosisResult());

        drugDatas.addAll(data.getMedicineList());
        if (drugDatas.size() > 0) {
            if (drugDatas.size() > 1) {
                tvDrug1.setText("1." + drugDatas.get(0).getName() + "X" + drugDatas.get(0).getNum());
                tvDrug1Price.setText("¥：" + drugDatas.get(0).getPrice());
                tvDrug1Spec.setText(drugDatas.get(0).getSpecifications() + "");
                tvDrug1Use.setText(drugDatas.get(0).getUsageAndDosage() + "");

                tvDrug2.setText("2." + drugDatas.get(1).getName() + "X" + drugDatas.get(1).getNum());
                tvDrug2Price.setText("¥：" + drugDatas.get(1).getPrice());
                tvDrug2Spec.setText(drugDatas.get(1).getSpecifications() + "");
                tvDrug2Use.setText(drugDatas.get(1).getUsageAndDosage() + "");
            } else {
                tvDrug1.setText("1." + drugDatas.get(0).getName() + "X" + drugDatas.get(0).getNum());
                tvDrug1Price.setText("¥：" + drugDatas.get(0).getPrice());
                tvDrug1Spec.setText(drugDatas.get(0).getSpecifications() + "");
                tvDrug1Use.setText(drugDatas.get(0).getUsageAndDosage() + "");

                tvDrug2.setVisibility(View.GONE);
                tvDrug2Price.setVisibility(View.GONE);
                tvDrug2Spec.setVisibility(View.GONE);
                tvDrug2Use.setVisibility(View.GONE);
            }
        }else{
            tvDrug1.setVisibility(View.GONE);
            tvDrug1Price.setVisibility(View.GONE);
            tvDrug1Spec.setVisibility(View.GONE);
            tvDrug1Use.setVisibility(View.GONE);
            tvDrug2.setVisibility(View.GONE);
            tvDrug2Price.setVisibility(View.GONE);
            tvDrug2Spec.setVisibility(View.GONE);
            tvDrug2Use.setVisibility(View.GONE);
        }
    }
}

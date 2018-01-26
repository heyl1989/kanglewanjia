package cn.v1.kanglewanjia.ui.family_file;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.InquiryOrderListData;
import cn.v1.kanglewanjia.model.PatientInfoData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DrugQRCodeActivity;
import cn.v1.kanglewanjia.ui.LookDrugOrderActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import rx.functions.Action1;

public class FamilyFileDetailActivity extends BaseActivity {

    @Bind(R.id.tv_edit)
    TextView tvEdit;
    @Bind(R.id.ll_bind_medical)
    LinearLayout llBindMedical;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_focuesed_item)
    TextView tvFocuesedItem;
    @Bind(R.id.tv_drug_code)
    TextView tvDrugCode;
    @Bind(R.id.tv_look_drug)
    TextView tvLookDrug;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    @Bind(R.id.img_id_card)
    ImageView imgIdCard;
    @Bind(R.id.ll_no_history)
    LinearLayout llNoHistory;
    @Bind(R.id.img_role)
    ImageView imgRole;
    @Bind(R.id.tv_role)
    TextView tvRole;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.ll_top)
    LinearLayout llTop;
    @Bind(R.id.tv_medical_no)
    TextView tvMedicalNo;
    @Bind(R.id.img_medical_card)
    ImageView imgMedicalCard;
    @Bind(R.id.fr_medical_info)
    FrameLayout frMedicalInfo;
    @Bind(R.id.ll_to_bind_medical)
    LinearLayout llToBindMedical;
    @Bind(R.id.ll_to_bind_id)
    LinearLayout llToBindId;
    @Bind(R.id.fr_id_info)
    FrameLayout frIdInfo;
    @Bind(R.id.ll_bind_id)
    LinearLayout llBindId;
    @Bind(R.id.tv_id_number)
    TextView tvIdNumber;
    @Bind(R.id.img_medical_card_state)
    ImageView imgMedicalCardState;
    @Bind(R.id.img_id_card_state)
    ImageView imgIdCardState;


    private String addFamilyFile;
    private String patientId;
    private FamilyFileDetailOrderAdapter familyFileDetailOrderAdapter;
    private List<InquiryOrderListData.DataData.ListData> listDats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_file_detail);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
        selectPatientInfo();
        getPatientOrderList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManagerUtil.getInstance().finishActivityclass(FamilyFileBasicInfoActivity.class);
        ActivityManagerUtil.getInstance().finishActivityclass(FamilyFileBindInfoActivity.class);
        ActivityManagerUtil.getInstance().finishActivityclass(FamilyFileCardInfoActivity2.class);
        ActivityManagerUtil.getInstance().finishActivityclass(FamilyFileCardInfoActivity2.class);
    }

    @OnClick({R.id.tv_edit, R.id.ll_bind_medical, R.id.ll_bind_id, R.id.tv_drug_code, R.id.tv_look_drug})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                //编辑，已废弃
                goNewActivity(FamilyFileBasicInfoActivity.class);
                break;
            case R.id.ll_bind_medical:
                //绑定医保卡
                Intent intent1 = new Intent(context, FamilyFileCameraActivity.class);
                intent1.putExtra("for", "medicalCard");
                intent1.putExtra("for_edit", "edit");
                intent1.putExtra("id", patientId);
                startActivity(intent1);
                break;
            case R.id.ll_bind_id:
                //绑定身份证
                Intent intent2 = new Intent(context, FamilyFileCameraActivity.class);
                intent2.putExtra("for", "idCard");
                intent2.putExtra("for_edit", "edit");
                intent2.putExtra("id", patientId);
                startActivity(intent2);
                break;
            case R.id.tv_drug_code:
                //处方二维码，已废弃
                Bitmap bmp1 = FastBlurUtility.getBlurBackgroundDrawer(FamilyFileDetailActivity.this);
                imgBg.setImageBitmap(bmp1);
                goNewActivity(DrugQRCodeActivity.class);
                break;
            case R.id.tv_look_drug:
                //查看处方，已废弃
                Bitmap bmp2 = FastBlurUtility.getBlurBackgroundDrawer(FamilyFileDetailActivity.this);
                imgBg.setImageBitmap(bmp2);
                goNewActivity(LookDrugOrderActivity.class);
                break;
        }
    }

    private void initData() {
        String to = getIntent().getStringExtra("to");
        if (TextUtils.equals("look", to)) {
            tvEdit.setVisibility(View.GONE);
            if (getIntent().hasExtra("imgPath")) {
                String imgPath = getIntent().getStringExtra("imgPath");
                if (!TextUtils.isEmpty(imgPath)) {
                    if(getIntent().hasExtra("for")){
                        String forCamera = getIntent().getStringExtra("for");
                        if(TextUtils.equals("idCard",forCamera)){
                            Glide.with(context).load("file://" + imgPath).into(imgIdCard);
                        }
                        if(TextUtils.equals("medicalCard",forCamera)){
                            Glide.with(context).load("file://" + imgPath).into(imgMedicalCard);
                        }
                    }
                }
            }
        }
        if (TextUtils.equals("edit", to)) {
            tvEdit.setVisibility(View.VISIBLE);
        }
        addFamilyFile = getIntent().getStringExtra("addFamilyFile");
        if (TextUtils.equals("add", addFamilyFile)) {
            llNoHistory.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            tvFocuesedItem.setVisibility(View.GONE);
            tvDrugCode.setVisibility(View.GONE);
            tvLookDrug.setVisibility(View.GONE);
        }
        if (getIntent().hasExtra("id")) {
            patientId = getIntent().getStringExtra("id");
        }
    }

    private void initView() {
        llBindId.setEnabled(false);
        llBindMedical.setEnabled(false);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        familyFileDetailOrderAdapter = new FamilyFileDetailOrderAdapter();
        recyclerView.setAdapter(familyFileDetailOrderAdapter);
    }

    /**
     * 查询档案详细信息
     */
    private void selectPatientInfo() {
        bindObservable(mAppClient.selectPatientInfo(patientId), new Action1<PatientInfoData>() {
            @Override
            public void call(PatientInfoData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    dataForBasicInfo(data.getData());
                } else {
                    showTost(data.getMessage() + "");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }


    /**
     * 患者订单列表
     */
    private void getPatientOrderList() {
        bindObservable(mAppClient.getPatientOrderList(patientId), new Action1<InquiryOrderListData>() {
            @Override
            public void call(InquiryOrderListData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    listDats.clear();
                    listDats.addAll(data.getData().getList());
                    if(listDats.size() == 0){
                        llNoHistory.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else{
                        familyFileDetailOrderAdapter.notifyDataSetChanged();
                    }
                }else{
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
     * 基本信息赋值
     *
     * @param data
     */
    private void dataForBasicInfo(PatientInfoData.DataData data) {
        String role = data.getRelationShip();
        tvRole.setText(data.getRelationShip() + "");
        tvName.setText("姓名：" + data.getRealName() + "");
        tvAge.setText("年龄：" + data.getAge() + "");
        if (TextUtils.isEmpty(data.getSecurityNo())) {
            llToBindMedical.setVisibility(View.VISIBLE);
            frMedicalInfo.setVisibility(View.GONE);
            llBindMedical.setEnabled(true);
        } else {
            llToBindMedical.setVisibility(View.GONE);
            frMedicalInfo.setVisibility(View.VISIBLE);
            llBindMedical.setEnabled(false);
            tvMedicalNo.setText(data.getSecurityNo() + "");
            Log.e("☆",data.getSecurityUrl());
            Glide.with(context)
                    .load(data.getSecurityUrl())
                    .override(960,540)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(imgMedicalCard);
            imgMedicalCardState.setImageResource(R.drawable.icon_passed);
        }
        if (TextUtils.isEmpty(data.getIdCardNo())) {
            llToBindId.setVisibility(View.VISIBLE);
            frIdInfo.setVisibility(View.GONE);
            llBindId.setEnabled(true);
        } else {
            llToBindId.setVisibility(View.GONE);
            frIdInfo.setVisibility(View.VISIBLE);
            llBindId.setEnabled(false);
            tvIdNumber.setText(data.getIdCardNo() + "");
            Glide.with(context)
                    .load(data.getIdCardUrl())
                    .override(960,540)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(imgIdCard);
            imgIdCardState.setImageResource(R.drawable.icon_passed);
        }

        if (TextUtils.equals("爷爷", role)) {
            imgRole.setImageResource(R.drawable.icon_yeye);
        }
        if (TextUtils.equals("奶奶", role)) {
            imgRole.setImageResource(R.drawable.icon_nainai);
        }
        if (TextUtils.equals("爸爸", role)) {
            imgRole.setImageResource(R.drawable.icon_dad);
        }
        if (TextUtils.equals("妈妈", role)) {
            imgRole.setImageResource(R.drawable.icon_mother);

        }
        if (TextUtils.equals("儿子", role)) {
            imgRole.setImageResource(R.drawable.icon_son);
        }
        if (TextUtils.equals("女儿", role)) {
            imgRole.setImageResource(R.drawable.icon_doctor);
        }
    }


    class FamilyFileDetailOrderAdapter extends RecyclerView.Adapter<FamilyFileDetailOrderAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_family_file_detail_order, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tvOrderId.setText("问诊订单ID："+listDats.get(position).getOrderId());
            holder.tvHospital.setText("医院："+listDats.get(position).getHospitalName());
            holder.tvDepartment.setText("科室："+listDats.get(position).getDepartName());
            holder.tvDate.setText("就诊时间："+listDats.get(position).getCreateTime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,OrderDetailActivity.class);
                    intent.putExtra("orderId",listDats.get(position).getOrderId()+"");
                    intent.putExtra("orderType",listDats.get(position).getOrderType()+"");
                    startActivity(intent);
                }
            });
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        tvFocuesedItem.setText((position + 1) + "/" + getItemCount());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return listDats.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_order_id)
            TextView tvOrderId;
            @Bind(R.id.tv_hospital)
            TextView tvHospital;
            @Bind(R.id.tv_department)
            TextView tvDepartment;
            @Bind(R.id.tv_date)
            TextView tvDate;
            @Bind(R.id.tv_symptom)
            TextView tvSymptom;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

}

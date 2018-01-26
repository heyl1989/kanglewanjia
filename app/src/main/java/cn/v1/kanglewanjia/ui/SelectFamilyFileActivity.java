package cn.v1.kanglewanjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.ActiveFamlilyFileData;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.DepartmentData;
import cn.v1.kanglewanjia.model.PatientListData;
import cn.v1.kanglewanjia.model.SaveOrderData;
import cn.v1.kanglewanjia.ui.family_doctor.FamilyDoctorDetailActivity;
import cn.v1.kanglewanjia.ui.family_file.FamilyFileBindInfoActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryPayMentActivity;
import cn.v1.kanglewanjia.util.CameraProviderUtil;
import rx.functions.Action1;

/**
 * 选择患者档案页面
 * 1.从首页跳转过来为家庭医生选择
 * 2.从问诊过来去支付页面
 * 3.从家庭医生列表过来finish()回传数据
 */
public class SelectFamilyFileActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_add_file)
    TextView tvAddFile;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private String from;
    private List<PatientListData.DataData> list = new ArrayList<>();
    private SelectFileAdapter selectFileAdapter;
    private String hosDepId = "";
    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_family_file);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        getPatientList();
    }

    @OnClick(R.id.tv_add_file)
    public void onClick() {
//        showTost("添加档案");
        Intent intent = new Intent(context, FamilyFileBindInfoActivity.class);
        intent.putExtra("addFamilyFile", "add");
        startActivity(intent);
    }

    private void initView() {
        BorderView border = new BorderView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        border.attachTo(recyclerView);
        border.setBackgroundResource(R.drawable.border_green_light);
        selectFileAdapter = new SelectFileAdapter();
        recyclerView.setAdapter(selectFileAdapter);

    }

    private void initData() {
        from = getIntent().getStringExtra("from");
        if (TextUtils.equals("main", from)) {
            tvTitle.setText("请选择匹配家庭医生的家人档案，如果您的家庭医生在康乐万家中，您将可以随时与他沟通");
        }
        if (getIntent().hasExtra("hosDepId")) {
            hosDepId = getIntent().getStringExtra("hosDepId");
        }
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
    }

    /**
     * 获取档案列表
     */
    private void getPatientList() {

        bindObservable(mAppClient.selectPatientInfoById("", "2"), new Action1<PatientListData>() {
            @Override
            public void call(PatientListData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    list.addAll(data.getData());
                    tvNum.setText("(" + (0) + "/" + list.size() + ")");
                    selectFileAdapter.notifyDataSetChanged();
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

    class SelectFileAdapter extends RecyclerView.Adapter<SelectFileAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_select_family_file, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        tvNum.setText("(" + (position + 1) + "/" + getItemCount() + ")");
                    }
                }
            });

            String idNumber = list.get(position).getIdCardNo();
//            String medicalCard = list.get(position).getIdCardNo();
            if (TextUtils.isEmpty(idNumber)) {
                holder.imgHaveIdcard.setImageResource(R.drawable.icon_wrong);
            } else {
                holder.imgHaveIdcard.setImageResource(R.drawable.icon_right);
            }
            String securityNo = list.get(position).getSecurityNo();
            if (TextUtils.isEmpty(securityNo)) {
                holder.imgHavaMedicalCard.setImageResource(R.drawable.icon_wrong);
            } else {
                holder.imgHavaMedicalCard.setImageResource(R.drawable.icon_right);
            }
            String role = list.get(position).getRelationShip();
            holder.tvFamilyName.setText(list.get(position).getRealName() + "（" + role + ")");
            if (TextUtils.equals("爷爷", role)) {
                holder.imgFamilyAvatar.setImageResource(R.drawable.icon_yeye);
            }
            if (TextUtils.equals("奶奶", role)) {
                holder.imgFamilyAvatar.setImageResource(R.drawable.icon_nainai);
            }
            if (TextUtils.equals("爸爸", role)) {
                holder.imgFamilyAvatar.setImageResource(R.drawable.icon_dad);
            }
            if (TextUtils.equals("妈妈", role)) {
                holder.imgFamilyAvatar.setImageResource(R.drawable.icon_mother);

            }
            if (TextUtils.equals("儿子", role)) {
                holder.imgFamilyAvatar.setImageResource(R.drawable.icon_son);
            }
            if (TextUtils.equals("女儿", role)) {
                holder.imgFamilyAvatar.setImageResource(R.drawable.icon_doctor);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.equals(from, "main")) {
                        activeFamilyDoctorSign(list.get(position).getId());
                    } else if (TextUtils.equals(from, "familyDoctor")) {

                    } else {
                        if (CameraProviderUtil.hasCamera()) {
                            saveInquiryOrder(list.get(position).getId() + "");
                        } else {
                            showTost("请安装摄像头");
                        }
                    }
                }
            });

        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {


            @Bind(R.id.img_family_avatar)
            ImageView imgFamilyAvatar;
            @Bind(R.id.tv_family_name)
            TextView tvFamilyName;
            @Bind(R.id.img_have_idcard)
            ImageView imgHaveIdcard;
            @Bind(R.id.img_hava_medical_card)
            ImageView imgHavaMedicalCard;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    /**
     * 4.1.36.	激活家庭医生签约记录接口(对接人:盖伟伟)
     *
     * @param id
     */
    private void activeFamilyDoctorSign(final int id) {
        showDialog();
        bindObservable(mAppClient.activeFamilyDoctorSign(id + ""), new Action1<ActiveFamlilyFileData>() {
            @Override
            public void call(ActiveFamlilyFileData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    Intent intent = new Intent(context, FamilyDoctorDetailActivity.class);
                    intent.putExtra("doctorId", data.getData().getDoctorId() + "");
                    intent.putExtra("patientId", id + "");
                    startActivity(intent);
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
     * 下单接口
     *
     * @param patientId
     */
    private void saveInquiryOrder(String patientId) {
        showDialog();
        bindObservable(mAppClient.saveWzOrder("0", patientId, hosDepId, doctorId), new Action1<SaveOrderData>() {
            @Override
            public void call(SaveOrderData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    Intent intent = new Intent(context, InquiryPayMentActivity.class);
                    intent.putExtra("orderId", data.getData().getOrderId() + "");
                    intent.putExtra("doctorId", doctorId);
                    startActivity(intent);
                    finish();
                } else {
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
}

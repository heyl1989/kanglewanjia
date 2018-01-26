package cn.v1.kanglewanjia.ui.inquiry_doctor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.DepartmentData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DialogActivity;
import cn.v1.kanglewanjia.ui.SelectFamilyFileActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import rx.functions.Action1;


public class InquiryDepartmentActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_select_department)
    LinearLayout llSelectDepartment;
    @Bind(R.id.tv_common_disease)
    TextView tvCommonDisease;
    @Bind(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @Bind(R.id.tv_slow_disease)
    TextView tvSlowDisease;
    @Bind(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @Bind(R.id.img_bg)
    ImageView imgBg;

    private String hospitalId = "";
    private CommonDiseaseAdapter commonDiseaseAdapter;
    private SlowDiseaseAdapter slowDiseaseAdapter;
    private List<DepartmentData.DataData.DepartsData> departsDatas = new ArrayList<>();
    private List<DepartmentData.DataData.DepartsCjbData> departsCjbDatas = new ArrayList<>();
    private List<DepartmentData.DataData.DepartsMxbData> departsMxbDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_department);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initData();
        initView();
        getHospitalDepartment();
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

    @OnClick(R.id.ll_select_department)
    public void onClick() {
        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(InquiryDepartmentActivity.this);
        imgBg.setImageBitmap(bmp);
        Intent intent = new Intent(context,AllDepartmentActivity.class);
        intent.putExtra("departsDatas", (Serializable) departsDatas);
        startActivity(intent);

    }

    private void initData() {
        if (getIntent().hasExtra("hospitalName")) {
            String hospitalName = getIntent().getStringExtra("hospitalName");
            tvTitle.setText(hospitalName);
        }
        if (getIntent().hasExtra("hospitalId")) {
            hospitalId = getIntent().getStringExtra("hospitalId");
        }
    }

    private void initView() {
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setFocusable(false);
        commonDiseaseAdapter = new CommonDiseaseAdapter();
        recyclerView1.setAdapter(commonDiseaseAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setFocusable(false);
        slowDiseaseAdapter = new SlowDiseaseAdapter();
        recyclerView2.setAdapter(slowDiseaseAdapter);
    }


    /**
     * 获取医院科室列表
     */
    private void getHospitalDepartment() {
        showDialog();
        bindObservable(mAppClient.getHospitalDepart(hospitalId), new Action1<DepartmentData>() {
            @Override
            public void call(DepartmentData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    departsDatas.addAll(data.getData().getDeparts());
                    departsCjbDatas.addAll(data.getData().getDepartsCjb());
                    commonDiseaseAdapter.notifyItemRangeChanged(0, departsCjbDatas.size());
                    departsMxbDatas.addAll(data.getData().getDepartsMxb());
                    slowDiseaseAdapter.notifyItemRangeChanged(0, departsMxbDatas.size());
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

    class CommonDiseaseAdapter extends RecyclerView.Adapter<CommonDiseaseAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_diease, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

//            holder.imgDisease.setImageResource(R.drawable.icon_busy);
            DepartmentData.DataData.DepartsCjbData departsCjbData = departsCjbDatas.get(position);
            holder.tvDeparementName.setText(departsCjbData.getDepartName()+"");
            holder.tvDiseases.setText(departsCjbData.getDescription()+"");
            if(TextUtils.equals("1",departsCjbData.getDepartStatus())){
                holder.imgDiseaseState.setImageResource(R.drawable.icon_offline);
                holder.tvLineNo.setText("当前科室暂无法问诊");
            }
            if(TextUtils.equals("0",departsCjbData.getDepartStatus())){
                if(TextUtils.equals("0",departsCjbData.getLineUpCount())){
                    holder.imgDiseaseState.setImageResource(R.drawable.icon_online);
                    holder.tvLineNo.setText("无人排队，立即问诊");
                }else{
                    holder.imgDiseaseState.setImageResource(R.drawable.icon_busy);
                    holder.tvLineNo.setText("当前排队人数：" + departsCjbData.getLineUpCount());
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.equals("1",departsCjbDatas.get(position).getDepartStatus())){
                        showTost("医生离线");
                    }else{
                        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(InquiryDepartmentActivity.this);
                        imgBg.setImageBitmap(bmp);
                        Intent intent = new Intent(context,SelectFamilyFileActivity.class);
                        intent.putExtra("hosDepId",departsCjbDatas.get(position).getHosDepId()+"");
                        intent.putExtra("doctorId",departsCjbDatas.get(position).getDoctorId()+"");
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return departsCjbDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_deparement_name)
            TextView tvDeparementName;
            @Bind(R.id.tv_diseases)
            TextView tvDiseases;
            @Bind(R.id.img_disease_state)
            ImageView imgDiseaseState;
            @Bind(R.id.tv_line_no)
            TextView tvLineNo;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    class SlowDiseaseAdapter extends RecyclerView.Adapter<SlowDiseaseAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_diease, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            holder.imgDisease.setImageResource(R.drawable.icon_offline);
            DepartmentData.DataData.DepartsMxbData departsMxbData = departsMxbDatas.get(position);
            holder.tvDeparementName.setText(departsMxbData.getDepartName()+"");
            holder.tvDiseases.setText(departsMxbData.getDescription()+"");
            if(TextUtils.equals("1",departsMxbData.getDepartStatus())){
                holder.imgDiseaseState.setImageResource(R.drawable.icon_offline);
                holder.tvLineNo.setText("当前科室暂无法问诊");
            }
            if(TextUtils.equals("0",departsMxbData.getDepartStatus())){
                if(TextUtils.equals("0",departsMxbData.getLineUpCount())){
                    holder.imgDiseaseState.setImageResource(R.drawable.icon_online);
                    holder.tvLineNo.setText("无人排队，立即问诊");
                }else{
                    holder.imgDiseaseState.setImageResource(R.drawable.icon_busy);
                    holder.tvLineNo.setText("当前排队人数：" + departsMxbData.getLineUpCount());
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.equals("1",departsMxbDatas.get(position).getDepartStatus())){
                        showTost("医生离线");
                    }else{
                        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(InquiryDepartmentActivity.this);
                        imgBg.setImageBitmap(bmp);
                        goNewActivity(SelectFamilyFileActivity.class);
                        Intent intent = new Intent(context,SelectFamilyFileActivity.class);
                        intent.putExtra("hosDepId",departsMxbDatas.get(position).getHosDepId()+"");
                        intent.putExtra("doctorId",departsMxbDatas.get(position).getDoctorId()+"");
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return departsMxbDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_deparement_name)
            TextView tvDeparementName;
            @Bind(R.id.tv_diseases)
            TextView tvDiseases;
            @Bind(R.id.img_disease_state)
            ImageView imgDiseaseState;
            @Bind(R.id.tv_line_no)
            TextView tvLineNo;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}

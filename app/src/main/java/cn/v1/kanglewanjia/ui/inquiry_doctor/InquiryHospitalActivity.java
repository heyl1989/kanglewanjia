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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.evilbinary.tv.widget.BorderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.HospitalListData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.LoginActivity;
import cn.v1.kanglewanjia.ui.SelectFamilyFileActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import cn.v1.kanglewanjia.util.GlideUtil;
import rx.functions.Action1;


public class InquiryHospitalActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.img_bg)
    ImageView imgBg;


    private BorderView border;
    private List<HospitalListData.DataData> list = new ArrayList<>();
    private Bitmap bmp;
    private HospitalAdapter hospitalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiryhospital);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initData() {
        bindObservable(mAppClient.getHospitalList(), new Action1<HospitalListData>() {
            @Override
            public void call(HospitalListData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    list.addAll(data.getData());
                    hospitalAdapter.notifyDataSetChanged();
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

    private void initView() {

        border = new BorderView(this);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        border.setBackgroundResource(0);
        border.attachTo(recyclerView);
        hospitalAdapter = new HospitalAdapter();
        recyclerView.setAdapter(hospitalAdapter);
    }

    class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_hospital, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Glide.with(context)
                    .load(list.get(position).getHospitalPic())
                    .override(380,520)
                    .placeholder(R.drawable.hospital_1)
                    .error(R.drawable.hospital_2)
                    .transform(new GlideUtil.GlideRoundTransform(context, 3))
                    .into(holder.imgHospitalPic);
            holder.tvHospitalName.setText(list.get(position).getHospitalName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isLogin()){
                        Intent intent = new Intent(context, InquiryDepartmentActivity.class);
                        intent.putExtra("hospitalName", list.get(position).getHospitalName());
                        intent.putExtra("hospitalId",list.get(position).getHospitalId()+"");
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(context,LoginActivity.class);
                        intent.putExtra("from","noMain");
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.img_hospital_pic)
            ImageView imgHospitalPic;
            @Bind(R.id.tv_hospital_name)
            TextView tvHospitalName;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

}

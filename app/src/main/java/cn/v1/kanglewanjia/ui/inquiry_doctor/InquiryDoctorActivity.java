package cn.v1.kanglewanjia.ui.inquiry_doctor;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.view.CustomRecycleView;

public class InquiryDoctorActivity extends BaseActivity {

    @Bind(R.id.tv_select_department)
    TextView tvSelectDepartment;
    @Bind(R.id.recyclerView)
    CustomRecycleView recyclerView;
    @Bind(R.id.rl_parent)
    RelativeLayout rlParent;
    @Bind(R.id.tv_focuesd_item)
    TextView tvFocuesdItem;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private LinearLayoutManager layoutManager;
    private BorderView border;
    private InquiryDoctorAdapter familyDoctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_doctor);
        ButterKnife.bind(this);
        initData();
        initView();
//        tvTitle.setFocusable(true);
//        tvTitle.requestFocus();
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        tvTitle.requestFocus();
//        familyDoctorAdapter.notifyDataSetChanged();
//        //tvTitle.requestFocus();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        border.detach();
//    }

    @OnClick(R.id.tv_select_department)
    public void onClick() {
        showTost("选择部门");
    }


    private void initData() {
        if(getIntent().hasExtra("department")){
            String department = getIntent().getStringExtra("department");
            tvTitle.setText(department);
        }
    }


    private void initView() {
        tvFocuesdItem.bringToFront();
        border = new BorderView(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        border.attachTo(recyclerView);
//        border.setBackgroundResource(R.drawable.border_green_light);
        familyDoctorAdapter = new InquiryDoctorAdapter();
        recyclerView.setAdapter(familyDoctorAdapter);
//        recyclerView.addOnScrollListener(mOnScrollListener);
        familyDoctorAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
//                linearLayoutManager.scrollToPositionWithOffset(position,350);
                recyclerView.smoothToCenter(position);
            }
        });
        recyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("abc", "hasfocus:" + hasFocus);
                if (hasFocus) {
                    if (recyclerView.getChildCount() > 0) {
                        layoutManager.scrollToPositionWithOffset(0, 0);
                        recyclerView.getChildAt(0).requestFocus();
                    }
                }
            }
        });

    }

    class InquiryDoctorAdapter extends RecyclerView.Adapter<InquiryDoctorAdapter.ViewHolder> {

        private OnItemSelectListener mSelectListener;
        private int currentPosition;
        private int[] doctorAvatorList = new int[]{R.drawable.img_doctor_avator1, R.drawable.img_doctor_avator2,
                R.drawable.img_doctor_avator3, R.drawable.img_doctor_avator4, R.drawable.img_doctor_avator5, R.drawable.img_doctor_avator1,
                R.drawable.img_doctor_avator2, R.drawable.img_doctor_avator3, R.drawable.img_doctor_avator4, R.drawable.img_doctor_avator5,
                R.drawable.img_doctor_avator1,};

        public void setOnItemSelectListener(OnItemSelectListener listener) {
            mSelectListener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_inquiry_doctor, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.imgDoctorAvatar.setImageResource(doctorAvatorList[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goNewActivity(InquiryDoctorDetailActivity.class);
                }
            });
            holder.itemView.setTag(position);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.i("adapter", "hasfocus:" + position + "--" + hasFocus);
                    if (hasFocus) {
                        tvFocuesdItem.setText("(" + (position + 1) + "/" + getItemCount() + ")");
                        currentPosition = (int) holder.itemView.getTag();
                        mSelectListener.onItemSelect(holder.itemView, currentPosition);
                    }
                }
            });

        }


        @Override
        public int getItemCount() {
            return 11;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.img_doctor_avatar)
            ImageView imgDoctorAvatar;
            @Bind(R.id.tv_doctor_name)
            TextView tvDoctorName;
            @Bind(R.id.tv_inquiry)
            TextView tvInquiry;
            @Bind(R.id.tv_department)
            TextView tvDepartment;
            @Bind(R.id.tv_hospital)
            TextView tvHospital;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    public interface OnItemSelectListener {
        void onItemSelect(View view, int position);
    }
}

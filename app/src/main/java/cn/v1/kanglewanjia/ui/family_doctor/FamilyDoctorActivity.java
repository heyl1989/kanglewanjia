package cn.v1.kanglewanjia.ui.family_doctor;

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
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.view.CustomRecycleView;

public class FamilyDoctorActivity extends BaseActivity {


    @Bind(R.id.recyclerView)
    CustomRecycleView recyclerView;
    @Bind(R.id.rl_parent)
    RelativeLayout rlParent;
    @Bind(R.id.tv_focuesd_item)
    TextView tvFocuesdItem;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_doctor);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        BorderView border = new BorderView(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        border.attachTo(recyclerView);
//        border.setBackgroundResource(R.drawable.border_highlight);
        FamilyDoctorAdapter familyDoctorAdapter = new FamilyDoctorAdapter();
        recyclerView.setAdapter(familyDoctorAdapter);
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

    class FamilyDoctorAdapter extends RecyclerView.Adapter<FamilyDoctorAdapter.ViewHolder> {
        private OnItemSelectListener mSelectListener;
        private int currentPosition;

        public void setOnItemSelectListener(OnItemSelectListener listener) {
            mSelectListener = listener;
        }

        @Override
        public int getItemViewType(int position) {

            return super.getItemViewType(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_family_doctor, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder,final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == getItemCount() - 1){

                    }else{
                        goNewActivity(FamilyDoctorDetailActivity.class);
                    }
                }
            });
            holder.itemView.setTag(position);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.i("adapter", "hasfocus:" + position + "--" + hasFocus);
                    if (hasFocus) {
                        tvFocuesdItem.setText("(" + (position + 1 ) + "/" + getItemCount() + ")");
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

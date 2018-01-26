package cn.v1.kanglewanjia.ui.family_doctor_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.DoctorRecordData;
import cn.v1.kanglewanjia.model.PatientListData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import rx.functions.Action1;


public class PersonalFamilyDoctorRecordActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.patient_recycleview)
    RecyclerView patientRecycleview;

    private DoctorRecordAdapter doctorRecordAdapter;
    private List<String> patientList = new ArrayList<>();
    List<DoctorRecordData.Record> recordList = new ArrayList<DoctorRecordData.Record>();
    private String focusPatient = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_family_doctor_record);
        ButterKnife.bind(this);
        initView();
        getPatientList();
    }


    private void initView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        doctorRecordAdapter = new DoctorRecordAdapter();
        recyclerView.setAdapter(doctorRecordAdapter);
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                DoctorRecordData.Record record = new DoctorRecordData.Record();
                record.setImgId(R.drawable.img_video_record);
                record.setTag(0);
                recordList.add(record);
            } else {
                DoctorRecordData.Record record = new DoctorRecordData.Record();
                record.setImgId(R.drawable.img_message_record);
                record.setTag(1);
                recordList.add(record);
            }
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
                    List<PatientListData.DataData> list = data.getData();
                    patientList.add("全部");
                    for (int i = 0; i < list.size(); i++) {
                        patientList.add(list.get(i).getRealName());
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    patientRecycleview.setLayoutManager(linearLayoutManager);
                    patientRecycleview.setFocusable(false);
                    PatientListDataAdapter patientListDataAdapter = new PatientListDataAdapter();
                    patientRecycleview.setAdapter(patientListDataAdapter);
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
     * 患者列表
     */
    class PatientListDataAdapter extends RecyclerView.Adapter<PatientListDataAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_patient_name, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tvName.setText(patientList.get(position) + "");
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        focusPatient = patientList.get(position);
                        if(position == 0){
                            doctorRecordAdapter.setItemCount(4);
                        }else{
                            doctorRecordAdapter.setItemCount(1);
                        }
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return patientList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_name)
            TextView tvName;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    /**
     * 医生回复列表
     */
    class DoctorRecordAdapter extends RecyclerView.Adapter<DoctorRecordAdapter.ViewHolder> {


        private int itemCount;

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
            notifyDataSetChanged();
        }

        private List<DoctorRecordData.Record> recordList = new ArrayList<>();

        public void setDatas(List<DoctorRecordData.Record> recordList) {
            this.recordList = recordList;
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_record, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.imgContent.setImageResource(R.drawable.img_message_record);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,PersonalMessageRecordActivity.class);
                    intent.putExtra("focusPatient",focusPatient);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return itemCount;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_content)
            ImageView imgContent;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

}

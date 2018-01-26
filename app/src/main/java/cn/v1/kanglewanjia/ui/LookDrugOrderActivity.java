package cn.v1.kanglewanjia.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.PatientDrugData;
import cn.v1.kanglewanjia.view.ScrollListView;

import static android.text.Html.fromHtml;

public class LookDrugOrderActivity extends BaseActivity {

    @Bind(R.id.tv_drug_number)
    TextView tvDrugNumber;
    @Bind(R.id.tv_drug_department)
    TextView tvDrugDepartment;
    @Bind(R.id.tv_patient_info)
    TextView tvPatientInfo;
    @Bind(R.id.tv_symptom)
    TextView tvSymptom;
    @Bind(R.id.tv_supplement)
    TextView tvSupplement;
    @Bind(R.id.tv_pay_fee)
    TextView tvPayFee;
    @Bind(R.id.tv_drug_date)
    TextView tvDrugDate;
    @Bind(R.id.lv_drug)
    ScrollListView lvDrug;
    @Bind(R.id.tv_doctor)
    TextView tvDoctor;

    private List<PatientDrugData.DataData.MedicineListData> drugDatas = new ArrayList<>();
    private MedicineAdapter medicineAdapter;
    private PatientDrugData.DataData drugInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_drug_order);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        medicineAdapter = new MedicineAdapter();
        lvDrug.setFocusable(false);
        lvDrug.setAdapter(medicineAdapter);
    }

    private void initData() {
        if (getIntent().hasExtra("drugInfo")) {
            drugInfo = (PatientDrugData.DataData) getIntent().getSerializableExtra("drugInfo");
        }
        initPrescription();
    }

    /**
     * 详情
     */
    @SuppressLint("SetTextI18n")
    private void initPrescription() {
        tvDrugNumber.setText("NO." + drugInfo.getPrescriptionId());
        tvDrugDepartment.setText("科室：" + drugInfo.getDepartMent());
        tvPatientInfo.setText("姓名：" + drugInfo.getPatientName() +
                "    性别：" + drugInfo.getPatientSex() +
                "    年龄：" + drugInfo.getPatientAge());
        tvSymptom.setText("临床诊断：" + drugInfo.getDiagnosisResult());
        tvSupplement.setText("补充说明：" + "\n" + drugInfo.getAddExplain());
        tvDrugDate.setText("日期：" + drugInfo.getPrescriptionDate());
        tvDoctor.setText("医师：" + drugInfo.getDoctorName());
        drugDatas.addAll(drugInfo.getMedicineList());
        double totalPrice = 0;
        for (int i = 0; i < drugDatas.size(); i++) {
            totalPrice += drugDatas.get(i).getPrice();
        }
        String needPay = "药费（" + drugInfo.getFeeType() + "）: " + "<font color='#fc8224'>¥" + totalPrice + "</font>";
        tvPayFee.setText(fromHtml(needPay));
        medicineAdapter.notifyDataSetChanged();
    }


    class MedicineAdapter extends BaseAdapter {

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_drug_info, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvDrugName.setText((position + 1) + "." + drugDatas.get(position).getName()
                    + " X " + drugDatas.get(position).getNum());
            holder.tvDrugPrice.setText("¥：" + drugDatas.get(position).getPrice());
            holder.tvDrugSpec.setText(drugDatas.get(position).getSpecifications() + "");
            holder.tvDrugUse.setText(drugDatas.get(position).getUsageAndDosage() + "");


            return convertView;
        }

        @Override
        public int getCount() {
            return drugDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            @Bind(R.id.tv_drug_name)
            TextView tvDrugName;
            @Bind(R.id.tv_drug_price)
            TextView tvDrugPrice;
            @Bind(R.id.tv_drug_spec)
            TextView tvDrugSpec;
            @Bind(R.id.tv_drug_use)
            TextView tvDrugUse;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}

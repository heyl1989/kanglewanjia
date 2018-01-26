package cn.v1.kanglewanjia.ui.drug_order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.AddressListData;
import cn.v1.kanglewanjia.model.InquiryOrderDetailData;
import cn.v1.kanglewanjia.model.PatientDrugData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.PayMentActivity;
import cn.v1.kanglewanjia.ui.address_manage.AdressListActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.view.ScrollListView;
import rx.functions.Action1;


public class DrugFillInfoActivity extends BaseActivity {

    @Bind(R.id.tv_receiver)
    TextView tvReceiver;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_select_address)
    TextView tvSelectAddress;
    @Bind(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    @Bind(R.id.tv_pay_fee)
    TextView tvPayFee;
    @Bind(R.id.tv_tip_fee)
    TextView tvTipFee;
    @Bind(R.id.tv_confirm_pay)
    TextView tvConfirmPay;
    @Bind(R.id.img_yihu)
    ImageView imgYihu;
    @Bind(R.id.img_dingdang)
    ImageView imgDingdang;
    @Bind(R.id.lv_drug)
    ScrollListView lvDrug;

    private String orderType = "";
    private String orderId = "";
    private List<PatientDrugData.DataData.MedicineListData> drugList = new ArrayList<>();
    private MedicineAdapter medicineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_fill_info);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick({R.id.ll_select_address, R.id.tv_confirm_pay, R.id.img_yihu, R.id.img_dingdang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_yihu:
                imgYihu.setImageResource(R.drawable.icon_select);
                imgDingdang.setImageResource(R.drawable.selector_circle_right);
                break;
            case R.id.img_dingdang:
                imgYihu.setImageResource(R.drawable.selector_circle_right);
                imgDingdang.setImageResource(R.drawable.icon_select);
                break;
            case R.id.ll_select_address:
                Intent intent = new Intent(context, AdressListActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.tv_confirm_pay:
                Intent intent2 = new Intent(context, PayMentActivity.class);
                intent2.putExtra("from", "drug");
                startActivity(intent2);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 100) {
            if (data.hasExtra("address")) {
                AddressListData.DataData addressData = (AddressListData.DataData) data.getSerializableExtra("address");
                tvReceiver.setText("收  货 人        " + addressData.getUserRealName() + "");
                tvPhone.setText("联系电话        " + addressData.getMobile() + "");
                tvAddress.setText("收货地址        " + addressData.getDetailAddress() + "");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        medicineAdapter = new MedicineAdapter();
        lvDrug.setFocusable(false);
        lvDrug.setAdapter(medicineAdapter);
    }

    private void initData() {

        if (getIntent().hasExtra("orderType")) {
            orderType = getIntent().getStringExtra("orderType");
        }
        if (getIntent().hasExtra("orderId")) {
            orderId = getIntent().getStringExtra("orderId");
        }
        if (getIntent().hasExtra("drugList")) {
            drugList = (List<PatientDrugData.DataData.MedicineListData>) getIntent().getSerializableExtra("drugList");
            medicineAdapter.notifyDataSetChanged();
        }
        getOrderDetail();
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
                    tvReceiver.setText("收  货 人        " + data.getData().getPatientRealName());
                    tvPhone.setText("联系电话        " + (String) SPUtil.get(context, Common.USER_PHONE_NUMBER, ""));
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

            holder.tvDrugName.setText((position + 1) + "." + drugList.get(position).getName()
                    + " X " + drugList.get(position).getNum());
            holder.tvDrugPrice.setText("¥：" + drugList.get(position).getPrice());
            holder.tvDrugSpec.setText(drugList.get(position).getSpecifications() + "");
            holder.tvDrugUse.setText(drugList.get(position).getUsageAndDosage() + "");


            return convertView;
        }

        @Override
        public int getCount() {
            return drugList.size();
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

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.InquiryOrderDetailData;
import cn.v1.kanglewanjia.model.PatientDrugData;
import cn.v1.kanglewanjia.ui.drug_order.DrugActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryDepartmentActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryDoctorActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryDoctorDetailActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryHospitalActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryPayMentActivity;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import rx.functions.Action1;


public class EvaluateActivity extends BaseActivity {

    @Bind(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @Bind(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @Bind(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @Bind(R.id.tv_confirm_evaluate)
    TextView tvConfirmEvaluate;
    @Bind(R.id.tv_cancel_evaluate)
    TextView tvCancelEvaluate;


    private boolean[] speciality = new boolean[5];
    private boolean[] service = new boolean[5];
    private boolean[] timely = new boolean[5];
    private final int CONFIRM_EVALUATE = 100;
    private final int CANCEL_EVALUATE = 200;
    private View focusedView;
    private String orderId = "";
    private String orderType = "";
    private String doctorId;
    private List<PatientDrugData.DataData.MedicineListData> drugDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        initView();
        initData();
        closeOtherActivity();
    }

    @Override
    public void onBackPressed() {
    }

    @OnClick({R.id.tv_confirm_evaluate, R.id.tv_cancel_evaluate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_evaluate:
                getOrderDetail(CONFIRM_EVALUATE);
                break;
            case R.id.tv_cancel_evaluate:
                getOrderDetail(CANCEL_EVALUATE);
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
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
    }

    private void closeOtherActivity() {
        ActivityManagerUtil.getInstance().finishActivityclass(InquiryHospitalActivity.class);
        ActivityManagerUtil.getInstance().finishActivityclass(InquiryDepartmentActivity.class);
        ActivityManagerUtil.getInstance().finishActivityclass(InquiryDoctorActivity.class);
        ActivityManagerUtil.getInstance().finishActivityclass(InquiryDoctorDetailActivity.class);
        ActivityManagerUtil.getInstance().finishActivityclass(InquiryPayMentActivity.class);
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail(final int type) {
        showDialog();
        bindObservable(mAppClient.getOrderDetail(orderId, orderType), new Action1<InquiryOrderDetailData>() {
            @Override
            public void call(InquiryOrderDetailData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    //订单状态：0-初始；1-待服务；2-已服务；3-已取消
                    String orderState = data.getData().getOrderStatus() + "";
                    if (TextUtils.equals("2", orderState)) {
                        getPrescriptionInfo(type);
                    } else {
                        if (CONFIRM_EVALUATE == type) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("orderType", orderType);
                            startActivity(intent);
                            showTost("评价成功");
                            finish();
                        }
                        if (CANCEL_EVALUATE == type) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("orderType", orderType);
                            startActivity(intent);
                            finish();
                        }
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
    private void getPrescriptionInfo(final int type) {
        showDialog();
        bindObservable(mAppClient.getPrescriptionInfo(orderId, doctorId), new Action1<PatientDrugData>() {
            @Override
            public void call(PatientDrugData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    if (null != data.getData() && null != data.getData().getMedicineList() && data.getData().getMedicineList().size() != 0) {
                        drugDatas.addAll(data.getData().getMedicineList());
                        if (CONFIRM_EVALUATE == type) {
                            Intent intent1 = new Intent(context, DrugActivity.class);
                            intent1.putExtra("from", "call");
                            intent1.putExtra("orderId", orderId);
                            intent1.putExtra("orderType", orderType);
                            intent1.putExtra("drugList", (Serializable) drugDatas);
                            startActivity(intent1);
                            showTost("评价成功");
                            finish();
                        }
                        if (CANCEL_EVALUATE == type) {
                            Intent intent2 = new Intent(context, DrugActivity.class);
                            intent2.putExtra("from", "call");
                            intent2.putExtra("orderId", orderId);
                            intent2.putExtra("orderType", orderType);
                            intent2.putExtra("drugList", (Serializable) drugDatas);
                            startActivity(intent2);
                            finish();
                        }
                    }else{
                        if (CONFIRM_EVALUATE == type) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("orderType", orderType);
                            startActivity(intent);
                            showTost("评价成功");
                            finish();
                        }
                        if (CANCEL_EVALUATE == type) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("orderType", orderType);
                            startActivity(intent);
                            finish();
                        }
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

    private void initView() {

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setFocusable(false);
        EvaluateAdapter evaluateAdapter1 = new EvaluateAdapter();
        recyclerView1.setAdapter(evaluateAdapter1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setFocusable(false);
        EvaluateAdapter evaluateAdapter2 = new EvaluateAdapter();
        recyclerView2.setAdapter(evaluateAdapter2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setFocusable(false);
        EvaluateAdapter evaluateAdapter3 = new EvaluateAdapter();
        recyclerView3.setAdapter(evaluateAdapter3);
    }

    class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {

        private boolean[] list = new boolean[5];

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_evaluate_star, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (list[position]) {
                holder.imgStar.setImageResource(R.drawable.icon_star_selected);
            } else {
                holder.imgStar.setImageResource(R.drawable.icon_star);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list.length; i++) {
                        if (i < position + 1) {
                            list[i] = true;
                        } else {
                            list[i] = false;
                        }
                    }
                    for (int i = 0; i < list.length; i++) {
                        notifyItemChanged(i);
                    }
//                    notifyItemRangeChanged(0,5);
//                    new Handler().postDelayed(new Runnable(){
//                        public void run(){
//                            focusedView.setFocusable(true);
//                            focusedView.requestFocus();
//                        }
//                    },500);
                }
            });
//            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if(hasFocus){
//                        focusedView = v;
//                    }
//                }
//            });
        }


        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_star)
            ImageView imgStar;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}

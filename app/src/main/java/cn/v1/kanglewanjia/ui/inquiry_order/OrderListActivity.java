package cn.v1.kanglewanjia.ui.inquiry_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.InquiryOrderListData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.view.CustomRecycleView;
import rx.functions.Action1;

public class OrderListActivity extends BaseActivity {

    @Bind(R.id.tv_all_orders)
    TextView tvAllOrders;
    @Bind(R.id.tv_serviced_order)
    TextView tvServicedOrder;
    @Bind(R.id.tv_canceled_order)
    TextView tvCanceledOrder;
    @Bind(R.id.recyclerView)
    CustomRecycleView recyclerView;
    @Bind(R.id.tv_focuesd_item)
    TextView tvFocuesdItem;


    private LinearLayoutManager layoutManager;
    private OrderListAdapter orderListAdapter;
    private List<InquiryOrderListData.DataData.ListData> inquiryOrderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ActivityManagerUtil.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initView() {

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
//        border.setBackgroundResource(R.drawable.border_highlight);
        orderListAdapter = new OrderListAdapter();
        recyclerView.setAdapter(orderListAdapter);
//        recyclerView.addOnScrollListener(mOnScrollListener);
        orderListAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
//                linearLayoutManager.scrollToPositionWithOffset(position,350);
                recyclerView.smoothToCenter(position);
            }
        });
        recyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (recyclerView.getChildCount() > 0) {
                        layoutManager.scrollToPositionWithOffset(0, 0);
                        recyclerView.getChildAt(0).requestFocus();
                    }
                }
            }
        });


        tvAllOrders.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中全部了");
                    getOrderList("-1");
                    tvFocuesdItem.setText("(1/6)");

                }
            }
        });
        tvServicedOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中已服务订单了");
                    getOrderList("2");
                    tvFocuesdItem.setText("(1/6)");

                }
            }
        });
        tvCanceledOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中取消订单了");
                    getOrderList("3");
                }
            }
        });
    }


    /**
     * 获取订单列表
     */
    private void getOrderList(String states) {
        showDialog();
        bindObservable(mAppClient.getOrderList(states), new Action1<InquiryOrderListData>() {
            @Override
            public void call(InquiryOrderListData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    inquiryOrderList.clear();
                    inquiryOrderList.addAll(data.getData().getList());
                    orderListAdapter.setDatas(inquiryOrderList);
                    tvFocuesdItem.setText("(0/" + inquiryOrderList.size() + ")");
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

    class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


        private OnItemSelectListener mSelectListener;
        private int currentPosition;

        public void setOnItemSelectListener(OnItemSelectListener listener) {
            mSelectListener = listener;
        }

        private List<InquiryOrderListData.DataData.ListData> datas = new ArrayList<>();

        public void setDatas(List<InquiryOrderListData.DataData.ListData> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            InquiryOrderListData.DataData.ListData data = datas.get(position);
            holder.tvOrderId.setText("订单ID：" + data.getOrderId());
            //订单状态：0-初始；1-待服务；2-已服务；3-已取消
            String orderState = data.getOrderStatus();
            if (TextUtils.equals("0", orderState)) {
                holder.tvOrderState.setText("状态：待付款");
            }
            if (TextUtils.equals("1", orderState)) {
                holder.tvOrderState.setText("状态：待服务");
            }
            if (TextUtils.equals("2", orderState)) {
                holder.tvOrderState.setText("状态：已服务");
            }
            if (TextUtils.equals("3", orderState)) {
                holder.tvOrderState.setText("状态：已取消");
            }
            holder.tvPatient.setText("患者：" + data.getPatientRealName());
            holder.tvDoctor.setText("医生：" + data.getDoctorName());
            holder.tvDepartment.setText("科室：" + data.getDepartName());
            holder.tvHospital.setText("医院：" + data.getHospitalName());
            holder.tvDate.setText("时间：" + data.getCreateTime());
            //"订单类型：0-普通问诊；1-家医问诊",
            if (TextUtils.equals("0", data.getOrderType())) {
                holder.tvOrderType.setText("类型：普通问诊");
                holder.tvOrderPrice.setText("订单金额：" + data.getPrice());
                holder.tvMedicalPrice.setText("医保金额：" + data.getMedicalInsuranceDeductibleAmount());
                holder.tvNeedPay.setText("实付金额：" + data.getRealPrice());
                holder.tvOrderPrice.setVisibility(View.VISIBLE);
                holder.tvMedicalPrice.setVisibility(View.VISIBLE);
                holder.tvNeedPay.setVisibility(View.VISIBLE);
            }
            if (TextUtils.equals("1", data.getOrderType())) {
                holder.tvOrderType.setText("类型：家医问诊");
                holder.tvOrderPrice.setVisibility(View.GONE);
                holder.tvMedicalPrice.setVisibility(View.GONE);
                holder.tvNeedPay.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,OrderDetailActivity.class);
                    intent.putExtra("orderId",datas.get(position).getOrderId()+"");
                    intent.putExtra("orderType",datas.get(position).getOrderType()+"");
                    startActivity(intent);
                }
            });
            holder.itemView.setTag(position);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
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
            return datas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_order_id)
            TextView tvOrderId;
            @Bind(R.id.tv_order_state)
            TextView tvOrderState;
            @Bind(R.id.tv_order_type)
            TextView tvOrderType;
            @Bind(R.id.tv_patient)
            TextView tvPatient;
            @Bind(R.id.tv_doctor)
            TextView tvDoctor;
            @Bind(R.id.tv_department)
            TextView tvDepartment;
            @Bind(R.id.tv_hospital)
            TextView tvHospital;
            @Bind(R.id.tv_date)
            TextView tvDate;
            @Bind(R.id.tv_order_price)
            TextView tvOrderPrice;
            @Bind(R.id.tv_medical_price)
            TextView tvMedicalPrice;
            @Bind(R.id.tv_need_pay)
            TextView tvNeedPay;

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

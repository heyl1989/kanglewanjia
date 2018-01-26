package cn.v1.kanglewanjia.ui.test_order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.view.CustomRecycleView;


public class MedicalTestOrderListActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    CustomRecycleView recyclerView;
    @Bind(R.id.tv_focuesd_item)
    TextView tvFocuesdItem;
    @Bind(R.id.tv_all_orders)
    TextView tvAllOrders;
    @Bind(R.id.tv_wait_pay)
    TextView tvWaitPay;
    @Bind(R.id.tv_wait_sign)
    TextView tvWaitSign;
    @Bind(R.id.tv_wait_service)
    TextView tvWaitService;
    @Bind(R.id.tv_serviced_order)
    TextView tvServicedOrder;
    @Bind(R.id.tv_canceled_order)
    TextView tvCanceledOrder;

    private LinearLayoutManager layoutManager;
    private MedicalTestOrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_test_order_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
//        border.setBackgroundResource(R.drawable.border_highlight);
        orderListAdapter = new MedicalTestOrderListAdapter();
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
                    orderListAdapter.setItemCount(6);
                    tvFocuesdItem.setText("(1/6)");

                }
            }
        });
        tvWaitPay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中待支付订单了");
                    orderListAdapter.setItemCount(0);
                    tvFocuesdItem.setText("(0/0)");

                }
            }
        });
        tvWaitSign.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中待签约订单了");
                    orderListAdapter.setItemCount(0);
                    tvFocuesdItem.setText("(0/0)");

                }
            }
        });
        tvWaitService.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中带服务订单了");
                    orderListAdapter.setItemCount(0);
                    tvFocuesdItem.setText("(0/0)");

                }
            }
        });
        tvServicedOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中已服务订单了");
                    orderListAdapter.setItemCount(6);
                    tvFocuesdItem.setText("(1/6)");

                }
            }
        });
        tvCanceledOrder.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showTost("选中取消订单了");
                    orderListAdapter.setItemCount(0);
                    tvFocuesdItem.setText("(0/0)");

                }
            }
        });
    }

    class MedicalTestOrderListAdapter extends RecyclerView.Adapter<MedicalTestOrderListAdapter.ViewHolder> {


        private OnItemSelectListener mSelectListener;
        private int currentPosition;

        public void setOnItemSelectListener(OnItemSelectListener listener) {
            mSelectListener = listener;
        }

        private int itemCount;

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
            notifyDataSetChanged();
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_medical_test_order, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goNewActivity(MedicalTestOrderDetailActivity.class);
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
            return itemCount;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_order_id)
            TextView tvOrderId;
            @Bind(R.id.tv_order_state)
            TextView tvOrderState;
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

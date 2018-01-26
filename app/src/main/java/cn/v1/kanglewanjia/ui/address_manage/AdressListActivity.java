package cn.v1.kanglewanjia.ui.address_manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.AddressListData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import rx.functions.Action1;


public class AdressListActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private GridLayoutManager layoutManager;
    private LinearLayout oldFocusView;
    private AddressListAdapter addressListAdapter;
    private List<AddressListData.DataData> addressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    private void initView() {
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        addressListAdapter = new AddressListAdapter();
        recyclerView.setAdapter(addressListAdapter);
    }

    /**
     * 获取地址列表
     */
    private void getAddressList(){
        showDialog();
        bindObservable(mAppClient.getConsigneeAddressList(), new Action1<AddressListData>() {
            @Override
            public void call(AddressListData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    addressList.clear();
                    addressList.addAll(data.getData());
                    addressList.add(new AddressListData.DataData());
                    addressListAdapter.notifyDataSetChanged();
                }else{
                    showTost(data.getMessage()+"");
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


    class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_address_list, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (position == getItemCount() - 1) {
                //添加
                holder.imgAdd.setVisibility(View.VISIBLE);
                holder.llContent.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        goNewActivity(AddressEditActivity.class);
                    }
                });
            } else {
                holder.imgAdd.setVisibility(View.GONE);
                holder.llContent.setVisibility(View.VISIBLE);
                holder.tvNamePhone.setText(addressList.get(position).getUserRealName() +
                        "    " + addressList.get(position).getMobile());
                holder.tvAddress.setText(addressList.get(position).getDetailAddress()+"");
                holder.tvLook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showTost("使用");
                        holder.llButton.setVisibility(View.GONE);
                        setResult(100,new Intent().putExtra("address",(Serializable) addressList.get(position)));
                        finish();
                    }
                });
                holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showTost("编辑");
                        holder.llButton.setVisibility(View.GONE);
                        Intent intent = new Intent(context,AddressEditActivity.class);
                        intent.putExtra("editAddress",(Serializable) addressList.get(position));
                        startActivity(intent);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        holder.llButton.setVisibility(View.VISIBLE);
                        oldFocusView = holder.llButton;
                        holder.tvLook.requestFocus();
                    }
                });
            }
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (null != oldFocusView) {
                            oldFocusView.setVisibility(View.GONE);
                        }
                    }
                }
            });

        }


        @Override
        public int getItemCount() {
            return addressList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_add)
            ImageView imgAdd;
            @Bind(R.id.tv_name_phone)
            TextView tvNamePhone;
            @Bind(R.id.tv_address)
            TextView tvAddress;
            @Bind(R.id.ll_content)
            LinearLayout llContent;
            @Bind(R.id.tv_look)
            TextView tvLook;
            @Bind(R.id.tv_edit)
            TextView tvEdit;
            @Bind(R.id.ll_button)
            LinearLayout llButton;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}

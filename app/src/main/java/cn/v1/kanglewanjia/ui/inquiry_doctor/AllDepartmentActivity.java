package cn.v1.kanglewanjia.ui.inquiry_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.DepartmentData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DialogActivity;
import cn.v1.kanglewanjia.ui.SelectFamilyFileActivity;


public class AllDepartmentActivity extends BaseActivity {


    @Bind(R.id.gridView)
    GridView gridView;

    private List<DepartmentData.DataData.DepartsData> departsDatas = new ArrayList<>();
    private DepartmentAdapter departmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_department);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        if (getIntent().hasExtra("departsDatas")) {
            departsDatas = (List<DepartmentData.DataData.DepartsData>) getIntent().getSerializableExtra("departsDatas");
            departmentAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {

        departmentAdapter = new DepartmentAdapter();
        gridView.setFocusable(false);
        gridView.setAdapter(departmentAdapter);
    }

    class DepartmentAdapter extends BaseAdapter {

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_department, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final DepartmentData.DataData.DepartsData datas = departsDatas.get(position);
            holder.tvItem.setText(datas.getDepartName() + "");
            if (TextUtils.equals("1", datas.getDepartStatus())) {
                holder.imgState.setImageResource(R.drawable.icon_offline);
            }
            if (TextUtils.equals("0", datas.getDepartStatus())) {
                if (TextUtils.equals("0", datas.getLineUpCount())) {
                    holder.imgState.setImageResource(R.drawable.icon_online);
                } else {
                    holder.imgState.setImageResource(R.drawable.icon_busy);
                }
            }
            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.equals("1", departsDatas.get(position).getDepartStatus())) {
                        showTost("医生离线");
                    } else {
                        Intent intent = new Intent(context, SelectFamilyFileActivity.class);
                        intent.putExtra("hosDepId", departsDatas.get(position).getHosDepId() + "");
                        intent.putExtra("doctorId",departsDatas.get(position).getDoctorId()+"");
                        startActivity(intent);
                        finish();
                    }
                }
            });
            return convertView;
        }

        @Override
        public int getCount() {
            return departsDatas.size();
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
            @Bind(R.id.img_state)
            ImageView imgState;
            @Bind(R.id.tv_item)
            TextView tvItem;
            @Bind(R.id.ll_item)
            LinearLayout llItem;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}

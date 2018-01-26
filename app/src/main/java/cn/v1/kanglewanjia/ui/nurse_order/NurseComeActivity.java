package cn.v1.kanglewanjia.ui.nurse_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;


public class NurseComeActivity extends BaseActivity {

    @Bind(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @Bind(R.id.tv_more1)
    TextView tvMore1;
    @Bind(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @Bind(R.id.tv_more2)
    TextView tvMore2;
    @Bind(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @Bind(R.id.tv_more3)
    TextView tvMore3;

    private String[] nurseCome = new String[]{"留置胃管", "上门输液", "上门换药", "压疮护理"
            , "静脉采血", "吸痰", "导尿"};
    private String[] muYin = new String[]{"新生儿基础护理套餐", "新生儿院外护理套餐", "新生儿居家护理套餐",
            "产后开奶"};
    private String[] anMo = new String[]{"中医推拿", "精英足疗", "全息足疗", "足疗推拿套餐"
            , "中式保健", "全身推拿"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursse_come);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick({R.id.tv_more1, R.id.tv_more2, R.id.tv_more3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more1:
                Intent intent1 = new Intent(context,MoreServiceActivity.class);
                intent1.putExtra("title","护士上门");
                startActivity(intent1);
                break;
            case R.id.tv_more2:
                Intent intent2 = new Intent(context,MoreServiceActivity.class);
                intent2.putExtra("title","产后母婴");
                startActivity(intent2);
                break;
            case R.id.tv_more3:
                Intent intent3 = new Intent(context,MoreServiceActivity.class);
                intent3.putExtra("title","推拿按摩");
                startActivity(intent3);
                break;
        }
    }

    private void initView() {

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setFocusable(false);
        NurseComeAdapter nurseComeAdapter1 = new NurseComeAdapter(nurseCome);
        recyclerView1.setAdapter(nurseComeAdapter1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setFocusable(false);
        NurseComeAdapter nurseComeAdapter2 = new NurseComeAdapter(muYin);
        recyclerView2.setAdapter(nurseComeAdapter2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setFocusable(false);
        NurseComeAdapter nurseComeAdapter3 = new NurseComeAdapter(anMo);
        recyclerView3.setAdapter(nurseComeAdapter3);
    }

    class NurseComeAdapter extends RecyclerView.Adapter<NurseComeAdapter.ViewHolder> {

        private String[] list = new String[]{};

        public NurseComeAdapter(String[] list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_nurse_come, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tvItem.setText(list[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,NurseComeServiceIntroActivity.class);
                    intent.putExtra("title",list[position]);
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return list.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item)
            TextView tvItem;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}

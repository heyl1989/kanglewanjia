package cn.v1.kanglewanjia.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.inquiry_order.OrderDetailActivity;


public class YiHuDaoJiaActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yi_hu_dao_jia);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        finish();
        if(getIntent().hasExtra("from")){
            if(TextUtils.equals("call",getIntent().getStringExtra("from"))){
                goNewActivity(OrderDetailActivity.class);
            }
        }
    }

    private void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        YiHuDaoJiaIntroduceAdapter familyDoctorAdapter = new YiHuDaoJiaIntroduceAdapter();
        recyclerView.setAdapter(familyDoctorAdapter);
    }

    class YiHuDaoJiaIntroduceAdapter extends RecyclerView.Adapter<YiHuDaoJiaIntroduceAdapter.ViewHolder> {

        private int[] YiHuDaoJiaList = new int[]{R.drawable.yihudaojia_1, R.drawable.yihudaojia_2};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_yihudaojia, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.imgYihudaojia.setImageResource(YiHuDaoJiaList[position]);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        Log.e("☆",position+"");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return YiHuDaoJiaList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.img_yihudaojia)
            ImageView imgYihudaojia;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}

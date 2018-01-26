package cn.v1.kanglewanjia.ui.nurse_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.YiHuNurseData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import rx.functions.Action1;


public class MoreServiceActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private GridLayoutManager layoutManager;
    private List<YiHuNurseData.DataData> nurseDatas = new ArrayList<>();
    private MoreServiceAdapter moreServiceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_service);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        initView();
        initData();
        getYiHuNurseServise();
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    private void initData() {
        if (getIntent().hasExtra("title")) {
            String title = getIntent().getStringExtra("title");
            tvTitle.setText(title);
        }
    }

    private void initView() {

        layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        moreServiceAdapter = new MoreServiceAdapter();
        recyclerView.setAdapter(moreServiceAdapter);
    }

    /**
     * 获取医护到家护士上门服务项
     */
    private void getYiHuNurseServise() {
        showDialog();
        bindObservable(mAppClient.getYiHuNurseItemData(), new Action1<YiHuNurseData>() {
            @Override
            public void call(YiHuNurseData data) {
                closeDialog();
                if (TextUtils.equals("0000", data.getCode())) {
                    nurseDatas.clear();
                    nurseDatas.addAll(data.getData());
                    moreServiceAdapter.notifyDataSetChanged();
                } else {
                    showTost(data.getMessage() + "");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    class MoreServiceAdapter extends RecyclerView.Adapter<MoreServiceAdapter.ViewHolder> {

        private int[] service = new int[]{R.drawable.img_nurse_come_dazhen, R.drawable.img_nurse_come_caixue,
                R.drawable.img_nurse_come_shuye, R.drawable.img_nurse_come_huli, R.drawable.img_nurse_come_huanyao,
                R.drawable.img_nurse_come_daoniao, R.drawable.img_nurse_come_guanchang};

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_more_service, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.imgPic.setImageResource(service[position]);
            holder.tvTitle.setText(nurseDatas.get(position).getName() + "");
            holder.tvDescribe.setText(nurseDatas.get(position).getDescrip() + "");
            holder.tvPrice.setText(nurseDatas.get(position).getSinglePrice() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NurseComeServiceIntroActivity.class);
                    intent.putExtra("title", nurseDatas.get(position).getName()+"");
                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return nurseDatas.size() == 0 ? 0 : service.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_pic)
            ImageView imgPic;
            @Bind(R.id.tv_title)
            TextView tvTitle;
            @Bind(R.id.tv_describe)
            TextView tvDescribe;
            @Bind(R.id.tv_price)
            TextView tvPrice;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }
}

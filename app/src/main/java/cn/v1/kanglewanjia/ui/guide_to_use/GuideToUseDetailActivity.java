package cn.v1.kanglewanjia.ui.guide_to_use;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.YiHuDaoJiaActivity;


public class GuideToUseDetailActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private int[] sign = new int[]{R.drawable.img_sign1, R.drawable.img_sign2,
            R.drawable.img_sign3, R.drawable.img_sign4};
    private int[] familyFile = new int[]{R.drawable.img_family_file1, R.drawable.img_family_file2,
            R.drawable.img_family_file3, R.drawable.img_family_file4};
    private int[] inquiry = new int[]{R.drawable.img_inquiry1, R.drawable.img_inquiry2,
            R.drawable.img_inquiry3, R.drawable.img_inquiry4, R.drawable.img_inquiry5};
    private int[] familyDoctor = new int[]{R.drawable.img_family_doctor1, R.drawable.img_family_doctor2,
            R.drawable.img_family_doctor3, R.drawable.img_family_doctor4};
    private KangLeIntroduceAdapter kangLeIntroduceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_to_use_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        if (getIntent().hasExtra("guide")) {
            int guide = getIntent().getIntExtra("guide", 0);
            switch (guide){
                case 1:
                    kangLeIntroduceAdapter.setData(sign);
                    break;
                case 2:
                    kangLeIntroduceAdapter.setData(familyFile);
                    break;
                case 3:
                    kangLeIntroduceAdapter.setData(inquiry);
                    break;
                case 4:
                    kangLeIntroduceAdapter.setData(familyDoctor);
                    break;
            }
        }
    }

    private void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        kangLeIntroduceAdapter = new KangLeIntroduceAdapter();
        recyclerView.setAdapter(kangLeIntroduceAdapter);
    }

    class KangLeIntroduceAdapter extends RecyclerView.Adapter<KangLeIntroduceAdapter.ViewHolder> {

        private int[] listData = new int[]{};

        public void setData(int[] listData) {
            this.listData = listData;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_yihudaojia, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.imgYihudaojia.setImageResource(listData[position]);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.e("☆", position + "");
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return listData.length;
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

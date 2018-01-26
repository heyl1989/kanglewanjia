package cn.v1.kanglewanjia.ui.family_file;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.view.CustomRecycleViewVertical;
import cn.v1.kanglewanjia.view.RLoopRecyclerView;

public class FamilyFileBasicInfoActivity extends BaseActivity {


    @Bind(R.id.recyclerView_role)
    RLoopRecyclerView recyclerViewRole;
    @Bind(R.id.recyclerView_birth)
    CustomRecycleViewVertical recyclerViewBirth;
    @Bind(R.id.recyclerView_hight)
    CustomRecycleViewVertical recyclerViewHight;
    @Bind(R.id.recyclerView_weight)
    CustomRecycleViewVertical recyclerViewWeight;
    @Bind(R.id.tv_next)
    TextView tvNext;


    private LinearLayoutManager weightLayoutManger;
    private LinearLayoutManager heightLayoutManger;
    private LinearLayoutManager birthLayoutManager;
    private ArrayList<String> birthList;
    private String addFamilyFile = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_file_basic_info);
        ButterKnife.bind(this);
        initView();
        initData();
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void onBackPressed() {
        ActivityManagerUtil.getInstance().finishActivity(this);
    }

    @OnClick(R.id.tv_next)
    public void onClick() {
        Intent intent = new Intent(context,FamilyFileBindInfoActivity.class);
        intent.putExtra("addFamilyFile",addFamilyFile);
        startActivity(intent);
    }

    private void initData() {
        if(getIntent().hasExtra("addFamilyFile")){
            addFamilyFile = getIntent().getStringExtra("addFamilyFile");
        }
    }

    private void initView() {

        recyclerViewRole.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewRole.setFocusable(false);
        ArrayList<String> list = new ArrayList<>();
        list.add("爷爷");
        list.add("奶奶");
        list.add("爸爸");
        list.add("妈妈");
        FamilyFileRoleAdapter familyFileRoleAdapter = new FamilyFileRoleAdapter(list);
        recyclerViewRole.setAdapter(familyFileRoleAdapter);
        recyclerViewRole.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    recyclerViewBirth.smoothToCenter(birthList.size()-20);
                }
            }
        });

        BorderView border = new BorderView(this);
        birthLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewBirth.setLayoutManager(birthLayoutManager);
        recyclerViewBirth.setFocusable(false);
        border.attachTo(recyclerViewBirth);
        birthList = new ArrayList<>();
        for (int i = 1900; i <= 2017; i++) {
            birthList.add(i + "");
        }
        FamilyFileDateAdapter familyFileDateAdapter = new FamilyFileDateAdapter(birthList);
        recyclerViewBirth.setAdapter(familyFileDateAdapter);
        recyclerViewBirth.scrollToPosition(birthList.size()-20);
        familyFileDateAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
                recyclerViewBirth.smoothToCenter(position);
            }
        });
//        recyclerViewBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.i("abc", "hasfocus:" + hasFocus);
//                if (hasFocus) {
//                    if (recyclerViewBirth.getChildCount() > 0) {
//                        birthLayoutManager.scrollToPositionWithOffset(0, 0);
//                        recyclerViewBirth.getChildAt(0).requestFocus();
//                    }
//                }
//            }
//        });

        heightLayoutManger = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewHight.setLayoutManager(heightLayoutManger);
        recyclerViewHight.setFocusable(false);
        border.attachTo(recyclerViewHight);
        ArrayList<String> hightList = new ArrayList<>();
        for (int i = 100; i <= 300; i++) {
            hightList.add(i + "");
        }
        FamilyFileDateAdapter familyFileHightAdapter = new FamilyFileDateAdapter(hightList);
        recyclerViewHight.scrollToPosition(70);
        recyclerViewHight.setAdapter(familyFileHightAdapter);
        familyFileHightAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
                recyclerViewHight.smoothToCenter(position);
            }
        });
        recyclerViewHight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("abc", "hasfocus:" + hasFocus);
                if (hasFocus) {
                    if (recyclerViewHight.getChildCount() > 0) {
                        heightLayoutManger.scrollToPositionWithOffset(0, 0);
                        recyclerViewHight.getChildAt(0).requestFocus();
                    }
                }
            }
        });

        weightLayoutManger = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewWeight.setLayoutManager(weightLayoutManger);
        recyclerViewWeight.setFocusable(false);
        border.attachTo(recyclerViewWeight);
        ArrayList<String> weightList = new ArrayList<>();
        for (int i = 30; i <= 100; i++) {
            weightList.add(i + "");
        }
        FamilyFileDateAdapter familyFileWeightAdapter = new FamilyFileDateAdapter(weightList);
        recyclerViewWeight.scrollToPosition(30);
        recyclerViewWeight.setAdapter(familyFileWeightAdapter);
        familyFileWeightAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
                recyclerViewWeight.smoothToCenter(position);
            }
        });
        recyclerViewWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("abc", "hasfocus:" + hasFocus);
                if (hasFocus) {
                    if (recyclerViewWeight.getChildCount() > 0) {
                        weightLayoutManger.scrollToPositionWithOffset(0, 0);
                        recyclerViewWeight.getChildAt(0).requestFocus();
                    }
                }
            }
        });

    }


    class FamilyFileRoleAdapter extends RLoopRecyclerView.LoopAdapter<FamilyFileRoleAdapter.ViewHolder> {

        protected List<String> datas = new ArrayList<>();
        public FamilyFileRoleAdapter(ArrayList<String> list) {
            this.datas = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_family_file_role, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindLoopViewHolder(ViewHolder holder, int position) {
            holder.tvRole.setText(datas.get(position));

        }


        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img_role)
            ImageView imgRole;
            @Bind(R.id.tv_role)
            TextView tvRole;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


    class FamilyFileDateAdapter extends RecyclerView.Adapter<FamilyFileDateAdapter.ViewHolder> {

        private List<String> datas = new ArrayList<>();
        private OnItemSelectListener mSelectListener;
        private int currentPosition;

        public void setOnItemSelectListener(OnItemSelectListener listener) {
            mSelectListener = listener;
        }

        public FamilyFileDateAdapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_family_file_date, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tvContent.setText(datas.get(position));
            holder.itemView.setTag(position);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.i("adapter", "hasfocus:" + position + "--" + hasFocus);
                    if (hasFocus) {
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

            @Bind(R.id.tv_content)
            TextView tvContent;

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

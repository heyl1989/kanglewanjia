package cn.v1.kanglewanjia.ui.family_file;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.FamilyFileListData;
import cn.v1.kanglewanjia.model.LoginData;
import cn.v1.kanglewanjia.model.PatientListData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.ui.DialogActivity;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import rx.functions.Action1;

public class FamilyFileActivity extends BaseActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.img_bg)
    ImageView imgBg;


    private BorderView border;
    private LinearLayout oldFocusView;
    private List<PatientListData.DataData> list = new ArrayList<>();
    private FamilyFileAdapter familyFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_file);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
        border.setBackgroundResource(R.drawable.border_green_light);
        getPatientList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        border.setBackgroundResource(0);
    }

    /**
     * 获取档案列表
     */
    private void getPatientList() {

        bindObservable(mAppClient.selectPatientInfoById("", "2"), new Action1<PatientListData>() {
            @Override
            public void call(PatientListData data) {
                if (TextUtils.equals("0000", data.getCode())) {
                    if(list.size() > 0){
                        list.clear();
                    }
                    list.addAll(data.getData());
                    list.add(new PatientListData.DataData());
                    familyFileAdapter.notifyDataSetChanged();
                } else {
                    showTost(data.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private void initView() {
        border = new BorderView(this);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        border.setBackgroundResource(R.drawable.border_green_light);
        border.attachTo(recyclerView);
        familyFileAdapter = new FamilyFileAdapter();
        recyclerView.setAdapter(familyFileAdapter);

    }

    class FamilyFileAdapter extends RecyclerView.Adapter<FamilyFileAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_family_file, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (position == (getItemCount() - 1)) {
                holder.roundImageview1.setImageResource(R.drawable.icon_add_file);
                holder.tvRole.setText("添加档案");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, FamilyFileBindInfoActivity.class);
                        intent.putExtra("addFamilyFile", "add");
                        startActivity(intent);
//                        showTost("添加档案");
                    }
                });
            } else {
                holder.tvRole.setText(list.get(position).getRelationShip() +
                        "(" + list.get(position).getRealName() + ")");
                String role = list.get(position).getRelationShip();
                if (TextUtils.equals("爷爷", role)) {
                    holder.roundImageview1.setImageResource(R.drawable.icon_yeye);
                }
                if (TextUtils.equals("奶奶", role)) {
                    holder.roundImageview1.setImageResource(R.drawable.icon_nainai);
                }
                if (TextUtils.equals("爸爸", role)) {
                    holder.roundImageview1.setImageResource(R.drawable.icon_dad);
                }
                if (TextUtils.equals("妈妈", role)) {
                    holder.roundImageview1.setImageResource(R.drawable.icon_mother);

                }
                if (TextUtils.equals("儿子", role)) {
                    holder.roundImageview1.setImageResource(R.drawable.icon_son);
                }
                if (TextUtils.equals("女儿", role)) {
                    holder.roundImageview1.setImageResource(R.drawable.icon_doctor);
                }
                holder.tvLook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showTost("查看");
                        holder.llButton.setVisibility(View.GONE);
                        Intent intent = new Intent(context, FamilyFileDetailActivity.class);
                        intent.putExtra("to", "look");
                        intent.putExtra("id", list.get(position).getId() + "");
                        startActivity(intent);
                    }
                });
                holder.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showTost("编辑");
                        holder.llButton.setVisibility(View.GONE);
                        Intent intent = new Intent(context, FamilyFileDetailActivity.class);
                        intent.putExtra("to", "edit");
                        startActivity(intent);
                    }
                });
                holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showTost("删除");
                        holder.llButton.setVisibility(View.GONE);
                        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(FamilyFileActivity.this);
                        imgBg.setImageBitmap(bmp);
                        Intent intent = new Intent(context, DialogActivity.class);
                        intent.putExtra("from", "deletePatientInfo");
                        intent.putExtra("id", list.get(position).getId() + "");
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
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.round_imageview1)
            ImageView roundImageview1;
            @Bind(R.id.tv_role)
            TextView tvRole;
            @Bind(R.id.tv_look)
            TextView tvLook;
            @Bind(R.id.tv_edit)
            TextView tvEdit;
            @Bind(R.id.tv_delete)
            TextView tvDelete;
            @Bind(R.id.ll_button)
            LinearLayout llButton;
            @Bind(R.id.partent)
            FrameLayout partent;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


}

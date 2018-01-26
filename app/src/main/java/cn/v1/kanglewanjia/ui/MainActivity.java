/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.v1.kanglewanjia.ui;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.evilbinary.tv.widget.BorderView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.ui.family_doctor.FamilyDoctorActivity;
import cn.v1.kanglewanjia.ui.family_file.FamilyFileActivity;
import cn.v1.kanglewanjia.ui.guide_to_use.AboutUsActivity;
import cn.v1.kanglewanjia.ui.guide_to_use.GuideToUseListActivity;
import cn.v1.kanglewanjia.ui.inquiry_doctor.InquiryHospitalActivity;
import cn.v1.kanglewanjia.util.ActivityManagerUtil;
import cn.v1.kanglewanjia.util.FastBlurUtility;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/*
 * MainActivity class that loads MainFragment
 */
public class MainActivity extends BaseActivity {


    @Bind(R.id.main)
    RelativeLayout main;
    @Bind(R.id.tv_inquiry)
    TextView tvInquiry;
    @Bind(R.id.img_bg)
    ImageView imgBg;
    private BorderView border;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityManagerUtil.getInstance().addActivity(this);
        getDeviceInfo();
        initView();
//        getRongToken();
//        connectRongYun("yVdzvKI65AdviH9NgmwOg5Sh87zLtLwsZwshiggPASk1FPKWmIY5UhtosvOb8LUpCjvOpMvHSQil3XrkmC3Ah/HXdulOAM4D");
    }

    @Override
    public void onBackPressed() {
        border.setBackgroundResource(0);
        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(MainActivity.this);
        imgBg.setImageBitmap(bmp);
        Intent intent = new Intent(context,DialogActivity.class);
        intent.putExtra("from","exit");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgBg.setImageBitmap(null);
        border.setBackgroundResource(R.drawable.border_highlight);
    }

    private void initView() {
        border = new BorderView(this);
        border.setBackgroundResource(R.drawable.border_highlight);
        border.attachTo(main);
        //得到AssetManager
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Tensentype JinZhuanHeiJ.ttf");
        //设置字体
        tvInquiry.setTypeface(tf);
    }

    @OnClick({R.id.rl_home_inquiry, R.id.rl_home_doctor, R.id.rl_home_information, R.id.rl_home_family_file, R.id.rl_home_personal_center, R.id.rl_home_guide_to_use})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home_inquiry:
                //showTost("问诊医生");
                goNewActivity(InquiryHospitalActivity.class);
                break;
            case R.id.rl_home_doctor:
                //家庭医生
                if (isLogin()) {
                    if(TextUtils.isEmpty((String) SPUtil.get(context, Common.PATIENT_ID,""))){
                        border.setBackgroundResource(0);
                        Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(MainActivity.this);
                        imgBg.setImageBitmap(bmp);
                        Intent intent = new Intent(context,SelectFamilyFileActivity.class);
                        intent.putExtra("from","main");
                        startActivity(intent);
                    }else{
                        goNewActivity(FamilyDoctorActivity.class);
                    }
                } else {
                    border.setBackgroundResource(0);
                    Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(MainActivity.this);
                    imgBg.setImageBitmap(bmp);
                    Intent intent1 = new Intent(context, DialogActivity.class);
                    intent1.putExtra("from", "toLogin");
                    intent1.putExtra("title", "您当前未登录，无法查看家庭医生，请前往登录。");
                    startActivity(intent1);
                }
                break;
            case R.id.rl_home_information:
//                showTost("一键咨询");
//                RongCallKit.startSingleCall(context, "receive", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
//                goNewActivity(OneKeyConsultationActivity.class);
                break;
            case R.id.rl_home_family_file:
                //家庭档案
                if (isLogin()) {
                    goNewActivity(FamilyFileActivity.class);
                } else {
                    border.setBackgroundResource(0);
                    Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(MainActivity.this);
                    imgBg.setImageBitmap(bmp);
                    Intent intent1 = new Intent(context, DialogActivity.class);
                    intent1.putExtra("from", "toLogin");
                    intent1.putExtra("title", "您当前未登录，无法查看家庭档案，请前往登录。");
                    startActivity(intent1);
                }
                break;
            case R.id.rl_home_personal_center:
                //showTost("个人中心");
                if (isLogin()) {
                    goNewActivity(PersonalCenterActivity.class);
                } else {
                    border.setBackgroundResource(0);
                    Bitmap bmp = FastBlurUtility.getBlurBackgroundDrawer(MainActivity.this);
                    imgBg.setImageBitmap(bmp);
                    Intent intent1 = new Intent(context, DialogActivity.class);
                    intent1.putExtra("from", "toLogin");
                    intent1.putExtra("title", "您当前未登录，无法查看个人中心，请前往登录。");
                    startActivity(intent1);
                }
                break;
            case R.id.rl_home_guide_to_use:
//                showTost("使用指南");
                goNewActivity(GuideToUseListActivity.class);
                break;
        }
    }
}

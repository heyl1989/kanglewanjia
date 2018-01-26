package cn.v1.kanglewanjia.ui.family_file;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.ui.BaseActivity;
import cn.v1.kanglewanjia.view.CameraSurfaceView;


public class FamilyFileCameraActivity extends BaseActivity {

    @Bind(R.id.surfaceView)
    CameraSurfaceView surfaceView;
    @Bind(R.id.img_camera)
    ImageView imgCamera;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.tv_repeat)
    TextView tvRepeat;
    @Bind(R.id.ll_btn)
    LinearLayout llBtn;

    private String TAG = FamilyFileCameraActivity.class.getSimpleName();
    private String filePath;
    private String addFamilyFile;
    private String forCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famlily_file_camera);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        if (getIntent().hasExtra("addFamilyFile")) {
            addFamilyFile = getIntent().getStringExtra("addFamilyFile");
        }
        if (getIntent().hasExtra("for")) {
            forCamera = getIntent().getStringExtra("for");
        }
    }

    private void initView() {
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceView.mCamera.autoFocus(mAutoFocusCallback);
            }
        });
    }

    /**
     * 自动对焦
     */
    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {

        public void onAutoFocus(boolean success, Camera camera) {
            // TODO Auto-generated method stub
            if (success) {
                camera.takePicture(shutter, raw, jpeg);
            }
        }
    };

    // 拍照瞬间调用
    private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {

        @Override
        public void onShutter() {
            Log.i(TAG, "shutter");
        }
    };

    // 获得没有压缩过的图片数据
    private Camera.PictureCallback raw = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {
            Log.i(TAG, "raw");
        }
    };

    //创建jpeg图片回调数据对象
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {
            BufferedOutputStream bos = null;
            Bitmap bm = null;
            try {
                // 获得图片
                bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Log.i(TAG, "Environment.getExternalStorageDirectory()=" + Environment.getExternalStorageDirectory());
                    filePath = "/sdcard/klwj" + System.currentTimeMillis() + ".jpg";//照片保存路径
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中

                } else {
                    showTost("没有检测到内存卡");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.flush();//输出
                    bos.close();//关闭
                    bm.recycle();// 回收bitmap空间
                    surfaceView.mCamera.stopPreview();// 关闭预览
                    surfaceView.mCamera.startPreview();// 开启预览
//                    setResult(1000,new Intent().putExtra("filePath",filePath));
                    Glide.with(context).load("file://" + filePath).into(imgCamera);
                    tvConfirm.requestFocus();
                    llBtn.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private void goCardInfo(String filePath) {
        Intent intent1 = new Intent(context, FamilyFileCardInfoActivity2.class);
        intent1.putExtra("addFamilyFile", addFamilyFile);
        intent1.putExtra("for", forCamera);
        intent1.putExtra("filePath", filePath);
        intent1.putExtra("for_edit", getIntent().getStringExtra("for_edit"));
        intent1.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent1);
        finish();
    }


    @OnClick({R.id.tv_confirm, R.id.tv_repeat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                goCardInfo(filePath);
                break;
            case R.id.tv_repeat:
                imgCamera.setImageResource(R.drawable.img_camera_bg);
                llBtn.setVisibility(View.GONE);
                break;
        }
    }
}

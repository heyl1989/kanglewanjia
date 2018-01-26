package cn.v1.kanglewanjia.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.RongyunTokenData;
import cn.v1.kanglewanjia.network.AppClient;
import cn.v1.kanglewanjia.view.MDProgressGifDialog;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit.mime.TypedString;
import rx.Observable;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by qy on 2017/11/9.
 */

public class BaseActivity extends Activity {

    protected Context context;
    protected AppClient mAppClient;
    private MDProgressGifDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = this;
        mAppClient = new AppClient();
    }

    protected void showTost(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    protected void showDialog() {
        if(mDialog == null){
            mDialog = new MDProgressGifDialog(this);
        }
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialog.show();
    }
    public void closeDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    protected void goNewActivity(Class newClass) {
        Intent intent = new Intent(context, newClass);
        startActivity(intent);
    }

    protected void getDeviceInfo() {

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        // 屏幕高度（像素）
        int height = metric.heightPixels;
        // 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
        // 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL + ",\nSDK版本:"
                + android.os.Build.VERSION.SDK + ",\n系统版本:"
                + android.os.Build.VERSION.RELEASE  + "\n屏幕宽度（像素）: "                       +width + "\n屏幕高度（像素）: " + height + "\n屏幕密度:  "                              +density+"\n屏幕密度DPI: "+densityDpi;
        Log.d("System INFO", info);
    }


    /**
     * 查询是否登录
     *
     * @return
     */
    protected boolean isLogin() {
        return (Boolean) SPUtil.get(this, Common.ISLOGIN, false);
    }

    /**
     * 设置登录
     */
    protected void login() {
        SPUtil.put(this, Common.ISLOGIN, true);
    }

    /**
     * 设置退出登录
     */
    protected void logOut() {
        SPUtil.put(this, Common.ISLOGIN, false);
    }

    /**
     * 网络请求公共方法
     *
     * @param observable
     * @param <T>
     * @return
     */
    public <T> void bindObservable(final Observable<T> observable, final Action1<T> nextAction, final Action1<Throwable> errorAction) {

        getmCompositeSubscription().add(AppObservable.bindActivity(this, observable).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<T>() {
            @Override
            public void call(T t) {
                //handleNetworkError(null);
                nextAction.call(t);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                errorAction.call(throwable);
            }
        }));
    }

    CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public CompositeSubscription getmCompositeSubscription() {
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        else if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }


    /**
     * 获取融云Token
     */
    protected void getRongToken(String orderId) {
        String url = "http://www.yihu365.com/kangle/images/huanzhemr.png";
        bindObservable(new AppClient().getRongToken(orderId, "patient", url), new Action1<TypedString>() {
            @Override
            public void call(TypedString typedString) {
                String responseStr = new String(typedString.getBytes());
                RongyunTokenData rongTokenData = new Gson().fromJson(responseStr, RongyunTokenData.class);
                Log.e("☆",responseStr);
                connectRongYun(rongTokenData.getToken());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    /**
     * 连接融云服务器
     */
    protected void connectRongYun(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
//                showTost(" Token 错误");
                Log.e("☆", " Token 错误");
            }

            @Override
            public void onSuccess(String s) {
//                showTost("连接融云成功");
                Log.e("☆", "连接融云成功" + s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
//                showTost("连接融云失败");
                Log.e("☆", "连接融云失败");
            }
        });

    }
}

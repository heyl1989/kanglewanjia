package cn.v1.kanglewanjia;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

import io.rong.imkit.RongIM;

/**
 * Created by qy on 2017/11/16.
 */

public class App extends Application {

    private static App app;

    public static App getInstance(){
        if(app == null)
            throw new NullPointerException("app not create or be terminated!");
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        RongIM.init(this);
    }

}

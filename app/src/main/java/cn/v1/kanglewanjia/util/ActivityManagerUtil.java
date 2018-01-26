package cn.v1.kanglewanjia.util;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by qy on 2017/7/8.
 * <p>
 * 管类Activity的工具类
 */

public class ActivityManagerUtil {

    private static ActivityManagerUtil mActivityManagerUtils;


    static {
        mActivityManagerUtils = new ActivityManagerUtil();
    }

    private ActivityManagerUtil() {
        /**
         * 这里面写一些需要执行初始化的工作
         */
    }

    public static ActivityManagerUtil getInstance() {
        return mActivityManagerUtils;

    }

    /**
     * 打开的activity
     **/

    private List<Activity> activities = new ArrayList<Activity>();


    /**
     * 新建了一个activity
     *
     * @param activity
     */

    public void addActivity(Activity activity) {
        activities.add(activity);
        Log.e("☆", activities.size() + activity.getClass().getSimpleName());
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */

    public void finishActivity(Activity activity) {

        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 应用退出，结束所有的activity
     */

    public void exit() {

        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);

    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivityclass(Class<?> cls) {
        if (activities != null) {
            for (int i = 0; i < activities.size(); i++) {
                Log.e("☆", activities.size() + "/" +  activities.get(i).getClass().getSimpleName());
                if (activities.get(i).getClass().equals(cls)) {
                    finishActivity(activities.get(i));
                }
            }
        }

    }
}

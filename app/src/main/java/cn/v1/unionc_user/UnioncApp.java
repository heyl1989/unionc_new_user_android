package cn.v1.unionc_user;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import io.rong.imkit.RongIM;

/**
 * Created by qy on 2018/2/1.
 */

public class UnioncApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        initLog();
    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}

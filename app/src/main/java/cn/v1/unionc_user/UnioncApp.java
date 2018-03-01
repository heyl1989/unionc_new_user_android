package cn.v1.unionc_user;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.v1.unionc_user.tecent_qcloud.InitSDK;
import cn.v1.unionc_user.tecent_qcloud.UserConfig;

/**
 * Created by qy on 2018/2/1.
 */

public class UnioncApp extends MultiDexApplication {

    private static UnioncApp app;

    public static UnioncApp getInstance(){
        if(app == null)
            throw new NullPointerException("app not create or be terminated!");
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //取消融云
        //RongIM.init(this);
        initLog();
        initTIM();
    }

    /**
     * 腾讯云初始化
     */
    private void initTIM() {
        InitSDK.init(getInstance());
        UserConfig.init();
    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}

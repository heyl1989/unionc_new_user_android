package cn.v1.unionc_user.tecent_qcloud;

import android.content.Context;
import android.os.Environment;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.qcloud.sdk.Constant;

/**
 * Created by qy on 2018/2/27.
 */

public class InitSDK {

    public static void init(Context context) {
        //初始化SDK基本配置
        TIMSdkConfig config = new TIMSdkConfig(Constant.SDK_APPID)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

        //初始化SDK
        TIMManager.getInstance().init(context, config);
    }

}

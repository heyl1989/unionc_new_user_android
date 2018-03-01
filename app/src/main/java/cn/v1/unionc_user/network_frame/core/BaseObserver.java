package cn.v1.unionc_user.network_frame.core;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import java.net.SocketTimeoutException;

import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.utils.NetWorkUtils;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by qy on 2018/1/31.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private Context context;

    public <T> BaseObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        Logger.json(new Gson().toJson(t));
        if (BaseData.class.isAssignableFrom(t.getClass())) {
            BaseData baseData = (BaseData)t;
            //检查Token是否过期
            if(TextUtils.equals("0100",baseData.getCode())){
                SPUtil.remove(context, Common.USER_TOKEN);
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                //登出
                TIMManager.getInstance().logout(new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {

                        //错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code列表请参见错误码表
                        Logger.e("logout failed. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        //登出成功
                    }
                });
            }
        }
        onResponse(t);
    }

    public abstract void onResponse(T t);

    @Override
    public void onError(@NonNull Throwable e) {
        Logger.e(new Gson().toJson(e));
        if (!NetWorkUtils.isNetworkConnected(context)) {
//            NetWorkSetDialog.showSetNetworkUI(context);
            Toast.makeText(context, "没有可用的网络", Toast.LENGTH_LONG).show();
        }
        if (e.getMessage().contains("404")) {
            Toast.makeText(context, "网络404错误", Toast.LENGTH_LONG).show();
        }
        if (e.getMessage().contains("500")) {
            Toast.makeText(context, "网络500错误", Toast.LENGTH_LONG).show();
        }
        if (e instanceof IllegalStateException) {
            Toast.makeText(context, "数据解析异常", Toast.LENGTH_LONG).show();
        }
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "请求超时", Toast.LENGTH_LONG).show();
        }
        e.printStackTrace();
        onFail(e);
    }

    public abstract void onFail(Throwable e);

    @Override
    public void onComplete() {
        Logger.i("-----------已完成----------");
    }
}

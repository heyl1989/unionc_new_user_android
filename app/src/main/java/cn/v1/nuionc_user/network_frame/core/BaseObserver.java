package cn.v1.nuionc_user.network_frame.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.SocketTimeoutException;

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
        Log.i("observer",new Gson().toJson(t));
        onResponse(t);
    }

    public abstract void onResponse(T t);

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("Observer",new Gson().toJson(e));
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
        if(e instanceof IllegalStateException){
            Toast.makeText(context, "数据解析异常", Toast.LENGTH_LONG).show();
        }
        if(e instanceof SocketTimeoutException){
            Toast.makeText(context, "请求超时", Toast.LENGTH_LONG).show();
        }
        e.printStackTrace();
    }

    public abstract void onfa(T t);

    @Override
    public void onComplete() {
        Log.i("observable", "-----------已完成----------");
    }
}

package cn.v1.nuionc_user.network_frame.core;

import cn.v1.nuionc_user.network_frame.UnionAPI;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qy on 2018/1/31.
 */

public class ConnectHttp<T> {

    //创建 GitHub API 接口的一个实例。
    public static UnionAPI getGitHubAPI() {
        return RetrofitConfigure.githubRetrofit.create(UnionAPI.class);
    }

    //创建  WuLiu API 口的一个实例。
    public static UnionAPI getPictureAPI() {
        return RetrofitConfigure.pictureRetrofit.create(UnionAPI.class);
    }

    /**
     * 连接网络
     * @param observable
     * @param baseObserver
     */
    public static <T> void connect(Observable<T> observable, BaseObserver<T> baseObserver) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((baseObserver));
    }
}

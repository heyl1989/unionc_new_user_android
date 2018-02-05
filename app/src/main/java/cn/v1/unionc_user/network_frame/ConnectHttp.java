package cn.v1.unionc_user.network_frame;

import cn.v1.unionc_user.network_frame.UnionAPI;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.network_frame.core.RetrofitConfigure;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qy on 2018/1/31.
 */

public class ConnectHttp<T> {

    //创建 Unionc API 接口的一个实例。
    public static UnionAPI getUnionAPI() {
        return RetrofitConfigure.unioncRetrofit.create(UnionAPI.class);
    }

    //创建 Rong API 接口的一个实例。
    public static RongAPI getRongAPI() {
        return RetrofitConfigure.rongRetrofit.create(RongAPI.class);
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

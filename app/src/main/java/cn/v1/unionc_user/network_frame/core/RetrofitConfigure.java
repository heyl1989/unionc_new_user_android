package cn.v1.unionc_user.network_frame.core;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qy on 2018/1/31.
 */

public class RetrofitConfigure {

    /**
     * GitHub配置
     */
    private static final String Unionc_Host = "https://api.github.com";

    public static Retrofit unioncRetrofit = new Retrofit.Builder()
            .baseUrl(Unionc_Host)
            .client(OkHttpConfigure.httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    /**
     * 融云服务器
     */
    private static final String Rong_Host = "http://api.cn.ronghub.com";

    public static Retrofit rongRetrofit = new Retrofit.Builder()
            .baseUrl(Rong_Host)
            .client(OkHttpConfigure.httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}

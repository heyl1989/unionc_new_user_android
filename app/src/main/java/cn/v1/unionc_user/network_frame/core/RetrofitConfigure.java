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
    private static final String GitHub_Host = "https://api.github.com";

    public static Retrofit githubRetrofit = new Retrofit.Builder()
            .baseUrl(GitHub_Host)
            .client(OkHttpConfigure.httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    /**
     * 物流100配置
     */
    private static final String WuLiu_Host = "http://www.kuaidi100.com";

    public static Retrofit wuLiuRetrofit = new Retrofit.Builder()
            .baseUrl(WuLiu_Host)
            .client(OkHttpConfigure.httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    /**
     * 图片下载
     */
    private static String picture_Host = "https://search.maven.org";

    public static void setPicture_Host(String picture_Host) {
        RetrofitConfigure.picture_Host = picture_Host;
    }

    public static Retrofit pictureRetrofit = new Retrofit.Builder()
            .baseUrl(picture_Host)
            .client(OkHttpConfigure.httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}

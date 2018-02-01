package cn.v1.nuionc_user.network_frame;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by qy on 2018/1/31.
 */

public interface UnionAPI {

    //https://api.github.com/repos/square/retrofit/contributors
//    @GET("/repos/{owner}/{repo}/contributors")
//    Observable<List<Contributor>> getContributors(@Path("owner") String owner,
//                                                  @Path("repo") String repo);
    //https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=converter-gson&v=LATEST
    @GET("/remote_content?g=com.squareup.retrofit2&a=converter-gson&v=LATEST")
    Observable<ResponseBody> getcontributorsAvator();

    // !!! 当心大文件，当是大文件时
    @Streaming
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

}

package cn.v1.unionc_user.network_frame;

import java.util.Map;

import cn.v1.unionc_user.model.RongTokenData;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by qy on 2018/2/3.
 */

public interface RongAPI {
    @FormUrlEncoded
    @POST("/user/getToken.json")
    Observable<RongTokenData> getRongToken(@HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

}

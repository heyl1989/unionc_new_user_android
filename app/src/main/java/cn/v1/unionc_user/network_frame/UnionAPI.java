package cn.v1.unionc_user.network_frame;

import java.util.Map;

import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.UserInfoData;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by qy on 2018/1/31.
 */

public interface UnionAPI {
    /**
     * 验证码发送
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("authcode-send")
    Observable<BaseData> getAuthCode(@FieldMap Map<String, Object> params);

    /**
     * 登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginData> login(@FieldMap Map<String, Object> params);

    /**
     * 获取用户信息
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("user/user-info")
    Observable<UserInfoData> getUserInfo(@FieldMap Map<String, Object> params);


}

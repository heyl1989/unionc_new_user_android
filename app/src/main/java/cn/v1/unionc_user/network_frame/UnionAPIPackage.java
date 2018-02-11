package cn.v1.unionc_user.network_frame;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.UserInfoData;
import cn.v1.unionc_user.utils.MobileConfigUtil;
import io.reactivex.Observable;

/**
 * Created by qy on 2018/1/31.
 */

public class UnionAPIPackage {


    private static Gson gson = new Gson();

    /**
     * 数据处理
     * @param params 传递的参数
     * @return
     */
    private static Map<String,Object> dataProcess(Map<String,String> params){
        HashMap<String, Object> processData = new HashMap<>();
        processData.put("data", gson.toJson(params).toString());
        processData.put("encryption",false);
        return processData;
    }

    /**
     * 验证码下发
     * @param userMobile 手机号
     * @return
     */
    public static Observable<BaseData> getAuthCode(String userMobile) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userMobile",userMobile);
        return ConnectHttp.getUnionAPI().getAuthCode(dataProcess(params));
    }

    /**
     * 登录
     * @param userMobile 手机号
     * @param authCode   验证码
     * @return
     */
    public static Observable<LoginData> login(String userMobile, String authCode) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userMobile",userMobile);
        params.put("authCode",authCode);
        params.put("imei", MobileConfigUtil.getMacCode());
        return ConnectHttp.getUnionAPI().login(dataProcess(params));
    }

    /**
     * 获取用户信息
     * @return
     */
    public static Observable<UserInfoData> getUserInfo(String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        return ConnectHttp.getUnionAPI().getUserInfo(dataProcess(params));
    }


    /**
     * 获取首页列表
     * @return
     */
    public static Observable<HomeListData> getHomeList(String token , String longitude, String latitude) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        params.put("imei",MobileConfigUtil.getMacCode());
        params.put("longitude",longitude);
        params.put("latitude",latitude);
        return ConnectHttp.getUnionAPI().getHomeList(dataProcess(params));
    }

}

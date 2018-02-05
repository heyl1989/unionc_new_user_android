package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/3.
 */

public class RongTokenData {


    private int code;
    private String token;
    private String userId;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}

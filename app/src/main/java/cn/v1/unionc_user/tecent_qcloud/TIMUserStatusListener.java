package cn.v1.unionc_user.tecent_qcloud;

/**
 * Created by qy on 2018/2/28.
 */

public interface TIMUserStatusListener {

    void userForceOffline();

    void userSigExpired();
}

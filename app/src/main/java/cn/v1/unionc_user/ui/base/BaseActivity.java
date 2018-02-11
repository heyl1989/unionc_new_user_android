package cn.v1.unionc_user.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.RongTokenData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.RongAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by qy on 2018/2/1.
 */

public class BaseActivity extends FragmentActivity {

    protected Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
    }

    /**
     * tusi
     *
     * @param message
     */
    protected void showTost(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转新的Activity
     *
     * @param activity
     */
    protected void goNewActivity(Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

    /**
     * 登录
     *
     * @param token
     */
    protected void login(String token) {
        SPUtil.put(context, Common.USER_TOKEN, (String) token);
    }

    /**
     * 是否登录
     */
    protected boolean isLogin() {
        return SPUtil.contains(context, Common.USER_TOKEN);
    }

    /**
     * 退出
     */
    protected void logout() {
        SPUtil.remove(context, Common.USER_TOKEN);
    }


    /**
     * 跳转新的Activity
     *
     * @param activity
     */
    protected void goNewActivity(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(Common.DATA, bundle);
        startActivity(intent);
    }

    /**
     * 展示加载框
     *
     * @param message 需要提示的信息
     */
    protected void showDialog(String message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void closeDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 获取融云Token
     *
     * @return rongToken
     */
    protected String getRongToken() {
        String rongToken = "";
        ConnectHttp.connect(RongAPIPackage.getRongToken("client", "", ""), new BaseObserver<RongTokenData>(context) {
            @Override
            public void onResponse(RongTokenData rongTokenData) {
                connect(rongTokenData.getToken());
            }

            @Override
            public void onFail(Throwable e) {
                rongConnectFailure();
            }
        });

        return rongToken;
    }

    /**
     * 连接融云服务器
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                            2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    rongConnectFailure();
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    rongConnectSuccessed(userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    rongConnectFailure();
                }
            });
        }
    }

    /**
     * 融云连接成功
     *
     * @param userId
     */
    protected void rongConnectSuccessed(String userId) {
    }

    /**
     * 融云连接失败
     */
    protected void rongConnectFailure() {
        showTost("融云连接失败");
    }


    protected Bundle outState;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        this.outState = outState;
        super.onSaveInstanceState(outState);
    }
}

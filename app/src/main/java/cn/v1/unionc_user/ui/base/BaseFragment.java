package cn.v1.unionc_user.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;

/**
 * Created by qy on 2018/2/6.
 */

public class BaseFragment extends Fragment {

    protected Context context;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
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
    protected void goNewActivity(Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
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


}

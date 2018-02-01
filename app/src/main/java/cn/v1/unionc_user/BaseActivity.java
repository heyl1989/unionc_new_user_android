package cn.v1.unionc_user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import cn.v1.unionc_user.Data.Common;

/**
 * Created by qy on 2018/2/1.
 */

public class BaseActivity extends Activity {

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    /**
     * tusi
     * @param message
     */
    protected void showTost(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转新的Activity
     * @param activity
     */
    protected void goNewActivity(Class<?> activity){
        Intent intent = new Intent(context,activity);
        startActivity(intent);
    }


    /**
     * 跳转新的Activity
     * @param activity
     */
    protected void goNewActivity(Class<?> activity ,Bundle bundle){
        Intent intent = new Intent(context,activity);
        intent.putExtra(Common.DATA,bundle);
        startActivity(intent);
    }
}

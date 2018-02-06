package cn.v1.unionc_user.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import cn.v1.unionc_user.data.Common;

/**
 * Created by qy on 2018/2/6.
 */

public class BaseFragment extends Fragment {

    protected Context context = getActivity();


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

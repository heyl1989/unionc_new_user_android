package cn.v1.unionc_user.utils;

import android.content.Context;

/**
 * Created by qy on 2018/2/24.
 */

public class DP_PX {
    //像素单位转换
    public static int dip2px(Context context , int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}

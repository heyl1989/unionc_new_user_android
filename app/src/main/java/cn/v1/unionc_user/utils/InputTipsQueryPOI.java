package cn.v1.unionc_user.utils;

import android.content.Context;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by qy on 2018/2/27.
 */

public abstract class InputTipsQueryPOI {

    private Context context;
    private String keywords;
    private String city;

    public InputTipsQueryPOI(Context context,String keywords, String city) {
        this.context = context;
        this.keywords = keywords;
        this.city = city;
        init();
    }

    private void init() {
        InputtipsQuery inputquery = new InputtipsQuery(keywords, city);
        inputquery.setCityLimit(true);//限制在当前城
        Inputtips inputTips = new Inputtips(context, inputquery);
        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (1000 == i) {
                    Logger.i(new Gson().toJson(list));
                }
                onGetInputtipsResult(list,i);

            }
        });
        inputTips.requestInputtipsAsyn();
    }

    public abstract void onGetInputtipsResult(List<Tip> list, int i);

}

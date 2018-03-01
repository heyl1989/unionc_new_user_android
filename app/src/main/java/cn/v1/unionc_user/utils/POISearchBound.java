package cn.v1.unionc_user.utils;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by qy on 2018/2/27.
 */

public abstract class POISearchBound {

    private Context context;
    private double lat;
    private double lon;
    private String city;

    public POISearchBound(Context context,double lat ,double lon ,String city) {
        this.context = context;
        this.lat = lat;
        this.lon = lon;
        this.city = city;
        searchBound();
    }

    private void searchBound(){
        PoiSearch.Query query = new PoiSearch.Query("住宅", "", city);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(context, query);
        //设置周边搜索的中心点以及半径
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lon), 5000));
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                Logger.i(new Gson().toJson(poiResult));
                onPoiSearchedResult(poiResult,i);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {}
        });

        poiSearch.searchPOIAsyn();
    }

    /**
     * 附近检索成功
     * @param poiResult
     * @param i
     */
    protected abstract void  onPoiSearchedResult(PoiResult poiResult, int i);


}

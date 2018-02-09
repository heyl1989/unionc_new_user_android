package cn.v1.unionc_user.utils;

import android.content.Context;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qy on 2018/2/3.
 */

public abstract class Location {

    private Context context;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

    public Location(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        mlocationClient = new AMapLocationClient(context);
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        /**
         * 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
         注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
         在定位结束后，在合适的生命周期调用onDestroy()方法
         在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
         */
        // 启动定位
        mlocationClient.startLocation();
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    Logger.i(new Gson().toJson(amapLocation));
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
                        amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        amapLocation.getCountry();//国家信息
                        amapLocation.getProvince();//省信息
                        amapLocation.getCity();//城市信息
                        amapLocation.getDistrict();//城区信息
                        amapLocation.getStreet();//街道信息
                        amapLocation.getStreetNum();//街道门牌号信息
                        amapLocation.getCityCode();//城市编码
                        amapLocation.getAdCode();//地区编码
//                        amapLocation.getAOIName();//获取当前定位点的AOI信息
                        locationSuccess(amapLocation);
                    } else {
                        locationFailure();
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Logger.e("location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        mlocationClient.stopLocation();
    }

    /**
     * 定位成功
     *
     * @param amapLocation
     */
    protected abstract void locationSuccess(AMapLocation amapLocation);

    /**
     * 定位失败
     */
    protected abstract void locationFailure();
}

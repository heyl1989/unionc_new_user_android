package cn.v1.unionc_user;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_qm_health)
    TextView tvQmHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        getRongToken();
        initLocation();
    }

    @OnClick(R.id.tv_qm_health)
    public void onClick() {
        goNewActivity(QMWebViewActivity.class);
    }

    @Override
    protected void rongConnectSuccessed(String userId) {
        showTost("融云连接成功");
        super.rongConnectSuccessed(userId);
    }

    /**
     * 获取位置信息
     */
    private void initLocation() {
        new Location(context){
            @Override
            protected void locationSuccess(AMapLocation amapLocation) {
                showTost("定位成功" + amapLocation.getCity());
                stopLocation();
            }
            @Override
            protected void locationFailure() {
                showTost("定位失败,尝试重新定位" );
            }
        };
    }


}

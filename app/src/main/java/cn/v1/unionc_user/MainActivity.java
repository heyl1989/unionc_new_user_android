package cn.v1.unionc_user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.view.TimePicker;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_qm_health)
    TextView tvQmHealth;
    @Bind(R.id.tv_qr_code)
    TextView tvQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        getRongToken();
        initLocation();
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
        new Location(context) {
            @Override
            protected void locationSuccess(AMapLocation amapLocation) {
                showTost("定位成功" + amapLocation.getCity());
                stopLocation();
            }

            @Override
            protected void locationFailure() {
                showTost("定位失败,尝试重新定位");
            }
        };
    }

    @OnClick({R.id.tv_qm_health, R.id.tv_qr_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qm_health:
                goNewActivity(QMWebViewActivity.class);
                break;
            case R.id.tv_qr_code:
//                goNewActivity(CaptureActivity.class);
                new TimePicker(context,TimePicker.YEAR_MONTH_DATE,TimePicker.SHIELD_PREVIOUS){
                    @Override
                    public void onTimeSelected(Date date) {
                        showTost(date.toString());
                    }
                };
                break;

        }
    }


}

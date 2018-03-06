package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.LocationUpdateEventData;
import cn.v1.unionc_user.model.NearbyPOIData;
import cn.v1.unionc_user.ui.adapter.POIAdapter;
import cn.v1.unionc_user.ui.adapter_interface.OnItemClicListener;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.Distance;
import cn.v1.unionc_user.utils.InputTipsQueryPOI;
import cn.v1.unionc_user.utils.Location;
import cn.v1.unionc_user.utils.POISearchBound;

public class LocationUpdateActivity extends BaseActivity {

    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.et_address)
    EditText etAddress;
    @Bind(R.id.tv_current_poi)
    TextView tvCurrentPoi;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.img_close)
    ImageView imgClose;

    private double lat;
    private double lon;
    private String city;
    private List<NearbyPOIData> datas = new ArrayList<>();
    private POIAdapter poiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);
        ButterKnife.bind(this);
        initView();
    }


    @OnClick({R.id.img_close, R.id.tv_current_poi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.tv_current_poi:
                location();
                break;
        }
    }

    private void initView() {
        location();
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        poiAdapter = new POIAdapter(context);
        recycleView.setAdapter(poiAdapter);
        poiAdapter.setOnItemClicListener(new OnItemClicListener() {
            @Override
            public void onItemClick(View view, int postion) {
                LocationUpdateEventData eventData = new LocationUpdateEventData();
                eventData.setLat(datas.get(postion).getLat());
                eventData.setLon(datas.get(postion).getLon());
                eventData.setPoiName(datas.get(postion).getPOIName());
                BusProvider.getInstance().post(eventData);
                finish();
            }
        });
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keywords = s.toString();
                if (!TextUtils.isEmpty(keywords)) {
                    inputTips(keywords);
                }
            }
        });

    }

    private void location() {
        new Location(context) {

            @Override
            protected void locationSuccess(AMapLocation amapLocation) {
                lat = amapLocation.getLatitude();
                lon = amapLocation.getLongitude();
                city = amapLocation.getCity();
                tvCity.setText(city);
                boundPOI();
                stopLocation();
            }

            @Override
            protected void locationFailure() {
                stopLocation();
            }
        };
    }

    private void boundPOI() {
        new POISearchBound(context, lat, lon, city) {
            @Override
            protected void onPoiSearchedResult(PoiResult poiResult, int i) {
                if (null != poiResult.getPois() && poiResult.getPois().size() > 0) {
                    datas.clear();
                    List<PoiItem> pois = poiResult.getPois();
                    for (int j = 0; j < pois.size(); j++) {
                        double mlat = pois.get(j).getLatLonPoint().getLatitude();
                        double mlon = pois.get(j).getLatLonPoint().getLongitude();
                        NearbyPOIData poidata = new NearbyPOIData();
                        poidata.setLat(mlat);
                        poidata.setLon(mlon);
                        poidata.setPOIName(pois.get(j).getTitle());
                        poidata.setAddress(pois.get(j).getSnippet());
                        poidata.setDistance(Distance.GetDistance(mlon, mlat, lon, lat));
                        datas.add(poidata);
                    }
                    poiAdapter.setData(datas);
                    recycleView.scrollToPosition(0);
                }

            }
        };
    }

    private void inputTips(String keywords) {
        new InputTipsQueryPOI(context, keywords, city) {
            @Override
            public void onGetInputtipsResult(List<Tip> list, int i) {
                if (1000 == i) {
                    if (null != list && list.size() > 0) {
                        datas.clear();
                        for (int j = 0; j < list.size(); j++) {
                            double mlat = list.get(j).getPoint().getLatitude();
                            double mlon = list.get(j).getPoint().getLongitude();
                            NearbyPOIData poidata = new NearbyPOIData();
                            poidata.setLat(mlat);
                            poidata.setLon(mlon);
                            poidata.setPOIName(list.get(j).getName());
                            poidata.setAddress(list.get(j).getAddress());
                            poidata.setDistance(Distance.GetDistance(mlon, mlat, lon, lat));
                            datas.add(poidata);
                        }
                        poiAdapter.setData(datas);
                        recycleView.scrollToPosition(0);
                    } else {
                        datas.clear();
                        poiAdapter.setData(datas);
                    }
                } else {
                    showTost("没有搜索到");
                }

            }
        };
    }


}

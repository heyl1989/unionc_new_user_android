package cn.v1.unionc_user.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.model.LocationUpdateEventData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.HomeListAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.utils.Location;
import cn.v1.unionc_user.view.BannerView;
import cn.v1.unionc_user.view.LocationDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {


    @Bind(R.id.banner)
    BannerView banner;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.img_message)
    ImageView imgMessage;
    @Bind(R.id.tv_saoma)
    TextView tvSaoma;
    @Bind(R.id.tv_guahao)
    TextView tvGuahao;
    @Bind(R.id.tv_yihu)
    TextView tvYihu;
    @Bind(R.id.tv_health)
    TextView tvHealth;
    @Bind(R.id.main_recycleview)
    RecyclerView mainRecycleview;

    private int[] imgs = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private HomeListAdapter homeListAdapter;
    private List<HomeListData.DataData.HomeData> datas = new ArrayList<>();
    private String currentPoiname;
    private String longitude;
    private String latitude;
    private LocationDialog locationDialog;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initLocation();
    }

    @OnClick({R.id.tv_address, R.id.img_message, R.id.tv_saoma, R.id.tv_guahao, R.id.tv_yihu, R.id.tv_health})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                confirmLocationDialog();
                break;
            case R.id.img_message:
                break;
            case R.id.tv_saoma:
                goNewActivity(CaptureActivity.class);
                break;
            case R.id.tv_guahao:
                goNewActivity(HospitalDetailActivity.class);
                break;
            case R.id.tv_yihu:
                break;
            case R.id.tv_health:
                goNewActivity(HealthClassActivity.class);
                break;
        }
    }

    private void initView() {
        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        banner.setViewList(viewList);
        banner.startLoop(true);

        mainRecycleview.setLayoutManager(new LinearLayoutManager(context));
        homeListAdapter = new HomeListAdapter(context);
        mainRecycleview.setAdapter(homeListAdapter);
    }

    @Subscribe
    public void subscribeLocationUpdate(LocationUpdateEventData data) {
        currentPoiname = data.getPoiName();
        tvAddress.setText(data.getPoiName());
        latitude = data.getLat() + "";
        longitude = data.getLon() + "";
        SPUtil.put(context, Common.LONGITUDE, longitude);
        SPUtil.put(context, Common.LATITUDE, latitude);
        getHomeList(longitude, latitude);
    }


    private void initLocation() {
        showDialog("定位中...");
        new Location(context) {
            @Override
            protected void locationSuccess(AMapLocation amapLocation) {
                currentPoiname = amapLocation.getPoiName();
                tvCity.setText(amapLocation.getCity());
                tvAddress.setText(amapLocation.getPoiName());
                stopLocation();
                //位置确定弹框
                confirmLocationDialog();
                //获取经纬度
                longitude = amapLocation.getLongitude() + "";
                latitude = amapLocation.getLatitude() + "";
                SPUtil.put(context, Common.LONGITUDE, longitude);
                SPUtil.put(context, Common.LATITUDE, latitude);
                //请求数据
                getHomeList(longitude, latitude);
                closeDialog();
            }

            @Override
            protected void locationFailure() {
                tvAddress.setText("请选择");
                stopLocation();
                closeDialog();
            }
        };
    }

    /**
     * 位置确定弹框
     */
    private void confirmLocationDialog() {
        if (null == locationDialog) {
            locationDialog = new LocationDialog(context);
        }
        locationDialog.show();
        locationDialog.setCurrentPOI(currentPoiname);
        locationDialog.setOnButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onConfirmClick() {
            }

            @Override
            public void onCancelClick() {
                goNewActivity(LocationUpdateActivity.class);
            }
        });
    }


    private void getHomeList(String longitude, String latitude) {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getHomeList(token, longitude, latitude), new BaseObserver<HomeListData>(context) {
            @Override
            public void onResponse(HomeListData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    datas.clear();
                    if (data.getData().getRecommendDoctors().size() != 0) {
                        int index = datas.size();
                        int recommendDoctors = data.getData().getRecommendDoctors().size();
                        for (int i = 0; i < recommendDoctors; i++) {
                            datas.add(data.getData().getRecommendDoctors().get(i));
                            datas.get(index + i).setType(Common.RECOMMEND_DOCTOR);
                        }
                    }
                    if (data.getData().getSignedDoctros().size() != 0) {
                        int index = datas.size();
                        int SignedDoctros = data.getData().getSignedDoctros().size();
                        for (int i = 0; i < SignedDoctros; i++) {
                            datas.add(data.getData().getSignedDoctros().get(i));
                            datas.get(index + i).setType(Common.SIGNED_DOCTROS);
                        }
                    }
                    if (data.getData().getAttendingDoctors().size() != 0) {
                        int index = datas.size();
                        int attendingDoctors = data.getData().getAttendingDoctors().size();
                        for (int i = 0; i < attendingDoctors; i++) {
                            datas.add(data.getData().getAttendingDoctors().get(i));
                            datas.get(index + i).setType(Common.ATTENDING_DOCTORS);
                        }

                    }
                    homeListAdapter.setData(datas);
                    Logger.json(new Gson().toJson(datas));
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

package cn.v1.unionc_user.ui.message;


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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.HomeListAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.utils.Location;
import cn.v1.unionc_user.view.BannerView;

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

    public MessageFragment() {
        // Required empty public constructor
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
                break;
            case R.id.img_message:
                break;
            case R.id.tv_saoma:
                goNewActivity(CaptureActivity.class);
                break;
            case R.id.tv_guahao:
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


    private void initLocation() {
        showDialog("定位中...");
        new Location(context) {
            @Override
            protected void locationSuccess(AMapLocation amapLocation) {
                tvCity.setText(amapLocation.getCity());
                tvAddress.setText(amapLocation.getPoiName());
                stopLocation();
                String longitude = amapLocation.getLongitude() + "";
                String latitude = amapLocation.getLatitude() + "";
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


    private void getHomeList(String longitude, String latitude) {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.getHomeList(token, longitude, latitude), new BaseObserver<HomeListData>(context) {
            @Override
            public void onResponse(HomeListData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    if (data.getData().getAttendingDoctors().size() != 0) {
                        int attendingDoctors = data.getData().getAttendingDoctors().size();
                        for (int i = 0; i < attendingDoctors; i++) {
                            datas.add(data.getData().getAttendingDoctors().get(i));
                            datas.get(i).setType(Common.ATTENDING_DOCTORS);
                        }

                    }
                    if (data.getData().getRecommendDoctors().size() != 0) {
                        int recommendDoctors = data.getData().getRecommendDoctors().size();
                        for (int i = 0; i < recommendDoctors; i++) {
                            datas.add(data.getData().getRecommendDoctors().get(i));
                            datas.get(i).setType(Common.RECOMMEND_DOCTOR);
                        }
                    }
                    if (data.getData().getSignedDoctros().size() != 0) {
                        int SignedDoctros = data.getData().getSignedDoctros().size();
                        for (int i = 0; i < SignedDoctros; i++) {
                            datas.add(data.getData().getSignedDoctros().get(i));
                            datas.get(i).setType(Common.SIGNED_DOCTROS);
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

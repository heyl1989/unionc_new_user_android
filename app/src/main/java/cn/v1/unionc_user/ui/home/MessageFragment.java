package cn.v1.unionc_user.ui.home;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.otto.Subscribe;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;

import java.util.ArrayList;
import java.util.Iterator;
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
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.MessageFactory;
import cn.v1.unionc_user.tecent_qcloud.tim_util.TimeUtil;
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
    @Bind(R.id.ll_search)
    LinearLayout llSearch;
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
    @Bind(R.id.tv_recommond)
    TextView tvRecommond;
    @Bind(R.id.rl_recommond)
    RelativeLayout rlRecommond;

    private int[] imgs = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private HomeListAdapter homeListAdapter;
    private List<HomeListData.DataData.HomeData> datas = new ArrayList<>();
    private List<HomeListData.DataData.HomeData> newConversations = new ArrayList<>();
    private String currentPoiname;
    private String longitude;
    private String latitude;
    private LocationDialog locationDialog;
    private final int REQUEST_PHONE_PERMISSIONS = 0;

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

    @OnClick({R.id.tv_address, R.id.ll_search, R.id.tv_saoma, R.id.tv_guahao, R.id.tv_yihu, R.id.tv_health})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                confirmLocationDialog();
                break;
            case R.id.ll_search:
                goNewActivity(SearchWebViewActivity.class);
                break;
            case R.id.tv_saoma:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((context.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PHONE_PERMISSIONS);
                    } else {
                        goNewActivity(CaptureActivity.class);
                    }
                } else {
                    goNewActivity(CaptureActivity.class);
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goNewActivity(CaptureActivity.class);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initView() {
        rlRecommond.setVisibility(View.GONE);
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

        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {

                Iterator<HomeListData.DataData.HomeData> it = datas.iterator();
                while (it.hasNext()) {
                    String type = it.next().getType();
                    if (TextUtils.equals(Common.CONVERSATIONS, type)) {
                        it.remove();
                    }
                }
                getCoversationList();
                datas.addAll(newConversations);
                Logger.d(new Gson().toJson(datas));
                homeListAdapter.notifyDataSetChanged();
                return false;
            }
        });
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

    @Subscribe
    public void subscribeUpdate(LoginUpdateEventData data) {
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
                    if (!isLogin()) {
                        rlRecommond.setVisibility(View.VISIBLE);
                    } else {
                        if (data.getData().getSignedDoctros().size() != 0 ||
                                data.getData().getAttendingDoctors().size() != 0) {
                            rlRecommond.setVisibility(View.GONE);
                        } else {
                            rlRecommond.setVisibility(View.VISIBLE);
                        }
                    }
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
                    getCoversationList();
                    for (int i = 0; i < newConversations.size(); i++) {
                        String conversationIdentifier = newConversations.get(i).getIdentifier();
                        Iterator<HomeListData.DataData.HomeData> it = datas.iterator();
                        while (it.hasNext()) {
                            String datasIdentifier = it.next().getIdentifier();
                            if (TextUtils.equals(conversationIdentifier, datasIdentifier)) {
                                it.remove();
                            }
                        }
                    }
                    datas.addAll(newConversations);
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

    private void getCoversationList() {
        //获取会话列表
        if (isLogin()) {
            newConversations.clear();
            List<TIMConversation> conversations = TIMManagerExt.getInstance().getConversationList();
            Logger.e(new Gson().toJson(conversations));
            for (int i = 0; i < conversations.size(); i++) {
                if (TIMConversationType.System != conversations.get(i).getType()) {
                    TIMConversationExt conExt = new TIMConversationExt(conversations.get(i));
                    List<TIMMessage> timMessages = conExt.getLastMsgs(10);
                    Logger.e(new Gson().toJson(timMessages.get(0)));
                    //获取会话未读数
                    long num = conExt.getUnreadMessageNum();
                    HomeListData.DataData.HomeData homeData = new HomeListData.DataData.HomeData();
                    homeData.setIdentifier(conversations.get(i).getPeer());
                    homeData.setLastMessage(MessageFactory.getMessage(timMessages.get(0)));
                    homeData.setLasttime(TimeUtil.getTimeStr(timMessages.get(0).timestamp()) + "");
                    homeData.setType(Common.CONVERSATIONS);
                    if (0 != num) {
                        homeData.setUnReadMessage(num + "");
                    }
                    newConversations.add(homeData);
                }
            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

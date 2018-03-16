package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.ClinicActivityData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.Capture_activityActivityAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class SignactivityActivity extends BaseActivity {

    @Bind(R.id.recycleView_activity)
    RecyclerView recycleViewActivity;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.img_close)
    ImageView imgClose;

    private Capture_activityActivityAdapter capture_activityActivityAdapter;
    private String clinicId;
    private List<ClinicActivityData.DataData.ActivitiesData> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signactivity);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    @OnClick({R.id.tv_sign, R.id.img_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign:
                signActivities();
                break;
            case R.id.img_close:
                finish();
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("clinicId")) {
            clinicId = getIntent().getStringExtra("clinicId");
        }
        clinicActivities();
    }

    private void initView() {

        recycleViewActivity.setLayoutManager(new LinearLayoutManager(context));
        capture_activityActivityAdapter = new Capture_activityActivityAdapter(context);
        recycleViewActivity.setAdapter(capture_activityActivityAdapter);

    }

    /**
     * 查询医院活动
     */
    private void clinicActivities() {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.clinicActivities(clinicId, token), new BaseObserver<ClinicActivityData>(context) {
            @Override
            public void onResponse(ClinicActivityData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    activities.clear();
                    activities.addAll(data.getData().getActivities());
                    capture_activityActivityAdapter.setDatas(activities);
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }

    private void signActivities() {
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        String activityIds = "";
        for (int i = 0; i < activities.size(); i++) {
            if (TextUtils.equals("1", activities.get(i).getIsSignIn())) {
                activityIds += activities.get(i).getActivityId() + ",";
            }
        }
        if(TextUtils.isEmpty(activityIds)){
            showTost("请选择要签到的活动");
            return;
        }
        activityIds = activityIds.substring(0, activityIds.length() - 1);
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.signActivities(activityIds, token), new BaseObserver<BaseData>(context) {
            @Override
            public void onResponse(BaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("签到成功");
                    finish();
                } else {
                    showTost(data.getMessage() + "");
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
            }
        });
    }


}

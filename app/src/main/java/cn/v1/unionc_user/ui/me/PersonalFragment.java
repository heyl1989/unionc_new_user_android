package cn.v1.unionc_user.ui.me;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.otto.Subscribe;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.UpdatePersonalEventData;
import cn.v1.unionc_user.model.UserInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.view.CircleImageView;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends BaseFragment {


    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.img_person_avator)
    CircleImageView imgAvator;
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.tv_edit)
    TextView tvEdit;
    @Bind(R.id.img_user_state)
    ImageView imgUserState;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.tv_doctor_num)
    TextView tvDoctorNum;
    @Bind(R.id.tv_hospital_num)
    TextView tvHospitalNum;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;
    @Bind(R.id.tv_yaoqing)
    TextView tvYaoqing;
    @Bind(R.id.tv_kefu)
    TextView tvKefu;

    private String servicePhone = "";
    private String workingHours = "";
    private UserInfoData.DataData userInfo;

    public PersonalFragment() {
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
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserInfo();
    }

    @OnClick({R.id.img_back, R.id.tv_right, R.id.tv_edit, R.id.tv_yaoqing, R.id.tv_my_activity, R.id.tv_kefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.tv_right:
                goNewActivity(SettingActivity.class);
                break;
            case R.id.tv_edit:
                Intent intent = new Intent(context, EditUserInfoActivity.class);
                intent.putExtra("userInfo", (Serializable) userInfo);
                startActivity(intent);
                break;
            case R.id.tv_yaoqing:
                break;
            case R.id.tv_my_activity:
                goNewActivity(MyactivityActivity.class);
                break;
            case R.id.tv_kefu:
                PromptDialog contactDialog = new PromptDialog(context);
                contactDialog.show();
                contactDialog.setTitle(servicePhone + "");
                contactDialog.setMessage(workingHours + "");
                contactDialog.setTvConfirm("取消");
                contactDialog.setTvCancel("呼叫");
                contactDialog.setOnButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onConfirmClick() {
                    }

                    @Override
                    public void onCancelClick() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + servicePhone);
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    @Subscribe
    public void subscribeUpdate(LoginUpdateEventData data) {
        getUserInfo();
    }

    @Subscribe
    public void subscribeUpdate(UpdatePersonalEventData data) {
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getUserInfo((String) SPUtil.get(context, Common.USER_TOKEN, "")),
                new BaseObserver<UserInfoData>(context) {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(UserInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            servicePhone = data.getData().getServicePhone();
                            workingHours = data.getData().getWorkingHours();
                            userInfo = data.getData();
                            SPUtil.put(context, Common.USER_AVATOR, data.getData().getHeadImage());
                            Glide.with(context).load(data.getData().getHeadImage())
                                    .placeholder(R.drawable.icon_default_avator)
                                    .error(R.drawable.icon_default_avator)
                                    .into(imgAvator);
                            tvNumber.setText(data.getData().getUserName() + "");
                            tvDoctorNum.setText(data.getData().getDoctorCount() + "");
                            tvHospitalNum.setText(data.getData().getClinicCount() + "");
                            tvCommentNum.setText(data.getData().getEvaluateCount() + "");
                            if (data.getData().getIsCertification() == 0) {
                                imgUserState.setImageResource(R.drawable.icon_no_passed);
                                tvState.setText("未认证");
                                tvState.setTextColor(R.color.red_rz);
                            } else {
                                imgUserState.setImageResource(R.drawable.icon_passed);
                                tvState.setText("已认证");
                                tvState.setTextColor(R.color.qm_blue);
                            }

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

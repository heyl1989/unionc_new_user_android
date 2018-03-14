package cn.v1.unionc_user.ui.me;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.UpdatePersonalEventData;
import cn.v1.unionc_user.model.UserInfoData;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.CircleImageView;

public class EditUserInfoActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_avator)
    CircleImageView imgAvator;
    @Bind(R.id.rl_avator)
    RelativeLayout rlAvator;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.rl_nickname)
    RelativeLayout rlNickname;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.rl_sex)
    RelativeLayout rlSex;
    @Bind(R.id.img_user_state)
    ImageView imgUserState;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.rl_auth)
    RelativeLayout rlAuth;
    @Bind(R.id.tv_bindphone)
    TextView tvBindphone;
    @Bind(R.id.rl_bindphone)
    RelativeLayout rlBindphone;


    private UserInfoData.DataData userInfo;
    private String avator = "";
    private String nickname = "";
    private String sex = "";
    private final int AVATOR = 100;
    private final int NICKNAME = 200;
    private final int SEX = 300;
    private final int AUTH = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BusProvider.getInstance().post(new UpdatePersonalEventData(true));
    }

    @OnClick({R.id.img_back, R.id.rl_avator, R.id.rl_nickname, R.id.rl_sex, R.id.rl_auth, R.id.rl_bindphone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                BusProvider.getInstance().post(new UpdatePersonalEventData(true));
                break;
            case R.id.rl_avator:
                Intent avatorIntent = new Intent(context, UpdateAvatorActivity.class);
                avatorIntent.putExtra("avator", avator);
                startActivityForResult(avatorIntent, AVATOR);
                break;
            case R.id.rl_nickname:
                Intent nicknameIntent = new Intent(context, UpdateNicknameActivity.class);
                nicknameIntent.putExtra("nickname", nickname);
                startActivityForResult(nicknameIntent, NICKNAME);
                break;
            case R.id.rl_sex:
                Intent sexIntent = new Intent(context, UpdateSexActivity.class);
                sexIntent.putExtra("sex", sex);
                startActivityForResult(sexIntent, SEX);
                break;
            case R.id.rl_auth:
                Intent authIntent = new Intent(context, RealNameAuthActivity.class);
                startActivityForResult(authIntent, AUTH);
                break;
            case R.id.rl_bindphone:
                break;
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AVATOR) {
                try {
                    String avator = data.getStringExtra("avator");
                    Glide.with(context).load("file://" + avator)
                            .placeholder(R.drawable.icon_default_avator)
                            .error(R.drawable.icon_default_avator)
                            .into(imgAvator);
                } catch (Exception e) {

                }
            }
            if (requestCode == NICKNAME) {
                try {
                    String nickName = data.getStringExtra("nickName");
                    tvNickname.setText(nickName + "");
                } catch (Exception e) {

                }
            }
            if (requestCode == SEX) {
                try {
                    String sexData = data.getStringExtra("sex");
                    //性别（0：男 1：女）
                    if (TextUtils.equals("0", sexData)) {
                        tvSex.setText("男");
                    }
                    if (TextUtils.equals("1", sexData)) {
                        tvSex.setText("女");
                    }
                } catch (Exception e) {

                }
            }
            if (requestCode == AUTH) {
                try {
                    imgUserState.setImageResource(R.drawable.icon_no_passed);
                    tvState.setText("审核中");
                    tvState.setTextColor(R.color.red_rz);
                } catch (Exception e) {

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @SuppressLint("ResourceAsColor")
    private void initData() {
        if (getIntent().hasExtra("userInfo")) {
            userInfo = (UserInfoData.DataData) getIntent().getSerializableExtra("userInfo");
            if (null != userInfo) {
                avator = userInfo.getHeadImage();
                sex = userInfo.getGender();
                nickname = userInfo.getUserName();
                Glide.with(context).load(userInfo.getHeadImage())
                        .placeholder(R.drawable.icon_default_avator)
                        .error(R.drawable.icon_default_avator)
                        .into(imgAvator);
                tvNickname.setText(userInfo.getUserName() + "");
                //性别（0：男 1：女）
                if (TextUtils.equals("0", userInfo.getGender())) {
                    tvSex.setText("男");
                }
                if (TextUtils.equals("1", userInfo.getGender())) {
                    tvSex.setText("女");
                }
                if (userInfo.getIsCertification() == 0) {
                    imgUserState.setImageResource(R.drawable.icon_no_passed);
                    tvState.setText("未认证");
                    tvState.setTextColor(R.color.red_rz);
                } else {
                    imgUserState.setImageResource(R.drawable.icon_passed);
                    tvState.setText("已认证");
                    tvState.setTextColor(R.color.qm_blue);
                }
                tvBindphone.setText(userInfo.getTelphone() + "");
            }

        }
    }
}

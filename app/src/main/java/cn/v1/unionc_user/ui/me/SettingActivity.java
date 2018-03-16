package cn.v1.unionc_user.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.LoginActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_service_agreement)
    TextView tvServiceAgreement;
    @Bind(R.id.tv_about_us)
    TextView tvAboutUs;
    @Bind(R.id.tv_logout)
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.img_back, R.id.tv_service_agreement, R.id.tv_about_us, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_service_agreement:
                break;
            case R.id.tv_about_us:
                goNewActivity(AboutYIBASHIActivity.class);
                break;
            case R.id.tv_logout:
                PromptDialog logoutDialog = new PromptDialog(context);
                logoutDialog.show();
                logoutDialog.setTitle("退出登录");
                logoutDialog.setMessage("确认退出登录？");
                logoutDialog.setTvConfirm("确定");
                logoutDialog.setTvCancel("取消");
                logoutDialog.setOnButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onConfirmClick() {
                        logout();
                        goNewActivity(LoginActivity.class);
                    }

                    @Override
                    public void onCancelClick() {
                    }
                });
                break;
        }
    }


    private void initView() {
        tvTitle.setText("设置");
    }

}

package cn.v1.unionc_user.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class UpdateNicknameActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.img_close)
    ImageView imgClose;

    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickname);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick({R.id.img_back, R.id.tv_right, R.id.img_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
                updateUserInfo();
                break;
            case R.id.img_close:
                etNickname.setText("");
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("nickname")) {
            String nickname = getIntent().getStringExtra("nickname");
            etNickname.setText(nickname);
        }
    }

    /**
     * 修改用户信息
     */
    private void updateUserInfo() {
        showDialog("修改用户资料...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        nickName = etNickname.getText().toString().trim();
        ConnectHttp.connect(UnionAPIPackage.updateUserInfo(token, nickName, "", "", "", ""),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(nickName)) {
                                Intent intent = new Intent();
                                intent.putExtra("nickName", nickName + "");
                                setResult(Activity.RESULT_OK, intent);
                            }
                            finish();
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


}

package cn.v1.unionc_user.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import cn.v1.unionc_user.network_frame.UnionAPI;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class UpdateSexActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.cb_maile)
    CheckBox cbMaile;
    @Bind(R.id.cb_female)
    CheckBox cbFemale;

    private String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sex);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @OnClick({R.id.img_back, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_right:
                updateUserInfo();
                break;
        }
    }

    private void initView() {
        cbMaile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbFemale.setChecked(false);
                }
            }
        });

        cbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbMaile.setChecked(false);
                }
            }
        });
    }


    private void initData() {
        if (getIntent().hasExtra("sex")) {
            String sex = getIntent().getStringExtra("sex");
            //性别（0：男 1：女）
            if (TextUtils.equals("0", sex)) {
                cbMaile.setChecked(true);
            }
            if (TextUtils.equals("1", sex)) {
                cbFemale.setChecked(true);
            }
        }
    }

    /**
     * 修改用户信息
     */
    private void updateUserInfo() {
        showDialog("修改用户资料...");
        if (cbMaile.isChecked()) {
            sex = "0";
        }
        if (cbFemale.isChecked()) {
            sex = "1";
        }
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.updateUserInfo(token, "", "", "", sex, ""),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (!TextUtils.isEmpty(sex)) {
                                Intent intent = new Intent();
                                intent.putExtra("sex", sex + "");
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

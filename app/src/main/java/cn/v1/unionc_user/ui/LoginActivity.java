package cn.v1.unionc_user.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.BusProvider;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.LoginData;
import cn.v1.unionc_user.model.LoginUpdateEventData;
import cn.v1.unionc_user.model.TIMSigData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;


public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.img_check_agreement)
    ImageView imgCheckAgreement;
    @Bind(R.id.tv_agreement)
    TextView tvAgreement;

    private boolean runningThree;
    private boolean isCheckAgreement;
    private String from;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }
    }

    @OnClick({R.id.img_back, R.id.tv_code, R.id.tv_login, R.id.img_check_agreement, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_code:
                String phoneNumber = etPhone.getText().toString().trim();
                if (runningThree) {
                } else {
                    if (TextUtils.isEmpty(phoneNumber)) {
                        showTost("请填写手机号码");
                    } else {
                        downTimer.start();
                        getAuthCode(phoneNumber);
                    }
                }
                break;
            case R.id.tv_login:
                String phoneNumberlogin = etPhone.getText().toString().trim();
                String authCode = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumberlogin)) {
                    showTost("请填写手机号码");
                    return;
                }
                if (TextUtils.isEmpty(authCode)) {
                    showTost("请填写验证码");
                    return;
                }
                if (!isCheckAgreement) {
                    showTost("请同意用户协议");
                    return;
                }
                login(phoneNumberlogin, authCode);
                break;
            case R.id.img_check_agreement:
                if (isCheckAgreement) {
                    isCheckAgreement = false;
                    imgCheckAgreement.setImageResource(R.drawable.agreement_circle);
                } else {
                    isCheckAgreement = true;
                    imgCheckAgreement.setImageResource(R.drawable.icon_check);
                }
                break;
            case R.id.tv_agreement:
                goNewActivity(LoginAgreementActivity.class);
                break;
        }
    }

    private void initView() {
        isCheckAgreement = false;
        imgCheckAgreement.setImageResource(R.drawable.agreement_circle);
        tvAgreement.setText(Html.fromHtml("<font color=\'#9B9CA0\'>我同意并遵守《</font>" +
                "<font color=\'#4e78f6\'>用户使用协议</font>" +
                "<font color=\'#9B9CA0\'>》</font>"));
    }


    /**
     * 获取验证码
     */
    private void getAuthCode(String phoneNumber) {
        showDialog("发送中...");
        ConnectHttp.connect(UnionAPIPackage.getAuthCode(phoneNumber), new BaseObserver<BaseData>(context) {

            @Override
            public void onResponse(BaseData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    showTost("验证码发送成功");
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

    /**
     * 登录
     *
     * @param phoneNumber
     * @param authCode
     */
    private void login(final String phoneNumber, String authCode) {
        showDialog("登录中...");
        ConnectHttp.connect(UnionAPIPackage.login(phoneNumber, authCode), new BaseObserver<LoginData>(context) {

            @Override
            public void onResponse(LoginData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    String identifier = data.getData().getIdentifier() + "";
                    SPUtil.put(context, Common.IDENTIFIER, identifier);
                    SPUtil.put(context, Common.USER_PHONE, phoneNumber);
                    getTIMSig(data.getData().getToken(), identifier);
                } else {
                    showTost(data.getMessage());
                }
            }

            @Override
            public void onFail(Throwable e) {
                closeDialog();
                showTost("登录失败");
            }
        });
    }

    /**
     * 获取TIM sig
     */
    private void getTIMSig(final String token, final String identifier) {
        showDialog("登录中...");
        ConnectHttp.connect(UnionAPIPackage.getTIMSig(token, identifier), new BaseObserver<TIMSigData>(context) {

            @Override
            public void onResponse(TIMSigData data) {
                closeDialog();
                if (TextUtils.equals("4000", data.getCode())) {
                    // identifier为用户名，userSig 为用户登录凭证
                    SPUtil.put(context, Common.TIM_SIG, data.getData().getTls());
                    initTIMUserConfig();
                    TIMManager.getInstance().login(identifier, data.getData().getTls(), new TIMCallBack() {
                        @Override
                        public void onError(int code, String desc) {
                            //错误码code和错误描述desc，可用于定位请求失败原因
                            //错误码code列表请参见错误码表
                            Logger.e("login failed. code: " + code + " errmsg: " + desc);
                            showTost(desc + "");
                        }

                        @Override
                        public void onSuccess() {
                            Logger.d("login succ");
                            showTost("登录成功");
                            if (TextUtils.equals("start", from)) {
                                goNewActivity(MainActivity.class);
                            }
                            login(token);
                            //通知首页和我的页面刷新数据
                            BusProvider.getInstance().post(new LoginUpdateEventData(true));
                            finish();
                        }
                    });
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


    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            tvCode.setText(Html.fromHtml("<font color=\'#ff0000\'> " + (l / 1000) + "</font>" +
                    "<font color=\'#9B9CA0\'>s后重新获取</font>"));
        }

        @Override
        public void onFinish() {
            runningThree = false;
            tvCode.setText("重新发送");
        }
    };


}

package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.imsdk.TIMConversationType;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.IsDoctorSignData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.UnioncURL;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.me.RealNameAuthActivity;
import cn.v1.unionc_user.view.PromptDialog;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

public class SignDoctorAgreeMentWebViewActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.webview_sign_doctor)
    WebView webviewSignDoctor;
    @Bind(R.id.tv_sign)
    TextView tvSign;


    private String doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_doctor_agree_ment_web_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @OnClick({R.id.img_back, R.id.tv_sign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_sign:
                PromptDialog signDoctor = new PromptDialog(context);
                signDoctor.show();
                signDoctor.setTitle("签约医生");
                signDoctor.setMessage("将这位医生签约为家庭医生?");
                signDoctor.setTvCancel("签约");
                signDoctor.setTvConfirm("取消");
                signDoctor.setOnButtonClickListener(new OnButtonClickListener() {
                    @Override
                    public void onConfirmClick() {
                    }

                    @Override
                    public void onCancelClick() {
                        isCertification();
                    }
                });
                break;
        }
    }

    private void initData() {
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
        isDoctorSign();
    }


    private void initView() {
        tvTitle.setText("签约家庭医生");
        webviewSignDoctor.loadUrl("http://192.168.21.93:8080/unionApp/H5/index.html");
        webviewSignDoctor.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 查询是否实名认证
     */
    private void isCertification() {
        showTost("查询用户信息...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.isCertification(token),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            signDoctor();
                        } else if (TextUtils.equals("4021", data.getCode())) {
                            gotoAuthDialog();
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
     * 签约医生
     */
    private void signDoctor() {
        showDialog("签约医生...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.signDoctor(token, doctorId),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            showTost("签约成功");
                            finish();
                        } else if (TextUtils.equals("4021", data.getCode())) {
                            gotoAuthDialog();
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
     * 查询医生是否签约
     */
    private void isDoctorSign() {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.isDoctorSign(token, doctorId),
                new BaseObserver<IsDoctorSignData>(context) {
                    @Override
                    public void onResponse(IsDoctorSignData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            if (TextUtils.equals("1", data.getData().getIsSigned())) {
                                tvSign.setVisibility(View.GONE);
                            } else {
                                tvSign.setVisibility(View.VISIBLE);
                            }
                        } else if (TextUtils.equals("4021", data.getCode())) {
                            gotoAuthDialog();
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


    private void gotoAuthDialog() {
        PromptDialog signDoctor = new PromptDialog(context);
        signDoctor.show();
        signDoctor.setTitle("实名认证");
        signDoctor.setMessage("签约家庭医生需要实名认证，先去实名认证?");
        signDoctor.setTvCancel("确定");
        signDoctor.setTvConfirm("取消");
        signDoctor.setOnButtonClickListener(new OnButtonClickListener() {
            @Override
            public void onConfirmClick() {
            }

            @Override
            public void onCancelClick() {
                goNewActivity(RealNameAuthActivity.class);
            }
        });
    }
}

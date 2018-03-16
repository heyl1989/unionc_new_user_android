package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
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
import cn.v1.unionc_user.view.PromptOnebtnDialog;
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
                signDoctor.setTitle("小巴提示：");
                signDoctor.setMessage("确认申请签约家医服务?");
                signDoctor.setTvCancel("确定");
                signDoctor.setTvConfirm("关闭");
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
        showDialog("加载中...");
        webviewSignDoctor.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    closeDialog();
                }
            }
        });
        webviewSignDoctor.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//设置支持https
            }
        });
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webviewSignDoctor.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        WebSettings webSettings = webviewSignDoctor.getSettings();
        //多窗口
        webSettings.supportMultipleWindows();
        //获取触摸焦点
        webviewSignDoctor.requestFocusFromTouch();
        //允许访问文件
        webSettings.setAllowFileAccess(true);
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webviewSignDoctor.loadUrl("http://192.168.21.93:8080/unionApp/H5/index.html");
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
                            String signState = data.getData().getIsSigned();
                            if (TextUtils.equals("1", signState)) {
                                //已签约
                                showPromptDialog("恭喜您，已签约家庭医生！");
                                tvSign.setVisibility(View.GONE);
                            } else if (TextUtils.equals("-1", signState)) {
                                //审核
                                showPromptDialog("申请已提交，审核结果将在信息提交成功后的48小时内反馈，" +
                                "注意手机保持开机，当地医院会与您电话联系！");
                            } else if (TextUtils.equals("0", signState)) {
                                //可以签约
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

    private void showPromptDialog(String message){
        PromptOnebtnDialog promptOnebtnDialog = new PromptOnebtnDialog(context){
            @Override
            public void onClosed() {

            }
        };
        promptOnebtnDialog.show();
        promptOnebtnDialog.setTitle("小巴提示：");
        promptOnebtnDialog.setMessage(message);
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

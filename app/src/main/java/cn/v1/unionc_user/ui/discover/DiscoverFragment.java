package cn.v1.unionc_user.ui.discover;


import android.app.ProgressDialog;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.utils.NetWorkUtils;

import static cn.v1.unionc_user.data.Common.APP_CACAHE_DIRNAME;
import static cn.v1.unionc_user.data.Common.QM_SET;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends BaseFragment {


    @Bind(R.id.webview)
    WebView webview;

    private ProgressDialog progressdialog;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    private final String url = "https://192.168.21.93:8085/unionApp/page/index.html";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showDialog("加载中...");
        webViewConfigure();
    }

    /**
     * 配置webview
     */
    private void webViewConfigure() {
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    closeDialog();
                }
            }
        });
        webview.setWebViewClient(new WebViewClient() {

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
            webview.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        WebSettings webSettings = webview.getSettings();
        //多窗口
        webSettings.supportMultipleWindows();
        //获取触摸焦点
        webview.requestFocusFromTouch();
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
        webview.loadUrl(url);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (webview != null) {
            webview.clearHistory();
            webview.destroy();
            webview = null;
        }
    }
}

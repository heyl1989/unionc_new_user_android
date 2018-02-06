package cn.v1.unionc_user.ui.discover;


import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
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

    public DiscoverFragment() {
        // Required empty public constructor
    }


    private final String url = "https://192.168.11.216:8085/unionApp/page/index.html";


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
        webViewConfigure();
    }

    /**
     * 配置webview
     */
    private void webViewConfigure() {
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
        //设置webview中缓存
        if (NetWorkUtils.isNetworkConnected(getActivity())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        if(!SPUtil.contains(getActivity(),QM_SET)){
            String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + File.separator + APP_CACAHE_DIRNAME;
            Logger.e(cacheDirPath);
            webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
            SPUtil.put(getActivity(), QM_SET,true);
        }
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

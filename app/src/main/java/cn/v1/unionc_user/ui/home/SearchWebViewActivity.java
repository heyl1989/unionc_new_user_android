package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.network_frame.UnioncURL;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class SearchWebViewActivity extends BaseActivity {

    @Bind(R.id.webview_search)
    WebView webviewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_web_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        webviewSearch.loadUrl("http://192.168.21.93:8080/unionApp/page/index.html#/city");
        webviewSearch.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}

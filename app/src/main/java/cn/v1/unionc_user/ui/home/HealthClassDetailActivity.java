package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class HealthClassDetailActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.tv_recommend)
    TextView tvRecommend;
    @Bind(R.id.img_follow)
    ImageView imgFollow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_class_detail);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.img_ear, R.id.ll_recommend, R.id.ll_follow, R.id.ll_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.img_ear:
                break;
            case R.id.ll_recommend:
                break;
            case R.id.ll_follow:
                break;
            case R.id.ll_comment:
                break;
        }
    }
}

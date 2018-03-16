package cn.v1.unionc_user.ui.me;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.MobileConfigUtil;

public class AboutYIBASHIActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yibashi);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    private void initView() {
        tvTitle.setText("关于医巴士");
        tvVersion.setText("医巴士"+ MobileConfigUtil.getVersionName());
    }

}

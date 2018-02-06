package cn.v1.unionc_user;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class StartActivity extends BaseActivity {

    @Bind(R.id.rl_start)
    RelativeLayout rlStart;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goNewActivity(MainActivity.class);
                finish();
            }
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

package cn.v1.unionc_user;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity {

    @Bind(R.id.rl_start)
    RelativeLayout rlStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goNewActivity(MainActivity.class);
                finish();
            }
        },2000);
    }
}

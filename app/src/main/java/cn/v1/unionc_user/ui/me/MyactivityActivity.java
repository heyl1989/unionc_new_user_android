package cn.v1.unionc_user.ui.me;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.adapter.ActivityAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class MyactivityActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cb_had_register)
    CheckBox cbHadRegister;
    @Bind(R.id.cb_collect)
    CheckBox cbCollect;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivity);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }


    private void initView() {
        tvTitle.setText("我的活动");
        cbHadRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbHadRegister.setClickable(false);
                    cbCollect.setChecked(false);
                } else {
                    cbHadRegister.setClickable(true);
                }
            }
        });
        cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbCollect.setClickable(false);
                    cbHadRegister.setChecked(false);
                } else {
                    cbCollect.setClickable(true);
                }
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        ActivityAdapter activityAdapter = new ActivityAdapter(context);
        recycleView.setAdapter(activityAdapter);
    }


}

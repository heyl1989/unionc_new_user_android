package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.adapter.HospitalDoctorAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.view.ScrollListView;

public class HospitalDetailActivity extends BaseActivity {

    @Bind(R.id.tv_summary)
    TextView tvSummary;
    @Bind(R.id.tv_open)
    TextView tvOpen;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.img_hospital)
    ImageView imgHospital;
    @Bind(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @Bind(R.id.labels)
    LabelsView labels;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.img_dial)
    ImageView imgDial;
    @Bind(R.id.listView)
    ScrollListView listView;
    private int mLines;

    private HospitalDoctorAdapter hospitalDoctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.img_back, R.id.img_share, R.id.img_dial})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
            case R.id.img_dial:
                break;
        }
    }

    private void initView() {
        tvSummary.post(new Runnable() {
            @Override
            public void run() {
                mLines = tvSummary.getLineCount();
                if (mLines > 1) {
                    tvSummary.setMaxLines(2);
                    tvSummary.setEllipsize(TextUtils.TruncateAt.END);
                    tvOpen.setVisibility(View.VISIBLE);
                }
                tvOpen.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        tvSummary.setMaxLines(mLines);
                        tvOpen.setVisibility(View.GONE);
                    }
                });
            }
        });
        listView.setFocusable(false);
        hospitalDoctorAdapter = new HospitalDoctorAdapter(context);
        listView.setAdapter(hospitalDoctorAdapter);
    }


}

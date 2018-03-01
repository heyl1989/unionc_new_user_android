package cn.v1.unionc_user.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.utils.DP_PX;
import cn.v1.unionc_user.view.CircleImageView;
import cn.v1.unionc_user.view.ObserverScrollView;

public class DoctorDetailActivity extends BaseActivity {

    @Bind(R.id.tab)
    TabLayout tabLayout;
    @Bind(R.id.vp_doctor)
    ViewPager vpDoctor;
    @Bind(R.id.scrollView)
    ObserverScrollView scrollView;
    @Bind(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @Bind(R.id.img_doctor_avator)
    CircleImageView imgDoctorAvator;
    @Bind(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @Bind(R.id.tv_department)
    TextView tvDepartment;
    @Bind(R.id.tv_hospital)
    TextView tvHospital;
    @Bind(R.id.tv_summary)
    TextView tvSummary;
    @Bind(R.id.tv_open)
    TextView tvOpen;


    private String[] tabItemText = new String[]{"Ta回答的问题", "坐诊时间"};
    private int[] tabItemIcon = new int[]{R.drawable.selector_doctor_tab_1, R.drawable.selector_doctor_tab_2};
    private List<Fragment> doctorDetailFragments = new ArrayList<>();
    private String doctorId;
    private int mLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @OnClick({R.id.img_back, R.id.img_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                break;
        }
    }

    private void initView() {
        //处理tablayout中间的分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(context, R.drawable.divider_line));
        linearLayout.setDividerPadding(DP_PX.dip2px(context, 15));
        //tablayout添加自定义布局
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //依次获取标签
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //为每个标签设置布局
            tab.setCustomView(R.layout.layout_tabitem_doctor);
            ImageView icon = tab.getCustomView().findViewById(R.id.img_icon);
            icon.setImageResource(tabItemIcon[i]);
            TextView title = tab.getCustomView().findViewById(R.id.tv_title);
            title.setText(tabItemText[i]);
        }
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        //viewpager初始化
        DoctorDetailPageAdapter doctorDetailPageAdapter = new DoctorDetailPageAdapter(getSupportFragmentManager());
        vpDoctor.setAdapter(doctorDetailPageAdapter);
        doctorDetailFragments.add(DoctorAnswerFragment.newInstance());
        doctorDetailFragments.add(DoctorOnlineFragment.newInstance());
        //滑动监听
        scrollView.setOnScrollChangedListener(new ObserverScrollView.OnScrollChangedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onScrollChanged(ObserverScrollView scrollView, int l, int t, int oldl, int oldt) {
                if (DP_PX.dip2px(context, 44) < t) {
                    rlToolbar.setBackgroundResource(R.color.qm_blue);
                } else {
                    rlToolbar.setBackgroundResource(Color.TRANSPARENT);
                }
            }
        });
        //展开
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
    }


    private void initData() {
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
        getDoctorInfo();
    }


    /**
     * 获取医生信息
     */
    private void getDoctorInfo() {
        showDialog("加载中...");
        ConnectHttp.connect(UnionAPIPackage.getDoctorInfo(doctorId,
                (String) SPUtil.get(context, Common.LONGITUDE, ""),
                (String) SPUtil.get(context, Common.LATITUDE, "")
                ),
                new BaseObserver<DoctorInfoData>(context) {
                    @Override
                    public void onResponse(DoctorInfoData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            bindData(data);
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

    private void bindData(DoctorInfoData data) {
        DoctorInfoData.DataData.DoctorsData doctorsData = data.getData().getDoctors();
        Glide.with(context).load(doctorsData.getImagePath()).into(imgDoctorAvator);
        tvDoctorName.setText(doctorsData.getDoctorName() + "");
        tvDepartment.setText(doctorsData.getDepartName() + "  " + doctorsData.getProfessLevel());
        tvHospital.setText(doctorsData.getFirstClinicName()+"");
    }


    /**
     * tablayout选择监听
     */
    TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            vpDoctor.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    @OnClick(R.id.tv_open)
    public void onClick() {
    }

    class DoctorDetailPageAdapter extends FragmentPagerAdapter {

        public DoctorDetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return doctorDetailFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItemText[position];
        }
    }

}

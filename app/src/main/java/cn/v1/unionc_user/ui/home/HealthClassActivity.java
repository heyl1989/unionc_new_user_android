package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class HealthClassActivity extends BaseActivity {

    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.vp_content)
    ViewPager vpContent;

    private List<Fragment> healthFragment = new ArrayList<>();
    private String[] tabItems = new String[]{"热门", "关注"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_class);
        ButterKnife.bind(this);
        initView();
    }


    @OnClick(R.id.img_back)
    public void onClick() {
        finish();
    }

    private void initView() {
        tab.setupWithViewPager(vpContent);
        HealthClassPageAdapter healthClassPageAdapter = new HealthClassPageAdapter(getSupportFragmentManager());
        vpContent.setAdapter(healthClassPageAdapter);
        healthFragment.add(HealthClassHotFragment.newInstance());
        healthFragment.add(HealthClassFollowFragment.newInstance());

    }

    class HealthClassPageAdapter extends FragmentPagerAdapter {

        public HealthClassPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return healthFragment.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabItems[position];
        }
    }


}

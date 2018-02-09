package cn.v1.unionc_user.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;
import cn.v1.unionc_user.ui.discover.DiscoverFragment;
import cn.v1.unionc_user.ui.me.PersonalFragment;
import cn.v1.unionc_user.ui.message.MessageFragment;

public class MainActivity extends BaseActivity {

    @Bind(R.id.rg)
    RadioGroup rg;

    private Fragment mCurrentfragment;//记录选中的fragment
    private int mCurrentCheckedId;//记录选中的id
    private MessageFragment messageFragment;
    private DiscoverFragment discoverFragment;
    private PersonalFragment personalFragment;
    private final String MESSAGE = "message";
    private final String DISCOVER = "discover";
    private final String PERSONAL = "personal";
    private String[] tags = new String[]{MESSAGE, DISCOVER, PERSONAL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        getRongToken();
//        initLocation();
        initView();
    }

    private void initView() {
        rg.check(R.id.message);
        mCurrentCheckedId = R.id.message;
        stateCheck(outState);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.message:
                        mCurrentCheckedId = R.id.message;
                        if (null == messageFragment) {
                            messageFragment = new MessageFragment();
                        }
                        switchContent(messageFragment, 0);
                        break;
                    case R.id.discover:
                        mCurrentCheckedId = R.id.discover;
                        if (null == discoverFragment) {
                            discoverFragment = new DiscoverFragment();
                        }
                        switchContent(discoverFragment, 1);
                        break;
                    case R.id.personal:
                        if (null == personalFragment) {
                            personalFragment = new PersonalFragment();
                        }
                        if (isLogin()) {
                            switchContent(personalFragment, 2);
                        } else {
                            goNewActivity(LoginActivity.class);
                            rg.check(mCurrentCheckedId);
                        }
                        break;
                }
            }
        });
    }

    /**
     * fragment 切换
     *
     * @param to
     */
    public void switchContent(Fragment to, int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentfragment != to) {
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mCurrentfragment)
                        .add(R.id.ll_fragment_container, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mCurrentfragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
        mCurrentfragment = to;
    }

    /**
     * 状态检测 用于内存不足时的时候保证fragment不会重叠
     */
    private void stateCheck(Bundle saveInstanceState) {
        Logger.i(new Gson().toJson(saveInstanceState));
        if (null != saveInstanceState) {
            //通过tag找回失去引用但是存在内存中的fragment.id相同
            MessageFragment messageFragment = (MessageFragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
            DiscoverFragment discoverFragment = (DiscoverFragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
            PersonalFragment personalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentByTag(tags[2]);
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(messageFragment)
                    .hide(discoverFragment)
                    .hide(personalFragment)
                    .commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (null == messageFragment) {
                messageFragment = new MessageFragment();
            }
            transaction.add(R.id.ll_fragment_container, messageFragment, tags[0]).commit();
        }
        mCurrentfragment = messageFragment;
    }

}

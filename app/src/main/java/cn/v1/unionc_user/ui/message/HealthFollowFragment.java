package cn.v1.unionc_user.ui.message;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseFragment;

/**
 * A simple {@link BaseFragment} subclass.
 * 关注热门课堂
 */
public class HealthFollowFragment extends BaseFragment {

    public static HealthFollowFragment newInstance() {
        HealthFollowFragment fragment = new HealthFollowFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_follow, container, false);
    }

}

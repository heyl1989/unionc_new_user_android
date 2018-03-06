package cn.v1.unionc_user.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.adapter.DoctorAnswerAdapter;
import cn.v1.unionc_user.ui.adapter.HospitalDoctorAdapter;
import cn.v1.unionc_user.ui.base.BaseFragment;
import cn.v1.unionc_user.view.ScrollListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorAnswerFragment extends BaseFragment {


    @Bind(R.id.listView)
    ScrollListView listView;

    public static DoctorAnswerFragment newInstance() {
        DoctorAnswerFragment fragment = new DoctorAnswerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_answer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        listView.setFocusable(false);
        DoctorAnswerAdapter doctorAnswerAdapter = new DoctorAnswerAdapter(context);
        listView.setAdapter(doctorAnswerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

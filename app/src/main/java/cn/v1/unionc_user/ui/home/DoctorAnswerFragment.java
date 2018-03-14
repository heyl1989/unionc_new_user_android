package cn.v1.unionc_user.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.DoctorInfoData;
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

    private List<DoctorInfoData.DataData.QuestionsData> questionsDataList = new ArrayList<>();
    private DoctorAnswerAdapter doctorAnswerAdapter;
    private String doctorId = "";

    public static DoctorAnswerFragment newInstance(String doctorId) {
        DoctorAnswerFragment fragment = new DoctorAnswerFragment();
        Bundle args = new Bundle();
        args.putString("doctorId", doctorId);
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String doctorId = getArguments().getString("doctorId");
            if (null != doctorId) {
                this.doctorId = doctorId;
            }
        }
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
        doctorAnswerAdapter = new DoctorAnswerAdapter(context,doctorId);
        listView.setAdapter(doctorAnswerAdapter);
    }


    public void setData(List<DoctorInfoData.DataData.QuestionsData> questionsDataList) {
        this.questionsDataList = questionsDataList;
        doctorAnswerAdapter.setData(questionsDataList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

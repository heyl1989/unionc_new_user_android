package cn.v1.unionc_user.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.v1.unionc_user.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorOnlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorOnlineFragment extends Fragment {

    public static DoctorOnlineFragment newInstance() {
        DoctorOnlineFragment fragment = new DoctorOnlineFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_online, container, false);
    }

}

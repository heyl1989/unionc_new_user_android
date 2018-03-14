package cn.v1.unionc_user.ui.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.DoctorScheduleData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorOnlineFragment#} factory method to
 * create an instance of this fragment.
 */
public class DoctorOnlineFragment extends BaseFragment {

    @Bind(R.id.tv_preview)
    TextView tvPreview;
    @Bind(R.id.tv_next)
    TextView tvNext;
    @Bind(R.id.tv_day1)
    TextView tvDay1;
    @Bind(R.id.tv_day2)
    TextView tvDay2;
    @Bind(R.id.tv_day3)
    TextView tvDay3;
    @Bind(R.id.tv_day4)
    TextView tvDay4;
    @Bind(R.id.tv_day5)
    TextView tvDay5;
    @Bind(R.id.tv_day6)
    TextView tvDay6;
    @Bind(R.id.tv_day7)
    TextView tvDay7;
    @Bind(R.id.tv_am1)
    TextView tvAm1;
    @Bind(R.id.tv_am2)
    TextView tvAm2;
    @Bind(R.id.tv_am3)
    TextView tvAm3;
    @Bind(R.id.tv_am4)
    TextView tvAm4;
    @Bind(R.id.tv_am5)
    TextView tvAm5;
    @Bind(R.id.tv_am6)
    TextView tvAm6;
    @Bind(R.id.tv_am7)
    TextView tvAm7;
    @Bind(R.id.tv_pm1)
    TextView tvPm1;
    @Bind(R.id.tv_pm2)
    TextView tvPm2;
    @Bind(R.id.tv_pm3)
    TextView tvPm3;
    @Bind(R.id.tv_pm4)
    TextView tvPm4;
    @Bind(R.id.tv_pm5)
    TextView tvPm5;
    @Bind(R.id.tv_pm6)
    TextView tvPm6;
    @Bind(R.id.tv_pm7)
    TextView tvPm7;
    @Bind(R.id.tv_night1)
    TextView tvNight1;
    @Bind(R.id.tv_night2)
    TextView tvNight2;
    @Bind(R.id.tv_night3)
    TextView tvNight3;
    @Bind(R.id.tv_night4)
    TextView tvNight4;
    @Bind(R.id.tv_night5)
    TextView tvNight5;
    @Bind(R.id.tv_night6)
    TextView tvNight6;
    @Bind(R.id.tv_night7)
    TextView tvNight7;


    private String doctorId = "";
    private TextView[] dayView;
    private TextView[] amView;
    private TextView[] pmView;
    private TextView[] nightView;

    public static DoctorOnlineFragment newInstance(String doctorId) {
        DoctorOnlineFragment fragment = new DoctorOnlineFragment();
        Bundle args = new Bundle();
        args.putString("doctorId", doctorId);
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
        View view = inflater.inflate(R.layout.fragment_doctor_online, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_preview, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_preview:
                break;
            case R.id.tv_next:
                break;
        }
    }


    private void initView() {
        tvPreview.setText("<上一周");
        tvNext.setText("下一周>");
        dayView = new TextView[]{tvDay1, tvDay2, tvDay3, tvDay4, tvDay5, tvDay6, tvDay7};
        amView = new TextView[]{tvAm1, tvAm2, tvAm3, tvAm4, tvAm5, tvAm6, tvAm7};
        pmView = new TextView[]{tvPm1, tvPm2, tvPm3, tvPm4, tvPm5, tvPm6, tvPm7};
        nightView = new TextView[]{tvNight1, tvNight2, tvNight3, tvNight4, tvNight5, tvNight6, tvNight7};
    }


    private void initData(int pageNum) {
        showDialog("加载中...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.doctorSchedule(token, doctorId, pageNum + ""),
                new BaseObserver<DoctorScheduleData>(context) {
                    @Override
                    public void onResponse(DoctorScheduleData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            List<DoctorScheduleData.DataData.DaysData> dayData = data.getData().getDays();
                            for (int i = 0; i < dayData.size(); i++) {
                                dayView[i].setText(dayData.get(i).getScheduleDate() + "\n" + dayData.get(i).getWeek());
                            }

                        } else {
                            showTost(data.getMessage() + "");
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        closeDialog();
                    }
                });

    }

}

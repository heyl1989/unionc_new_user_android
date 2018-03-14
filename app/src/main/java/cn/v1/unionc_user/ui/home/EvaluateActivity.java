package cn.v1.unionc_user.ui.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.model.BaseData;
import cn.v1.unionc_user.model.DoctorEvaluateData;
import cn.v1.unionc_user.network_frame.ConnectHttp;
import cn.v1.unionc_user.network_frame.UnionAPIPackage;
import cn.v1.unionc_user.network_frame.core.BaseObserver;
import cn.v1.unionc_user.ui.adapter.EvaluateAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class EvaluateActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.recycleView_evaluate)
    RecyclerView recycleViewEvaluate;
    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.tv_send)
    TextView tvSend;

    private EvaluateAdapter evaluateAdapter;
    private String doctorId = "";
    private List<DoctorEvaluateData.DataData.EvaluatesData> evaluates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        initData();
        initView();
        getEvaluates();
    }

    @OnClick({R.id.img_back, R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_send:
                saveDoctorEvaluate();
                break;
        }
    }


    private void initView() {
        recycleViewEvaluate.setLayoutManager(new LinearLayoutManager(context));
        evaluateAdapter = new EvaluateAdapter(context);
        recycleViewEvaluate.setAdapter(evaluateAdapter);
    }

    private void initData() {
        if (getIntent().hasExtra("doctorId")) {
            doctorId = getIntent().getStringExtra("doctorId");
        }
    }


    /**
     * 获取医生评论
     */
    private void getEvaluates() {
        showDialog("提交...");
        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        ConnectHttp.connect(UnionAPIPackage.doctorevaluates(token, "1", "1", "10", doctorId),
                new BaseObserver<DoctorEvaluateData>(context) {
                    @Override
                    public void onResponse(DoctorEvaluateData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            evaluates.clear();
                            evaluates.addAll(data.getData().getEvaluates());
                            evaluateAdapter.setDatas(evaluates);
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


    /**
     * 保存医生评论
     */
    private void saveDoctorEvaluate() {

        String token = (String) SPUtil.get(context, Common.USER_TOKEN, "");
        String content = etInput.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        showDialog("提交评论...");
        ConnectHttp.connect(UnionAPIPackage.saveDoctorEvaluate(token, doctorId, content),
                new BaseObserver<BaseData>(context) {
                    @Override
                    public void onResponse(BaseData data) {
                        closeDialog();
                        if (TextUtils.equals("4000", data.getCode())) {
                            etInput.setText("");
                            getEvaluates();
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

}

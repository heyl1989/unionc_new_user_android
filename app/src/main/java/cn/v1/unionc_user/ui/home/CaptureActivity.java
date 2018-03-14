package cn.v1.unionc_user.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class CaptureActivity extends BaseActivity {

    @Bind(R.id.scanner_view)
    ScannerView mScannerView;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_light)
    TextView tvLight;
    @Bind(R.id.tv_sao)
    TextView tvSao;
    @Bind(R.id.tv_input)
    TextView tvInput;

    private boolean lightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }


    private void init() {
        mScannerView.setScannerOptions(new ScannerOptions.Builder()
                .setLaserLineColor(R.color.qm_blue)
                .setFrameCornerColor(R.color.qm_blue)
                .setFrameCornerWidth(5)
                .setMediaResId(R.raw.beep).build());
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                Logger.d(new Gson().toJson(rawResult));
                Logger.d(new Gson().toJson(parsedResult));
                Logger.d(new Gson().toJson(barcode));
                String text = rawResult.getText();
                if (rawResult.getText().contains("unionWeb/activity/clinic-activities")) {
                    //医院二维码
                    try {
                        String[] splitText1 = text.split("clinicId=\"");
                        String[] splitText2 = splitText1[1].split("\"}");
                        String clinicId = splitText2[0];
                    } catch (Exception e) {

                    }

                }
                if (rawResult.getText().contains("unionWeb/scanDoctQRCode.jsp")) {
                    //医生二维码
                    try {
                        String doctId;
                        String[] splitText1 = text.split("doctId=");
                        Logger.d(Arrays.toString(splitText1));
                        if (splitText1[1].contains("&")) {
                            String[] splitText2 = splitText1[1].split("&");
                            doctId = splitText2[0];
                        } else {
                            doctId = splitText1[1];
                        }
                        if(doctId.contains("\"")){
                            doctId =  doctId.replaceAll("\"","");
                        }
                        Intent intent = new Intent(context, DoctorDetailActivity.class);
                        intent.putExtra("doctorId", doctId);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {

                    }
                }

                mScannerView.restartPreviewAfterDelay(1000);
            }
        });
    }


    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @OnClick({R.id.img_back, R.id.tv_light, R.id.tv_sao, R.id.tv_input})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_light:
                if (lightOn) {
                    mScannerView.toggleLight(false);
                    lightOn = false;
                } else {
                    mScannerView.toggleLight(true);
                    lightOn = true;
                }
                break;
            case R.id.tv_sao:
                break;
            case R.id.tv_input:
                break;
        }
    }
}

package cn.v1.unionc_user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class CaptureActivity extends BaseActivity {

    @Bind(R.id.scanner_view)
    ScannerView mScannerView;

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
        mScannerView.setScannerOptions(new ScannerOptions.Builder().setMediaResId(R.raw.beep).build());
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                Logger.d(new Gson().toJson(rawResult));
                Logger.d(new Gson().toJson(parsedResult));
                Logger.d(new Gson().toJson(barcode));
                mScannerView.restartPreviewAfterDelay(1000);
            }
        });
    }


    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

}

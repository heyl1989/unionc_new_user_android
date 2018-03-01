package cn.v1.unionc_user.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * Created by qy on 2018/2/26.
 */

public class LocationDialog extends Dialog {

    private TextView currentPOI;
    private TextView update;
    private TextView confirm;
    private OnButtonClickListener onButtonClickListener;

    public LocationDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_location_dialog);
        initView();
    }

    private void initView() {

        currentPOI = (TextView) findViewById(R.id.tv_current_poi);
        update = (TextView) findViewById(R.id.tv_update);
        confirm = (TextView) findViewById(R.id.tv_confirm);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onButtonClickListener){
                    onButtonClickListener.onCancelClick();
                }
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onButtonClickListener){
                    onButtonClickListener.onConfirmClick();
                }
                dismiss();
            }
        });
    }

    /**
     * 设置当前定位的poi
     * @param poi 当前位置
     */
    public void setCurrentPOI(String poi){
        currentPOI.setText(poi);
    }

    /**
     * 设置监听
     *
     * @param onButtonClickListener 按键监听
     */
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}

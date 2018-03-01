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
 * Created by qy on 2018/2/11.
 * 提示框
 */

public class PromptDialog extends Dialog {


    private OnButtonClickListener onButtonClickListener;
    private TextView tvTitle;
    private TextView tvMessage;

    public PromptDialog(@NonNull Context context) {
        super(context,R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt_dialog);
        initView();
    }

    private void initView() {
         tvTitle = findViewById(R.id.tv_title);
         tvMessage = findViewById(R.id.tv_message);
         findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (null != onButtonClickListener) {
                     onButtonClickListener.onConfirmClick();
                 }
                 dismiss();
             }
         });
         findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (null != onButtonClickListener) {
                     onButtonClickListener.onCancelClick();
                 }
                 dismiss();
             }
         });
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 设置message
     *
     * @param message 消息
     */
    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    /**
     * 设置监听
     *
     * @param onButtonClickListener 按键监听
     */
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismiss();
    }
}

package cn.v1.unionc_user.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.view.dialog_interface.OnButtonClickListener;

/**
 * Created by qy on 2018/2/11.
 * 提示框
 */

public abstract class PromptOnebtnDialog extends Dialog {


    private TextView tvTitle;
    private TextView tvMessage;
    private TextView tvClose;

    public PromptOnebtnDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt_one_btn);
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvMessage = findViewById(R.id.tv_message);
        tvClose = findViewById(R.id.tv_close);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClosed();
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
     * 设置确定信息
     *
     * @param closeMessage
     */
    public void setTvConfirm(String closeMessage) {
        tvClose.setText(closeMessage);
    }


    public abstract void onClosed();

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismiss();
    }
}

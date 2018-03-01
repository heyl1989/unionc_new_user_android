package cn.v1.unionc_user.tecent_qcloud.tim_model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFileElem;
import com.tencent.imsdk.TIMMessage;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.tecent_qcloud.ChatAdapter;
import cn.v1.unionc_user.tecent_qcloud.tim_util.TIMFileUtil;

/**
 * 文件消息
 */
public class FileMessage extends Message {


    public FileMessage(TIMMessage message) {
        this.message = message;
    }

    public FileMessage(String filePath) {
        message = new TIMMessage();
        TIMFileElem elem = new TIMFileElem();
        elem.setPath(filePath);
        elem.setFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
        message.addElement(elem);
    }


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {
        clearView(viewHolder);
        if (checkRevoke(viewHolder)) return;
        TIMFileElem e = (TIMFileElem) message.getElement(0);
        TextView tv = new TextView(UnioncApp.getInstance());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv.setTextColor(UnioncApp.getInstance().getResources().getColor(isSelf() ? R.color.white : R.color.black));
        tv.setText(e.getFileName());
        getBubbleView(viewHolder).addView(tv);
        showStatus(viewHolder);
    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        String str = getRevokeSummary();
        if (str != null) return str;
        return "[文件]";
    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {
        if (message == null) return;
        final TIMFileElem e = (TIMFileElem) message.getElement(0);
        String[] str = e.getFileName().split("/");
        String filename = str[str.length - 1];
        if (TIMFileUtil.isFileExist(filename, Environment.DIRECTORY_DOWNLOADS)) {
            Toast.makeText(UnioncApp.getInstance(), "文件已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        e.getToFile(TIMFileUtil.getCacheFilePath(filename), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "getFile failed. code: " + i + " errmsg: " + s);
            }

            @Override
            public void onSuccess() {

            }
        });

    }
}

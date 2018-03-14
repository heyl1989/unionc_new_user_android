package cn.v1.unionc_user.tecent_qcloud;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.tecent_qcloud.tim_model.Message;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;

/**
 * 聊天界面adapter
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    private final String TAG = "ChatAdapter";

    private int resourceId;
    private Context context;
    private View view;
    private ViewHolder viewHolder;
    private DoctorInfo doctorInfo;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ChatAdapter(Context context, int resource, List<Message> objects, DoctorInfo doctorInfo) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
        this.doctorInfo = doctorInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftMessage = (RelativeLayout) view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = (RelativeLayout) view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = (RelativeLayout) view.findViewById(R.id.leftPanel);
            viewHolder.rightAvatar = (com.tencent.qcloud.ui.CircleImageView) view.findViewById(R.id.rightAvatar);
            viewHolder.leftAvatar = (com.tencent.qcloud.ui.CircleImageView) view.findViewById(R.id.leftAvatar);
            viewHolder.rightPanel = (RelativeLayout) view.findViewById(R.id.rightPanel);
            viewHolder.sending = (ProgressBar) view.findViewById(R.id.sending);
            viewHolder.error = (ImageView) view.findViewById(R.id.sendError);
            viewHolder.sender = (TextView) view.findViewById(R.id.sender);
            viewHolder.rightDesc = (TextView) view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = (TextView) view.findViewById(R.id.systemMessage);
            view.setTag(viewHolder);
        }
        if (position < getCount()) {
            final Message data = getItem(position);
            data.showMessage(viewHolder, getContext());
            Glide.with(context)
                    .load(SPUtil.get(context, Common.USER_AVATOR, ""))
                    .into(viewHolder.rightAvatar);
            if (null != doctorInfo) {
                if(TextUtils.isEmpty(doctorInfo.getImagePath())){
                    viewHolder.leftAvatar.setImageResource(R.drawable.icon_doctor_default);
                }else{
                    Glide.with(context)
                            .load(doctorInfo.getImagePath())
                            .into(viewHolder.leftAvatar);
                }
                viewHolder.leftAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DoctorDetailActivity.class);
                        intent.putExtra("doctorIdentifier",doctorInfo.getIdentifier());
                        context.startActivity(intent);
                    }
                });
            }
        }
        return view;
    }


    public class ViewHolder {
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public com.tencent.qcloud.ui.CircleImageView rightAvatar;
        public com.tencent.qcloud.ui.CircleImageView leftAvatar;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
    }
}

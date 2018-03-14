package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.model.HomeListData;
import cn.v1.unionc_user.tecent_qcloud.TIMChatActivity;
import cn.v1.unionc_user.tecent_qcloud.tim_model.DoctorInfo;
import cn.v1.unionc_user.ui.home.DoctorDetailActivity;

/**
 * Created by qy on 2018/2/7.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private Context context;
    private List<HomeListData.DataData.HomeData> datas = new ArrayList<>();

    public HomeListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeListData.DataData.HomeData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_list, null));
    }


    @Override
    public void onBindViewHolder(final HomeListAdapter.ViewHolder holder, final int position) {
        final HomeListData.DataData.HomeData homeData = datas.get(position);
        if (TextUtils.equals(homeData.getType(), Common.INQUIRY_RECORD)) {
            Glide.with(context).load(homeData.getImagePath()).into(holder.imgMessageAvator);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvDescribe.setText(homeData.getClinicName() + "\n" +
                    homeData.getMajor());
        }
        if (TextUtils.equals(homeData.getType(), Common.ATTENDING_DOCTORS)) {
            Glide.with(context).load(homeData.getImagePath()).into(holder.imgMessageAvator);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText("（主治医生）");
            holder.tvDescribe.setText("最近的聊天记录");
        }
        if (TextUtils.equals(homeData.getType(), Common.SIGNED_DOCTROS)) {
            Glide.with(context).load(homeData.getImagePath()).into(holder.imgMessageAvator);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText("（家庭医生）");
            holder.tvDescribe.setText("最近的聊天记录");
        }
        if (TextUtils.equals(homeData.getType(), Common.RECOMMEND_DOCTOR)) {
            Glide.with(context).load(homeData.getImagePath()).into(holder.imgMessageAvator);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText(homeData.getDepartName() + "  " +
                    homeData.getProfessLevel());
            holder.tvDescribe.setText(homeData.getClinicName() + "\n" +
                    homeData.getMajor());
        }
        if (TextUtils.equals(homeData.getType(), Common.CONVERSATIONS)) {
            try {
                //待获取用户资料的用户列表
                List<String> users = new ArrayList<String>();
                users.add(homeData.getIdentifier());
                //获取用户资料
                TIMFriendshipManager.getInstance().getUsersProfile(users, new TIMValueCallBack<List<TIMUserProfile>>() {
                    @Override
                    public void onError(int code, String desc) {
                        //错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code列表请参见错误码表
                        Log.e("tim", "getUsersProfile failed: " + code + " desc");
                    }

                    @Override
                    public void onSuccess(List<TIMUserProfile> result) {
                        Log.e("tim", "getUsersProfile succ");
                        for (TIMUserProfile res : result) {
                            Log.e("tim", "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                                    + " remark: " + res.getRemark() + "faceurl" + res.getFaceUrl());
                            if (!TextUtils.isEmpty(res.getNickName())) {
                                holder.tvMessageName.setText(res.getNickName() + "");
                                homeData.setDoctorName(res.getNickName() + "");
                            }
                            if (!TextUtils.isEmpty(res.getFaceUrl())) {
                                Glide.with(context).load(res.getFaceUrl()).into(holder.imgMessageAvator);
                                homeData.setImagePath(res.getFaceUrl() + "");
                            }
                        }
                    }
                });
                holder.imgMessageAvator.setImageResource(R.drawable.icon_doctor_default);
                holder.tvMessageName.setText(homeData.getIdentifier() + "");
                holder.tvDescribe.setText(homeData.getLastMessage().getSummary() + "");
                holder.tvTime.setText(homeData.getLasttime() + "");
            } catch (Exception e) {

            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = datas.get(position).getType();
                String doctorId = datas.get(position).getDoctId() + "";
                String identifier = datas.get(position).getIdentifier() + "";
                if (TextUtils.equals(type, Common.RECOMMEND_DOCTOR) ||
                        TextUtils.equals(type, Common.SIGNED_DOCTROS) ||
                        TextUtils.equals(type, Common.ATTENDING_DOCTORS)) {
                    Intent intent = new Intent(context, DoctorDetailActivity.class);
                    intent.putExtra("doctorId", doctorId);
                    context.startActivity(intent);
                }
                if (TextUtils.equals(type, Common.CONVERSATIONS)) {
                    DoctorInfo doctorInfo = new DoctorInfo();
                    doctorInfo.setDoctorName(homeData.getDoctorName() + "");
                    doctorInfo.setIdentifier(homeData.getIdentifier() + "");
                    doctorInfo.setImagePath(homeData.getImagePath() + "");
                    TIMChatActivity.navToChat(context, doctorInfo, TIMConversationType.C2C);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_message_avator)
        ImageView imgMessageAvator;
        @Bind(R.id.tv_message_name)
        TextView tvMessageName;
        @Bind(R.id.tv_role)
        TextView tvRole;
        @Bind(R.id.tv_describe)
        TextView tvDescribe;
        @Bind(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

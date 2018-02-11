package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.model.HomeListData;

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
    public void onBindViewHolder(HomeListAdapter.ViewHolder holder, int position) {
        HomeListData.DataData.HomeData homeData = datas.get(position);
        if (TextUtils.equals(homeData.getType(), Common.INQUIRY_RECORD)) {

        }
        if (TextUtils.equals(homeData.getType(), Common.ATTENDING_DOCTORS)) {

        }
        if (TextUtils.equals(homeData.getType(), Common.SIGNED_DOCTROS)) {

        }
        if (TextUtils.equals(homeData.getType(), Common.RECOMMEND_DOCTOR)) {
            Glide.with(context).load("").into(holder.imgMessageAvator);
            holder.tvMessageName.setText(homeData.getDoctorName() + "  ");
            holder.tvRole.setText(homeData.getDepartName() + "  " +
                    homeData.getProfessLevel());
            holder.tvDescribe.setText(homeData.getClinicName() + "\n" +
                    homeData.getMajor());
        }

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

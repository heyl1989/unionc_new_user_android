package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.DoctorEvaluateData;
import cn.v1.unionc_user.view.CircleImageView;

/**
 * Created by qy on 2018/3/9.
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {

    private Context context;
    private List<DoctorEvaluateData.DataData.EvaluatesData> evaluates = new ArrayList<>();

    public EvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<DoctorEvaluateData.DataData.EvaluatesData> evaluates) {
        this.evaluates = evaluates;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_evaluate, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DoctorEvaluateData.DataData.EvaluatesData evaluatesData = evaluates.get(position);
        holder.tvName.setText(evaluatesData.getUserName() + "");
        holder.tvTime.setText(evaluatesData.getCreatedTime() + "");
        holder.tvContent.setText(evaluatesData.getContent() + "");
        Glide.with(context).load(evaluatesData.getHeadImage()).into(holder.imgUserAvator);

    }

    @Override
    public int getItemCount() {
        return evaluates.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_user_avator)
        CircleImageView imgUserAvator;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

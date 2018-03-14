package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;

/**
 * Created by qy on 2018/3/12.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private Context context;

    public ActivityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_activity, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_activity_title)
        TextView tvActivityTitle;
        @Bind(R.id.tv_activity_content)
        TextView tvActivityContent;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_activity_state)
        TextView tvActivityState;
        @Bind(R.id.tv_had_registe)
        TextView tvHadRegiste;
        @Bind(R.id.tv_regis)
        TextView tvRegis;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

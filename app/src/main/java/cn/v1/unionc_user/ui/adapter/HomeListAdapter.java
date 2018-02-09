package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;

/**
 * Created by qy on 2018/2/7.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private Context context;

    public HomeListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_list, null));
    }


    @Override
    public void onBindViewHolder(HomeListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
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

package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.NearbyPOIData;
import cn.v1.unionc_user.ui.adapter_interface.OnItemClicListener;

/**
 * Created by qy on 2018/2/27.
 */

public class POIAdapter extends RecyclerView.Adapter<POIAdapter.ViewHolder> {

    private Context context;
    private List<NearbyPOIData> datas = new ArrayList<>();
    private OnItemClicListener onItemClicListener;

    public POIAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NearbyPOIData> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemClicListener(OnItemClicListener onItemClicListener){
        this.onItemClicListener = onItemClicListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_poi, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvPoiName.setText((position + 1) + "  " + datas.get(position).getPOIName()+"");
        holder.tvAddress.setText(datas.get(position).getAddress()+"");
        holder.tvDistance.setText("--" + String.format("%.2f",datas.get(position).getDistance())+"km");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onItemClicListener){
                    onItemClicListener.onItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_poi_name)
        TextView tvPoiName;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.tv_distance)
        TextView tvDistance;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

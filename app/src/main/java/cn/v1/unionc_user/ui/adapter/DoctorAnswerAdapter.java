package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.v1.unionc_user.R;

/**
 * Created by qy on 2018/2/23.
 */

public class DoctorAnswerAdapter extends BaseAdapter {

    private Context context;

    public DoctorAnswerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_answer, null);
        return view;
    }
}

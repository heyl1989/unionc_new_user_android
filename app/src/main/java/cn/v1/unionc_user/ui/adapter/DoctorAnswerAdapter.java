package cn.v1.unionc_user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.R;
import cn.v1.unionc_user.model.DoctorInfoData;
import cn.v1.unionc_user.ui.home.ViewDoctorAnswerActivity;

/**
 * Created by qy on 2018/2/23.
 */

public class DoctorAnswerAdapter extends BaseAdapter {

    private Context context;
    private String doctorId = "";
    private List<DoctorInfoData.DataData.QuestionsData> questionsDataList = new ArrayList<>();

    public DoctorAnswerAdapter(Context context) {
        this.context = context;
    }

    public DoctorAnswerAdapter(Context context, String doctorId) {
        this.context = context;
        this.doctorId = doctorId;
    }

    public void setData(List<DoctorInfoData.DataData.QuestionsData> questionsDataList) {
        this.questionsDataList = questionsDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return questionsDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doctor_answer, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder); //绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
        }
        holder.tvTitle.setText(questionsDataList.get(position).getQuestion() + "");
        holder.tvLooks.setText(questionsDataList.get(position).getPageView() + "人看过");
        holder.tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewDoctorAnswerActivity.class);
                intent.putExtra("doctorId", doctorId);
                intent.putExtra("questionId", questionsDataList.get(position).getQuestionId() + "");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_look)
        TextView tvLook;
        @Bind(R.id.tv_looks)
        TextView tvLooks;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

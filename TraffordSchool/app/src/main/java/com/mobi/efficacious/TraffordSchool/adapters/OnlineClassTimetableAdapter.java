package com.mobi.efficacious.TraffordSchool.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetail;
import java.util.ArrayList;


public class OnlineClassTimetableAdapter extends RecyclerView.Adapter<OnlineClassTimetableAdapter.ViewHolder> {
    private ArrayList<DashboardDetail> data;
    public OnlineClassTimetableAdapter(ArrayList<DashboardDetail> dataList) {
        data = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlne_class_timetable_row,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.todate_timetable.setTextColor(Color.RED);
            holder.fromdate_timetable.setText(data.get(position).getDtFromTime().toString());
            holder.todate_timetable.setText(data.get(position).getDtToTime().toString());
            holder.tv_lecture_number.setText("Notice:"+data.get(position).getVchLecture_name().toString());
            holder.tv_online_class_date.setText("Subject:"+data.get(position).getDtLecture_date().toString());
            holder.tv_teacher_name.setText("Notice:"+data.get(position).getTeacher_name().toString());
            holder.tv_subject_name.setText("Subject:"+data.get(position).getVchSubjectName().toString());

        }catch (Exception ex)
        {

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromdate_timetable,todate_timetable,tv_lecture_number,tv_online_class_date, tv_teacher_name, tv_subject_name;
        public ViewHolder(View itemView) {
            super(itemView);
            fromdate_timetable=(TextView)itemView.findViewById(R.id.fromdate_timetable);
            todate_timetable=(TextView)itemView.findViewById(R.id.todate_timetable);
            tv_lecture_number=(TextView)itemView.findViewById(R.id.tv_lecture_number);
            tv_online_class_date=(TextView)itemView.findViewById(R.id.tv_online_class_date);
            tv_teacher_name=(TextView)itemView.findViewById(R.id.tv_teacher_name);
            tv_subject_name=(TextView)itemView.findViewById(R.id.tv_subject_name);
        }
    }
}

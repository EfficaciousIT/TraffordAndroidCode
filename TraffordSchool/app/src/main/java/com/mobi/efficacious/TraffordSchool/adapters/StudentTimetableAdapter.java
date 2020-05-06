package com.mobi.efficacious.TraffordSchool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.entity.TimeTableDetail;

public class StudentTimetableAdapter  extends RecyclerView.Adapter<StudentTimetableAdapter.TimeTableListHolder> {

    Context context;
    ArrayList<TimeTableDetail> itemsArrayList;
    String PageNmae;
    public StudentTimetableAdapter(Context context, ArrayList<TimeTableDetail> itemsArrayList,String Pagenmae) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.PageNmae=Pagenmae;
    }

    @Override
    public TimeTableListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.studenttimetable_row, parent, false);
        return new TimeTableListHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeTableListHolder holder, final int position) {
        try
        {
            holder.Period_No.setText(itemsArrayList.get(position).getVchLectureName());
            holder.time.setText(itemsArrayList.get(position).getTime());
            holder.subject.setText(itemsArrayList.get(position).getVchSubjectName());
            if(PageNmae.contentEquals("Teacher"))
            {
                holder.teacher.setText(itemsArrayList.get(position).getVchStandardName());
            }else
            {
                holder.teacher.setText(itemsArrayList.get(position).getTName());
            }

        }catch (Exception ex)
        {

        }
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class TimeTableListHolder extends RecyclerView.ViewHolder {

        TextView time,subject,teacher,Period_No;
        TimeTableListHolder(View itemView) {
            super(itemView);
            Period_No=itemView.findViewById(R.id.Teacher_pid);
             time=(TextView)itemView.findViewById(R.id.PeriodTime_studenttimetable);
             subject = (TextView)itemView.findViewById(R.id.Subject_studenttimetable);
             teacher = (TextView)itemView.findViewById(R.id.Teacher_studenttimetable);
        }


    }

}



package com.mobi.efficacious.TraffordSchool.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.Tab.StudentAttendanceActivity;
import com.mobi.efficacious.TraffordSchool.Tab.TimetableActivity_student;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.entity.StandardDetail;



public class Student_division_adapter  extends RecyclerView.Adapter<Student_division_adapter.DivisionHolder> {

    private ArrayList<StandardDetail> dataList;
    private final Context mcontext;
    private String pagename;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    private String role_id, intSchool_id;
    public static String div,std_id;

    public Student_division_adapter(Context context, ArrayList<StandardDetail> dataList, String value) {
        this.dataList = dataList;
        this.mcontext = context;
        this.pagename = value;
    }

    @Override
    public DivisionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.standard_division, parent, false);
        return new DivisionHolder(view);
    }

    @Override
    public void onBindViewHolder(final DivisionHolder holder, final int position) {
        try {
            holder.textView.setText(dataList.get(position).getVchDivisionName());
        } catch (Exception ex) {

        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                div = String.valueOf(dataList.get(position).getIntDivisionId());
                std_id= StandardAdapter.Std_id_division;
//                settings.edit().putString("TAG_DIVISIONID", div).commit();
//                settings.edit().putString("TAG_DIV_ID", String.valueOf(dataList.get(position).getIntDivisionId())).commit();
                if (pagename.contentEquals("Standarad_division")) {
                    try {
                        ((Activity) mcontext).finish();
                        StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                        Bundle args = new Bundle();
                        args.putString("std_id", StandardAdapter.Std_id_division);
                        args.putString("std_name", StandardAdapter.std_name_division);
                        args.putString("std_div", String.valueOf(dataList.get(position).getIntDivisionId()));
                        args.putString("selected_layout", "Stdwise_name");
                        studentAttendanceActivity.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                    } catch (Exception ex) {

                    }

                } else if (pagename.contentEquals("Standarad_TimeTable")) {
                    try {
                        ((Activity) mcontext).finish();
                        TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                       /* settings.edit().putString("TAG_DIVISIONID", div).commit();
                        Bundle args = new Bundle();
                        args.putString("std_id", StandardAdapter.Std_id_division);
                        args.putString("std_name", StandardAdapter.std_name_division);
                        args.putString("std_div", String.valueOf(dataList.get(position).getIntDivisionId()));
                        args.putString("selected_layout", "Stdwise_name");
                        timetableActivity_student.setArguments(args);*/
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                } else if (pagename.contentEquals("Standarad_Result")) {
                    try {
                        ((Activity) mcontext).finish();
                    } catch (Exception ex) {

                    }

                    if (role_id.contentEquals("3")) {
                        try {
//                            Result_Tab studentResultFragment = new Result_Tab();
//                            Bundle args = new Bundle();
//                            args.putString("Div_name", dataList.get(position).getVchDivisionName());
//                            args.putString("Std_name", StandardAdapter.std_name_division);
//                            args.putInt("Div_id", Integer.parseInt(String.valueOf(dataList.get(position).getIntDivisionId())));
//                            args.putInt("Std_id", Integer.parseInt(StandardAdapter.Std_id_division));
//                            studentResultFragment.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentResultFragment).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else {
                        try {
//                            StudentResultFragment studentResultFragmen = new StudentResultFragment();
//                            Bundle args = new Bundle();
//                            args.putString("Div_name", dataList.get(position).getVchDivisionName());
//                            args.putString("Std_name", StandardAdapter.std_name_division);
//                            args.putInt("Div_id", Integer.parseInt(String.valueOf(dataList.get(position).getIntDivisionId())));
//                            args.putInt("Std_id", Integer.parseInt(StandardAdapter.Std_id_division));
//                            studentResultFragmen.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentResultFragmen).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    }


                } else if (pagename.contentEquals("Timetable")) {
                    TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                    Bundle args = new Bundle();
                    args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                    args.putString("std_name", StandardAdapter.std_name_division);
                    args.putString("std_div", String.valueOf(dataList.get(position).getIntDivisionId()));
                    args.putString("selected_layout", "Stdwise_name");
                    if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                        intSchool_id = String.valueOf(dataList.get(position).getIntschoolId());
                    }
                    timetableActivity_student.setArguments(args);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();
                } else {
                    try {
                        TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                        Bundle args = new Bundle();
                        args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        args.putString("div_id", String.valueOf(dataList.get(position).getIntDivisionId()));
                        timetableActivity_student.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DivisionHolder extends RecyclerView.ViewHolder {

        TextView textView;

        DivisionHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView38);
        }


    }

}



package com.mobi.efficacious.TraffordSchool.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.Tab.TimetableActivity_student;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.dialogbox.Standard_division_dialog;
import com.mobi.efficacious.TraffordSchool.entity.StandardDetail;
import com.mobi.efficacious.TraffordSchool.entity.StandardDetailsPojo;
import com.mobi.efficacious.TraffordSchool.fragment.StandardWise_Book;
import com.mobi.efficacious.TraffordSchool.fragment.StudentExamFragment;
import com.mobi.efficacious.TraffordSchool.fragment.StudentSyllabusFragment;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StandardAdapter extends RecyclerView.Adapter<StandardAdapter.StandardListHolder> {

    private ArrayList<StandardDetail> dataList;
    private final Context mcontext;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id;
    String Standard_id, userid, school_id, academic_id;
    public static String Std_id_division, std_name_division, page_name, std_id, intSchool_id;
    private String pagename;
    private CompositeDisposable mCompositeDisposable;

    public StandardAdapter(ArrayList<StandardDetail> dataList, Context context, String value) {
        this.dataList = dataList;
        this.mcontext = context;
        this.pagename = value;
    }

    @Override
    public StandardListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.standard_row, parent, false);

        return new StandardListHolder(view);
    }

    @Override
    public void onBindViewHolder(final StandardListHolder holder, final int position) {
        try {
            settings = mcontext.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
            school_id = settings.getString("TAG_SCHOOL_ID", "");
            userid = settings.getString("TAG_USERID", "");
            academic_id = settings.getString("TAG_ACADEMIC_ID", "");
//            Standard_id = settings.getString("TAG_STANDERDID", "");
            Standard_id = String.valueOf(dataList.get(position).getIntStandardId());

            try {
                holder.id.setText(String.valueOf(dataList.get(position).getIntStandardId()));
                holder.name.setText(dataList.get(position).getVchStandardName());
            } catch (Exception ex) {

            }
        } catch (Exception ex) {

        }
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                if (pagename.equalsIgnoreCase("Exam")) {

                    try {
                        StudentExamFragment studentExamActivity = new StudentExamFragment();
                        Bundle args = new Bundle();
                        args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                            args.putString("intSchool_id", String.valueOf(dataList.get(position).getIntschoolId()));
                        }
                        studentExamActivity.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentExamActivity).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }
                } else if (pagename.equalsIgnoreCase("Syllabus")) {
                    try {
                        StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
                        Bundle args = new Bundle();
                        args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        subjectFragment.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }
                } else if (pagename.equalsIgnoreCase("Std")) {
                    try {
                        page_name = "Standarad_division";
                        Std_id_division = String.valueOf(dataList.get(position).getIntStandardId());
                        std_name_division = dataList.get(position).getVchStandardName();
                        if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                            intSchool_id = String.valueOf(dataList.get(position).getIntschoolId());
                        }
                        Intent intent = new Intent(mcontext, Standard_division_dialog.class);
                        intent.putExtra("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        intent.putExtra("std_name", dataList.get(position).getVchStandardName());
                        intent.putExtra("selected_layout", "Stdwise_name");
                        mcontext.startActivity(intent);
                    } catch (Exception ex) {

                    }


                } else if (pagename.equalsIgnoreCase("Std_timetable")) {
                    try {
                        page_name = "Standarad_TimeTable";
                        Std_id_division = String.valueOf(dataList.get(position).getIntStandardId());
                        settings.edit().putString("TAG_DIVISIONID", Std_id_division).commit();
                        std_name_division = dataList.get(position).getVchStandardName();
                        if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                            intSchool_id = String.valueOf(dataList.get(position).getIntschoolId());
                        }

                        Intent intent = new Intent(mcontext, Standard_division_dialog.class);
//                        intent.putExtra("selected_layout", "Stdwise_name");
                        mcontext.startActivity(intent);
                    } catch (Exception ex) {

                    }
                } else if (pagename.equalsIgnoreCase("timetable")) {
                    try {
//                        if (role_id.contentEquals("5")) {
//                            Toast.makeText(mcontext, "Click on " + (String.valueOf(dataList.get(position).getIntStandardId())), Toast.LENGTH_LONG).show();
////                            callDivision();
//                            std_id = String.valueOf(dataList.get(position).getIntStandardId());
//                            Intent intent = new Intent(mcontext, TimeTable_Division_Dialogbox.class);
//                            intent.putExtra("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
//                            intent.putExtra("std_name", dataList.get(position).getVchStandardName());
//                            intent.putExtra("selected_layout", "Stdwise_name");
//                            mcontext.startActivity(intent);
//
//                        } else {
                        TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                        Bundle args = new Bundle();
                        args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                            intSchool_id = String.valueOf(dataList.get(position).getIntschoolId());
                        }
                        timetableActivity_student.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                } else if (pagename.equalsIgnoreCase("LibraryTeacher")) {
                    try {
                        std_id = String.valueOf(dataList.get(position).getIntStandardId());
                        StandardWise_Book standardWise_book = new StandardWise_Book();
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, standardWise_book).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                } else if (pagename.equalsIgnoreCase("Standarad_Result")) {
                    try {
                        page_name = "Standarad_Result";
                        String stn1 = dataList.get(position).getVchStandardName();
                        Std_id_division = String.valueOf(dataList.get(position).getIntStandardId());
                        std_name_division = dataList.get(position).getVchStandardName();
                        Intent intent = new Intent(mcontext, Standard_division_dialog.class);
                        intent.putExtra("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        intent.putExtra("std_name", dataList.get(position).getVchStandardName());
                        intent.putExtra("selected_layout", "Stdwise_name");
                        mcontext.startActivity(intent);
                    } catch (Exception ex) {

                    }

                } else {
                    try {
                        StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
                        Bundle args = new Bundle();
                        args.putString("std_id", String.valueOf(dataList.get(position).getIntStandardId()));
                        subjectFragment.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();

                    } catch (Exception ex) {

                    }

                }
            }
        });
    }

    private void callDivision() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("3")) {
                mCompositeDisposable.add(service.getStandardDetails("selectDivisionByLectures", school_id, academic_id, Standard_id, "", userid, "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            } else {
                mCompositeDisposable.add(service.getStandardDetails("GetDivision", school_id, "", Standard_id, "", "", "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            }

        } catch (Exception ex) {
        }

    }

    public DisposableObserver<StandardDetailsPojo> getObserver() {
        return new DisposableObserver<StandardDetailsPojo>() {

            @Override
            public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateDivByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(mcontext, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void generateDivByLectures(ArrayList<StandardDetail> standardDetails) {

        Toast.makeText(mcontext, "" + standardDetails.get(0).getVchDivisionName(), Toast.LENGTH_LONG).show();

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class StandardListHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        RelativeLayout linear;

        StandardListHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id_standard);
            name = (TextView) itemView.findViewById(R.id.name_standard);
            linear = (RelativeLayout) itemView.findViewById(R.id.Linear);
        }


    }

}
package com.mobi.efficacious.TraffordSchool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.Tab.TimetableActivity_student;
import com.mobi.efficacious.TraffordSchool.adapters.StandardAdapter;
import com.mobi.efficacious.TraffordSchool.adapters.StudentTimetableAdapter;
import com.mobi.efficacious.TraffordSchool.adapters.Student_division_adapter;
import com.mobi.efficacious.TraffordSchool.entity.TimeTableDetail;
import com.mobi.efficacious.TraffordSchool.entity.TimeTableDetailPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MondayFragment extends Fragment {
    String Std_id,school_id,role_id,Academic_id,div_id;

    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    private CompositeDisposable mCompositeDisposable;
    private ProgressDialog progress;
    public MondayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mrecyclerView = (RecyclerView) getActivity().findViewById(R.id.monday_list);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Academic_id= settings.getString("TAG_ACADEMIC_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        if (role_id.contentEquals("5"))
            div_id=Student_division_adapter.div;
        else
            div_id=settings.getString("TAG_DIVISIONID","");

        try
        {
            if(role_id.contentEquals("6")||role_id.contentEquals("7")||role_id.contentEquals("3"))
            {
                school_id= StandardAdapter.intSchool_id;
            }else
            {
                school_id= settings.getString("TAG_SCHOOL_ID", "");
            }
//            Std_id= Student_division_adapter.std_id;
        }catch (Exception ex)
        {

        }
        try {
            if (role_id.contentEquals("5"))
                Std_id= Student_division_adapter.std_id;
            else
                Std_id = TimetableActivity_student.stdid;

        } catch (Exception ex) {

        }
        try
        {
            StudtimeAsync ();
        }catch (Exception ex)
        {

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monday, container, false);


    }

    public void  StudtimeAsync (){
        try {
            DataService service=RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add((Disposable) service.getTimeTableDetails("selectStudentTT",school_id,"monday",Academic_id,Std_id,"",div_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<TimeTableDetailPojo> getObserver(){
        return new DisposableObserver<TimeTableDetailPojo>() {

            @Override
            public void onNext(@NonNull TimeTableDetailPojo timeTableDetailPojo) {
                try {
                    generateTimetableList((ArrayList<TimeTableDetail>) timeTableDetailPojo.getTimeTableDetail());
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    public void generateTimetableList(ArrayList<TimeTableDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new StudentTimetableAdapter(getActivity(),taskListDataList,"Student");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                mrecyclerView.setLayoutManager(layoutManager);

                mrecyclerView.setAdapter(madapter);
            } else {


            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}
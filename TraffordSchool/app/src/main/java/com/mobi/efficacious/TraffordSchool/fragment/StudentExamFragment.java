package com.mobi.efficacious.TraffordSchool.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.adapters.Student_Exam_Timetable;
import com.mobi.efficacious.TraffordSchool.entity.TimeTableDetail;
import com.mobi.efficacious.TraffordSchool.entity.TimeTableDetailPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class StudentExamFragment extends Fragment {
    View myview;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    private CompositeDisposable mCompositeDisposable;
    Context mContext;
    String Std_id, school_id,div_id;
    String stand_id, academic_id, role_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_studentexam, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Std_id = settings.getString("TAG_STANDERDID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        div_id=settings.getString("TAG_DIVISIONID","");
        mContext = getActivity();
        try {
            if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                school_id = getArguments().getString("intSchool_id");
            } else {
                school_id = settings.getString("TAG_SCHOOL_ID", "");
            }
        } catch (Exception ex) {

        }

        try {
            stand_id = getArguments().getString("std_id");
        } catch (Exception ex) {

        }

        recyclerView = (RecyclerView) myview.findViewById(R.id.exam_list);
        try {
            ExamAsync ();
        } catch (Exception ex) {

        }


        myview.setFocusableInTouchMode(true);
        myview.requestFocus();
        myview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        try {
                            if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                                stand_id = settings.getString("TAG_STANDERDID", "");
                                StudentExamFragment studentExamActivity = new StudentExamFragment();
                                Bundle args = new Bundle();
                                args.putString("std_id", stand_id);
                                studentExamActivity.setArguments(args);
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentExamActivity).commitAllowingStateLoss();
                            } else {
                                Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                                Bundle args = new Bundle();
                                args.putString("pagename", "Exam");
                                student_std_activity.setArguments(args);
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                            }
                        } catch (Exception ex) {

                        }


                        return true;
                    }
                }
                return false;
            }
        });
        return myview;
    }
    public void ExamAsync () {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Log.i("TAG","ExamAsync- "+school_id+"/"+academic_id+"/"+stand_id+"/"+"/"+div_id);
            mCompositeDisposable.add(service.getTimeTableDetails("selectExamTT",school_id,"",academic_id,stand_id,"", div_id)
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
                    generateExamTimeTable((ArrayList<TimeTableDetail>) timeTableDetailPojo.getTimeTableDetail());
                } catch (Exception ex) {

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


    public void generateExamTimeTable(ArrayList<TimeTableDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                adapter = new Student_Exam_Timetable(getActivity(),taskListDataList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}

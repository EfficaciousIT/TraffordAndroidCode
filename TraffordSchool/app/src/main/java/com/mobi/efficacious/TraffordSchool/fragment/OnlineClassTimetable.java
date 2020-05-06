package com.mobi.efficacious.TraffordSchool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.adapters.OnlineClassDetailAdapter;
import com.mobi.efficacious.TraffordSchool.adapters.OnlineClassTimetableAdapter;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;
import com.mobi.efficacious.TraffordSchool.database.Databasehelper;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetail;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetailsPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class OnlineClassTimetable extends Fragment {

    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    Databasehelper mydb;
    RecyclerView rv_online_classes;
    RecyclerView.Adapter madapter;
    String Schooli_id,role_id,intStandard_id,academic_id, teacher_Id;
    View myview;
    ConnectionDetector cd;
    private ProgressDialog progress;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myview=inflater.inflate(R.layout.onlineclassdetail,null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        teacher_Id = settings.getString("TAG_USERTYPEID", "");

        try{
            if(role_id.contentEquals("1")||role_id.contentEquals("2")){
                intStandard_id= settings.getString("TAG_STANDERDID", "");
            }
        }catch (Exception ex){}

        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        rv_online_classes=(RecyclerView)myview.findViewById(R.id.rv_online_classes);
        mydb=new Databasehelper(getActivity(),"Notifications",null,1);
        cd = new ConnectionDetector(getActivity().getApplicationContext());

        if (!cd.isConnectingToInternet()){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK",null);
            alert.show();
        }
        else {
            try{
                OnlineClassDetailAsync ();
            }catch (Exception ex){}
        }
        return myview;
    }


    public void OnlineClassDetailAsync() {
        try {
            Observable<DashboardDetailsPojo> call = null;
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);

            //Parents and Students
            if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                call = service.getOnlineClassTimetable("StandardWiseList", teacher_Id, academic_id, Schooli_id, intStandard_id);
            }
            // Admin
            else if(role_id.contentEquals("5")){
                call = service.getOnlineClassTimetable("AdminWiseList", teacher_Id, academic_id, Schooli_id, intStandard_id);
            }
            //Teachers
            else if(role_id.contentEquals("3")){
                call = service.getOnlineClassTimetable("TeacherWiseList", teacher_Id, academic_id, Schooli_id, intStandard_id);
            }

            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DashboardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(DashboardDetailsPojo body) {
                    try {
                        generateNoticeList((ArrayList<DashboardDetail>) body.getDashboardDetails());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateNoticeList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                rv_online_classes.setHasFixedSize(true);
                rv_online_classes.setLayoutManager(new LinearLayoutManager(getActivity()));
                madapter = new OnlineClassTimetableAdapter(taskListDataList);
                rv_online_classes.setAdapter(madapter);
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
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.mobi.efficacious.TraffordSchool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.adapters.NoticeBoardAdapter;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;
import com.mobi.efficacious.TraffordSchool.database.Databasehelper;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetail;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetailsPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Noticeboard  extends Fragment {
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    Databasehelper mydb;
    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    String Schooli_id,role_id,intStandard_id,academic_id, division_id;
    View myview;
    ConnectionDetector cd;
    private ProgressDialog progress;
    FloatingActionButton addButton;
    ArrayList<DashboardDetail> noticeboard=new ArrayList<DashboardDetail>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview=inflater.inflate(R.layout.noticeboard,null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
       // division_id = settings.getString("TA")
        addButton=(FloatingActionButton) myview.findViewById(R.id.addButton);
        addButton.setVisibility(View.GONE);
        if(role_id.contentEquals("5")||role_id.contentEquals("6")||role_id.contentEquals("7"))
        {
            addButton.setVisibility(View.VISIBLE);
        }else
        {
            addButton.setVisibility(View.GONE);
        }
        try
        {
            if(role_id.contentEquals("1")||role_id.contentEquals("2"))
            {
                intStandard_id= settings.getString("TAG_STANDERDID", "");
            }
        }catch (Exception ex)
        {

        }
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        progress.show();
        mrecyclerView=(RecyclerView)myview.findViewById(R.id.chat_recyclerview);
        mydb=new Databasehelper(getActivity(),"Notifications",null,1);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        if (!cd.isConnectingToInternet())
        {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK",null);
            alert.show();

        }
        else {
            try
            {
                NoticeboardAsync ();
            }catch (Exception ex)
            {

            }

        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeBoard_application noticeBoard_application = new NoticeBoard_application();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, noticeBoard_application).commitAllowingStateLoss();
            }
        });
        return myview;
    }


    public void NoticeboardAsync() {
        try {
            Observable<DashboardDetailsPojo> call;
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                 call = service.getDashboardDetails("NoticeBoardPrincipal", academic_id);
        }else if(role_id.contentEquals("2"))
        {
            call = service.getDashboardDetails("NoticeBoard", academic_id, Schooli_id);
        }else if(role_id.contentEquals("3"))
        {
           call = service.getDashboardDetails("NoticeBoardTeacher", academic_id, Schooli_id);
        }else if(role_id.contentEquals("1"))
            {
                Log.d("RESULT234",""+intStandard_id+"Sss"+academic_id);
                call = service.getDashboardDetails("NoticeBoardStudent",academic_id,"1", intStandard_id);
            }
        else
        {
             call = service.getDashboardDetails("NoticeBoard", academic_id, Schooli_id);
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
                mrecyclerView.setHasFixedSize(true);
                mrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                madapter = new NoticeBoardAdapter(taskListDataList);
                mrecyclerView.setAdapter(madapter);
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
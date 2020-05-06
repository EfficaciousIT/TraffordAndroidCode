package com.mobi.efficacious.TraffordSchool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;

import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.adapters.EventListAdapter;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;

import com.mobi.efficacious.TraffordSchool.entity.EventDetail;
import com.mobi.efficacious.TraffordSchool.entity.EventDetailPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Event_list_fragment extends Fragment {
    View myview;
    RecyclerView recyclerView;
    ArrayAdapter adapter;
    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String  Year_id,Schooli_id;
    String userid, role_id, value,vchstandard_id;
    private ProgressDialog progress;
    EventListAdapter madapter;
    FloatingActionButton addButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_leavelist, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id= settings.getString("TAG_SCHOOL_ID", "");
        recyclerView = (RecyclerView) myview.findViewById(R.id.leavelist_list);
        cd = new ConnectionDetector(getContext().getApplicationContext());
        addButton=(FloatingActionButton) myview.findViewById(R.id.addButton);
        addButton.setVisibility(View.GONE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        if(role_id.contentEquals("5")||role_id.contentEquals("6")||role_id.contentEquals("7"))
        {
            addButton.setVisibility(View.VISIBLE);
        }else
        {
            addButton.setVisibility(View.GONE);
        }
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            try
            {
                vchstandard_id = settings.getString("TAG_STANDERDID", "");
                if(vchstandard_id.equalsIgnoreCase("1"))
                {
                    vchstandard_id="Nursery";
                }else if(vchstandard_id.equalsIgnoreCase("2"))
                {
                    vchstandard_id="Play Group";
                }else if(vchstandard_id.equalsIgnoreCase("3"))
                {
                    vchstandard_id="KGI";
                }else if(vchstandard_id.equalsIgnoreCase("4"))
                {
                    vchstandard_id="KGII";
                }else if(vchstandard_id.equalsIgnoreCase("5"))
                {
                    vchstandard_id="I";
                }else if(vchstandard_id.equalsIgnoreCase("6"))
                {
                    vchstandard_id="II";
                }else if(vchstandard_id.equalsIgnoreCase("7"))
                {
                    vchstandard_id="III";
                }else if(vchstandard_id.equalsIgnoreCase("8"))
                {
                    vchstandard_id="IV";
                }else if(vchstandard_id.equalsIgnoreCase("9"))
                {
                    vchstandard_id="V";
                }else if(vchstandard_id.equalsIgnoreCase("10"))
                {
                    vchstandard_id="VI";
                }else if(vchstandard_id.equalsIgnoreCase("11"))
                {
                    vchstandard_id="VII";
                }else if(vchstandard_id.equalsIgnoreCase("12"))
                {
                    vchstandard_id="VIII";
                }else if(vchstandard_id.equalsIgnoreCase("13"))
                {
                    vchstandard_id="IX";
                }else if(vchstandard_id.equalsIgnoreCase("14"))
                {
                    vchstandard_id="X";
                }else if(vchstandard_id.equalsIgnoreCase("15"))
                {
                    vchstandard_id="XI";
                }
                else
                {

                }
                userid = settings.getString("TAG_USERID", "");
                Year_id = settings.getString("TAG_ACADEMIC_ID", "");
                value = "Event";
                progress = new ProgressDialog(getActivity());
                progress.setCancelable(false);
                progress.setCanceledOnTouchOutside(false);
                progress.setMessage("loading...");
                progress.show();
                EventAsync();
            }catch (Exception ex)
            {

            }

        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event_application_fragment event_application_fragment = new Event_application_fragment();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, event_application_fragment).commitAllowingStateLoss();
            }
        });
        return myview;
    }

    public void  EventAsync (){
        try {
            String command = null;
            if(role_id.contentEquals("1")||role_id.contentEquals("2"))
            {
                command="SelectStudentApi";
            }else if(role_id.contentEquals("3"))
            {
                command="SelectTeacherApi";

            }else if(role_id.contentEquals("6")||role_id.contentEquals("7"))
            {
                command="SelectPrincipal";
            }
            else
            {
                command="SelectAdmin";
            }

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<EventDetailPojo> call = service.getEventDetails(command,vchstandard_id,Year_id,Schooli_id,userid);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<EventDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(EventDetailPojo body) {
                    try {
                        generateEventDetail((ArrayList<EventDetail>) body.getEventDetail());
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

    public void generateEventDetail(ArrayList<EventDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new EventListAdapter(taskListDataList,getActivity(),"EventList");

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(madapter);
            } else {

            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

}
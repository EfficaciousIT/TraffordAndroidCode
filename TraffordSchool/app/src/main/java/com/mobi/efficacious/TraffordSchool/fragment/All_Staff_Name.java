package com.mobi.efficacious.TraffordSchool.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.Tab.Timetable_sliding_tab;
import com.mobi.efficacious.TraffordSchool.adapters.AllStaffAdapter;
import com.mobi.efficacious.TraffordSchool.adapters.AllTeacherAdapter;
import com.mobi.efficacious.TraffordSchool.database.Databasehelper;
import com.mobi.efficacious.TraffordSchool.entity.TeacherDetail;
import com.mobi.efficacious.TraffordSchool.entity.TeacherDetailPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class All_Staff_Name extends Fragment {
    View myview;
    Cursor cursor;
    int count;
    RecyclerView recyclerView;
    AllStaffAdapter adapter;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    androidx.appcompat.widget.SearchView searchView;
    int a;
    String b,c,d;
    String date,academic_id;
    String Page="";
    String school_id,role_id;
    private CompositeDisposable mCompositeDisposable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview=inflater.inflate(R.layout.activity_allteacher,null);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        searchView = (androidx.appcompat.widget.SearchView) myview.findViewById(R.id.search_view_teacher);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        school_id=settings.getString("TAG_SCHOOL_ID", "");
        recyclerView  = (RecyclerView) myview.findViewById(R.id.allteacher_list);
            try
            {
                Page="attendence";
            }catch (Exception ex)
            {

            }
        try
        {
            LoginAsync ();
        }catch (Exception ex)
        {

        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return myview;
    }
    private void setupSearchView()
    {
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Staff Name Here");
    }


    public void LoginAsync ()
    {
        try {
            String command;
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                command="selectByPrincipal";
            }
            else
            {
                command="select";
            }
            mCompositeDisposable.add(service.getStaffDetails(command,school_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }
    public DisposableObserver<TeacherDetailPojo> getObserver(){
        return new DisposableObserver<TeacherDetailPojo>() {

            @Override
            public void onNext(@NonNull TeacherDetailPojo teacherDetailPojo) {
                try {
                    generateTeacherList((ArrayList<TeacherDetail>) teacherDetailPojo.getTeacherDetail());
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


    public void generateTeacherList(ArrayList<TeacherDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                adapter = new AllStaffAdapter(getActivity(), taskListDataList,Page);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setAdapter(adapter);
                setupSearchView();
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

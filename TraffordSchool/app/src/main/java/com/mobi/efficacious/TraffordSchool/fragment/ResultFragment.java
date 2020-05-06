package com.mobi.efficacious.TraffordSchool.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;
import com.mobi.efficacious.TraffordSchool.entity.ProfileDetailsPojo;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import io.reactivex.Observable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    WebView webView;
    private ProgressDialog progress;
    String command,User_id,student_id,role_id,academic_id ;
    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_result, container, false);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");

        student_id= settings.getString("TAG_STANDERDID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        User_id = settings.getString("TAG_USERID", "");
        webView=view.findViewById(R.id.webview);
        progress.show();

        String url=RetrofitInstance.resultUrl+"strStudentId="+User_id+"&strStandardId="+student_id;
        Log.e("URL","Url"+url);

//        webView.loadUrl( createUrl(Schooli_id,role_id,academic_id,User_id));
        webView.loadUrl(RetrofitInstance.resultUrl+"strStudentId="+User_id+"&strStandardId="+student_id);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.dismiss();
            }

            });

        return view;

    }


}

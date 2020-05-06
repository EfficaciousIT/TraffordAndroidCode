package com.mobi.efficacious.TraffordSchool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

public class GrievancesFragment extends Fragment implements View.OnClickListener {
    View myview;
    Button gri_btn;
    EditText edt_text;
    Spinner spn_type;
    String USERNAME;
    String PASSWORD;
    String User_id;
    public String User_name, role_id;
    public String Academic_id, Schooli_id;
    ConnectionDetector cd;
    private ProgressDialog progressBar;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.fragment_grievances, null);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        gri_btn = myview.findViewById(R.id.gri_btnSubmit);
        gri_btn.setOnClickListener(this);
        edt_text = myview.findViewById(R.id.edt_gri_text);
        spn_type = myview.findViewById(R.id.gri_spinner);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        User_id = settings.getString("TAG_USERID", "");
        Academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        USERNAME = settings.getString("TAG_USERNAME", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        progressBar = new ProgressDialog(getActivity());
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setMessage("processing...");
        String[] values =
                {"Select ", "Complaint", "Suggestion"};

        List<String> categories = new ArrayList<String>();

        categories.add("Complaint");
        categories.add("Suggestion");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
        spn_type.setAdapter(dataAdapter);
        return myview;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.gri_btnSubmit:
                if (edt_text.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter Text ", Toast.LENGTH_LONG).show();
                } else {
                    Intent it = new Intent(Intent.ACTION_SEND);
                    it.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@efficacious.co.in"});
                    it.putExtra(Intent.EXTRA_SUBJECT, spn_type.getSelectedItem().toString() + " " + "From" + " " + USERNAME + " " + "of" + " " + "SKP School");
                    it.putExtra(Intent.EXTRA_TEXT, "Academic id=" + Academic_id + "\n" + "Body:\n " + edt_text.getText().toString());
                    it.setType("message/rfc822");
                    startActivity(Intent.createChooser(it, "Choose Mail App"));
                    break;
                }
                break;

        }
    }
}

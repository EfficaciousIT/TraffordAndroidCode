package com.mobi.efficacious.TraffordSchool.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;

import com.mobi.efficacious.TraffordSchool.entity.SyllabusDetail;
import com.mobi.efficacious.TraffordSchool.fragment.SyllabusDetailFragment;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;


public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectListHolder> {

    private ArrayList<SyllabusDetail> dataList;
    private Context mcontext;
    ConnectionDetector cd;
    String PageName, Url;
    String holder_s;
    public SubjectAdapter(Context context, ArrayList<SyllabusDetail> dataList, String Pagename) {
        this.dataList = dataList;
        this.mcontext = context;
        this.PageName = Pagename;
    }

    @Override
    public SubjectListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_row, parent, false);
        return new SubjectListHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubjectListHolder holder, final int position) {
        try {

            if (PageName.contentEquals("SubjectName")) {
                holder.name.setText(dataList.get(position).getVchSubjectName());
            } else {
                holder.name.setText(dataList.get(position).getVchTopicName());
            }

        } catch (Exception ex) {

        }
        /*holder.syllabus.setOnClickListener(v -> {
            cd = new ConnectionDetector(mcontext.getApplicationContext());
            if (!cd.isConnectingToInternet()) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                alert.setMessage("No InternetConnection");
                alert.setPositiveButton("OK", null);
                alert.show();

            } else {
                if (PageName.contentEquals("SubjectName")) {
                    try {

                        SyllabusDetailFragment syllabusDetailFragment = new SyllabusDetailFragment();
                        Bundle args = new Bundle();
                        args.putString("sub_id", String.valueOf(dataList.get(position).getVchSubjectName()));
                        args.putString("stand_id", String.valueOf(dataList.get(position).getIntSTDId()));
                        syllabusDetailFragment.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, syllabusDetailFragment).commitAllowingStateLoss();


                    } catch (Exception ex) {

                    }
                } else if (PageName.contentEquals("SyllabusDetail")) {
                    try {
                        Url = "http://eserveshiksha.co.in/Trafford/pdf/" + dataList.get(position).getSyllabusurl();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                        mcontext.startActivity(browserIntent);
                    } catch (Exception ex) {

                    }

                }
            }

        });*/
        holder.name.setOnClickListener(v -> {
            cd = new ConnectionDetector(mcontext.getApplicationContext());
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                alert.setMessage("No InternetConnection");
                alert.setPositiveButton("OK", null);
                alert.show();

            } else {
                if (PageName.contentEquals("SubjectName")) {
                    try {
                        showDialog(position);
//                        startfragment(position,"PDF");
//                            holder_s = String.valueOf(dataList1.get(position).getIntSubject_id());
                    } catch (Exception ex) {
                        Toast.makeText(mcontext, "" + ex.toString(), Toast.LENGTH_LONG).show();
                    }
                } else if (PageName.contentEquals("SyllabusDetail")) {
                    try {
                        Url = RetrofitInstance.pdfUrl + dataList.get(position).getFilePath();
                        Log.d("TAG","PDFUrl"+Url);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                        mcontext.startActivity(browserIntent);
                    } catch (Exception ex) {

                    }

                }
            }

        });
    }

    private void startfragment(final int position, String holder_s) {

        SyllabusDetailFragment syllabusDetailFragment = new SyllabusDetailFragment();
        Bundle args = new Bundle();
        args.putString("sub_id", String.valueOf(dataList.get(position).getIntSubjectId()));
//                            args.putString("sub_id", String.valueOf(dataList1.get(position).getIntSubject_id()));
        args.putString("stand_id", String.valueOf(dataList.get(position).getIntSTDId()));
//                            args.putString("stand_id", String.valueOf(dataList1.get(position).getIntstandard_id()));
//                            args.putString("file_path",);
        args.putString("type", holder_s);
        Log.d("TAG","startfragment"+args);
        syllabusDetailFragment.setArguments(args);
        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, syllabusDetailFragment).commitAllowingStateLoss();

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SubjectListHolder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout syllabus;

        SubjectListHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.subName_subject);
            syllabus = (LinearLayout) itemView.findViewById(R.id.linearsubjt);
        }


    }


    private void showDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("Choose any one");

        final List<String> lables = new ArrayList<>();
        lables.add("PDF");
        lables.add("Text");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mcontext,
                android.R.layout.simple_dropdown_item_1line, lables);
        builder.setAdapter(dataAdapter, (dialog, which) -> {
            holder_s = lables.get(which);
            startfragment(position, holder_s);

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}


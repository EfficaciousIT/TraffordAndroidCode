package com.mobi.efficacious.TraffordSchool.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;
import com.mobi.efficacious.TraffordSchool.entity.SyllabusDetailsPDF;

import java.util.ArrayList;

public class SyllabusPDFAdapter extends RecyclerView.Adapter<SyllabusPDFAdapter.SubjectListHolder> {
    //    private ArrayList<SyllabusDetail> dataList;
    private ArrayList<SyllabusDetailsPDF> dataList1;
    private Context mcontext;
    ConnectionDetector cd;
    String PageName;
    private String Url;

    public SyllabusPDFAdapter(Context context, ArrayList<SyllabusDetailsPDF> dataList, String Pagename) {
        this.dataList1 = dataList;
        this.mcontext = context;
        this.PageName = Pagename;
    }

    @Override
    public SyllabusPDFAdapter.SubjectListHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.subject_row, parent, false);
        final SubjectListHolder viewHolder = new SubjectListHolder(view);
        return new SyllabusPDFAdapter.SubjectListHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SyllabusPDFAdapter.SubjectListHolder holder, int position) {
        try {
            AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
            alert.setMessage(""+dataList1.get(position).getName());
            alert.setPositiveButton("OK", null);
            alert.show();
            if (PageName.contentEquals("SyllabusDetail")) {
//                holder.name.setText(dataList1.get(position).getName());
                Url = "" + dataList1.get(position).getFilePath();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                mcontext.startActivity(browserIntent);
            }


        } catch (Exception ex) {

        }
        holder.syllabus.setOnClickListener(v -> {
            cd = new ConnectionDetector(mcontext.getApplicationContext());
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                alert.setMessage("No InternetConnection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {

                if (PageName.contentEquals("SyllabusDetail")) {
                    try {
                        // "http://eserveshiksha.co.in/SKPSchoolApi/PDF/"
                        Url = "" + dataList1.get(position).getFilePath();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url));
                        mcontext.startActivity(browserIntent);
                    } catch (Exception ex) {

                    }

                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
}

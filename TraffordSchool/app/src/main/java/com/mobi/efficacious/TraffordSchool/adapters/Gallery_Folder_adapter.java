package com.mobi.efficacious.TraffordSchool.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.dialogbox.image_zoom_dialog;
import com.mobi.efficacious.TraffordSchool.entity.SchoolDetail;
import com.mobi.efficacious.TraffordSchool.fragment.Gallery_fragment;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import java.util.ArrayList;

public class Gallery_Folder_adapter extends RecyclerView.Adapter<Gallery_Folder_adapter.ViewHolder> {
    private ArrayList<SchoolDetail> data;
    Context mcontext;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String school_id, role_id, ImageName;
    private ProgressDialog progress;

    public Gallery_Folder_adapter(ArrayList<SchoolDetail> dataList, Context context) {
        data = dataList;
        mcontext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_folder, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }


    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.eventDescriptiontv.setText(data.get(position).getEventName());
            holder.eventDescriptiontv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Folder_id= String.valueOf(data.get(position).getFolderid());
                    Gallery_fragment gallery_fragment = new Gallery_fragment();
                    Bundle args = new Bundle();
                    args.putString("Folder_id",Folder_id);
                    gallery_fragment.setArguments(args);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main,gallery_fragment).commitAllowingStateLoss();
                }
            });
        } catch (Exception ex) {

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventDescriptiontv;

        public ViewHolder(View itemView) {
            super(itemView);
            eventDescriptiontv = (TextView) itemView.findViewById(R.id.eventDescriptiontv);
        }
    }
}

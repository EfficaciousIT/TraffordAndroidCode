package com.mobi.efficacious.TraffordSchool.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetail;
import java.util.ArrayList;


public class OnlineClassDetailAdapter extends RecyclerView.Adapter<OnlineClassDetailAdapter.ViewHolder> {
    private ArrayList<DashboardDetail> data;
    public OnlineClassDetailAdapter(ArrayList<DashboardDetail> dataList) {
        data = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlne_class_detail_row,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.tv_meeting_details.setText(data.get(position).getIssueDate().toString());
            holder.tv_meeting_description.setText(data.get(position).getEndDate().toString());
            holder.tv_meeting_id.setText("Notice:"+data.get(position).getNotice().toString());
            holder.tv_meeting_password.setText("Subject:"+data.get(position).getSubject().toString());

        }catch (Exception ex) {}

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_meeting_details,tv_meeting_description,tv_meeting_id,tv_meeting_password;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_meeting_details=(TextView)itemView.findViewById(R.id.tv_meeting_details);
            tv_meeting_description=(TextView)itemView.findViewById(R.id.tv_meeting_description);
            tv_meeting_id=(TextView)itemView.findViewById(R.id.tv_meeting_id);
            tv_meeting_password=(TextView)itemView.findViewById(R.id.tv_meeting_password);
        }
    }
}

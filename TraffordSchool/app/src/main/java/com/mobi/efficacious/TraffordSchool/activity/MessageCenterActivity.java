package com.mobi.efficacious.TraffordSchool.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.adapters.MessageCenterAdapter;
import com.mobi.efficacious.TraffordSchool.database.Databasehelper;


public class MessageCenterActivity extends AppCompatActivity {
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    Databasehelper mydb;
    RecyclerView mrecyclerView;
    RecyclerView.Adapter madapter;
    HashMap<Object, Object> map;
    private ArrayList<HashMap<Object, Object>> dataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        mrecyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        mydb = new Databasehelper(MessageCenterActivity.this, "Notifications", null, 1);
        dataList = new ArrayList<HashMap<Object, Object>>();
        try {
            MessageCenterAsync messageCenterAsync = new MessageCenterAsync();
            messageCenterAsync.execute();
        } catch (Exception ex) {

        }


    }

    private class MessageCenterAsync extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(MessageCenterActivity.this);

        @Override
        protected Void doInBackground(Void... params) {

            try {
//             Cursor  cursor =mydb.querydata("Select Message,MessageDate from MessageCenter ");
                Cursor cursor = mydb.querydata("Select Message,MessageDate from MessageCenter order by ID desc");
                int count = cursor.getCount();
                if (count == 0) {
                    map = new HashMap<Object, Object>();
                    map.put("Message", "No Data Available");
                    map.put("MessageDate", "");
                    dataList.add(map);
                }

                cursor.moveToFirst();
                if (cursor != null) {

                    if (cursor.moveToFirst()) {
                        do {
                            map = new HashMap<Object, Object>();
                            map.put("Message", cursor.getString(cursor.getColumnIndex("Message")));
                            map.put("MessageDate", cursor.getString(cursor.getColumnIndex("MessageDate")));
                            dataList.add(map);
                        } while (cursor.moveToNext());

                    }

                }

            } catch (Exception e) {
                map = new HashMap<Object, Object>();
                map.put("Message", "No Data Available");
                map.put("MessageDate", "");
                dataList.add(map);

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                mrecyclerView.setHasFixedSize(true);
                mrecyclerView.setLayoutManager(new LinearLayoutManager(MessageCenterActivity.this));
                madapter = new MessageCenterAdapter(dataList, "MessageCenter");
                mrecyclerView.setAdapter(madapter);
            } catch (Exception ex) {

            }

            this.dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Processing...");
            dialog.show();
            //  progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}



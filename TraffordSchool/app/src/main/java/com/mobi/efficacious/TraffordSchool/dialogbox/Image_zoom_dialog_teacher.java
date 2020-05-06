package com.mobi.efficacious.TraffordSchool.dialogbox;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.mobi.efficacious.TraffordSchool.fragment.Staff_Calender_attendence;
import com.squareup.picasso.Picasso;

import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.fragment.Teacher_Calender_attendence;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;



public class Image_zoom_dialog_teacher extends Dialog {
    ImageView imageView;
    ImageView callimage, messageimage, videcallimage;
    SharedPreferences settings;
    String mobile_no="",role_id;

    public static String image;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final String PREFRENCES_NAME = "myprefrences";
    public Image_zoom_dialog_teacher(@NonNull Context context) {
        super(context);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_image);

        settings = getContext().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        imageView = (ImageView) findViewById(R.id.imageView6);
        callimage = (ImageView) findViewById(R.id.imageView12);
        messageimage = (ImageView) findViewById(R.id.imageView16);
        videcallimage = (ImageView) findViewById(R.id.imageView18);
        role_id = settings.getString("TAG_USERTYPEID", "");
        try {
//            if (role_id.equals("3")) {
//                mobile_no = Teacher_Calender_attendence.intMobileNo;
//                image=Teacher_Calender_attendence.image;
//            }
//            else {
//                mobile_no = Staff_Calender_attendence.intMobileNo;
//                image=Staff_Calender_attendence.image;
//            }
            if (role_id.equals("5")&&!Teacher_Calender_attendence.intMobileNo.isEmpty()) {
                mobile_no = Teacher_Calender_attendence.intMobileNo;
                image=Teacher_Calender_attendence.image;
            }
            else {
                mobile_no = Staff_Calender_attendence.intMobileNo;
                image=Staff_Calender_attendence.image;
            }

            Log.e("TAG","mobileno"+mobile_no);
            String url = RetrofitInstance.Image_URL +  image+ "";
            Glide.with(getContext())
                    .load(url)
                    .error(R.mipmap.profile)
                    .into(imageView);
        } catch (Exception ex) {

        }

        callimage.setOnClickListener(arg0 -> {
            try {
//                Toast.makeText(getContext(), "Call Image Clicked", Toast.LENGTH_LONG).show();
                Log.d("TAG","Mobile_Tag" +mobile_no);
                if (mobile_no.contentEquals("-") || mobile_no.contentEquals("")||mobile_no.equals(null)) {
                    Toast.makeText(getContext(), "Mobile No. not available", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("TAG","in_Else" +mobile_no);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile_no.trim()));
                    getContext().startActivity(intent);

                }
            } catch (Exception ex) {

                Log.e("TAG","Call_Exception"+ex.toString());

            }
        });
        videcallimage.setOnClickListener(view -> Toast.makeText(getContext(), "Mobile No. not available", Toast.LENGTH_LONG).show());
        messageimage.setOnClickListener(v -> {
            try {
                if (mobile_no.contentEquals("-") || mobile_no.contentEquals("")|| mobile_no.contentEquals(null)) {
                    Toast.makeText(getContext(), "Mobile No. not available", Toast.LENGTH_LONG).show();
                } else {
                    Uri SMS_URI = Uri.parse("smsto:" + mobile_no.trim()); //Replace the phone number
                    Intent sms = new Intent(Intent.ACTION_VIEW, SMS_URI);
                    sms.putExtra("sms_body", ""); //Replace the message witha a vairable
                    getContext().startActivity(sms);
                }
            } catch (Exception ex) {

            }


        });


    }
}




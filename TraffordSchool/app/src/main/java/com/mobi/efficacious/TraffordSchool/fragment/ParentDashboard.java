package com.mobi.efficacious.TraffordSchool.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.Tab.Attendence_sliding_tab;
import com.mobi.efficacious.TraffordSchool.Tab.Chating_Sliding_Tab;
import com.mobi.efficacious.TraffordSchool.Tab.TimetableActivity_student;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentDashboard extends Fragment implements View.OnClickListener {
    View myview;
    LinearLayout syllabusLayout, profile_layout, gallery_layout, timetable_layout, exam_layout, notification_layout, homework_layout, attandance_layout, noticeboard_layout, holiday_layout;
    String title = "";
    TextView username, userID;
    FragmentManager fragmentManager;
    SharedPreferences settings;
    String role_id, name1, user_id, academic_id, school_id, stud_id, stand_id, Url, name;
    private static final String PREFRENCES_NAME = "myprefrences";
    CircleImageView ProfileImage_dash;
    DrawerLayout mDrawerLayout;
    Button nav_button;
    ImageView chat, logout;

    public ParentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myview = inflater.inflate(R.layout.fragment_parent_dashboard, container, false);
        initialization(myview);
        fragmentManager = getFragmentManager();
        settings = getContext().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        user_id = settings.getString("TAG_USERID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        school_id = settings.getString("TAG_SCHOOL_ID", "");
//        name = settings.getString("TAG_USERNAME", "");
        name = settings.getString("TAG_NAME", "");
        operations();

//        mDrawerLayout.openDrawer(mDrawerLayout);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((MainActivity) getActivity()).setActionBarTitle("Dashboard");
        return myview;

    }

    private void operations() {
        Log.e("TAG", "Username" + name + "__" + user_id);
        username.setText(name);
        Log.e("Image", "TAG_PROFILE_IMAGE_PRO" + settings.getString("TAG_PROFILE_IMAGE_PRO", ""));
        String url = RetrofitInstance.Image_URL + settings.getString("TAG_PROFILE_IMAGE_PRO", "");
        Glide.with(getActivity())
                .load(url) // image url
                .error(R.mipmap.profile)
                .into(ProfileImage_dash);
        if (role_id.contentEquals("1")) {
            userID.setText("Parent");
        } else if (role_id.contentEquals("3")) {
            userID.setText("Teacher");
        } else if (role_id.contentEquals("5")) {
            userID.setText("Admin");
        }
    }

    private void initialization(View myview) {
        ProfileImage_dash = myview.findViewById(R.id.image_parent);
        profile_layout = myview.findViewById(R.id.profile_icon);
        timetable_layout = myview.findViewById(R.id.timetable_layout);
        exam_layout = myview.findViewById(R.id.exam_layout);
//        notification_layout = myview.findViewById(R.id.notification_layout);
        homework_layout = myview.findViewById(R.id.homework_layout);
        attandance_layout = myview.findViewById(R.id.attendance_layout);
        noticeboard_layout = myview.findViewById(R.id.noticeboard_layout);
        holiday_layout = myview.findViewById(R.id.holiday_layout);
        username = myview.findViewById(R.id.user_name);
        userID = myview.findViewById(R.id.user_id);
        gallery_layout = myview.findViewById(R.id.gallery_layout);
        syllabusLayout = myview.findViewById(R.id.syllabusLayout);

        nav_button = myview.findViewById(R.id.btn_navigation);

        chat = myview.findViewById(R.id.chat);
        logout = myview.findViewById(R.id.logout);

        //click listener
        profile_layout.setOnClickListener(this);
        timetable_layout.setOnClickListener(this);
        exam_layout.setOnClickListener(this);
//        notification_layout.setOnClickListener(this);
        homework_layout.setOnClickListener(this);
        attandance_layout.setOnClickListener(this);
        noticeboard_layout.setOnClickListener(this);
        holiday_layout.setOnClickListener(this);
        gallery_layout.setOnClickListener(this);
        syllabusLayout.setOnClickListener(this);
        nav_button.setOnClickListener(this);
        chat.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_icon:
//                Toast.makeText(getContext(), "Profile", Toast.LENGTH_LONG).show();
                fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                break;
            case R.id.timetable_layout:
                title = "TimeTable";
                stand_id = settings.getString("TAG_STANDERDID", "");
                TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                Bundle args = new Bundle();
                args.putString("std_id", stand_id);
                args.putString("key", "From_Parent_Dashboard");
                timetableActivity_student.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.content_main, timetableActivity_student, null).addToBackStack("TimeTable").commitAllowingStateLoss();

                break;
            case R.id.exam_layout:
                title = "Examination";
                stand_id = settings.getString("TAG_STANDERDID", "");
                StudentExamFragment studentExamActivity = new StudentExamFragment();
                Bundle args1 = new Bundle();
                args1.putString("std_id", stand_id);
                studentExamActivity.setArguments(args1);
                getFragmentManager().beginTransaction().replace(R.id.content_main, studentExamActivity).commitAllowingStateLoss();
                break;
            /*case R.id.notification_layout:
                try {
                    title = "Notification";
                    fragmentManager.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();
                } catch (Exception ex) {

                }
                break;*/
            case R.id.homework_layout:
                title = "Home Work";
                DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                Bundle hwargs = new Bundle();
                hwargs.putString("value", "HomeWork");
                dailyDiaryListFragment.setArguments(hwargs);
                fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                break;
            case R.id.attendance_layout:
                title = "Attendance";
                stud_id = settings.getString("TAG_STUDENTID", "");
                Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                Bundle attendance = new Bundle();
                attendance.putString("Stud_name", name1);
                attendance.putString("stud_id12", stud_id);
                attendance.putString("attendence", "student_self_attendence");
                attendence_sliding_tab.setArguments(attendance);
                getFragmentManager().beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();

                break;
            case R.id.noticeboard_layout:
                title = "Noticeboard";
                getFragmentManager().beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();

                break;
            case R.id.holiday_layout:
                title = "Holiday";
                getFragmentManager().beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();
                break;

            case R.id.gallery_layout:
                title = "Gallery";
                fragmentManager.beginTransaction().replace(R.id.content_main, new Gallery_fragment()).commitAllowingStateLoss();
                break;
            case R.id.syllabusLayout:
                title = "Syllabus";
                stand_id = settings.getString("TAG_STANDERDID", "");
                StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
                Bundle sargs = new Bundle();
                sargs.putString("std_id", stand_id);
                subjectFragment.setArguments(sargs);
                fragmentManager.beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();
                break;
            case R.id.btn_navigation:
                mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!mDrawerLayout.isDrawerOpen(Gravity.START))
                    mDrawerLayout.openDrawer(Gravity.START);
                else mDrawerLayout.closeDrawer(Gravity.END);
                break;

            case R.id.chat:
                mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);
                // If the navigation drawer is not open then open it, if its already open then close it.
//                getActivity().getActionBar().setTitle("Chat");
                fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();

                break;

            case R.id.logout:
//                mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);

                break;
        }

    }

    public void setActionBarTitle(String title) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }
}

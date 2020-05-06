package com.mobi.efficacious.TraffordSchool.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.tabs.TabLayout;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.activity.MainActivity;
import com.mobi.efficacious.TraffordSchool.fragment.FridayFragment;
import com.mobi.efficacious.TraffordSchool.fragment.MondayFragment;
import com.mobi.efficacious.TraffordSchool.fragment.SaterdayFragment;
import com.mobi.efficacious.TraffordSchool.fragment.ThursdayFragment;
import com.mobi.efficacious.TraffordSchool.fragment.TuesdayFragment;
import com.mobi.efficacious.TraffordSchool.fragment.WedsdayFragment;


public class TimetableActivity_student extends Fragment {
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    ViewPager viewPager;
    TabLayout tabLayout;
    String role_id;
    public static String stdid, div;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calender, container, false);
        try {
            stdid = getArguments().getString("std_id");
            settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
            div = getArguments().getString("std_div");
        } catch (Exception ex) {

        }
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        try {
                            if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                                stdid = settings.getString("TAG_STANDERDID", "");
                                TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                                Bundle args = new Bundle();
                                args.putString("std_id", stdid);
                                timetableActivity_student.setArguments(args);
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();
                            } else {
                                Timetable_sliding_tab timetable_sliding_tab = new Timetable_sliding_tab();
                                Bundle args = new Bundle();
                                args.putString("std_id", stdid);
                                args.putString("std_div", div);
                                args.putString("select_timetable", "select_timetable");
                                timetable_sliding_tab.setArguments(args);
                                MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, timetable_sliding_tab).commitAllowingStateLoss();
                            }
                        } catch (Exception ex) {

                        }


                        return true;
                    }
                }
                return false;
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;
        // activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightorange)));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager(viewPager);
        // after you set the adapter you have to check if view is laid out, i did a custom method for it
        if (ViewCompat.isLaidOut(tabLayout)) {
            setViewPagerListener();
        } else {
            tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    setViewPagerListener();
                    tabLayout.removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    private void setViewPagerListener() {
        tabLayout.setupWithViewPager(viewPager);
        // use class TabLayout.ViewPagerOnTabSelectedListener
        // note that it's a class not an interface as OnTabSelectedListener, so you can't implement it in your activity/fragment
        // methods are optional, so if you don't use them, you can not override them (e.g. onTabUnselected)
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        try {
            viewPagerAdapter.addFragment(new MondayFragment(), "MON");
            viewPagerAdapter.addFragment(new TuesdayFragment(), "TUE");
            viewPagerAdapter.addFragment(new WedsdayFragment(), "WED");
            viewPagerAdapter.addFragment(new ThursdayFragment(), "THR");
            viewPagerAdapter.addFragment(new FridayFragment(), "FRI");
            viewPagerAdapter.addFragment(new SaterdayFragment(), "SAT");
        } catch (Exception ex) {

        }


        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }
}
package com.mobi.efficacious.TraffordSchool.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mobi.efficacious.TraffordSchool.entity.LoginDetail;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import com.mobi.efficacious.TraffordSchool.Interface.DataService;
import com.mobi.efficacious.TraffordSchool.R;
import com.mobi.efficacious.TraffordSchool.activity.Login_activity;
import com.mobi.efficacious.TraffordSchool.adapters.Division_spinner_adapter;
import com.mobi.efficacious.TraffordSchool.adapters.NoticeBoardAdapter;
import com.mobi.efficacious.TraffordSchool.adapters.StudentListAdapter;
import com.mobi.efficacious.TraffordSchool.common.ConnectionDetector;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetail;
import com.mobi.efficacious.TraffordSchool.entity.DashboardDetailsPojo;
import com.mobi.efficacious.TraffordSchool.entity.Holiday;

import com.mobi.efficacious.TraffordSchool.webApi.RetrofitInstance;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class Admin_Dashboard extends Fragment {
    View myview;
    TextView TextView_student;
    TextView TextView_staff;
    TextView TextView_teacher;
    TextView TextView_pendingfee;
    TextView TextView_receivedfee;
    TextView TextView_totalfee;
    String Totalfee;
    int dayscount;
    String Receivedfee;
    String Pendingfee, Schooli_id;
    RecyclerView studentlist;
    StudentListAdapter studadapter;
    String newDate, fesival;
    ArrayList<Holiday> holiday1 = new ArrayList<Holiday>();
    Context mContext;
    RecyclerView noticeboard;
    RecyclerView.Adapter madapter;
    String Admin_id, academic_id;
    FrameLayout calenderview;
    Date holidayDay;
    ArrayList<String> dates = new ArrayList<String>();
    CaldroidFragment mCaldroidFragment = new CaldroidFragment();
    String a, role_id;
    String status, refreshedToken;
    ConnectionDetector cd;
    String date_selected;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String command, User_id;
    ColorDrawable bgToday;
    private CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myview = inflater.inflate(R.layout.activity_dashboard, null);
        cd = new ConnectionDetector(getActivity().getApplicationContext());
        mContext = getActivity();
        TextView_student = (TextView) myview.findViewById(R.id.tv_Student);
        TextView_staff = (TextView) myview.findViewById(R.id.tv_Staff);
        TextView_teacher = (TextView) myview.findViewById(R.id.tv_Teacher);
        TextView_pendingfee = (TextView) myview.findViewById(R.id.tv_pendingfee);
        TextView_receivedfee = (TextView) myview.findViewById(R.id.tv_receivedfee);
        TextView_totalfee = (TextView) myview.findViewById(R.id.tv_totalfee);
        noticeboard = (RecyclerView) myview.findViewById(R.id.dashnoticeboard_list);
        noticeboard.setNestedScrollingEnabled(false);
        studentlist = (RecyclerView) myview.findViewById(R.id.dashstud_list);
        studentlist.setNestedScrollingEnabled(false);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        Admin_id = settings.getString("TAG_USERID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.SUNDAY);
        calenderview = (FrameLayout) myview.findViewById(R.id.cal_container);
        mCaldroidFragment.setArguments(args);

        if (!cd.isConnectingToInternet()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No Internet Connection");
            alert.setPositiveButton("OK", null);
            alert.show();

        } else {

            try {
                HolidayAsync();
                studentAsync();
                teacherAsync();
                staffAsync();
                StudentListAsync();
                NoticeboardAsync();
                refreshedToken = FirebaseInstanceId.getInstance().getToken();
                sendTokenToServer(refreshedToken);
            } catch (Exception ex) {

            }

        }

        mCaldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.get(Calendar.YEAR);
                    if (Calendar.DAY_OF_WEEK == Calendar.SUNDAY) {
                        mCaldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(Color.RED), date);
                    }
                    int day1 = date.getDate();
                    int month1 = ((date.getMonth()) + 1);
                    NumberFormat f = new DecimalFormat("00");
                    date_selected = ((f.format(day1)) + "/" + (f.format(month1)) + "/" + String.valueOf(calendar.get(Calendar.YEAR)));
//                 date_selected= String.valueOf(day1)+("/")+ String.valueOf(month1)+("/")+ String.valueOf(calendar.get(Calendar.YEAR));

                    boolean status = dates.contains(date_selected);
                    if (status == true) {
                        // festivalnmae(date_selected);
                        try {
                            String date_selected1 = date_selected;
                            int sizee = holiday1.size();
                            for (int i = 0; i < holiday1.size(); i++) {
                                String holidaydate = holiday1.get(i).getFromDate();

                                if (date_selected1.contentEquals(holidaydate)) {
                                    String holidayname = holiday1.get(i).getHoliday_name();
                                    Toast.makeText(getActivity(), holidayname, Toast.LENGTH_SHORT).show();
                                    break;
                                }


                            }
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    } else {
                        Toast.makeText(getActivity(), " " + date_selected, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {

                }

            }
        });
        return myview;
    }

    public void studentAsync() {
        try {

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("StudentCountByPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserverStudentCount()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverStudentCount() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStudentCount((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void generateStudentCount(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                try {
                    TextView_totalfee.setText("Student: " + taskListDataList.get(0).getPresent() + "/" + taskListDataList.get(0).getCount());
                } catch (Exception ex) {
                    TextView_totalfee.setText("Student: 0/0");
                }
                try {
                    TextView_student.setText("Student: " + taskListDataList.get(1).getPresent() + "/" + taskListDataList.get(1).getCount());
                } catch (Exception ex) {
                    TextView_student.setText("Student: 0/0");
                }
            } else {
                TextView_totalfee.setText("Student: 0/0");
                TextView_student.setText("Student: 0/0");
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void staffAsync() {

        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("StaffCountByPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserverStaffCount()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverStaffCount() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStaffCount((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void generateStaffCount(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                try {
                    TextView_pendingfee.setText("Staff: " + taskListDataList.get(0).getPresent() + "/" + taskListDataList.get(0).getCount());
                } catch (Exception ex) {
                    TextView_pendingfee.setText("Staff: 0/0 ");
                }
                try {
                    TextView_staff.setText("Staff: " + taskListDataList.get(1).getPresent() + "/" + taskListDataList.get(1).getCount());
                } catch (Exception ex) {
                    TextView_staff.setText("Staff: 0/0 ");
                }
            } else {
                TextView_pendingfee.setText("Staff: 0/0 ");
                TextView_staff.setText("Staff: 0/0 ");
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }


    public void teacherAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("TeacherCountBYPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserverTeacherCount()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverTeacherCount() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateTeacherCount((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void generateTeacherCount(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                try {
                    TextView_receivedfee.setText("Teacher: " + taskListDataList.get(0).getPresent() + "/" + taskListDataList.get(0).getCount());
                } catch (Exception ex) {
                    TextView_receivedfee.setText("Teacher: 0/0");
                }
                try {
                    TextView_teacher.setText("Teacher: " + taskListDataList.get(1).getPresent() + "/" + taskListDataList.get(1).getCount());
                } catch (Exception ex) {
                    TextView_teacher.setText("Teacher: 0/0");
                }
            } else {
                TextView_receivedfee.setText("Teacher: 0/0");
                TextView_teacher.setText("Teacher: 0/0");
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }


    public void StudentListAsync() {

        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getDashboardDetails("genderwiseStudentBYPrincipal", academic_id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserver() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStudentList((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    public void generateStudentList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                studadapter = new StudentListAdapter(taskListDataList, getActivity());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                studentlist.setLayoutManager(layoutManager);

                studentlist.setAdapter(studadapter);
            } else {

                //   Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }


    public void NoticeboardAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                mCompositeDisposable.add(service.getDashboardDetails("NoticeBoardPrincipal", academic_id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserverNotice()));
            } else {
                mCompositeDisposable.add(service.getDashboardDetails("NoticeBoard", academic_id, Schooli_id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserverNotice()));
            }

        } catch (Exception ex) {
        }
    }

    public DisposableObserver<DashboardDetailsPojo> getObserverNotice() {
        return new DisposableObserver<DashboardDetailsPojo>() {

            @Override
            public void onNext(@NonNull DashboardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateNoticeboardList((ArrayList<DashboardDetail>) dashboardDetailsPojo.getDashboardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
            }
        };
    }

    public void generateNoticeboardList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                noticeboard.setHasFixedSize(true);
                noticeboard.setLayoutManager(new LinearLayoutManager(getActivity()));
                madapter = new NoticeBoardAdapter(taskListDataList);
                noticeboard.setAdapter(madapter);
            } else {

                //  Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void HolidayAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<DashboardDetailsPojo> call = service.getDashboardDetails("HolidaysAndVacation", academic_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DashboardDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(DashboardDetailsPojo body) {
                    try {
                        generateHolidayList((ArrayList<DashboardDetail>) body.getDashboardDetails());
                    } catch (Exception ex) {

                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {

                }
            });
        } catch (Exception ex) {

        }
    }


    public void generateHolidayList(ArrayList<DashboardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                holiday1.clear();
                for (int j = 0; j < taskListDataList.size(); j++) {
                    Holiday hol = new Holiday();
                    a = taskListDataList.get(j).getDtFromDate();
                    dayscount = taskListDataList.get(j).getIntNoOfDay();
                    fesival = taskListDataList.get(j).getHoliday();
                    hol.setFromDate(a);
                    hol.setHoliday_name(fesival);
                    for (int i = 0; i < dayscount - 1; i++) {
                        Holiday vac1 = new Holiday();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        try {
                            //Setting the date to the given date
                            if (i == 0) {
                                c.setTime(sdf.parse(a));
                            } else {
                                c.setTime(sdf.parse(newDate));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //Number of Days to add
                        c.add(Calendar.DAY_OF_MONTH, 1);
                        //Date after adding the days to the given date
                        newDate = sdf.format(c.getTime());
                        vac1.setFromDate(newDate);
                        vac1.setHoliday_name(fesival);
                        holiday1.add(vac1);
                        dates.add(newDate);

                    }
                    dates.add(a);
                    holiday1.add(hol);
                }
                holiday_list();
                try {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.cal_container, mCaldroidFragment).commit();
                    status = "";
                } catch (Exception ex) {

                }
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }

    }

    public void holiday_list() {
        int day = 0;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        for (int i = 0; i < dates.size(); i++) {
            String inputString2 = dates.get(i);
            String inputString1 = myFormat.format(date);

            try {
                //Converting String format to date format
                Date date1 = null;
                try {
                    date1 = myFormat.parse(inputString1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2 = myFormat.parse(inputString2);
                //Calculating number of days from two dates
                long diff = date2.getTime() - date1.getTime();
                long datee = diff / (1000 * 60 * 60 * 24);
                //Converting long type to int type
                day = (int) datee;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, day);
            holidayDay = cal.getTime();
//            ColorDrawable bgToday = new ColorDrawable(Color.RED);
            ColorDrawable bgToday = new ColorDrawable(Color.LTGRAY);
            mCaldroidFragment.setBackgroundDrawableForDate(bgToday, holidayDay);

        }
    }

    public void sendTokenToServer(final String strToken) {
        String email = settings.getString("TAG_USEREMAILID", "");
        if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
            User_id = settings.getString("TAG_STUDENTID", "");
        } else {
            User_id = settings.getString("TAG_USERID", "");
        }
        switch (Integer.parseInt(role_id)) {
            case 1:
                command = "FcmTokenStudent";
                break;
            case 2:
                command = "FcmTokenStudent";
                break;
            case 3:
                command = "FcmTokenTeacher";
                break;
            case 4:
                command = "FcmTokenStaff";
                break;
            case 5:
                command = "FcmTokenAdmin";
                break;
            case 6:
                command = "FcmTokenPrincipal";
                break;
            case 7:
                command = "FcmTokenManager";
                break;

        }

        settings.edit().putString("TAG_USERFIREBASETOKEN", strToken).apply();
        try {
            FCMTOKENASYNC(command, strToken, email);
        } catch (Exception ex) {

        }

    }

    public void FCMTOKENASYNC(String command, String Firebasetoken, String EMail) {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            LoginDetail loginDetail = new LoginDetail(Firebasetoken, EMail, Integer.parseInt(User_id), Integer.parseInt(Schooli_id), Integer.parseInt(academic_id));
            Observable<ResponseBody> call = service.FCMTokenUpdate(command, loginDetail);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onNext(ResponseBody body) {
                    try {

                    } catch (Exception ex) {


                    }
                }

                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onComplete() {
                }
            });
        } catch (Exception ex) {

        }
    }
}
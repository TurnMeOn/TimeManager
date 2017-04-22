package wesayallright.timemanager.surface;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

import wesayallright.timemanager.InnerLayer.LocalFile.LocalFile;
import wesayallright.timemanager.InnerLayer.User;
import wesayallright.timemanager.R;
import wesayallright.timemanager.surface.activitiesFragment.Activities;

public class MainActivity extends AppCompatActivity implements
        Calendar.OnFragmentInteractionListener,
        Activities.OnFragmentInteractionListener,
        Groups.OnFragmentInteractionListener,
        Me.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private static final int CALENDAR = 1;
    private static final int ACTIVITIES = 2;
    private static final int GROUPS = 3;
    private static final int ME = 4;

    // 保存每个fragment的实例对象，防止重复初始化
    private Activities activities_fragment_instance;
    private Calendar calendar_fragment_instance;
    private Groups groups_fragment_instance;
    private Me me_fragment_instance;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    showFragment(CALENDAR);
                    break;

                case R.id.navigation_activities:
                    showFragment(ACTIVITIES);
                    break;

                case R.id.navigation_me:
                    showFragment(ME);

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(CALENDAR);

        // 设置程序路径
        LocalFile.setCwd(getApplicationContext().getFilesDir().getAbsolutePath());
        Log.i("Path", getApplicationContext().getFilesDir().getAbsolutePath());
        try {
            User u = User.signIn("rightID", "rightPassword");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void hideFragments(FragmentTransaction ft) {
        if (calendar_fragment_instance != null) {
            ft.hide(calendar_fragment_instance);
        }
        if (activities_fragment_instance != null) {
            ft.hide(activities_fragment_instance);
        }
        if (groups_fragment_instance != null) {
            ft.hide(groups_fragment_instance);
        }
        if (me_fragment_instance != null) {
            ft.hide(me_fragment_instance);
        }
    }

    // 显示相应的fragment
    private void showFragment(int fragmentIndex) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);

        switch (fragmentIndex) {
            case CALENDAR:
                if (calendar_fragment_instance == null) {
                    calendar_fragment_instance = new Calendar();
                    ft.add(R.id.content, calendar_fragment_instance);
                } else {
                    ft.show(calendar_fragment_instance);
                }
                break;
            case ACTIVITIES:
                if (activities_fragment_instance == null) {
                    activities_fragment_instance = Activities.newInstance();
                    ft.add(R.id.content, activities_fragment_instance);
                } else {
                    ft.show(activities_fragment_instance);
                }
                break;
            case GROUPS:
                if (groups_fragment_instance == null) {
                    groups_fragment_instance = new Groups();
                    ft.add(R.id.content, groups_fragment_instance);
                } else {
                    ft.show(groups_fragment_instance);
                }
                break;
            case ME:
                if (me_fragment_instance == null) {
                    me_fragment_instance = new Me();
                    ft.add(R.id.content, me_fragment_instance);
                } else {
                    ft.show(me_fragment_instance);
                }
                break;
        }
        ft.commit();
    }

    public void onFragmentInteraction(Uri uri) {

    }
}

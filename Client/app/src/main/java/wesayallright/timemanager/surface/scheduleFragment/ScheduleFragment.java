package wesayallright.timemanager.surface.scheduleFragment;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.RequiresApi;
import android.support.annotation.NonNull;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import android.widget.NumberPicker;
import android.widget.ToggleButton;
import android.widget.AdapterView;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Spinner;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.Window;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Arrays;

import wesayallright.timemanager.R;

public class ScheduleFragment extends Fragment {
    int DialogDay, DialogStartHour, DialogStartMinute, DialogEndHour, DialogEndMinute, FirstMonth = 2, FirstDay = 27, FirstWeek, RealWeek, NowWeek, BeReplacedX, BeReplacedY, BeReplacedZ;
    boolean Activated = false, Running = false, IsLonger = false, Stop = false, Bottom = false, Top = false;
    NumberPicker DayPicker, StartHourPicker, StartMinutePicker, EndHourPicker, EndMinutePicker;
    Calendar Today = Calendar.getInstance(), First = Calendar.getInstance();
    ArrayList<ArrayList<ArrayList<Course>>> CourseArray = new ArrayList<>();//CourseArray.get(week).get(day).get(xth in day)
    ArrayList<ArrayList<TextView>> TextViewArray = new ArrayList<>();//TextViewArray.get(day).get(xth in day)
    ArrayList<ToggleButton> ToggleArray = new ArrayList<>();
    ArrayList<RelativeLayout> Layout = new ArrayList<>();
    ArrayList<Integer> Weeks = new ArrayList<>();
    ArrayList<Course> course = new ArrayList<>();
    static final String TAG = "ScheduleFragment";
    Course NullCourse = new Course(), using;
    SharedPreferences sharedPreferences;
    EditText EditName, EditRoom;
    TextView TextTime, TextWeek;
    ImageView STRImageView2;
    View RunnableView, view;
    float lastY;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        initializing();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        if (Activated)
            for (int i = 0; i < course.size(); i++)
                if (course.get(i).equals(NullCourse))
                    course.get(i).removeinfile(sharedPreferences);
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void initializing() {
        sharedPreferences = getActivity().getSharedPreferences("Schedule_Fragment_data0", Context.MODE_PRIVATE);
        InitializeTextViewCourseArray();
        InitializeDate();
        InitializeRelativeLayout();
        InitializeSpinner();
        InitializeImageView();
    }

    void InitializeTextViewCourseArray() {
        TextViewArray.clear();
        CourseArray.clear();
        for (int i = 0; i < 7; i++)
            TextViewArray.add(new ArrayList<TextView>());
        for (int i = 0; i < 20; i++) {
            CourseArray.add(new ArrayList<ArrayList<Course>>());
            for (int j = 0; j < 7; j++)
                CourseArray.get(i).add(new ArrayList<Course>());
        }
    }

    void InitializeDate() {
        if (Today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            RealWeek = Today.get(Calendar.WEEK_OF_YEAR) - 1;
        else
            RealWeek = Today.get(Calendar.WEEK_OF_YEAR);
        First.set(Calendar.YEAR, 2017);
        First.set(Calendar.MONTH, FirstMonth - 1);
        First.set(Calendar.DATE, FirstDay);
        FirstWeek = First.get(Calendar.WEEK_OF_YEAR);
        NowWeek = Today.get(Calendar.WEEK_OF_YEAR);
    }

    void InitializeRelativeLayout() {
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative1));
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative2));
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative3));
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative4));
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative5));
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative6));
        Layout.add((RelativeLayout) view.findViewById(R.id.SBrelative7));
        for (int i = 0; i < 7; i++)
            Layout.get(i).setOnTouchListener(RelativeLayoutOnTouchListener());
    }

    void InitializeSpinner() {
        Spinner spinner = (Spinner) view.findViewById(R.id.STMspinner);
        ArrayList<String> list = new ArrayList<>();
        String[] Items = {"第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周", "第八周",
                "第九周", "第十周", "第十一周", "第十二周", "第十三周", "第十四周", "第十五周", "第十六周",
                "第十七周", "第十八周", "第十九周", "第二十周"};
        list.addAll(Arrays.asList(Items).subList(0, 20));
        MyAdapter adapter = new MyAdapter(this.getActivity(), list, RealWeek - FirstWeek);//变红的!!!
        spinner.setAdapter(adapter);
        spinner.setSelection(RealWeek - FirstWeek);
        spinner.setOnItemSelectedListener(SpinnerOnItemSelectedListener());
    }

    void InitializeImageView() {
        ImageView STRImageView = (ImageView) view.findViewById(R.id.STRimageview);
        STRImageView.setOnClickListener(STRImageViewOnClickListener());
        STRImageView.setOnTouchListener(STRImageViewOnTouchListener());
    }

    static int ToPix(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    static int ToDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    String WeekProduct(ArrayList<Integer> week) {
        String string = "";
        for (int i = 0; i < week.size(); i++) {
            if (i != week.size() - 1)
                string += week.get(i) + " ";
            else
                string += week.get(i);
        }
        return string;
    }

    ArrayList<Integer> TimeParse(String time) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String[] day = new String[]{"一", "二", "三", "四", "五", "六", "日"};
        for (int i = 0; i < 7; i++)
            if (time.contains(day[i]))
                arrayList.add(i);
        int num = -1;
        for (int i = 0; i < time.length(); i++)
            if ('0' <= time.charAt(i) && time.charAt(i) <= '9')
                if (num != -1)
                    num = num * 10 + time.charAt(i) - '0';
                else
                    num = time.charAt(i) - '0';
            else {
                if (num != -1) {
                    arrayList.add(num);
                    num = -1;
                }
            }
        if (num != -1)
            arrayList.add(num);
        return arrayList;
    }

    ArrayList<Integer> WeekParse(String week) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        int num = -1;
        for (int i = 0; i < week.length(); i++)
            if ('0' <= week.charAt(i) && week.charAt(i) <= '9')
                if (num != -1)
                    num = num * 10 + week.charAt(i) - '0';
                else
                    num = week.charAt(i) - '0';
            else {
                if (num != -1) {
                    arrayList.add(num);
                    num = -1;
                }
            }
        if (num != -1)
            arrayList.add(num);
        return arrayList;
    }

    void LogICourse(String tag, Course x) {
        if (x.day == -1)
            return;
        String[] dayname = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        Log.i(TAG, tag + ": Course: name:" + x.name + "  room:" + x.room);
        Log.i(TAG, tag + ": Course: week:" + x.week);
        Log.i(TAG, tag + ": Course: day:" + dayname[x.day] + "  from " + x.starthour + ":" + x.startmin + " to " + x.endhour + ":" + x.endmin);
        Log.i(TAG, tag + ": Course: teacher" + x.teacher + "  color:" + x.color + "  priority:" + x.priority);
        Log.i(TAG, tag);
    }

    void SetDate(int position) {
        //更改SM日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, FirstMonth - 1);
        calendar.set(Calendar.DATE, FirstDay);
        calendar.add(Calendar.DAY_OF_YEAR, 7 * position);
        TextView month = (TextView) view.findViewById(R.id.SMmonth);

        int Month = calendar.get(Calendar.MONTH) + 1;
        month.setText(" " + Month + "月");
        month.postInvalidate();

        TextView mon = (TextView) view.findViewById(R.id.SMmon);
        TextView tue = (TextView) view.findViewById(R.id.SMtue);
        TextView wed = (TextView) view.findViewById(R.id.SMwed);
        TextView thu = (TextView) view.findViewById(R.id.SMthu);
        TextView fri = (TextView) view.findViewById(R.id.SMfri);
        TextView sat = (TextView) view.findViewById(R.id.SMsat);
        TextView sun = (TextView) view.findViewById(R.id.SMsun);
        TextView day[] = {mon, tue, wed, thu, fri, sat, sun};
        String daystr[] = {"一", "二", "三", "四", "五", "六", "日"};

        int dayofweek = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7;
        if (RealWeek == NowWeek)
            day[dayofweek].setBackgroundColor(0x7f1AE6E6);
        else
            day[dayofweek].setBackgroundColor(0x7fC3C7C0);

        int firstdayofweek = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 7; i++)
            if ((firstdayofweek + i) == (calendar.getActualMaximum(Calendar.DATE) + 1))
                day[i].setText(" " + "周" + daystr[i] + " " + " " + (Month + 1) + "月");
            else if ((firstdayofweek + i) < (calendar.getActualMaximum(Calendar.DATE) + 1))
                day[i].setText(" " + "周" + daystr[i] + " " + " " + ((firstdayofweek + i) % (calendar.getActualMaximum(Calendar.DATE) + 1)) + "日");
            else
                day[i].setText(" " + "周" + daystr[i] + " " + " " + ((firstdayofweek + i) % (calendar.getActualMaximum(Calendar.DATE))) + "日");
        for (int i = 0; i < 7; i++)
            day[i].postInvalidate();
    }

    void ReadCourse() {
        Log.i(TAG, "ReadCourse: READ COURSE START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        course.clear();
        int num;
        for (num = 0; ; num++)
            if (sharedPreferences.getString("week" + num, null) == null)
                break;
        Log.i(TAG, "ReadCourse: num:" + num);
        for (int i = 0; i < num; i++) {
            course.add(
                    new Course(
                            sharedPreferences.getString("week" + i, null),
                            sharedPreferences.getInt("day" + i, -1),
                            sharedPreferences.getInt("starthour" + i, -1),
                            sharedPreferences.getInt("startmin" + i, -1),
                            sharedPreferences.getInt("endhour" + i, -1),
                            sharedPreferences.getInt("endmin" + i, -1),
                            sharedPreferences.getString("name" + i, null),
                            sharedPreferences.getString("room" + i, null),
                            sharedPreferences.getString("teacher" + i, null),
                            sharedPreferences.getInt("color" + i, -1),
                            sharedPreferences.getInt("priority" + i, -1)
                    )
            );
        }
        Log.i(TAG, "ReadCourse: READ COURSE END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void AddCourse() {
        Log.i(TAG, "AddCourse: ADD COURSE START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < TextViewArray.size(); i++)
            for (int j = 0; j < TextViewArray.get(i).size(); j++) {
                TextViewArray.get(i).get(j).setVisibility(View.INVISIBLE);
                TextViewArray.get(i).get(j).postInvalidate();
            }
        InitializeTextViewCourseArray();
        for (int i = 0; i < course.size(); i++) {
            boolean in = false;
            ArrayList<Integer> parseweek = WeekParse(course.get(i).week);
            if (parseweek.size() % 2 == 1)
                course.get(i).removeinfile(sharedPreferences);
            for (int j = 0; j < parseweek.size(); j += 2) {
                if (parseweek.get(j) <= (NowWeek - FirstWeek) && (NowWeek - FirstWeek) <= parseweek.get(j + 1))
                    in = true;
                LogICourse("AddCourse", course.get(i));
                if (in) break;
            }
            if (!in) continue;
            //25dp:30min
            double min, length;
            TextViewArray.get(course.get(i).day).add(new TextView(getActivity()));
            CourseArray.get(NowWeek - FirstWeek).get(course.get(i).day).add(course.get(i));
            TextView textView = TextViewArray.get(course.get(i).day).get(TextViewArray.get(course.get(i).day).size() - 1);
            min = (course.get(i).endhour - course.get(i).starthour) * 60 + (course.get(i).endmin - course.get(i).startmin);
            length = min * 5.0 / 6;
            min = (course.get(i).starthour) + course.get(i).startmin / 60.0;
            min -= 7;
            min *= 50;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, ToPix(getActivity().getApplicationContext(), min), 0, 0);
            params.height = ToPix(getActivity().getApplicationContext(), length);
            textView.setLayoutParams(params);
            textView.setOnTouchListener(TextCourseOnTouchListener());
            textView.setText("@" + course.get(i).room + "\n" + course.get(i).name);
            textView.setTextSize(12);
            textView.setTextColor(0xffffffff);
            textView.setEms(4);
            textView.setPadding(10, 0, 0, 0);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setBackgroundResource(R.drawable.textview_style);
            GradientDrawable myGrad = (GradientDrawable) textView.getBackground();
            myGrad.setColor(course.get(i).color);
            Layout.get(course.get(i).day).addView(textView);
        }
        for (int i = 0; i < TextViewArray.size(); i++)
            for (int j = 0; j < TextViewArray.get(i).size(); j++)
                TextViewArray.get(i).get(j).postInvalidate();
        Log.i(TAG, "AddCourse: ADD CCOURSE END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @NonNull
    AdapterView.OnItemSelectedListener SpinnerOnItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                NowWeek = position + FirstWeek;
                ReadCourse();
                for (int i = 0; i < course.size(); i++)
                    if (course.get(i).equals(NullCourse)) {
                        course.get(i).removeinfile(sharedPreferences);
                        course.remove(i);
                        if (Activated) {
                            Activated = false;
                            STRImageView2.setVisibility(View.INVISIBLE);
                            ((RelativeLayout) STRImageView2.getParent()).removeView(view);
                        }
                    }
                AddCourse();
                SetDate(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    View.OnTouchListener RelativeLayoutOnTouchListener() {
        return new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (!Activated)
                            AddNullCourse(view, motionEvent);
                        else
                            CancelNullCourse(view);
                        break;
                    default:
                        break;
                }
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            private void AddNullCourse(View view, MotionEvent motionEvent) {
                Activated = true;
                // 对勾按钮
                AddSTRImageView2();
                using = new Course(-2);
                Weeks.clear();
                Weeks.add(NowWeek - FirstWeek);
                Weeks.add(NowWeek - FirstWeek);
                using.week = WeekProduct(Weeks);
                for (int i = 0; i < 7; i++)
                    if (view == Layout.get(i))
                        using.day = i;
                int dip = ToDip(getActivity().getApplicationContext(), motionEvent.getY());
                int min = dip * 6 / 5;
                if (min < 90) {
                    using.starthour = 7;
                    using.startmin = 0;
                    using.endhour = 10;
                    using.endmin = 0;
                } else if (min > 870) {
                    using.starthour = 20;
                    using.startmin = 0;
                    using.endhour = 23;
                    using.endmin = 0;
                } else {
                    min -= 90;
                    using.starthour = min / 60 + 7;
                    using.startmin = min % 60;
                    using.endhour = using.starthour + 3;
                    using.endmin = using.startmin;
                }
                NullCourse.clone(using);
                using.addinfile(sharedPreferences);
                ReadCourse();
                AddCourse();
                for (int i = 0; i < course.size(); i++)
                    if (course.get(i).equals(NullCourse)) {
                        TextView textView = TextViewArray.get(NullCourse.day).get(TextViewArray.get(NullCourse.day).size() - 1);
                        //滑动过程中第一次显示时间
                        final RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                        int topMargin = ToDip(getActivity().getApplicationContext(), Params.topMargin);
                        Log.i(TAG, "onTouch: topmargin:" + topMargin);
                        int mins = topMargin * 6 / 5 + 420;
                        int hei = ToDip(getActivity().getApplicationContext(), Params.height);
                        int len = hei * 6 / 5;
                        String text = mins / 60 + ":" + mins % 60 + "\n";
                        for (int j = 0; j < hei / 28 - 1; j++)
                            text += "\n";
                        text += len + "min";
                        for (int j = 0; j < hei / 14 - hei / 28 - 1; j++)
                            text += "\n";
                        text += (mins + len) / 60 + ":" + (mins + len) % 60;
                        textView.setText(text);
                        textView.setOnTouchListener(NullCourseOnTouchListener());
                        textView.postInvalidate();
                    }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            private void CancelNullCourse(View view) {
                Activated = false;
                STRImageView2.setVisibility(View.INVISIBLE);
                ((RelativeLayout) STRImageView2.getParent()).removeView(view);
                NullCourse.removeinfile(sharedPreferences);
                ReadCourse();
                AddCourse();
            }

            private void AddSTRImageView2() {
                ImageView imageView = new ImageView(getActivity());
                STRImageView2 = imageView;
                imageView.setImageResource(R.drawable.v);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 775;
                imageView.setLayoutParams(layoutParams);
                imageView.setOnClickListener(STRImageView2OnClickListener());
                ((RelativeLayout) getActivity().findViewById(R.id.STRimageview).getParent()).addView(imageView);
                imageView.postInvalidate();
            }

            @NonNull
            private View.OnClickListener STRImageView2OnClickListener() {
                return new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        TextView textView = TextViewArray.get(NullCourse.day).get(TextViewArray.get(NullCourse.day).size() - 1);
                        RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                        int topMargin = ToDip(getActivity().getApplicationContext(), Params.topMargin);
                        int mins = topMargin * 6 / 5 + 420;
                        int len = ToDip(getActivity().getApplicationContext(), Params.height) * 6 / 5;
                        NullCourse.starthour = mins / 60;
                        NullCourse.startmin = mins % 60;
                        NullCourse.endhour = (mins + len) / 60;
                        NullCourse.endmin = (mins + len) % 60;
                        using.removeinfile(sharedPreferences);
                        NullCourse.addinfile(sharedPreferences);
                        ReadCourse();
                        AddCourse();
                        view.setVisibility(View.INVISIBLE);
                        ((RelativeLayout) view.getParent()).removeView(view);
                        Activated = false;
                    }
                };
            }

            @NonNull
            private View.OnTouchListener NullCourseOnTouchListener() {
                return new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                lastY = motionEvent.getRawY();
                                if (motionEvent.getY() <= view.getHeight() / 4) {
                                    IsLonger = true;
                                    Top = true;
                                    Bottom = false;
                                } else if (motionEvent.getY() >= 3 * view.getHeight() / 4) {
                                    IsLonger = true;
                                    Top = false;
                                    Bottom = true;
                                } else {
                                    IsLonger = false;
                                    Top = false;
                                    Bottom = false;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                view.getParent().requestDisallowInterceptTouchEvent(false);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                view.getParent().requestDisallowInterceptTouchEvent(true);
                                if (!IsLonger) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    if (view.getTop() >= 0)
                                        params.setMargins(0, (int) (view.getTop() + motionEvent.getRawY() - lastY), 0, 0);
                                    else
                                        params.setMargins(0, 0, 0, 0);
                                    if (ToDip(getActivity().getApplicationContext(), view.getHeight()) + ToDip(getActivity().getApplicationContext(), view.getTop()) <= 800)
                                        params.height = view.getHeight();
                                    else
                                        params.height = ToPix(getActivity().getApplicationContext(), 800 - ToDip(getActivity().getApplicationContext(), view.getTop()));
                                    view.setLayoutParams(params);
                                    lastY = motionEvent.getRawY();
                                    view.postInvalidate();
                                } else if (Bottom) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(view.getLeft(), view.getTop(), 0, 0);
                                    if (ToDip(getActivity().getApplicationContext(), view.getHeight()) + ToDip(getActivity().getApplicationContext(), view.getTop()) <= 800)
                                        params.height = view.getHeight() + (int) (motionEvent.getRawY() - lastY);
                                    else
                                        params.height = ToPix(getActivity().getApplicationContext(), 800 - ToDip(getActivity().getApplicationContext(), view.getTop()));
                                    view.setLayoutParams(params);
                                    lastY = motionEvent.getRawY();
                                    view.postInvalidate();
                                } else if (Top) {
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    if (view.getTop() >= 0)
                                        params.setMargins(view.getLeft(), view.getTop() + (int) (motionEvent.getRawY() - lastY), 0, 0);
                                    else
                                        params.setMargins(view.getLeft(), 0, 0, 0);
                                    params.height = view.getHeight() - (int) (motionEvent.getRawY() - lastY);
                                    view.setLayoutParams(params);
                                    lastY = motionEvent.getRawY();
                                    view.postInvalidate();
                                }
                                break;
                        }
                        TextView textView = TextViewArray.get(NullCourse.day).get(TextViewArray.get(NullCourse.day).size() - 1);
                        //滑动过程中显示时间
                        RelativeLayout.LayoutParams Params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                        int topMargin = ToDip(getActivity().getApplicationContext(), Params.topMargin);
                        int mins = topMargin * 6 / 5 + 420;
                        int hei = ToDip(getActivity().getApplicationContext(), Params.height);
                        int len = hei * 6 / 5;
                        String text = mins / 60 + ":" + mins % 60 + "\n";
                        if (hei / 14 >= 3) {
                            for (int j = 0; j < hei / 28 - 1; j++)
                                text += "\n";
                            text += len + "min";
                            for (int j = 0; j < hei / 14 - hei / 28 - 1; j++)
                                text += "\n";
                            text += (mins + len) / 60 + ":" + (mins + len) % 60;
                        } else {
                            text += len + "min";
                        }
                        textView.setText(text);
                        textView.postInvalidate();
                        return true;
                    }
                };
            }
        };
    }

    View.OnClickListener STRImageViewOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 分享 */
                // send Information to server
                // get return from server
                // create image and save
                // shareDialog();
                Log.i("点击按钮", "分享");
                View v = View.inflate(getActivity(), R.layout.schedule_fragment, null);
                View schedule = v.findViewById(R.id.Sframe);

                if (schedule == null) try {
                    throw new Exception("获取日程view失败");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ShareDialog s = new ShareDialog();
                //user name::

                //::
                s.setTime("7:00", "23:59");
                int count = 0;
                for (int i = 0; i < course.size(); i++) {
                    ArrayList<Integer> parseweek = WeekParse(course.get(i).week);
                    for (int j = 0; j < parseweek.size(); j += 2) {
                        if (parseweek.get(j) <= (NowWeek - FirstWeek) && (NowWeek - FirstWeek) <= parseweek.get(j + 1)) {
                            Course c = course.get(i);
                            s.addPramas("" + (++count), c.name + "/" + (c.day + 1) + "/" + c.starthour + ":" + (c.startmin < 10 ? "0" + c.startmin : c.startmin) + "/" + c.endhour + ":" + (c.endmin < 10 ? "0" + c.endmin : c.endmin));
                        }
                    }
                }
                s.setCount(count);
                //(new SaveViewToImage()).save(schedule);
                s.show(getActivity());
            }
        };
    }

    View.OnTouchListener STRImageViewOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setBackgroundResource(R.drawable.plus_reverse);
                        view.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        view.setBackgroundResource(R.drawable.plus);
                        view.postInvalidate();
                        break;
                    default:
                        break;
                }
                return true;
            }
        };
    }

    View.OnTouchListener TextCourseOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                RunnableView = view;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(!ViewFilter(view)) break;
                        LongClickEvent();
                        BackGroundColorChange(view);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (StopLongClickEvent()) break;
                        if(!ViewFilter(view)) break;
                        BackGroundColorRecover(view);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (StopLongClickEvent()) break;
                        if(!ViewFilter(view)) break;
                        BackGroundColorRecover(view);
                        CreateDialog();
                        break;
                    default:
                        break;
                }
                return true;
            }

            boolean ViewFilter(View view){
                boolean is = false;
                for (int i = 0; i < 7; i++)
                    if (view.getParent() == Layout.get(i))
                        is = true;
                return is;
            }

            void BackGroundColorChange(View view) {

                int backgroundColor;
                int Break;
                int i;
                int j;
                backgroundColor = 1;
                Break = 0;
                for (i = 0; i < TextViewArray.size(); i++) {
                    for (j = 0; j < TextViewArray.get(i).size(); j++)
                        if (TextViewArray.get(i).get(j).getText() == ((TextView) view).getText()
                                && TextViewArray.get(i).get(j).getParent() == view.getParent()
                                && TextViewArray.get(i).get(j).getY() == view.getY()) {
                            backgroundColor = CourseArray.get(NowWeek - FirstWeek).get(i).get(j).color;
                            Break = 1;
                            break;
                        }
                    if (Break == 1)
                        break;
                }
                backgroundColor = backgroundColor % 0x01000000 + 0x3f000000;
                ((GradientDrawable) view.getBackground()).setColor(backgroundColor);
                view.postInvalidate();
            }

            void BackGroundColorRecover(View view) {
                int Break;
                Break = 0;
                for (int i = 0; i < TextViewArray.size(); i++) {
                    for (int j = 0; j < TextViewArray.get(i).size(); j++) {
                        Log.i(TAG, "onTouch: TextViewArray.get(" + i + ").get(" + j + "):" + TextViewArray.get(i).get(j).getText() + " Y" + TextViewArray.get(i).get(j).getY());
                        if (TextViewArray.get(i).get(j).getText() == ((TextView) view).getText()
                                && TextViewArray.get(i).get(j).getParent() == view.getParent()
                                && TextViewArray.get(i).get(j).getY() == view.getY()) {
                            using = new Course(CourseArray.get(NowWeek - FirstWeek).get(i).get(j));
                            Log.i(TAG, "onTouch: usingname:" + using.name);
                            BeReplacedX = NowWeek - FirstWeek;
                            BeReplacedY = i;
                            BeReplacedZ = j;
                            Break = 1;
                            break;
                        }
                    }
                    if (Break == 1)
                        break;
                }
                ((GradientDrawable) view.getBackground()).setColor(using.color);
                view.postInvalidate();
            }

            void CreateDialog() {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialogview = inflater.inflate(R.layout.schedule_course_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                InitializeDialogText(dialogview, builder);
                builder.setPositiveButton("保存", PositiveButtonOnClickListener());
                builder.setNegativeButton("取消", NegativeButtonOnClickListener());
                builder.setView(dialogview);
                AlertDialog dialog = builder.create();
                dialog.show();
                assert dialog.getWindow() != null;
                TextWeek = (TextView) dialog.getWindow().findViewById(R.id.dialogweek);
                TextTime = (TextView) dialog.getWindow().findViewById(R.id.dialogtime);
                TextWeek.setOnClickListener(TextWeekOnClickListener());
                TextTime.setOnClickListener(TextTimeOnClickListener());
            }

            private void InitializeDialogText(View dialogview, AlertDialog.Builder builder) {
                EditName = (EditText) dialogview.findViewById(R.id.SCourseEditTextName);
                EditRoom = (EditText) dialogview.findViewById(R.id.SCourseEditTextRoom);
                TextWeek = (TextView) dialogview.findViewById(R.id.dialogweek);
                TextTime = (TextView) dialogview.findViewById(R.id.dialogtime);
                EditName.setSingleLine(false);
                EditRoom.setSingleLine(false);
                EditName.setHorizontallyScrolling(false);
                EditRoom.setHorizontallyScrolling(false);
                EditName.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                EditRoom.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                builder.setTitle(using.name);
                EditName.setText(using.name);
                EditRoom.setText(using.room);
                SetTextWeek();
                UpdateTimeGlobalVariable();
                SetTextTime();
            }

            void SetTextWeek() {
                int i;
                ArrayList<Integer> weekparse = WeekParse(using.week);
                TextWeek.setText("");
                Weeks = weekparse;
                for (i = 0; i < weekparse.size(); i += 2)
                    if (weekparse.get(i).equals(weekparse.get(i + 1)))
                        TextWeek.setText(TextWeek.getText() + "" + (weekparse.get(i) + 1) + "周 ");
                    else
                        TextWeek.setText(TextWeek.getText() + "" + (weekparse.get(i) + 1) + "-" + (weekparse.get(i + 1) + 1) + "周 ");
            }

            void SetTextTime() {
                String[] DayName = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
                String time = "" + DayName[DialogDay] + " ";
                if (DialogStartHour < 10)
                    time += "0";
                time += DialogStartHour + "点";
                if (DialogStartMinute < 10)
                    time += "0";
                time += DialogStartMinute + "分到";
                if (DialogEndHour < 10)
                    time += "0";
                time += DialogEndHour + "点";
                if (DialogEndMinute < 10)
                    time += "0";
                time += DialogEndMinute;
                TextTime.setText(time);
            }

            @NonNull
            DialogInterface.OnClickListener NegativeButtonOnClickListener() {
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                };
            }

            @NonNull
            DialogInterface.OnClickListener PositiveButtonOnClickListener() {
                return new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        using.name = EditName.getText().toString().trim();
                        using.room = EditRoom.getText().toString().trim();
                        using.week = WeekProduct(Weeks);
                        using.day = DialogDay;
                        using.starthour = DialogStartHour;
                        using.startmin = DialogStartMinute;
                        using.endhour = DialogEndHour;
                        using.endmin = DialogEndMinute;
                        Log.i(TAG, "onClick: now you release the " + using.name + "     room:" + using.room + "          day:" + using.day + "         from" + using.starthour + ":" + using.startmin + " to " + using.endhour + ":" + using.endmin);
                        Log.i(TAG, "onClick: now you release the " + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).name + "     room:" + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).room + "          day:" + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).day + "         from" + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).starthour + ":" + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).startmin + " to " + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).endhour + ":" + CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).endmin);
                        CourseArray.get(BeReplacedX).get(BeReplacedY).get(BeReplacedZ).removeinfile(sharedPreferences);
                        using.addinfile(sharedPreferences);
                        ReadCourse();
                        AddCourse();
                        dialogInterface.dismiss();
                    }
                };
            }

            void UpdateTimeGlobalVariable() {
                DialogDay = using.day;
                DialogStartHour = using.starthour;
                DialogStartMinute = using.startmin;
                DialogEndHour = using.endhour;
                DialogEndMinute = using.endmin;
            }

            boolean StopLongClickEvent() {
                if (Running) {
                    Running = false;
                    return true;
                }
                Stop = true;
                return false;
            }

            void LongClickEvent() {
                Stop = false;
                android.os.Handler handler = new android.os.Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {//删除课程dialog
                        if (Stop) return;
                        Running = true;
                        boolean is = false;
                        int Break = 0;
                        for (int i = 0; i < 7; i++)
                            if (RunnableView.getParent() == Layout.get(i))
                                is = true;
                        if (!is)
                            return;

                        //背景色变换
                        for (int i = 0; i < TextViewArray.size(); i++) {
                            for (int j = 0; j < TextViewArray.get(i).size(); j++) {
                                Log.i(TAG, "onTouch: TextViewArray.get(" + i + ").get(" + j + "):" + TextViewArray.get(i).get(j).getText() + " Y" + TextViewArray.get(i).get(j).getY());
                                if (TextViewArray.get(i).get(j).getText() == ((TextView) RunnableView).getText()
                                        && TextViewArray.get(i).get(j).getParent() == RunnableView.getParent()
                                        && TextViewArray.get(i).get(j).getY() == RunnableView.getY()) {
                                    using = new Course(CourseArray.get(NowWeek - FirstWeek).get(i).get(j));
                                    Log.i(TAG, "onTouch: usingname:" + using.name);
                                    BeReplacedX = NowWeek - FirstWeek;
                                    BeReplacedY = i;
                                    BeReplacedZ = j;
                                    Break = 1;
                                    break;
                                }
                            }
                            if (Break == 1)
                                break;
                        }
                        ((GradientDrawable) RunnableView.getBackground()).setColor(using.color);
                        RunnableView.postInvalidate();
                        //删除课程dialog
                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                        View dialogview = inflater.inflate(R.layout.schedule_delete_dialog, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("删除课程");
                        //设置确定取消按钮
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Running = false;
                                using.removeinfile(sharedPreferences);
                                ReadCourse();
                                AddCourse();
                            }
                        });
                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Running = false;
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setView(dialogview);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        };
    }

    View.OnClickListener TextTimeOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater timeinflater = LayoutInflater.from(getActivity());
                View timeview = timeinflater.inflate(R.layout.schedule_time_dialog, null);
                AlertDialog.Builder timebuilder = new AlertDialog.Builder(getActivity());
                timebuilder.setTitle("时间选择");
                timebuilder.setPositiveButton("确定", PositiveButtonOnClickListener());
                timebuilder.setNegativeButton("取消", NegativeButtonOnClickListener());
                timebuilder.setView(timeview);
                AlertDialog timedialog = timebuilder.create();
                timedialog.show();
                Window timewindow = timedialog.getWindow();
                assert timewindow != null;
                InitializePlusButton(timewindow);
                InitializePicker(timewindow);
            }

            void InitializePlusButton(Window timewindow) {
                Button starthhplus = (Button) timewindow.findViewById(R.id.starthhplus);
                Button startmmplus = (Button) timewindow.findViewById(R.id.startmmplus);
                Button endhhplus = (Button) timewindow.findViewById(R.id.endhhplus);
                Button endmmplus = (Button) timewindow.findViewById(R.id.endmmplus);
                starthhplus.setOnClickListener(PlusButtonOnClickListener());
                startmmplus.setOnClickListener(PlusButtonOnClickListener());
                endhhplus.setOnClickListener(PlusButtonOnClickListener());
                endmmplus.setOnClickListener(PlusButtonOnClickListener());
            }

            void InitializePicker(Window timewindow) {
                DayPicker = (NumberPicker) timewindow.findViewById(R.id.daypicker);
                StartHourPicker = (NumberPicker) timewindow.findViewById(R.id.starthhpicker);
                StartMinutePicker = (NumberPicker) timewindow.findViewById(R.id.startmmpicker);
                EndHourPicker = (NumberPicker) timewindow.findViewById(R.id.endhhpicker);
                EndMinutePicker = (NumberPicker) timewindow.findViewById(R.id.endmmpicker);
                String[] hh = new String[17];
                for (int i = 7; i < 24; i++)
                    if (i < 10)
                        hh[i - 7] = "0" + ((Integer) i).toString() + "点";
                    else
                        hh[i - 7] = ((Integer) i).toString() + "点";
                String[] mm = new String[60];
                for (int i = 0; i < 60; i++)
                    if (i < 10)
                        mm[i] = "0" + ((Integer) i).toString() + "分";
                    else
                        mm[i] = ((Integer) i).toString() + "分";
                DayPicker.setOnValueChangedListener(PickerOnValueChangeListener());
                StartHourPicker.setOnValueChangedListener(PickerOnValueChangeListener());
                StartMinutePicker.setOnValueChangedListener(PickerOnValueChangeListener());
                EndHourPicker.setOnValueChangedListener(PickerOnValueChangeListener());
                EndMinutePicker.setOnValueChangedListener(PickerOnValueChangeListener());
                DayPicker.setDisplayedValues(new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"});
                DayPicker.setMinValue(0);
                DayPicker.setMaxValue(6);
                StartHourPicker.setDisplayedValues(hh);
                StartHourPicker.setMinValue(0);
                StartHourPicker.setMaxValue(16);
                StartMinutePicker.setDisplayedValues(mm);
                StartMinutePicker.setMinValue(0);
                StartMinutePicker.setMaxValue(59);
                EndHourPicker.setDisplayedValues(hh);
                EndHourPicker.setMinValue(0);
                EndHourPicker.setMaxValue(16);
                EndMinutePicker.setDisplayedValues(mm);
                EndMinutePicker.setMinValue(0);
                EndMinutePicker.setMaxValue(59);
                DayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                StartHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                StartMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                EndHourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                EndMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                ArrayList<Integer> time = TimeParse(TextTime.getText().toString());
                if (time.size() == 5) {
                    DayPicker.setValue(time.get(0));
                    StartHourPicker.setValue(time.get(1) - 7);
                    StartMinutePicker.setValue(time.get(2));
                    EndHourPicker.setValue(time.get(3) - 7);
                    EndMinutePicker.setValue(time.get(4));
                } else {
                    DayPicker.setValue(0);
                    StartHourPicker.setValue(1);
                    StartMinutePicker.setValue(30);
                    EndHourPicker.setValue(3);
                    EndMinutePicker.setValue(20);
                }
            }

            View.OnClickListener PlusButtonOnClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.starthhplus:
                                StartHourPicker.setValue((StartHourPicker.getValue() + 6) % (StartHourPicker.getMaxValue() + 1));
                                break;
                            case R.id.startmmplus:
                                StartMinutePicker.setValue((StartMinutePicker.getValue() + 15) % (StartMinutePicker.getMaxValue() + 1));
                                break;
                            case R.id.endhhplus:
                                EndHourPicker.setValue((EndHourPicker.getValue() + 6) % (EndHourPicker.getMaxValue() + 1));
                                break;
                            case R.id.endmmplus:
                                EndMinutePicker.setValue((EndMinutePicker.getValue() + 15) % (EndMinutePicker.getMaxValue() + 1));
                                break;
                            default:
                                break;
                        }
                    }
                };
            }

            @NonNull
            DialogInterface.OnClickListener NegativeButtonOnClickListener() {
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                };
            }

            @NonNull
            DialogInterface.OnClickListener PositiveButtonOnClickListener() {
                return new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {//saving time
                        DialogStartHour = StartHourPicker.getValue() + 7;
                        DialogStartMinute = StartMinutePicker.getValue();
                        DialogEndHour = EndHourPicker.getValue() + 7;
                        DialogEndMinute = EndMinutePicker.getValue();
                        TextTime.setText(
                                DayPicker.getDisplayedValues()[(DialogDay = DayPicker.getValue())] + " " +
                                        StartHourPicker.getDisplayedValues()[StartHourPicker.getValue()] +
                                        StartMinutePicker.getDisplayedValues()[StartMinutePicker.getValue()] + "到" +
                                        EndHourPicker.getDisplayedValues()[EndHourPicker.getValue()] +
                                        EndMinutePicker.getDisplayedValues()[EndMinutePicker.getValue()]);
                        dialogInterface.dismiss();
                    }
                };
            }

            NumberPicker.OnValueChangeListener PickerOnValueChangeListener() {
                return new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        switch (numberPicker.getId()) {
                            case R.id.daypicker:
                                //do nothing
                                break;
                            case R.id.starthhpicker:
                                EndHourPicker.setValue((StartHourPicker.getValue() + 2) % 24);
                                break;
                            case R.id.startmmpicker:
                                EndMinutePicker.setValue(StartMinutePicker.getValue());
                                break;
                            case R.id.endhhpicker:
                                if (EndHourPicker.getValue() < StartHourPicker.getValue())
                                    EndHourPicker.setValue(StartHourPicker.getValue());
                                if (EndMinutePicker.getValue() < StartMinutePicker.getValue())
                                    EndMinutePicker.setValue(StartMinutePicker.getValue());
                                break;
                            case R.id.endmmpicker:
                                if (EndMinutePicker.getValue() < StartMinutePicker.getValue() && EndHourPicker.getValue() <= StartHourPicker.getValue())
                                    EndMinutePicker.setValue(StartMinutePicker.getValue());
                                break;
                        }
                    }
                };
            }
        };
    }

    View.OnClickListener TextWeekOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflaterweek = LayoutInflater.from(getActivity());
                View weekview = inflaterweek.inflate(R.layout.schedule_week_dialog, null);
                final AlertDialog.Builder weekbuilder = new AlertDialog.Builder(getActivity());
                weekbuilder.setTitle("周数选择");
                //设置确定取消按钮
                weekbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {//save
                        boolean successive = false;
                        int start = -1;
                        Weeks.clear();
                        for (int i = 0; i < 20; i++)
                            if (ToggleArray.get(i).isChecked()) {
                                successive = true;
                                if (start == -1) {
                                    start = i;
                                    Weeks.add(i);
                                }
                            } else if (successive) {
                                successive = false;
                                Weeks.add(i - 1);
                                start = -1;
                            }
                        if (successive)
                            Weeks.add(19);
                        TextWeek.setText("");
                        for (int i = 0; i < Weeks.size(); i += 2)
                            if (Weeks.get(i).equals(Weeks.get(i + 1)))
                                TextWeek.setText(TextWeek.getText() + "" + (Weeks.get(i) + 1) + "周 ");
                            else
                                TextWeek.setText(TextWeek.getText() + "" + (Weeks.get(i) + 1) + "-" + (Weeks.get(i + 1) + 1) + "周 ");
                        dialogInterface.dismiss();
                    }
                });
                weekbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {//cancel
                        for (int i = 0; i < ToggleArray.size(); i++)
                            ToggleArray.get(i).setChecked(false);
                        dialogInterface.dismiss();
                    }
                });

                weekbuilder.setView(weekview);
                AlertDialog weekdialog = weekbuilder.create();
                weekdialog.show();
                Window weekwindow = weekdialog.getWindow();
                ToggleArray.clear();
                assert weekwindow != null;
                //ToggleArray.add((ToggleButton) weekwindow.findViewById);x28
                int[] ids = {R.id.week_togglebutton1, R.id.week_togglebutton2, R.id.week_togglebutton3,
                        R.id.week_togglebutton4, R.id.week_togglebutton5, R.id.week_togglebutton6,
                        R.id.week_togglebutton7, R.id.week_togglebutton8, R.id.week_togglebutton9,
                        R.id.week_togglebutton10, R.id.week_togglebutton11, R.id.week_togglebutton12,
                        R.id.week_togglebutton13, R.id.week_togglebutton14, R.id.week_togglebutton15,
                        R.id.week_togglebutton16, R.id.week_togglebutton17, R.id.week_togglebutton18,
                        R.id.week_togglebutton19, R.id.week_togglebutton20, R.id.week_togglebutton1_5,
                        R.id.week_togglebutton6_10, R.id.week_togglebutton11_15, R.id.week_togglebutton16_20,
                        R.id.week_togglebuttonsingle, R.id.week_togglebuttondouble, R.id.week_togglebuttonall,
                        R.id.week_togglebuttonthis};
                for (int i = 0; i < 28; i++)
                    ToggleArray.add((ToggleButton) weekwindow.findViewById(ids[i]));
                //ToggleArray.get(?).set(OnCheckChangeListener/Checked)
                for (int i = 0; i < ToggleArray.size(); i++)
                    ToggleArray.get(i).setOnCheckedChangeListener(ToggleButtonOnCheckedChangedListener());
                for (int i = 0; i < 20; i++)
                    ToggleArray.get(i).setChecked(false);
                for (int i = 0; i < Weeks.size(); i += 2)
                    for (int j = Weeks.get(i); j <= Weeks.get(i + 1); j++)
                        ToggleArray.get(j).setChecked(true);
            }

            ToggleButton.OnCheckedChangeListener ToggleButtonOnCheckedChangedListener() {
                return new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        boolean togglebuttonbool[] = new boolean[27];
                        switch (compoundButton.getId()) {
                            case R.id.week_togglebutton1:
                            case R.id.week_togglebutton2:
                            case R.id.week_togglebutton3:
                            case R.id.week_togglebutton4:
                            case R.id.week_togglebutton5:
                            case R.id.week_togglebutton6:
                            case R.id.week_togglebutton7:
                            case R.id.week_togglebutton8:
                            case R.id.week_togglebutton9:
                            case R.id.week_togglebutton10:
                            case R.id.week_togglebutton11:
                            case R.id.week_togglebutton12:
                            case R.id.week_togglebutton13:
                            case R.id.week_togglebutton14:
                            case R.id.week_togglebutton15:
                            case R.id.week_togglebutton16:
                            case R.id.week_togglebutton17:
                            case R.id.week_togglebutton18:
                            case R.id.week_togglebutton19:
                            case R.id.week_togglebutton20:
                                compoundButton.setChecked(b);
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebutton1_5:
                                compoundButton.setChecked(b);
                                if (b) {
                                    for (int i = 0; i < 5; i++)
                                        ToggleArray.get(i).setChecked(true);
                                } else {
                                    for (int i = 0; i < 5; i++)
                                        ToggleArray.get(i).setChecked(false);
                                }
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebutton6_10:
                                compoundButton.setChecked(b);
                                if (b) {
                                    for (int i = 5; i < 10; i++)
                                        ToggleArray.get(i).setChecked(true);
                                } else {
                                    for (int i = 5; i < 10; i++)
                                        ToggleArray.get(i).setChecked(false);
                                }
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebutton11_15:
                                compoundButton.setChecked(b);
                                if (b) {
                                    for (int i = 10; i < 15; i++)
                                        ToggleArray.get(i).setChecked(true);
                                } else {
                                    for (int i = 10; i < 15; i++)
                                        ToggleArray.get(i).setChecked(false);
                                }
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebutton16_20:
                                compoundButton.setChecked(b);
                                if (b) {
                                    for (int i = 15; i < 20; i++)
                                        ToggleArray.get(i).setChecked(true);
                                } else {
                                    for (int i = 15; i < 20; i++)
                                        ToggleArray.get(i).setChecked(false);
                                }
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebuttonsingle:
                                compoundButton.setChecked(b);
                                if (b)
                                    for (int i = 0; i < 20; i += 2)
                                        ToggleArray.get(i).setChecked(true);
                                else
                                    for (int i = 0; i < 20; i += 2)
                                        ToggleArray.get(i).setChecked(false);
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebuttondouble:
                                compoundButton.setChecked(b);
                                if (b)
                                    for (int i = 1; i < 20; i += 2)
                                        ToggleArray.get(i).setChecked(true);
                                else
                                    for (int i = 1; i < 20; i += 2)
                                        ToggleArray.get(i).setChecked(false);
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebuttonall:
                                compoundButton.setChecked(b);
                                if (b)
                                    for (int i = 0; i < 20; i++)
                                        ToggleArray.get(i).setChecked(true);
                                else
                                    for (int i = 0; i < 20; i++)
                                        ToggleArray.get(i).setChecked(false);
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                            case R.id.week_togglebuttonthis:
                                compoundButton.setChecked(b);
                                if (b)
                                    ToggleArray.get(RealWeek - FirstWeek).setChecked(true);
                                else
                                    ToggleArray.get(RealWeek - FirstWeek).setChecked(false);
                                save(togglebuttonbool);
                                check();
                                recover(togglebuttonbool);
                                break;
                        }
                        for (int i = 0; i < 20; i++)
                            ToggleArray.get(i).postInvalidate();
                    }
                };
            }

            void check() {
                boolean line1, line2, line3, line4, dialine1, dialine2;
                line1 = true;
                line2 = true;
                line3 = true;
                line4 = true;
                dialine1 = true;
                dialine2 = true;
                for (int i = 0; i < 5; i++)
                    if (!ToggleArray.get(i).isChecked())
                        line1 = false;
                for (int i = 5; i < 10; i++)
                    if (!ToggleArray.get(i).isChecked())
                        line2 = false;
                for (int i = 10; i < 15; i++)
                    if (!ToggleArray.get(i).isChecked())
                        line3 = false;
                for (int i = 15; i < 20; i++)
                    if (!ToggleArray.get(i).isChecked())
                        line4 = false;
                for (int i = 0; i < 20; i += 2)
                    if (!ToggleArray.get(i).isChecked())
                        dialine1 = false;
                for (int i = 1; i < 20; i += 2)
                    if (!ToggleArray.get(i).isChecked())
                        dialine2 = false;
                if (ToggleArray.get(RealWeek - FirstWeek).isChecked() && !ToggleArray.get(27).isChecked())
                    ToggleArray.get(27).setChecked(true);
                if (!ToggleArray.get(RealWeek - FirstWeek).isChecked() && ToggleArray.get(27).isChecked())
                    ToggleArray.get(27).setChecked(false);
                if (line1 && !ToggleArray.get(20).isChecked())
                    ToggleArray.get(20).setChecked(true);
                if (!line1 && ToggleArray.get(20).isChecked())
                    ToggleArray.get(20).setChecked(false);
                if (line2 && !ToggleArray.get(21).isChecked())
                    ToggleArray.get(21).setChecked(true);
                if (!line2 && ToggleArray.get(21).isChecked())
                    ToggleArray.get(21).setChecked(false);
                if (line3 && !ToggleArray.get(22).isChecked())
                    ToggleArray.get(22).setChecked(true);
                if (!line3 && ToggleArray.get(22).isChecked())
                    ToggleArray.get(22).setChecked(false);
                if (line4 && !ToggleArray.get(23).isChecked())
                    ToggleArray.get(23).setChecked(true);
                if (!line4 && ToggleArray.get(23).isChecked())
                    ToggleArray.get(23).setChecked(false);
                if (dialine1 && !ToggleArray.get(24).isChecked())
                    ToggleArray.get(24).setChecked(true);
                if (!dialine1 && ToggleArray.get(24).isChecked())
                    ToggleArray.get(24).setChecked(false);
                if (dialine2 && !ToggleArray.get(25).isChecked())
                    ToggleArray.get(25).setChecked(true);
                if (!dialine2 && ToggleArray.get(25).isChecked())
                    ToggleArray.get(25).setChecked(false);
                if (dialine1 && dialine2 && !ToggleArray.get(26).isChecked())
                    ToggleArray.get(26).setChecked(true);
                else if (!(dialine1 && dialine2) && ToggleArray.get(26).isChecked())
                    ToggleArray.get(26).setChecked(false);
            }

            void save(boolean[] togglebuttonbool) {
                for (int i = 0; i < 20; i++)
                    togglebuttonbool[i] = ToggleArray.get(i).isChecked();
            }

            void recover(boolean[] togglebuttonbool) {
                for (int i = 0; i < 20; i++)
                    ToggleArray.get(i).setChecked(togglebuttonbool[i]);
            }
        };
    }
}
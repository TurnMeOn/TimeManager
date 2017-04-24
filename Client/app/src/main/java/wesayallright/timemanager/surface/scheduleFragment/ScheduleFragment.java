package wesayallright.timemanager.surface.scheduleFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Pattern;

import wesayallright.timemanager.R;

public class ScheduleFragment extends Fragment implements
        View.OnClickListener , AdapterView.OnItemSelectedListener ,
        View.OnTouchListener , ToggleButton.OnCheckedChangeListener ,
        NumberPicker.OnValueChangeListener {

    private RelativeLayout layout1, layout2, layout3, layout4, layout5, layout6, layout7;
    private ArrayList<ArrayList<TextView>> textarr = new ArrayList<>();         //          textarr.get(day).get(xth in day)
    private ArrayList<ArrayList<ArrayList<Course>>> pcourse = new ArrayList<>();//pcourse.get(week).get(day).get(xth in day)
    private ArrayList<Course> course = new ArrayList<>();
    private ArrayList<RelativeLayout> layout = new ArrayList<>();
    private boolean[] week_togglebutton = new boolean[21];
    private static final String TAG = "ScheduleFragment";
    private View view;
    private int firstmonth = 2;
    private int firstday = 27;
    private Calendar first = Calendar.getInstance();
    private Calendar today = Calendar.getInstance();
    private int firstweek;
    private int nowweek;
    private int realweek;
    private Course using;
    private ArrayList<ToggleButton> togglearray = new ArrayList<>();
    private NumberPicker daypicker, starthhpicker, startmmpicker, endhhpicker, endmmpicker;
    private int dialog_day, dialog_starthh, dialog_startmm, dialog_endhh, dialog_endmm;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_fragment, container, false);
        initializing();
        return view;
    }

    public void initializing() {
        sharedPreferences = getActivity().getSharedPreferences("Schedule_Fragment_data0",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //pcourse
        for (int i = 0; i < 20; i++) {
            pcourse.add(new ArrayList<ArrayList<Course>>());
            for (int j = 0; j < 7; j++)
                pcourse.get(i).add(new ArrayList<Course>());
        }
        //initializing date
        realweek = today.get(Calendar.WEEK_OF_YEAR);
        first.set(Calendar.YEAR, 2017);
        first.set(Calendar.MONTH, firstmonth - 1);
        first.set(Calendar.DATE, firstday);
        firstweek = first.get(Calendar.WEEK_OF_YEAR);
        nowweek = today.get(Calendar.WEEK_OF_YEAR);
        //layout
        layout.add(layout1 = (RelativeLayout) view.findViewById(R.id.SBrelative1));
        layout.add(layout2 = (RelativeLayout) view.findViewById(R.id.SBrelative2));
        layout.add(layout3 = (RelativeLayout) view.findViewById(R.id.SBrelative3));
        layout.add(layout4 = (RelativeLayout) view.findViewById(R.id.SBrelative4));
        layout.add(layout5 = (RelativeLayout) view.findViewById(R.id.SBrelative5));
        layout.add(layout6 = (RelativeLayout) view.findViewById(R.id.SBrelative6));
        layout.add(layout7 = (RelativeLayout) view.findViewById(R.id.SBrelative7));
        for (int i = 0; i < 7; i++)
            layout.get(i).setOnClickListener(this);
        //course
//        course.add(new Course("0 6", 0, 8, 30, 10, 20, "大学生心理与健康教育(二)", "建筑A302", "未设置", 0xcf11ee11, 1));
//        course.add(new Course("0 6", 0, 10, 40, 12, 30, "高等数学(二)", "信息A112", "宋叔尼", 0xcfff0000, 1));
//        course.add(new Course("10 17", 0, 10, 40, 12, 30, "面向对象程序设计(C++)", "生命B502", "张天成", 0xcff7f709, 1));
//        course.add(new Course("5 16", 0, 14, 0, 15, 50, "大学英语(二)(听说)", "信息A309", "马新", 0xcf9222dd, 1));
//        course.add(new Course("0 8", 0, 16, 10, 18, 0, "大学物理(双语)(一)", "文管B230", "陈肖慧", 0xcf3CA9C4, 1));
//        course.add(new Course("0 18", 1, 8, 30, 10, 20, "高等数学(二)", "生命B201", "宋叔尼", 0xcfff0000, 1));
//        course.add(new Course("0 9", 1, 14, 0, 15, 50, "离散数学(二)", "文管A129", "张一飞", 0xcfDD9222, 1));
//        course.add(new Course("13 16", 1, 16, 10, 18, 0, "形势与政策(1)", "文管A227", "未设置", 0xcf09F7F7, 1));
//        course.add(new Course("5 16", 2, 8, 30, 10, 20, "大学英语(二)", "建筑B517", "马新", 0xcf9222dd, 1));
//        course.add(new Course("0 5 7 8 10 15", 2, 10, 40, 12, 30, "大学物理(双语)(一)", "文管B230", "陈肖慧", 0xcf3CA9C4, 1));
//        course.add(new Course("0 1 3 3 5 18", 3, 8, 30, 10, 20, "高等数学(二)", "建筑A302", "宋叔尼", 0xcfff0000, 1));
//        course.add(new Course("2 2", 3, 8, 30, 10, 20, "高等数学(二)", "信息A211", "宋叔尼", 0xcfff0000, 1));
//        course.add(new Course("0 9", 3, 10, 40, 12, 30, "离散数学(二)", "文管B230", "张一飞", 0xcfDD9222, 1));
//        course.add(new Course("10 17", 3, 10, 40, 12, 30, "面向对象程序设计(C++)", "建筑B403", "张天成", 0xcff7f709, 1));
//        course.add(new Course("3 14", 3, 14, 0, 15, 50, "体育(二)", "风雨操场", "厉中山", 0xcf0909F7, 1));
//        course.add(new Course("0 15", 3, 16, 10, 18, 0, "创业基础", "文管A227", "未设置", 0xcf42E61A, 1));
//        course.add(new Course("5 5", 4, 8, 30, 10, 20, "高等数学(二)", "信息A214", "宋叔尼", 0xcfff0000, 1));
//        course.add(new Course("13 13 15 15 17 17", 4, 8, 30, 10, 20, "高等数学(二)", "生命B201", "宋叔尼", 0xcfff0000, 1));
//        course.add(new Course("0 11", 4, 14, 0, 15, 50, "中国近现代史纲要", "生命B401", "段炼", 0xcf09F7F7, 1));
//        course.add(new Course("2 11", 6, 18, 30, 21, 20, "程序设计技术", "信息A210", "赵长宽", 0xcfDD2248, 1));
//        for(int i=0;i<course.size();i++)
//            course.get(i).addinfile(sharedPreferences,editor);
        //textview
        for (int i = 0; i < 7; i++)
            textarr.add(new ArrayList<TextView>());
        //spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.STMspinner);
        ArrayList<String> list = new ArrayList<>();
        list.add("第一周");
        list.add("第二周");
        list.add("第三周");
        list.add("第四周");
        list.add("第五周");
        list.add("第六周");
        list.add("第七周");
        list.add("第八周");
        list.add("第九周");
        list.add("第十周");
        list.add("第十一周");
        list.add("第十二周");
        list.add("第十三周");
        list.add("第十四周");
        list.add("第十五周");
        list.add("第十六周");
        list.add("第十七周");
        list.add("第十八周");
        list.add("第十九周");
        list.add("第二十周");
        MyAdapter adapter = new MyAdapter(this.getActivity(), list, nowweek - firstweek -1);//变红的!!!
        spinner.setAdapter(adapter);
        spinner.setSelection(nowweek - firstweek - 1);
        spinner.setOnItemSelectedListener(this);
        //ImageButton
        ImageView imageButton = (ImageView) view.findViewById(R.id.STRimageview);
        imageButton.setOnClickListener(this);
        imageButton.setOnTouchListener(this);
    }

    private ArrayList<Integer> weekparse(String week) {
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

    private String weekproduce(ArrayList<Integer> week) {
        String string = new String();
        for (int i = 0; i < week.size(); i++) {
            if (i != week.size() - 1)
                string += week.get(i) + " ";
            else
                string += week.get(i);
        }
        return string;
    }

    /*checked*/
    private void addcourse(ArrayList<Course> course, ArrayList<RelativeLayout> layout) {
        Log.i(TAG, "addcourse: ADD COURSE START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < textarr.size(); i++)
            for (int j = 0; j < textarr.get(i).size(); j++) {
                textarr.get(i).get(j).setVisibility(View.INVISIBLE);
                textarr.get(i).get(j).postInvalidate();
            }
        textarr.clear();
        for (int i = 0; i < 7; i++)
            textarr.add(new ArrayList<TextView>());
        for (int i = 0; i < course.size(); i++) {
            boolean in = false;
            ArrayList<Integer> parseweek = weekparse(course.get(i).week);
            for (int j = 0; j < parseweek.size(); j += 2) {
                if (parseweek.get(j) <= (nowweek - firstweek - 1) && (nowweek - firstweek - 1) <= parseweek.get(j + 1))
                    in = true;
                Log.i(TAG, "addcourse: " + course.get(i).name + " is weeked from " + parseweek.get(j) + " to " + parseweek.get(j + 1) + " and the realweek is " + (nowweek - firstweek - 1));
                if (in) break;
            }
            if (in)
                Log.i(TAG, "addcourse: in successed!");
            else
                Log.i(TAG, "addcourse: in failed");
            if (!in) continue;
            //25dp:30min
            double min, length;
            textarr.get(course.get(i).day).add(new TextView(getActivity()));
            TextView textView = textarr.get(course.get(i).day).get(textarr.get(course.get(i).day).size() - 1);
            min = (course.get(i).endhour - course.get(i).starthour) * 60 + (course.get(i).endmin - course.get(i).startmin);
            length = min * 5.0 / 6;
            min = (course.get(i).starthour) + course.get(i).startmin / 60.0;
            min -= 7;
            min *= 50;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, dip2px(getActivity().getApplicationContext(), min), 0, 0);
            textView.setLayoutParams(params);
            textView.setOnTouchListener(this);
            textView.setOnClickListener(this);
            textView.setHeight(dip2px(getActivity().getApplicationContext(), length));
            textView.setText("@" + course.get(i).room + "\n" + course.get(i).name);
            textView.setTextSize(12);
            textView.setBackgroundColor(course.get(i).color);
            textView.setTextColor(0xffffffff);
            textView.setEms(4);
            textView.setPadding(10, 0, 0, 0);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setBackgroundResource(R.drawable.textview_style);
            GradientDrawable myGrad = (GradientDrawable) textView.getBackground();
            myGrad.setColor(course.get(i).color);
            pcourse.get(nowweek - firstweek - 1).get(course.get(i).day).add(course.get(i));
            layout.get(course.get(i).day).addView(textView);
        }
        //postinvalidate
        for (int i = 0; i < textarr.size(); i++)
            for (int j = 0; j < textarr.get(i).size(); j++)
                textarr.get(i).get(j).postInvalidate();
        Log.i(TAG, "addcourse: ADD CCOURSE END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    private void readcourse() {
        Log.i(TAG, "readcourse: READ COURSE START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        course.clear();
        int num;
        for (num = 0; ; num++)
            if (sharedPreferences.getString("week" + num, null) == null)
                break;
        Log.i(TAG, "readcourse: num:"+num);
        for(int i=0;i<num;i++)
        {
            course.add(new Course(
            sharedPreferences.getString( "week" + i,null),
            sharedPreferences.getInt( "day" + i,-1),
            sharedPreferences.getInt( "starthour" + i,-1),
            sharedPreferences.getInt( "startmin" + i,-1),
            sharedPreferences.getInt( "endhour" + i,-1),
            sharedPreferences.getInt( "endmin" + i,-1),
            sharedPreferences.getString( "name" + i,null),
            sharedPreferences.getString( "room" + i,null),
            sharedPreferences.getString( "teacher" + i,null),
            sharedPreferences.getInt( "color" + i,-1),
            sharedPreferences.getInt( "priority" + i,-1)
            ));
        }
        Log.i(TAG, "readcourse: READ COURSE END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    /*checked*/
    private void redate(int position) {
        //更改SM日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, firstmonth - 1);
        calendar.set(Calendar.DATE, firstday);
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
        if (realweek == nowweek)
            day[dayofweek].setBackgroundColor(0x7f1AE6E6);
        else
            day[dayofweek].setBackgroundColor(0x7fC3C7C0);

        int firstdayofweek = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 7; i++)
            if ((firstdayofweek + i) == (calendar.getActualMaximum(Calendar.DATE)+1))
                day[i].setText(" " + "周" + daystr[i] + " " + " " + (Month + 1) + "月");
            else if((firstdayofweek + i) < (calendar.getActualMaximum(Calendar.DATE)+1))
                day[i].setText(" " + "周" + daystr[i] + " " + " " + ((firstdayofweek + i) % (calendar.getActualMaximum(Calendar.DATE)+1)) + "日");
            else
                day[i].setText(" " + "周" + daystr[i] + " " + " " + ((firstdayofweek + i) % (calendar.getActualMaximum(Calendar.DATE))) + "日");
        for (int i = 0; i < 7; i++)
            day[i].postInvalidate();
    }

    /*checked*/
    public static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*checked*/   private ArrayList<Integer> weeks = new ArrayList<>();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.STRimageview:
                break;
            case R.id.dialogweek:
                LayoutInflater inflaterweek = LayoutInflater.from(getActivity());
                View weekview = inflaterweek.inflate(R.layout.schedule_week_dialog, null);
                final AlertDialog.Builder weekbuilder = new AlertDialog.Builder(getActivity());
                weekbuilder.setTitle("周数选择");
                //解析view.gettext()中的周数 start
                String str = Pattern.compile("[^0-9-]").matcher(((TextView) view).getText()).replaceAll(" ");
                weeks.clear();
                ArrayList<Integer> singleweek = new ArrayList<>();
                boolean inweeks = false;
                int x = -1;
                for (int i = 0; i < str.length(); i++)
                    if (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                        if (x == -1)
                            x = str.charAt(i) - '0';
                        else
                            x = x * 10 + str.charAt(i) - '0';
                    else if (str.charAt(i) == '-' && x != -1) {
                        weeks.add(x);
                        x = -1;
                        inweeks = true;
                    } else if (x != -1 && inweeks) {
                        weeks.add(x);
                        x = -1;
                        inweeks = false;
                    } else if (x != -1) {
                        singleweek.add(x);
                        x = -1;
                    }
                if (x != -1 && inweeks)
                    weeks.add(x);
                else if (x != -1)
                    singleweek.add(x);
                for (int i = 0; i < 20; i++)
                    week_togglebutton[i] = false;
                for (int i = 0; i < weeks.size(); i++)
                    Log.i(TAG, "onClick: weeks[" + i + "]=" + weeks.get(i));
                for (int i = 0; i < singleweek.size(); i++)
                    Log.i(TAG, "onClick: singleweeks[" + i + "]=" + singleweek.get(i));
                for (int i = 0; i < singleweek.size(); i++)
                    week_togglebutton[singleweek.get(i) - 1] = true;
                for (int i = 0; i < weeks.size(); i += 2)
                    for (int j = weeks.get(i); j <= weeks.get(i + 1); j++)
                        week_togglebutton[j - 1] = true;
                //end
                //更改toggle button的状态
                weekbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //save
                        boolean successive = false;
                        int start = -1;
                        weeks.clear();
                        for (int i = 0; i < 20; i++)
                            if (togglearray.get(i).isChecked()) {
                                successive = true;
                                if (start == -1) {
                                    start = i;
                                    weeks.add(i);
                                }
                            } else if (successive) {
                                successive = false;
                                weeks.add(i - 1);
                                start = -1;
                            }
                        if (successive)
                            weeks.add(19);
                        textweek.setText("");
                        for (int i = 0; i < weeks.size(); i += 2)
                            if (weeks.get(i) == weeks.get(i + 1))
                                textweek.setText(textweek.getText() + "" + (weeks.get(i) + 1) + "周 ");
                            else
                                textweek.setText(textweek.getText() + "" + (weeks.get(i) + 1) + "-" + (weeks.get(i + 1) + 1) + "周 ");
                        dialogInterface.dismiss();
                    }
                });
                weekbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < togglearray.size(); i++)
                            togglearray.get(i).setChecked(false);
                        dialogInterface.dismiss();
                    }
                });
                weekbuilder.setView(weekview);
                AlertDialog weekdialog = weekbuilder.create();
                weekdialog.show();
                Window weekwindow = weekdialog.getWindow();
                togglearray.clear();
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton1));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton2));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton3));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton4));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton5));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton6));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton7));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton8));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton9));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton10));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton11));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton12));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton13));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton14));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton15));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton16));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton17));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton18));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton19));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton20));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton1_5));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton6_10));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton11_15));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebutton16_20));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebuttonsingle));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebuttondouble));
                togglearray.add((ToggleButton) weekwindow.findViewById(R.id.week_togglebuttonall));
                for (int i = 0; i < togglearray.size(); i++) {
                    togglearray.get(i).setOnCheckedChangeListener(this);
                }
                for (int i = 0; i < 20; i++)
                    togglearray.get(i).setChecked(week_togglebutton[i]);
                break;
            case R.id.dialogtime:
                /*
                *
                * DIALOGTIME START!!!
                *
                 */
                LayoutInflater timeinflater = LayoutInflater.from(getActivity());
                View timeview = timeinflater.inflate(R.layout.schedule_time_dialog, null);
                AlertDialog.Builder timebuilder = new AlertDialog.Builder(getActivity());
                timebuilder.setTitle("时间选择");
                timebuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //saving time!!!
                        texttime.setText(
                                daypicker.getDisplayedValues()[dialog_day = daypicker.getValue()] + " "
                                        + starthhpicker.getDisplayedValues()[dialog_starthh = starthhpicker.getValue()] + ":"
                                        + startmmpicker.getDisplayedValues()[dialog_startmm = startmmpicker.getValue()] + "-"
                                        + endhhpicker.getDisplayedValues()[dialog_endhh = endhhpicker.getValue()] + ":"
                                        + endmmpicker.getDisplayedValues()[dialog_endmm = endmmpicker.getValue()]);
                        dialogInterface.dismiss();
                    }
                });
                timebuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                timebuilder.setView(timeview);
                AlertDialog timedialog = timebuilder.create();
                timedialog.show();
                Window timewindow = timedialog.getWindow();
                daypicker = (NumberPicker) timewindow.findViewById(R.id.daypicker);
                starthhpicker = (NumberPicker) timewindow.findViewById(R.id.starthhpicker);
                startmmpicker = (NumberPicker) timewindow.findViewById(R.id.startmmpicker);
                endhhpicker = (NumberPicker) timewindow.findViewById(R.id.endhhpicker);
                endmmpicker = (NumberPicker) timewindow.findViewById(R.id.endmmpicker);
                String[] hh = new String[24];
                for (int i = 0; i < 24; i++)
                    if (i < 10)
                        hh[i] = "0" + ((Integer) i).toString();
                    else
                        hh[i] = ((Integer) i).toString();
                String[] mm = new String[60];
                for (int i = 0; i < 60; i++)
                    if (i < 10)
                        mm[i] = "0" + ((Integer) i).toString();
                    else
                        mm[i] = ((Integer) i).toString();
                daypicker.setOnValueChangedListener(this);
                starthhpicker.setOnValueChangedListener(this);
                startmmpicker.setOnValueChangedListener(this);
                endhhpicker.setOnValueChangedListener(this);
                endmmpicker.setOnValueChangedListener(this);
                daypicker.setMinValue(0);
                daypicker.setMaxValue(6);
                daypicker.setValue(0);
                daypicker.setDisplayedValues(new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"});
                starthhpicker.setMinValue(0);
                starthhpicker.setMaxValue(23);
                starthhpicker.setValue(8);
                starthhpicker.setDisplayedValues(hh);
                startmmpicker.setMinValue(0);
                startmmpicker.setMaxValue(59);
                startmmpicker.setValue(30);
                startmmpicker.setDisplayedValues(mm);
                endhhpicker.setMinValue(0);
                endhhpicker.setMaxValue(23);
                endhhpicker.setValue(10);
                endhhpicker.setDisplayedValues(hh);
                endmmpicker.setMinValue(0);
                endmmpicker.setMaxValue(59);
                endmmpicker.setValue(30);
                endmmpicker.setDisplayedValues(mm);
                /*
                 *
                 * DIALOGTIME END!!!
                 *
                 */
                break;
            default:
                weeks.add(0);
                weeks.add(19);
                int i;
                boolean isnull = true;
                using = new Course();
                for (i = 0; i < 7; i++)
                    if (view == layout.get(i))
                        isnull = false;
                if (isnull)
                    return;
                using.day = (today.get(Calendar.DAY_OF_WEEK) + 5) % 7;
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialogview = inflater.inflate(R.layout.schedule_course_dialog, null);
                //先注册一些控件
                final EditText editname, editroom;
                final TextView Textweek, Texttime;
                editname = (EditText) dialogview.findViewById(R.id.SCourseEditTextName);
                editroom = (EditText) dialogview.findViewById(R.id.SCourseEditTextRoom);
                Textweek = (TextView) dialogview.findViewById(R.id.dialogweek);
                Texttime = (TextView) dialogview.findViewById(R.id.dialogtime);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("添加新课程");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editname.getText().toString().trim() != "未填写" && editroom.getText().toString().trim() != "未填写" && Texttime.getText() != "未填写") {
                            using.name = editname.getText().toString().trim();
                            using.room = editroom.getText().toString().trim();
                            using.week = weekproduce(weeks);
                            Log.i(TAG, "onClick: the " + using.name + " is in the positive button!! And the weeks information is below:");
                            for (i = 0; i < weeks.size(); i++)
                                Log.i(TAG, "onClick: weeks[" + i + "]=" + weeks.get(i));
                            using.day = dialog_day;
                            using.starthour = dialog_starthh;
                            using.startmin = dialog_startmm;
                            using.endhour = dialog_endhh;
                            using.endmin = dialog_endmm;
                            using.addinfile(sharedPreferences,editor);
                            Log.i(TAG, "onClick: addinfile successed!");
                            readcourse();
                            addcourse(course, layout);
                            dialogInterface.dismiss();
                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setView(dialogview);
                AlertDialog dialog = builder.create();
                dialog.show();
                textweek = (TextView) dialog.getWindow().findViewById(R.id.dialogweek);
                texttime = (TextView) dialog.getWindow().findViewById(R.id.dialogtime);
                dialog.getWindow().findViewById(R.id.dialogweek).setOnClickListener(this);
                dialog.getWindow().findViewById(R.id.dialogtime).setOnClickListener(this);
                break;
        }
    }


    @Override
    /*checked*/ public void onItemSelected(AdapterView<?> adapterView, View view1, int position, long id) {
        nowweek = position + firstweek + 1;
        readcourse();
        addcourse(course, layout);
        redate(position);
    }

    @Override
    /*checked*/ public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /*checked*/   private EditText editname;
    /*checked*/   private EditText editroom;
    /*checked*/   private TextView textweek;
    /*checked*/   private TextView texttime;
    /*checked*/    private int bereplacedx, bereplacedy, bereplacedz;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i(TAG, "onTouch: INTO ONTOUCH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        int backgroundColor;
        int i, j, Break;
        boolean is;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (view.getId()) {
                    case R.id.STRimageview:
                        view.setBackgroundResource(R.drawable.plus_reverse);
                        view.postInvalidate();
                        break;
                    default:
                        backgroundColor = 1;
                        is = false;
                        Break = 0;
                        for (i = 0; i < 7; i++)
                            if (view.getParent() == layout.get(i))
                                is = true;
                        if (!is)
                            return false;

                        for (i = 0; i < textarr.size(); i++) {
                            for (j = 0; j < textarr.get(i).size(); j++)
                                if (textarr.get(i).get(j).getText() == ((TextView) view).getText()
                                        && textarr.get(i).get(j).getParent() == view.getParent()
                                        && textarr.get(i).get(j).getY() == view.getY()) {
                                    backgroundColor = pcourse.get(nowweek - firstweek - 1).get(i).get(j).color;
                                    Break = 1;
                                    break;
                                }
                            if (Break == 1)
                                break;
                        }
                        backgroundColor = backgroundColor % 0x01000000 + 0x3f000000;
                        ((GradientDrawable) view.getBackground()).setColor(backgroundColor);
                        view.postInvalidate();
                        break;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                switch (view.getId()) {
                    case R.id.STRimageview:
                        view.setBackgroundResource(R.drawable.plus);
                        view.postInvalidate();
                        break;
                    default:
                        using = new Course();
                        //筛选 view
                        is = false;
                        Break = 0;
                        backgroundColor = 0;
                        for (i = 0; i < 7; i++)
                            if (view.getParent() == layout.get(i))
                                is = true;
                        if (!is)
                            return false;

                        //背景色变换
                        for (i = 0; i < textarr.size(); i++) {
                            for (j = 0; j < textarr.get(i).size(); j++)
                                if (textarr.get(i).get(j).getText() == ((TextView) view).getText()
                                        && textarr.get(i).get(j).getParent() == view.getParent()
                                        && textarr.get(i).get(j).getY() == view.getY()) {
                                    backgroundColor = pcourse.get(nowweek - firstweek - 1).get(i).get(j).color;
                                    using = pcourse.get(nowweek - firstweek - 1).get(i).get(j);
                                    bereplacedx = nowweek - firstweek -1;
                                    bereplacedy = i;
                                    bereplacedz = j;
                                    Break = 1;
                                    break;
                                }
                            if (Break == 1)
                                break;
                        }
                        ((GradientDrawable) view.getBackground()).setColor(backgroundColor);
                        view.postInvalidate();

                        //响应点击事件 弹出dialog
                        if (motionEvent.getAction() != MotionEvent.ACTION_CANCEL) {
                            //onclick     dialog!!!
                            LayoutInflater inflater = LayoutInflater.from(getActivity());
                            View dialogview = inflater.inflate(R.layout.schedule_course_dialog, null);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            editname = (EditText) dialogview.findViewById(R.id.SCourseEditTextName);
                            editroom = (EditText) dialogview.findViewById(R.id.SCourseEditTextRoom);
                            textweek = (TextView) dialogview.findViewById(R.id.dialogweek);
                            texttime = (TextView) dialogview.findViewById(R.id.dialogtime);
                            builder.setTitle(using.name);
                            String[] dayname = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
                            editname.setText(using.name);
                            editroom.setText(using.room);
                            textweek.setText("");
                            ArrayList<Integer> weekparse = weekparse(using.week);
                            for (i = 0; i < weekparse.size(); i += 2)
                                if (Objects.equals(weekparse.get(i), weekparse.get(i + 1)))
                                    textweek.setText(textweek.getText() + "" + (weekparse.get(i) + 1) + "周 ");
                                else
                                    textweek.setText(textweek.getText() + "" + (weekparse.get(i) + 1) + "-" + (weekparse.get(i + 1) + 1) + "周 ");
                            texttime.setText(dayname[using.day] + " " + using.starthour + ":" + using.startmin + "-" + using.endhour + " " + using.endmin);
                            builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    using.name = editname.getText().toString().trim();
                                    using.room = editroom.getText().toString().trim();
                                    using.week = weekproduce(weeks);
                                    using.day = dialog_day;
                                    using.starthour = dialog_starthh;
                                    using.startmin = dialog_startmm;
                                    using.endhour = dialog_endhh;
                                    using.endmin = dialog_endmm;
                                    Log.i(TAG, "onClick: now you release the " + using.name + "     room:" + using.room + "          day:" + using.day + "         from" + using.starthour + ":" + using.startmin + " to " + using.endhour + ":" + using.endmin);
                                    using.updateinfile(sharedPreferences,editor,pcourse.get(bereplacedx).get(bereplacedy).get(bereplacedz));
                                    readcourse();
                                    addcourse(course, layout);
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setView(dialogview);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            textweek = (TextView) dialog.getWindow().findViewById(R.id.dialogweek);
                            texttime = (TextView) dialog.getWindow().findViewById(R.id.dialogtime);
                            textweek.setOnClickListener(this);
                            texttime.setOnClickListener(this);
                        }
                        break;
                }
                break;
            default:
                break;
        }
        Log.i(TAG, "onTouch: OUT TOUCH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        Log.i(TAG, "onCheckedChanged: ");
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
                        togglearray.get(i).setChecked(true);
                } else {
                    for (int i = 0; i < 5; i++)
                        togglearray.get(i).setChecked(false);
                }
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
            case R.id.week_togglebutton6_10:
                compoundButton.setChecked(b);
                if (b) {
                    for (int i = 5; i < 10; i++)
                        togglearray.get(i).setChecked(true);
                } else {
                    for (int i = 5; i < 10; i++)
                        togglearray.get(i).setChecked(false);
                }
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
            case R.id.week_togglebutton11_15:
                compoundButton.setChecked(b);
                if (b) {
                    for (int i = 10; i < 15; i++)
                        togglearray.get(i).setChecked(true);
                } else {
                    for (int i = 10; i < 15; i++)
                        togglearray.get(i).setChecked(false);
                }
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
            case R.id.week_togglebutton16_20:
                compoundButton.setChecked(b);
                if (b) {
                    for (int i = 15; i < 20; i++)
                        togglearray.get(i).setChecked(true);
                } else {
                    for (int i = 15; i < 20; i++)
                        togglearray.get(i).setChecked(false);
                }
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
            case R.id.week_togglebuttonsingle:
                compoundButton.setChecked(b);
                if (b)
                    for (int i = 0; i < 20; i += 2)
                        togglearray.get(i).setChecked(true);
                else
                    for (int i = 0; i < 20; i += 2)
                        togglearray.get(i).setChecked(false);
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
            case R.id.week_togglebuttondouble:
                compoundButton.setChecked(b);
                if (b)
                    for (int i = 1; i < 20; i += 2)
                        togglearray.get(i).setChecked(true);
                else
                    for (int i = 1; i < 20; i += 2)
                        togglearray.get(i).setChecked(false);
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
            case R.id.week_togglebuttonall:
                compoundButton.setChecked(b);
                if (b)
                    for (int i = 0; i < 20; i++)
                        togglearray.get(i).setChecked(true);
                else
                    for (int i = 0; i < 20; i++)
                        togglearray.get(i).setChecked(false);
                save(togglebuttonbool);
                check();
                recover(togglebuttonbool);
                break;
        }
        for (int i = 0; i < 20; i++)
            togglearray.get(i).postInvalidate();
    }

    /*checked*/
    public void check() {
        boolean line1, line2, line3, line4, dialine1, dialine2;
        line1 = true;
        line2 = true;
        line3 = true;
        line4 = true;
        dialine1 = true;
        dialine2 = true;
        for (int i = 0; i < 5; i++)
            if (!togglearray.get(i).isChecked())
                line1 = false;
        for (int i = 5; i < 10; i++)
            if (!togglearray.get(i).isChecked())
                line2 = false;
        for (int i = 10; i < 15; i++)
            if (!togglearray.get(i).isChecked())
                line3 = false;
        for (int i = 15; i < 20; i++)
            if (!togglearray.get(i).isChecked())
                line4 = false;
        for (int i = 0; i < 20; i += 2)
            if (!togglearray.get(i).isChecked())
                dialine1 = false;
        for (int i = 1; i < 20; i += 2)
            if (!togglearray.get(i).isChecked())
                dialine2 = false;
        if (line1 && !togglearray.get(20).isChecked())
            togglearray.get(20).setChecked(true);
        if (!line1 && togglearray.get(20).isChecked())
            togglearray.get(20).setChecked(false);
        if (line2 && !togglearray.get(21).isChecked())
            togglearray.get(21).setChecked(true);
        if (!line2 && togglearray.get(21).isChecked())
            togglearray.get(21).setChecked(false);
        if (line3 && !togglearray.get(22).isChecked())
            togglearray.get(22).setChecked(true);
        if (!line3 && togglearray.get(22).isChecked())
            togglearray.get(22).setChecked(false);
        if (line4 && !togglearray.get(23).isChecked())
            togglearray.get(23).setChecked(true);
        if (!line4 && togglearray.get(23).isChecked())
            togglearray.get(23).setChecked(false);
        if (dialine1 && !togglearray.get(24).isChecked())
            togglearray.get(24).setChecked(true);
        if (!dialine1 && togglearray.get(24).isChecked())
            togglearray.get(24).setChecked(false);
        if (dialine2 && !togglearray.get(25).isChecked())
            togglearray.get(25).setChecked(true);
        if (!dialine2 && togglearray.get(25).isChecked())
            togglearray.get(25).setChecked(false);
        if (dialine1 && dialine2 && !togglearray.get(26).isChecked())
            togglearray.get(26).setChecked(true);
        else if (!(dialine1 && dialine2) && togglearray.get(26).isChecked())
            togglearray.get(26).setChecked(false);
    }

    /*checked*/
    public void save(boolean[] togglebuttonbool) {
        for (int i = 0; i < 20; i++)
            togglebuttonbool[i] = togglearray.get(i).isChecked();
    }

    /*checked*/
    public void recover(boolean[] togglebuttonbool) {
        for (int i = 0; i < 20; i++)
            togglearray.get(i).setChecked(togglebuttonbool[i]);
    }

    /*checked*/   private boolean onValueChange_have_changed = false;

    @Override
    /*checked*/ public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        switch (numberPicker.getId()) {
            case R.id.daypicker:
                //do nothing
                break;
            case R.id.starthhpicker:
                if (!onValueChange_have_changed)
                    endhhpicker.setValue((starthhpicker.getValue() + 2) % 24);
                break;
            case R.id.startmmpicker:
                if (!onValueChange_have_changed)
                    endmmpicker.setValue(startmmpicker.getValue());
                break;
            case R.id.endhhpicker:
                onValueChange_have_changed = true;
                if (endhhpicker.getValue() < starthhpicker.getValue())
                    endhhpicker.setValue(starthhpicker.getValue());
                if (endmmpicker.getValue() < startmmpicker.getValue())
                    endmmpicker.setValue(startmmpicker.getValue());
                break;
            case R.id.endmmpicker:
                onValueChange_have_changed = true;
                if (endmmpicker.getValue() < startmmpicker.getValue() && endhhpicker.getValue() <= starthhpicker.getValue())
                    endmmpicker.setValue(startmmpicker.getValue());
                break;
        }
    }
}
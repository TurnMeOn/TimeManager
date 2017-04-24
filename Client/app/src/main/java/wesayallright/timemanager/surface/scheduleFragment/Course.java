package wesayallright.timemanager.surface.scheduleFragment;


import android.content.SharedPreferences;

import com.github.lzyzsd.randomcolor.RandomColor;

public class Course {
    public String week;
    public int day;
    public int starthour;
    public int startmin;
    public int endhour;
    public int endmin;
    public String name;
    public String room;
    public String teacher;
    public int color;
    public int priority;//0:normal 1:course 2:alert
    public final int PRIORITY_ACTIVITY = 2;
    public final int PRIORITY_COURSE = 1;
    public final int PRIORITY_NORMAL = 0;
    private static final String DATABASE_NAME = "Schedule_Fragment_data.db";
    private static final String TAG = "Course";
    public Course(String week, int day, int starthour, int startmin, int endhour, int endmin, String name, String room, String teacher, int color, int priority) {
        this.week=week;
        this.day = day;
        this.starthour = starthour;
        this.startmin = startmin;
        this.endhour = endhour;
        this.endmin = endmin;
        this.name = name;
        this.room = room;
        this.teacher = teacher;
        this.color = color;
        this.priority = priority;
    }

    public Course() {
        week=null;
        day = -1;
        starthour = -1;
        startmin = -1;
        endhour = -1;
        endmin = -1;
        name = "未填写";
        room = "未填写";
        teacher = "未填写";
        color = (new RandomColor()).randomColor();
        priority = -1;
    }

    public void recover() {
        week=null;
        day = -1;
        starthour = -1;
        startmin = -1;
        endhour = -1;
        endmin = -1;
        name = "未填写";
        room = "未填写";
        teacher = "未填写";
        color = -1;
        priority = -1;
    }

    public boolean empty() {
        return day < 0;
    }
    public void addinfile(SharedPreferences sharedPreferences,SharedPreferences.Editor editor) {
        int num;
        for (num = 0; ; num++)
            if (sharedPreferences.getString("week" + num, null) == null)
                break;
        this.removeinfile(sharedPreferences, editor);
        editor.putString("week" + num, week);
        editor.putInt("day" + num, day);
        editor.putInt("starthour" + num, starthour);
        editor.putInt("startmin" + num, startmin);
        editor.putInt("endhour" + num, endhour);
        editor.putInt("endmin" + num, endmin);
        editor.putString("name" + num, name);
        editor.putString("room" + num, room);
        editor.putString("teacher" + num, teacher);
        editor.putInt("color" + num, color);
        editor.putInt("priority" + num, priority);
        editor.commit();
    }
    public void removeinfile(SharedPreferences sharedPreferences,SharedPreferences.Editor editor) {
        int num;
        for (num = 0; ; num++)
            if (sharedPreferences.getString("week" + num, null) == null)
                break;
        for (int i = 0; i < num; i++) {
            if (sharedPreferences.getString("week" + i, null) == week &&
                    sharedPreferences.getInt("day" + i, -1) == day &&
                    sharedPreferences.getInt("starthour" + i, -1) == starthour &&
                    sharedPreferences.getInt("startmin" + i, -1) == startmin &&
                    sharedPreferences.getInt("endhour" + i, -1) == endhour &&
                    sharedPreferences.getInt("endmin" + i, -1) == endmin &&
                    sharedPreferences.getString("name" + i, null) == name &&
                    sharedPreferences.getString("room" + i, null) == room &&
                    sharedPreferences.getString("teacher" + i, null) == teacher &&
                    sharedPreferences.getInt("color" + i, -1) == color &&
                    sharedPreferences.getInt("priority" + i, -1) == priority) {
                editor.remove("week" + i);
                editor.remove("day" + i);
                editor.remove("starthour" + i);
                editor.remove("startmin" + i);
                editor.remove("endhour" + i);
                editor.remove("endmin" + i);
                editor.remove("name" + i);
                editor.remove("room" + i);
                editor.remove("teacher" + i);
                editor.remove("color" + i);
                editor.remove("priority" + i);
                editor.putString("week" + i     ,sharedPreferences.getString( "week" + (num-1),null));
                editor.putInt("day" + i         ,sharedPreferences.getInt( "day" + (num-1),-1));
                editor.putInt("starthour" + i   ,sharedPreferences.getInt( "starthour" + (num-1),-1));
                editor.putInt("startmin" + i    ,sharedPreferences.getInt( "startmin" + (num-1),-1));
                editor.putInt("endhour" + i     ,sharedPreferences.getInt( "endhour" + (num-1),-1));
                editor.putInt("endmin" + i      ,sharedPreferences.getInt( "endmin" + (num-1),-1));
                editor.putString("name" + i     ,sharedPreferences.getString( "name" + (num-1),null));
                editor.putString("room" + i     ,sharedPreferences.getString( "room" + (num-1),null));
                editor.putString("teacher" + i  ,sharedPreferences.getString( "teacher" + (num-1),null));
                editor.putInt("color" + i       ,sharedPreferences.getInt( "color" + (num-1),-1));
                editor.putInt("priority" +  i   ,sharedPreferences.getInt( "priority" + (num-1),-1));
                editor.commit();
                break;
            }
        }
    }
    public void updateinfile(SharedPreferences sharedPreferences,SharedPreferences.Editor editor,Course beupdated){
        beupdated.removeinfile(sharedPreferences,editor);
        this.addinfile(sharedPreferences,editor);
    }
}
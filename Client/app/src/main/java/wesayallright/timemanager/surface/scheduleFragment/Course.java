package wesayallright.timemanager.surface.scheduleFragment;


import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.github.lzyzsd.randomcolor.RandomColor;

import java.util.Objects;

class Course {
    String week;
    int day;
    int starthour;
    int startmin;
    int endhour;
    int endmin;
    public String name;
    String room;
    private String teacher;
    public int color;
    int priority;//0:normal 1:course 2:alert
//    public final int PRIORITY_ACTIVITY = 2;
//    public final int PRIORITY_COURSE = 1;
//    public final int PRIORITY_NORMAL = 0;
    private static final String TAG = "Course";
    Course(String week, int day, int starthour, int startmin, int endhour, int endmin, String name, String room, String teacher, int color, int priority) {
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
    Course(Course copy){
        this.week=copy.week;
        this.day = copy.day;
        this.starthour = copy.starthour;
        this.startmin = copy.startmin;
        this.endhour = copy.endhour;
        this.endmin = copy.endmin;
        this.name = copy.name;
        this.room = copy.room;
        this.teacher = copy.teacher;
        this.color = copy.color;
        this.priority = copy.priority;
    }
    Course() {
        week=null;
        day = -1;
        starthour = -1;
        startmin = -1;
        endhour = -1;
        endmin = -1;
        name = "未填写";
        room = "未填写";
        teacher = "未填写";
        color = (new RandomColor()).randomColor()-0x30000000;
        priority = 0;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void addinfile(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int num;
        for (num = 0; ; num++)
            if (sharedPreferences.getString("week" + num, null) == null)
                break;
        this.removeinfile(sharedPreferences);
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
        editor.apply();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void removeinfile(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.i(TAG, "removeinfile: REMOVE FILE START!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        int num;
        for (num = 0; ; num++)
            if (sharedPreferences.getString("week" + num, null) == null)
                break;
        Log.i(TAG, "removeinfile: week="+week+" day="+day+" "+starthour+":"+startmin+"-"+endhour+":"+endmin+" name"+name+" room"+room+" teacher="+teacher+" color"+color+" priority"+priority);
        for (int i = 0; i < num; i++) {
            Log.i(TAG, "removeinfile: week="+sharedPreferences.getString("week" + i, null)
                    +" day="+sharedPreferences.getInt("day" + i, -1)+" "+sharedPreferences.getInt("starthour" + i, -1) +":"+
                    sharedPreferences.getInt("startmin" + i, -1) +"-"+sharedPreferences.getInt("endhour" + i, -1)
                    +":"+sharedPreferences.getInt("endmin" + i, -1) +" name"+sharedPreferences.getString("name" + i, null)
                    +" room"+sharedPreferences.getString("room" + i, null) +" teacher="+sharedPreferences.getString("teacher" + i, null)
                    +" color"+sharedPreferences.getInt("color" + i, -1) +" priority"+sharedPreferences.getInt("priority" + i, -1));
            if (Objects.equals(sharedPreferences.getString("week" + i, null), week) &&
                    sharedPreferences.getInt("day" + i, -1) == day &&
                    sharedPreferences.getInt("starthour" + i, -1) == starthour &&
                    sharedPreferences.getInt("startmin" + i, -1) == startmin &&
                    sharedPreferences.getInt("endhour" + i, -1) == endhour &&
                    sharedPreferences.getInt("endmin" + i, -1) == endmin &&
                    Objects.equals(sharedPreferences.getString("name" + i, null), name) &&
                    Objects.equals(sharedPreferences.getString("room" + i, null), room) &&
                    Objects.equals(sharedPreferences.getString("teacher" + i, null), teacher) &&
                    sharedPreferences.getInt("color" + i, -1) == color &&
                    sharedPreferences.getInt("priority" + i, -1) == priority) {
                Log.i(TAG, "removeinfile: DELETE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
                editor.apply();
                editor.remove("week" + (num-1));
                editor.remove("day" + (num-1));
                editor.remove("starthour" + (num-1));
                editor.remove("startmin" + (num-1));
                editor.remove("endhour" + (num-1));
                editor.remove("endmin" + (num-1));
                editor.remove("name" + (num-1));
                editor.remove("room" + (num-1));
                editor.remove("teacher" + (num-1));
                editor.remove("color" + (num-1));
                editor.remove("priority" + (num-1));
                editor.commit();
                break;
            }
        }
        Log.i(TAG, "removeinfile: REMOVE FILE END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public void updateinfile(SharedPreferences sharedPreferences, SharedPreferences.Editor editor, Course beupdated){
//        beupdated.removeinfile(sharedPreferences,editor);
//        this.addinfile(sharedPreferences,editor);
//    }
}
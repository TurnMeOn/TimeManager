package wesayallright.timemanager.InnerLayer.Item;

/**
 * Created by mj on 17-5-9.
 * 每个项目
 */

public class Item {
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
    public String id;
    public int priority;//0:normal 1:course 2:alert
    private static final String TAG = "Course";

    Item(String week, int day, int starthour, int startmin, int endhour, int endmin, String name, String room, String teacher, int color, int priority) {
        this.week = week;
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
}

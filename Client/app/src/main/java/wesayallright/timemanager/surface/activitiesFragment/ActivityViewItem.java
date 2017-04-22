package wesayallright.timemanager.surface.activitiesFragment;

import java.util.Date;

/**
 * Created by mj on 17-4-22.
 * 用于显示每一个活动
 */

public class ActivityViewItem {
    public String name;
    public Date date;
    public ActivityViewItem(String n, Date d) {
        name = n;
        date = d;
    }
}
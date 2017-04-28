package wesayallright.timemanager.InnerLayer.Activities;

import android.util.Log;

import java.util.ArrayList;

import wesayallright.timemanager.InnerLayer.Package;

/**
 * Created by mj on 17-4-25.
 * 活动列表
 */

public class ActivityList {
    public ArrayList<String> activityList;

    public ActivityList() {
        if (Package.debug) {
            for(int i = 0; i < 20; i++) {
                activityList.add("A"+i);
            }
        }
        else {
            Log.e("ERROR", "还没写");
        }
    }

}

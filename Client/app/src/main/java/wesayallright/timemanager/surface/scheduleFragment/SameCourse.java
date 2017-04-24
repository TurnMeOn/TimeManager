package wesayallright.timemanager.surface.scheduleFragment;

import java.util.ArrayList;

public class SameCourse {
    private ArrayList<Course> arr;
    private String id;
    public void increase(Course x)
    {
        arr.add(x);
    }
//    public Course decrease(/*sometimes*/)
//    {
//
//    }
    public void setid(String id)
    {
        this.id=id;
    }
}

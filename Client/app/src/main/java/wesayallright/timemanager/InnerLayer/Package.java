package wesayallright.timemanager.InnerLayer;

import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Document;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by mj on 17-4-19.
 * 全局变量类
 */

public class Package {
    public static Document calendarXMLTree;
    //public static GroupList groups;
    public static User currentUser;
    public static boolean debug;
    public static DateFormat  dateFormatter;
    public static DateFormat  timeFormatter;
    public static DateFormat  dateTimeFormatter;
    public static Calendar today;

    public static SQLiteDatabase db;
    public static final String DB_NAME = "timeManager.db";

}

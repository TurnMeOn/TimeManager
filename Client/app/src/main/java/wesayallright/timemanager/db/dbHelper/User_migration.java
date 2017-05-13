package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import wesayallright.timemanager.InnerLayer.Package;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 */

public class User_migration extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "User";
    public User_migration(Context context) {
        super(context, Package.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS User (user_id vchar(10) NOT NULL ,password vchar(64) NOT NULL ,stu_id vchar(20) NOT NULL ,nickname vchar(15) NOT NULL ,realname vchar(10) NOT NULL ,birthday date NOT NULL ,mobile vchar(21) NOT NULL ,email vchar NOT NULL ,updatetime date NOT NULL ,id INTEGER NOT NULL PRIMER KEY)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
 

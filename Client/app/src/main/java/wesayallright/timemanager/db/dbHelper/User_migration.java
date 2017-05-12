package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import wesayallright.timemanager.InnerLayer.Package;

/**
 * Created by mj on 17-5-11.
 * 用户表
 */

public class User_migration extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "users";
    public User_migration(Context context) {
        super(context, Package.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(\"id\" integer NOT NULL PRIMARY KEY , " +
                "\"password\" varchar(64) NOT NULL, " +
                "\"stu_id\" varchar(20) NOT NULL, " +
                "\"nickname\" varchar(15) NOT NULL, " +
                "\"realname\" varchar(10) NULL, " +
                "\"gender\" smallint NOT NULL, " +
                "\"birthday\" date NULL, " +
                "\"mobile\" varchar(21) NOT NULL, " +
                "\"email\" varchar(254) NULL, " +
                "\"updatetime\" date NOT NULL, " +
                "\"major\" varchar(30) NOT NULL, " +
                "\"entry_date\" date NOT NULL, " +
                "\"school_index_id\" integer NOT NULL REFERENCES \""+ School_migration.TABLE_NAME+"\" (\"id\"), " +
                "\"user_id\" varchar(10) NOT NULL)";



        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}

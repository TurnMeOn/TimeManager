package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wesayallright.timemanager.InnerLayer.Package;

/**
 * Created by mj on 17-5-11.
 * 群组成员表
 */


public class Group_user_migration extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "groupusers";
    public Group_user_migration(Context context) {
        super(context, Package.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "\"id\" integer NOT NULL PRIMARY KEY, " +
                "\"privilege\" smallint NOT NULL, " +
                "\"visible\" bool NOT NULL, " +
                "\"update_time\" datetime NOT NULL, \"" +
                "group_index_id\" integer NOT NULL REFERENCES \""+ Group_migration.TABLE_NAME+"\" (\"id\"), " +
                "\"user_index_id\" integer NOT NULL REFERENCES \""+ User_migration.TABLE_NAME+"\" (\"id\"))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}

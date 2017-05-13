package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import wesayallright.timemanager.InnerLayer.Package;
import wesayallright.timemanager.db.dbHelper.Affair_migration;

/**
 * Created by mj on 17-5-11.
 * 单任务时间表
 */

public class Single_Affairs_Time_migration extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "singleaffairstime";
    public Single_Affairs_Time_migration(Context context) {
        super(context, Package.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(\"id\" integer NOT NULL PRIMARY KEY, " +
                "\"dateTime\" datetime NOT NULL, " +
                "\"affair_index_id\" integer NOT NULL REFERENCES \"" + Affair_migration.TABLE_NAME+ "\" (\"id\"))";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}

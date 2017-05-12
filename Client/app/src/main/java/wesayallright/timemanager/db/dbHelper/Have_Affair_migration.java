package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import wesayallright.timemanager.InnerLayer.Package;

/**
 * Created by mj on 17-5-11.
 * 是否有任务表, 此表既可以查找一个人有哪些任务, 也可以查参加同一个任务的有哪些人
 */

public class Have_Affair_migration extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "haveaffair";
    public Have_Affair_migration(Context context) {
        super(context, Package.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(\"id\" integer NOT NULL PRIMARY KEY, " +
                "\"affair_index_id\" integer NOT NULL REFERENCES \"" + Affair_migration.TABLE_NAME+"\" (\"id\"), "+
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

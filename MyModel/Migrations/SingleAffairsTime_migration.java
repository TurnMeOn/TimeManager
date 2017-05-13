package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import wesayallright.timemanager.InnerLayer.Package;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 */

public class SingleAffairsTime_migration extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "SingleAffairsTime";
    public Affair_migration(Context context) {
        super(context, Package.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE IF NOT EXISTS SingleAffairsTime (affair_index_id integer NOT NULL REFERENCE Affairs ,id INTEGER NOT NULL PRIMER KEY)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
 

package wesayallright.timemanager.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wesayallright.timemanager.InnerLayer.Package;

/**
 * Created by mj on 17-5-11.
 * 群组列表
 */

public class Group_migration extends SQLiteOpenHelper{

        private static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "groups";
        public Group_migration(Context context) {
            super(context, Package.DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(\"id\" integer NOT NULL PRIMARY KEY, " +
                    "\"name\" varchar(50) NOT NULL, " +
                    "\"introduction\" varchar(100) NOT NULL, " +
                    "\"establish_date\" date NOT NULL, " +
                    "\"update_date\" date NOT NULL, " +
                    "\"is_public\" bool NOT NULL, " +
                    "\"max_member\" smallint unsigned NOT NULL, " +
                    "\"group_id\" varchar(10) NOT NULL)";

            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(sql);
            onCreate(db);
        }

}

 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.SuccessiveAffairsTime_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class SuccessiveAffairsTime extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS SuccessiveAffairsTime (affair_index_id integer NOT NULL REFERENCE Affairs ,id INTEGER NOT NULL PRIMER KEY)


    public SuccessiveAffairsTime(Context c) {
        super(c, SuccessiveAffairsTime_migration.class, SuccessiveAffairsTime_migration.TABLE_NAME);
        helper = new SuccessiveAffairsTime_migration(c);
        db = helper.getWritableDatabase();
    }


    public void save() {
        super.saveOrInsert(this);
    }

    public void insert() {
        super.insert(this);
    }
    public void delete() {
        super.delete(this);
    }
    
    public int valueOf() {
        return id;
    }
}

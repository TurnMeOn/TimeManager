 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.SuccessiveAffairsTime_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class SuccessiveAffairsTime  extends DAO {
    private SQLiteDatabase db;
    private SuccessiveAffairsTime_migration helper;

        public Affairs affair_index;

 public Integer id;



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

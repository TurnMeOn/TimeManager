 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.SingleAffairsTime_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class SingleAffairsTime  extends DAO {
    private SQLiteDatabase db;
    private SingleAffairsTime_migration helper;

        public Affairs affair_index;

 public Integer id;



    public SingleAffairsTime(Context c) {
        super(c, SingleAffairsTime_migration.class, SingleAffairsTime_migration.TABLE_NAME);
        helper = new SingleAffairsTime_migration(c);
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

 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.HaveAffair_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class HaveAffair  extends DAO {
    private SQLiteDatabase db;
    private HaveAffair_migration helper;

        public User user_index;
    public Affairs affair_index;

 public Integer id;



    public HaveAffair(Context c) {
        super(c, HaveAffair_migration.class, HaveAffair_migration.TABLE_NAME);
        helper = new HaveAffair_migration(c);
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

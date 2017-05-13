 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.School_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class School  extends DAO {
    private SQLiteDatabase db;
    private School_migration helper;

        public String school_id;
    public String name;
    public String province;
    public String city;

 public Integer id;



    public School(Context c) {
        super(c, School_migration.class, School_migration.TABLE_NAME);
        helper = new School_migration(c);
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

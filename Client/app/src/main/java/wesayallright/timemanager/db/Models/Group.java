 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.Group_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class Group  extends DAO {
    private SQLiteDatabase db;
    private Group_migration helper;

        public String group_id;
    public String name;
    public String introduction;
    public Date establish_date;
    public Date update_date;

 public Integer id;



    public Group(Context c) {
        super(c, Group_migration.class, Group_migration.TABLE_NAME);
        helper = new Group_migration(c);
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

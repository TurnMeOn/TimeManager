 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.InGroup_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class InGroup  extends DAO {
    private SQLiteDatabase db;
    private InGroup_migration helper;

        public User user_index;
    public Group group_index;
    public Group_User role_index;

 public Integer id;



    public InGroup(Context c) {
        super(c, InGroup_migration.class, InGroup_migration.TABLE_NAME);
        helper = new InGroup_migration(c);
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

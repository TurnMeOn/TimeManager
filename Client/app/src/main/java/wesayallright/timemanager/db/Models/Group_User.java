 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.Group_User_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class Group_User  extends DAO {
    private SQLiteDatabase db;
    private Group_User_migration helper;

        public Group group_index;
    public User user_index;
    public Boolean visible;
    public Date update_time;

 public Integer id;



    public Group_User(Context c) {
        super(c, Group_User_migration.class, Group_User_migration.TABLE_NAME);
        helper = new Group_User_migration(c);
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

 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.Group_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class Group extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS Group (group_id vchar(10) NOT NULL ,name vchar(50) NOT NULL ,introduction vchar(100) NOT NULL ,establish_date date NOT NULL ,update_date date NOT NULL ,id INTEGER NOT NULL PRIMER KEY)


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

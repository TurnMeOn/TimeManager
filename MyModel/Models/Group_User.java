 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.Group_User_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class Group_User extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS Group_User (group_index_id integer NOT NULL REFERENCE Group ,user_index_id integer NOT NULL REFERENCE User ,visible bool NOT NULL ,update_time datetime NOT NULL ,id INTEGER NOT NULL PRIMER KEY)


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

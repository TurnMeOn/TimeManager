 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.InGroup_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class InGroup extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS InGroup (user_index_id integer NOT NULL REFERENCE User ,group_index_id integer NOT NULL REFERENCE Group ,role_index_id integer NOT NULL REFERENCE Group_User ,id INTEGER NOT NULL PRIMER KEY)


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

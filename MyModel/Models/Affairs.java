 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.Affairs_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class Affairs extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS Affairs (name vchar(20) NOT NULL ,place vchar(30) NOT NULL ,details vchar(100) NOT NULL ,organizer_index_id integer NOT NULL REFERENCE User ,id INTEGER NOT NULL PRIMER KEY)


    public Affairs(Context c) {
        super(c, Affairs_migration.class, Affairs_migration.TABLE_NAME);
        helper = new Affairs_migration(c);
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

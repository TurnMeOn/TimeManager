 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.School_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class School extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS School (school_id vchar(10) NOT NULL ,name vchar(30) NOT NULL ,province vchar(20) NOT NULL ,city vchar(30) NOT NULL ,id INTEGER NOT NULL PRIMER KEY)


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

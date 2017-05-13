 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.Affairs_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class Affairs  extends DAO {
    private SQLiteDatabase db;
    private Affairs_migration helper;

        public String name;
    public String place;
    public String details;
    public User organizer_index;

 public Integer id;



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

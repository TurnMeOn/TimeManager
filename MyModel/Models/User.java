 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.User_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:29:43
 *
 */

public class User extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

     CREATE TABLE IF NOT EXISTS User (user_id vchar(10) NOT NULL ,password vchar(64) NOT NULL ,stu_id vchar(20) NOT NULL ,nickname vchar(15) NOT NULL ,realname vchar(10) NOT NULL ,birthday date NOT NULL ,mobile vchar(21) NOT NULL ,email vchar NOT NULL ,updatetime date NOT NULL ,id INTEGER NOT NULL PRIMER KEY)


    public User(Context c) {
        super(c, User_migration.class, User_migration.TABLE_NAME);
        helper = new User_migration(c);
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

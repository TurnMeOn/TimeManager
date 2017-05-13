 
package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

import wesayallright.timemanager.db.DAO;
import wesayallright.timemanager.db.dbHelper.User_migration;

/**
 * MyModel 自动生成 at: 2017-05-13 17:46:14
 *
 */

public class User  extends DAO {
    private SQLiteDatabase db;
    private User_migration helper;

        public String user_id;
    public String password;
    public String stu_id;
    public String nickname;
    public String realname;
    public Date birthday;
    public String mobile;
    public String email;
    public Date updatetime;

 public Integer id;



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
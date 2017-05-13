package wesayallright.timemanager.db.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import wesayallright.timemanager.db.dbAccess.DAO;
import wesayallright.timemanager.db.dbHelper.Affair_migration;

/**
 * Created by mj on 17-5-11.
 *
 */

public class Affair extends DAO {
    private SQLiteDatabase db;
    private Affair_migration helper;

    public Integer id;
    public String name;
    public Integer type;
    public String place;
    public String details;
    public User organizer;


    public Affair(Context c) {
        super(c, Affair_migration.class, Affair_migration.TABLE_NAME);
        helper = new Affair_migration(c);
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
}

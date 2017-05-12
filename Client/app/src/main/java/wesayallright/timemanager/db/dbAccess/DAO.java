package wesayallright.timemanager.db.dbAccess;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by mj on 17-5-11.
 * 事务数据库模型
 */

public class DAO {

    private SQLiteOpenHelper helper;
    private String TABLE_NAME;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public DAO(Context c, Class helper, String TABLE_NAME) {
        try {
            Constructor helper_constructor = helper.getConstructor(Context.class);
            this.helper = (SQLiteOpenHelper)helper_constructor.newInstance(c);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        this.TABLE_NAME = TABLE_NAME;
    }
    @TargetApi(19)
    public Object[] where(Object o, Context context, String key, String operator, String value) {
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            Class c = o.getClass();
            String sql = "Select * from " + TABLE_NAME + " where ? ? ? ";
            try(Cursor cursor = db.rawQuery(sql, new String[]{key, operator, value})) {

                int count = cursor.getCount();
                Object[] ret = new Object[count];

                for (int i = 0; i < count; i++) {
                    String name = cursor.getColumnName(i);
                    Constructor constructor = c.getDeclaredConstructor(Context.class);
                    Object affair = constructor.newInstance(context);
                    Field f = affair.getClass().getField(name);
                    f.setAccessible(true);
                    switch (cursor.getType(i)) {
                        case Cursor.FIELD_TYPE_BLOB:
                            f.set(affair, cursor.getBlob(i));
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            f.set(affair, cursor.getFloat(i));
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            f.set(affair, cursor.getInt(i));
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            f.set(affair, null);
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            f.set(affair, cursor.getString(i));
                            break;
                    }
                    ret[i] = affair;
                }
                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(19)
    public void saveOrInsert(Object o) {
        try (SQLiteDatabase db = helper.getWritableDatabase()){
            Class c = o.getClass();
            Field f = c.getField("id");
            String sql = "Select * from " + TABLE_NAME + " Where id = ?";
           try( Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(f.getInt(o))})) {
               if (cursor.getCount() == 0) {
                   insert(o);
               } else {
                   save(o);
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void save(Object o) {
        try (SQLiteDatabase db = helper.getWritableDatabase()){
            Class c = o.getClass();
            StringBuilder sql = new StringBuilder("update " + TABLE_NAME + "set ");
            Field[] fields = c.getDeclaredFields();
            Integer id = null;
            for (Field f : fields) {
                if (f.getModifiers() != Modifier.PUBLIC)
                    continue;
                String name = f.getName();
                Class type = f.getType();
                Method valueOf = type.getDeclaredMethod("valueOf", String.class);
                if (name.equals( "id")) {
                    id = (Integer)valueOf.invoke(type, String.class);
                } else {
                    sql.append(name)
                            .append("=")
                            .append((String) valueOf.invoke(type, f.get(o)))
                            .append(" ");
                }
            }
            if (id == null) throw new Exception("数据库写入失败:id为空");
            sql.append(" where id = ").append(id);
            Log.i("save", sql.toString());
            db.rawQuery(sql.toString(), new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void insert(Object o) {
        try (SQLiteDatabase db = helper.getWritableDatabase()){
            Class c = o.getClass();
            String sql_head = "insert into " + TABLE_NAME + " ";
            StringBuilder keys = new StringBuilder();
            StringBuilder values = new StringBuilder();
            StringBuilder tail = new StringBuilder();
            Field[] fields = c.getDeclaredFields();
            Integer id = null;
            for (Field f : fields) {
                if (f.getModifiers() != Modifier.PUBLIC)
                    continue;
                String name = f.getName();
                Class type = f.getType();
                Method valueOf = type.getDeclaredMethod("valueOf", String.class);
                if (name.equals( "id")) {
                    id = (Integer)valueOf.invoke(type, String.class);
                } else {
                    keys.append(name).append(" ");
                    values.append((String) valueOf.invoke(type, f.get(o))).append(" ");
                }
            }
            if (id == null) throw new Exception("数据库写入失败:id为空");
            tail.append(" where id = ").append(id);
            String sql = sql_head + " ( " + keys.toString() + " ) VALUES (" + values.toString() + " ) " + tail;
            Log.i("save", sql);
            db.rawQuery(sql, new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    public void delete(Object o) {
        try(SQLiteDatabase db = helper.getWritableDatabase()) {
            Class c = o.getClass();
            Field f = c.getDeclaredField("id");
            db.rawQuery("delete from" + TABLE_NAME + "where id = ?" , new String[]{String.valueOf(f.getInt(o))});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
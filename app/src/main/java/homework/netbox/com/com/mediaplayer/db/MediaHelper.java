package homework.netbox.com.com.mediaplayer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MediaHelper extends SQLiteOpenHelper {
    public MediaHelper(Context context) {
        super(context, "MediaDB", null, 1);
    }

    /**
     *创建数据库
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_media_sql="create table media(id integer primary key autoincrement," +
                "title text,name text,length text,ima text )";
        sqLiteDatabase.execSQL(create_media_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

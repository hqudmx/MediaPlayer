package homework.netbox.com.com.mediaplayer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import homework.netbox.com.com.mediaplayer.bean.MediaBean;
import homework.netbox.com.com.mediaplayer.db.MediaHelper;

public class MediaModel {
    Context mContext;
    MediaHelper mediaHelper;
    MediaBean mediaBean;
    SQLiteDatabase readerDateBase;
    SQLiteDatabase writerDateBase;

    public MediaModel(Context mContext) {
        this.mContext = mContext;
        mediaHelper = new MediaHelper(mContext);
        readerDateBase = mediaHelper.getReadableDatabase();
        writerDateBase = mediaHelper.getWritableDatabase();
    }
/*

    */
/**
 * 插入数据
 *
 * @param mediaBean*/

/*
    public void insertMedia(MediaBean mediaBean) {
        ContentValues cv = new ContentValues();
        cv.put("title", mediaBean.mTitle);
        cv.put("name", mediaBean.mName);
        cv.put("length", mediaBean.mlength);
        cv.put("ima", mediaBean.mImageId);
        writerDateBase.insert("media", null, cv);
    }

    */
/**
 * 删除数据
 *
 * @param oldMedia
 *//*

    public void deleteMedia(MediaBean oldMedia) {
        writerDateBase.delete("media", "title=? and name =? and length =? and ima=?",
                new String[]{oldMedia.mTitle, oldMedia.mName, oldMedia.mlength, oldMedia.mImageId + ""});
    }

    */
/**
 * 更新数据
 *
 * @param oldMedia
 * @param newMedia
 *//*

    public void updateMedia(MediaBean oldMedia, MediaBean newMedia) {
        ContentValues cv = new ContentValues();
        cv.put("title", newMedia.mTitle);
        cv.put("name", newMedia.mName);
        cv.put("length", newMedia.mlength);
        cv.put("ima", mediaBean.mImageId);
        writerDateBase.update("media", cv, "title=? and name =? and length =? and ima=?",
                new String[]{oldMedia.mTitle, oldMedia.mName, oldMedia.mlength, oldMedia.mImageId + ""});

    }

    */
/**
 * 查询所有
 *
 * @return
 *//*

    public ArrayList<MediaBean> queryAllMedia() {
        ArrayList<MediaBean> media = new ArrayList<MediaBean>();
        Cursor cursor = readerDateBase.query("media", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String length = cursor.getString(cursor.getColumnIndex("length"));
            int ImaId = cursor.getInt(cursor.getColumnIndex("ima"));
            MediaBean mediaBean1 = new MediaBean(title, name, length, ImaId);

            media.add(mediaBean1);
        }
        return media;
    }

    */
/**
 * 模糊查询
 *
 * @param key 关键字
 * @return 查询结果
 *//*

    public ArrayList<MediaBean> queryLikeMedia(String key) {
        ArrayList<MediaBean> media = new ArrayList<>();
        Cursor cursor = readerDateBase.query("media", null, "title like '%"
                + key + "%' or name like '%" + key + "%'", null, null, null, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String length = cursor.getColumnName(cursor.getColumnIndex("length"));
            int ImaId = cursor.getInt(cursor.getColumnIndex("ima"));
            MediaBean mediaBean1 = new MediaBean(title, name, length, ImaId);
            media.add(mediaBean1);

        }

        return media;
    }

}
*/
}

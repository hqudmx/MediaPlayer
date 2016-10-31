package homework.netbox.com.com.mediaplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import homework.netbox.com.com.mediaplayer.bean.MediaBean;

/**
 * Created by hqudmx on 2016/9/5.
 */
public class MediaUtils {
    Context mContext;
    MediaBean mediaBean;

    public MediaUtils(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 获得所有歌曲
     *
     * @return
     */
    public ArrayList<MediaBean> getMedia() {

        ArrayList<MediaBean> media = new ArrayList<MediaBean>();

        Cursor cursor = mContext.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Video.Media.TITLE,  //音乐名
                                MediaStore.Audio.Media.DURATION,            //音乐的总时间
                                MediaStore.Audio.Media.ARTIST,          //艺术家
                                MediaStore.Audio.Media._ID,             //id号
                                MediaStore.Audio.Media.DISPLAY_NAME,        //音乐文件名
                                MediaStore.Audio.Media.DATA         //音乐文件的路径
                        }, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            long length = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            String mData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            if (length > 20000) {
                MediaBean mediaBean = new MediaBean(title, name, length, mData);
                media.add(mediaBean);
            }
        }
        return media;
    }

    /**
     * 关键字查询，模糊匹配
     *
     * @param key
     * @return
     */
    public ArrayList<MediaBean> queryLikeMedia(String key) {
        ArrayList<MediaBean> media = new ArrayList<MediaBean>();
        Uri url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = mContext.getContentResolver()
                .query(url,
                        new String[]{
                                MediaStore.Audio.Media.TITLE,
                                MediaStore.Audio.Media.ARTIST,
                                MediaStore.Audio.Media.DURATION,
                                MediaStore.Audio.Media.DATA
                        }, MediaStore.Audio.Media.TITLE + " like ?", new String[]{"%"+key+"%"}, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            long length = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            String mData = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

            //if (title.equals(key) | name.equals(key)) {
            if (length > 20000 ) {
                MediaBean mediaBean = new MediaBean(title, name, length, mData);
                media.add(mediaBean);
            }
            // }
            /*if (length > 20000 && (title.equals(key) || name.equals(key))) {
                MediaBean mediaBean = new MediaBean(title, name, length, mData);
                media.add(mediaBean);
            }*/
        }
        return media;
    }
}

package homework.netbox.com.com.mediaplayer.bean;

import java.io.Serializable;


public class MediaBean implements Serializable {
    public String mTitle;
    public String mName;
    public long mlength;
    public String artist;
    public String mData;


    public MediaBean(String mTitle, String mName, long mlength, String mData) {
        this.mTitle = mTitle;
        this.mName = mName;
        this.mlength = mlength;
        this.mData = mData;
        //this.mImageId = mImageId;
    }

    public MediaBean(String mTitle, String mName, long mlength) {
        this.mTitle = mTitle;
        this.mName = mName;
        this.mlength = mlength;
        //  this.artist=artist;
        //this.mImageId = mImageId;
    }


}

package homework.netbox.com.com.mediaplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import homework.netbox.com.R;
import homework.netbox.com.com.mediaplayer.bean.MediaBean;

public class MediaAdpter extends BaseAdapter {
    Context mContext;
    ArrayList<MediaBean> mArryList;

    public MediaAdpter(Context mContext, ArrayList<MediaBean> mArryList) {
        this.mContext = mContext;
        this.mArryList = mArryList;
    }

    @Override
    public int getCount() {
        return mArryList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.media_item, null);
        ImageView ima = (ImageView) v.findViewById(R.id.ima_item);
        TextView title = (TextView) v.findViewById(R.id.title_item);
        TextView name = (TextView) v.findViewById(R.id.name_item);
        TextView length = (TextView) v.findViewById(R.id.length_item);

        MediaBean mediaBean = mArryList.get(i);

        title.setText(mediaBean.mTitle);
        name.setText(mediaBean.mName);
        length.setText(toTime((int)mediaBean.mlength));
        //ima.setImageResource(mediaBean.mImageId);

        return v;
    }
    /*时间格式转换*/
    public String toTime(int time) {

        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }
    public void SetDataChanged(ArrayList<MediaBean> mArryList) {
        this.mArryList = mArryList;
        notifyDataSetChanged();
    }
}

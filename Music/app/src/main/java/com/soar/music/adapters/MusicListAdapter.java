package com.soar.music.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soar.music.R;
import com.soar.music.model.MusicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaofei on 2016/12/26.
 */
public class MusicListAdapter extends BaseAdapter {
    private List<MusicInfo> listData = new ArrayList<>();
    private LayoutInflater layoutInflater ;
    private Context context ;

    public MusicListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.music_info_item , null);
            viewHolder.nameText = (TextView)convertView.findViewById(R.id.text_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameText.setText(listData.get(position).getName());
        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }


    public void setListData(List<MusicInfo> list){
        this.listData = list;
        notifyDataSetChanged();
    }


    class ViewHolder{
        public TextView nameText;
    }
}

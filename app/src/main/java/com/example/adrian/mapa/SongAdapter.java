package com.example.adrian.mapa;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by adrian on 15/06/2017.
 */
public class SongAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;

    public SongAdapter(Context c, ArrayList<Song> theSongs) {
        songs = theSongs;
        songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    class ViewHolder{

        TextView songView;
        TextView artistView;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LinearLayout songLay = (LinearLayout)convertView;

        if (songLay == null) {

            songLay = (LinearLayout) songInf.inflate
                    (R.layout.song, parent, false);
            //get view holder instance
            holder = new ViewHolder();

            //populate viewholder with artist and text views

            holder.songView = (TextView) songLay.findViewById(R.id.song_title);
            holder.artistView = (TextView) songLay.findViewById(R.id.song_artist);

            songLay.setTag(R.string.TAG1,holder);
        }
        else {
            holder = (ViewHolder) songLay.getTag(R.string.TAG1);
        }

        //get song using position
        Song currSong = songs.get(position);

        //get title and artist strings
        holder.songView.setText(currSong.getTitle());
        holder.artistView.setText(currSong.getArtist());
        songLay.setTag(position);
        return songLay;
    }
}
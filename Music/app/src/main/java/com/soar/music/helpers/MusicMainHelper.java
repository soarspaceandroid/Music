package com.soar.music.helpers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.soar.music.interfaces.MusicMainInter;
import com.soar.music.interfaces.MusicScanLisenter;
import com.soar.music.model.AllMusic;
import com.soar.music.model.MusicInfo;
import com.soar.music.savemanager.DataManager;
import com.soar.music.utils.Keys;
import com.soar.music.widgets.LayoutFrames;

import java.util.ArrayList;

/**
 * Created by gaofei on 2016/12/26.
 */
public class MusicMainHelper implements MusicMainInter{


    @Override
    public DataManager initDataManager() {
        return getDataManager();
    }


    @Override
    public void scanMusic(final Context context , final DataManager dataManager, String path , final MusicScanLisenter musicScanLisenter) {

        new AsyncTask<Void , Void ,ArrayList<MusicInfo>>(){
            @Override
            protected void onPostExecute(ArrayList<MusicInfo> musicInfos) {
                musicScanLisenter.onFinish(musicInfos);
                super.onPostExecute(musicInfos);
            }

            @Override
            protected ArrayList<MusicInfo> doInBackground(Void... params) {
                ArrayList<MusicInfo> list = toScan(context , dataManager);
                return list;
            }
        }.execute();

    }


    @Override
    public void blueMainUI(Context context ,View rootView  ,LayoutFrames layoutFrames) {
        layoutFrames.displayBack(rootView);
    }




    /**
     * scan music
     * @param context
     * @param dataManager
     * @return
     */
    private ArrayList<MusicInfo> toScan(Context context , DataManager dataManager){
        Cursor mAudioCursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,// 字段　没有字段　就是查询所有信息　相当于SQL语句中的　“ * ”
                null, // 查询条件
                null, // 条件的对应?的参数
                MediaStore.Audio.AudioColumns.TITLE);// 排序方式
        // 循环输出歌曲的信息
        ArrayList<MusicInfo> mListData = new ArrayList<MusicInfo>();
        for (int i = 0; i < mAudioCursor.getCount(); i++) {
            mAudioCursor.moveToNext();

            int isMusic = mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.IS_MUSIC);//歌名
            if(!mAudioCursor.getString(isMusic).equals("1")){
                continue;
            }
            int id = mAudioCursor.getInt(mAudioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            String name = mAudioCursor.getString(mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));//歌名
            String artist = mAudioCursor.getString(mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));//艺术家
            String album = mAudioCursor.getString(mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));//专辑
            String path = mAudioCursor.getString(mAudioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            Log.e("soar" , "name  "+name+"  path  "+path);
            int lenth = mAudioCursor.getInt(mAudioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            String albumImage = getAlbumImage(context ,mAudioCursor.getInt(mAudioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
            mListData.add(new MusicInfo(id , name , artist ,album , path , lenth , albumImage));
        }
        dataManager.insertObject(Keys.MUSIC_INFO  ,new AllMusic(mListData));
        return mListData;
    }


    /**
     * 根据歌曲id获取图片
     *
     * @param albumId
     * @return
     */
    private String getAlbumImage(Context context  ,int albumId) {
        String result = "";
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    Uri.parse("content://media/external/audio/albums/"
                            + albumId), new String[]{"album_art"}, null,
                    null, null);
            for (cursor.moveToFirst(); !cursor.isAfterLast(); ) {
                result = cursor.getString(0);
                break;
            }
        } catch (Exception e) {
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }

        return null == result ? null : result;
    }




    /**
     * getDataManager
     * @return
     */
    public DataManager getDataManager(){
        DataManager dataManager = null;
        if(dataManager == null){
            synchronized (DataManager.class) {
                if(dataManager == null){
                    dataManager = new DataManager();
                }
            }
        }
        return dataManager;
    }
    
}

package com.soar.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gaofei on 2016/12/26.
 */
public class MusicInfo implements Parcelable {

    private int id;
    private String name; //歌名
    private String artst; //艺术家
    private String album; //专辑
    private String path;
    private int length;
    private String albumImage;

    public MusicInfo() {
    }

    public MusicInfo(int id, String name, String artst, String album, String path, int length, String albumImage) {
        this.id = id;
        this.name = name;
        this.artst = artst;
        this.album = album;
        this.path = path;
        this.length = length;
        this.albumImage = albumImage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtst() {
        return artst;
    }

    public void setArtst(String artst) {
        this.artst = artst;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(artst);
        dest.writeString(album);
        dest.writeString(path);
        dest.writeInt(length);
        dest.writeString(albumImage);
    }

    /**
     * 必须用 public static final 修饰符
     * 对象必须用 CREATOR
     */
    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {

        @Override
        public MusicInfo createFromParcel(Parcel source) {
            MusicInfo music = new MusicInfo();
            music.setId(source.readInt());
            music.setName(source.readString());
            music.setArtst(source.readString());
            music.setAlbum(source.readString());
            music.setPath(source.readString());
            music.setLength(source.readInt());
            music.setAlbumImage(source.readString());

            return music;
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }

    };
}

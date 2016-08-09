package sinia.com.linkfarmnew.utils.album.bean;

import java.util.ArrayList;

/**
 * Created by xujin on 2016/1/31.
 */
public class VideoBucket {

    private int count = 0;
    private String bucketName;
    private ArrayList<VideoItem> videoList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public ArrayList<VideoItem> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<VideoItem> videoList) {
        this.videoList = videoList;
    }
}

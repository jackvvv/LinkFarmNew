package sinia.com.linkfarmnew.utils.album.bean;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ImageBucket implements Serializable {
	private int count = 0;
	private String bucketName;
	private ArrayList<ImageItem> imageList;

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

	public ArrayList<ImageItem> getImageList() {
		return imageList;
	}

	public void setImageList(ArrayList<ImageItem> imageList) {
		this.imageList = imageList;
	}

}

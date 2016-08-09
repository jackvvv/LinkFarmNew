package sinia.com.linkfarmnew.utils.album.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ImageItem implements Serializable {
	private String imageId;
	private String thumbnailPath;
	private String imagePath;
	private int width;
	private int height;
	private long size;
	private Bitmap bitmap;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Bitmap getBitmap() {
		if (bitmap == null) {
//			bitmap = BitmapOpt.decodeSampledBitmapFromResource(imagePath,
//					800);
		}
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}

package sinia.com.linkfarmnew.utils.album;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import sinia.com.linkfarmnew.base.BaseUtils;
import sinia.com.linkfarmnew.utils.album.bean.ImageBucket;
import sinia.com.linkfarmnew.utils.album.bean.ImageItem;

public class AlbumHelper {
	Context context;
	ContentResolver cr;

	HashMap<String, String> thumbnailList = new HashMap<String, String>();

	List<HashMap<String, String>> albumList = new ArrayList<HashMap<String, String>>();
	HashMap<String, ImageBucket> bucketList = new HashMap<String, ImageBucket>();

	private static AlbumHelper instance;

	private AlbumHelper() {
	}

	public static AlbumHelper getHelper() {
		if (instance == null) {
			instance = new AlbumHelper();
		}
		return instance;
	}

	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
			cr = context.getContentResolver();
		}
	}

	private void getThumbnail() {
		String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID,
				Thumbnails.DATA };
		Cursor cursor = cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection,
				null, null, null);
		getThumbnailColumnData(cursor);
	}

	private void getThumbnailColumnData(Cursor cur) {
		if (cur.moveToFirst()) {
			int _id;
			int image_id;
			String image_path;
			int _idColumn = cur.getColumnIndex(Thumbnails._ID);
			int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
			int dataColumn = cur.getColumnIndex(Thumbnails.DATA);

			do {
				// Get the field values
				_id = cur.getInt(_idColumn);
				image_id = cur.getInt(image_idColumn);
				image_path = cur.getString(dataColumn);

				// Do something with the values.
				// Log.i(TAG, _id + " image_id:" + image_id + " path:"
				// + image_path + "---");
				// HashMap<String, String> hash = new HashMap<String, String>();
				// hash.put("image_id", image_id + "");
				// hash.put("path", image_path);
				// thumbnailList.add(hash);
				thumbnailList.put("" + image_id, image_path);
			} while (cur.moveToNext());
		}
	}

	boolean hasBuildImagesBucketList = false;

	@SuppressLint("InlinedApi")
	void buildImagesBucketList() {
		long startTime = System.currentTimeMillis();

		getThumbnail();

		String columns[] = new String[] { Media._ID, Media.BUCKET_ID,
				Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE,
				Media.SIZE, Media.BUCKET_DISPLAY_NAME, Media.WIDTH,
				Media.HEIGHT };
		Cursor cur = cr.query(Media.EXTERNAL_CONTENT_URI, columns, null, null,
				Media.DATE_MODIFIED + " DESC");
		if (cur.moveToFirst()) {
			int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
			int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
			int photoNameIndex = cur.getColumnIndexOrThrow(Media.DISPLAY_NAME);
			int photoTitleIndex = cur.getColumnIndexOrThrow(Media.TITLE);
			int photoSizeIndex = cur.getColumnIndexOrThrow(Media.SIZE);
			int bucketDisplayNameIndex = cur
					.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
			int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
			int picasaIdIndex = cur.getColumnIndexOrThrow(Media.PICASA_ID);
			int widthIndex = cur.getColumnIndexOrThrow(Media.WIDTH);
			int heightIndex = cur.getColumnIndexOrThrow(Media.HEIGHT);
			int totalNum = cur.getCount();

			do {
				String _id = cur.getString(photoIDIndex);
				String name = cur.getString(photoNameIndex);
				String path = cur.getString(photoPathIndex);
				String title = cur.getString(photoTitleIndex);
				String size = cur.getString(photoSizeIndex);
				String bucketName = cur.getString(bucketDisplayNameIndex);
				String bucketId = cur.getString(bucketIdIndex);
				String picasaId = cur.getString(picasaIdIndex);
				String width = cur.getString(widthIndex);
				String height = cur.getString(heightIndex);

				// 根据规定尺寸来过滤图片
				if (BaseUtils.getIntVal(width, 0) < 100
						|| BaseUtils.getIntVal(height, 0) < 100) {
					continue;
				}

				ImageBucket bucket = bucketList.get(bucketId);
				if (bucket == null) {
					bucket = new ImageBucket();
					bucketList.put(bucketId, bucket);
					bucket.setImageList(new ArrayList<ImageItem>());
					bucket.setBucketName(bucketName);
				}
				bucket.setCount(bucket.getCount() + 1);
				ImageItem imageItem = new ImageItem();
				imageItem.setImageId(_id);
				imageItem.setImagePath(path);
				imageItem.setThumbnailPath(thumbnailList.get(_id));
				imageItem.setHeight(BaseUtils.getIntVal(height, 0));
				imageItem.setWidth(BaseUtils.getIntVal(width, 0));
				imageItem.setSize(BaseUtils.getLongVal(size, 0l));
				bucket.getImageList().add(imageItem);
			} while (cur.moveToNext());
		}

		hasBuildImagesBucketList = true;
		long endTime = System.currentTimeMillis();
	}

	public List<ImageBucket> getImagesBucketList(boolean refresh) {
		if (refresh || (!refresh && !hasBuildImagesBucketList)) {
			thumbnailList.clear();
			bucketList.clear();
			albumList.clear();
			buildImagesBucketList();
		}
		List<ImageBucket> tmpList = new ArrayList<ImageBucket>();
		Iterator<Entry<String, ImageBucket>> itr = bucketList.entrySet()
				.iterator();
		while (itr.hasNext()) {
			Entry<String, ImageBucket> entry = (Entry<String, ImageBucket>) itr
					.next();
			tmpList.add(entry.getValue());
		}
		
		ArrayList<ImageItem> allImageItem = new ArrayList<ImageItem>();
		for (int i = 0; i < tmpList.size(); i++) {
			allImageItem.addAll(tmpList.get(i).getImageList());
		}

		return tmpList;
	}
}

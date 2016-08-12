package sinia.com.linkfarmnew.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyImageFolderAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Bimp;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.PhotoView;
import sinia.com.linkfarmnew.utils.ViewPagerFixed;

/***
 * 预览图片
 */
public class CurriculumGalleryActivity extends BaseActivity implements
		OnClickListener {
	private Intent intent;

	// 返回按钮
	private RelativeLayout back_layout;

	private TextView tv_img_num;

	// 发送按钮
	private Button send_bt;

	// 删除按钮
	private Button del_bt;

	// 顶部显示预览图片位置的textview
	private TextView positionTextView;

	// 获取前一个activity传过来的position
	private int position;

	// 当前的位置
	private int location = 0;

	private ViewPagerFixed pager;

	private MyPageAdapter adapter;

	private ArrayList<View> listViews = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.curriculum_camera_gallery);
		initViews();
	}

	private void initViews() {
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		back(back_layout);
		tv_img_num = (TextView) findViewById(R.id.tv_img_num);
		del_bt = (Button) findViewById(R.id.gallery_del);
		del_bt.setOnClickListener(this);
		intent = getIntent();
		position = Integer.parseInt(intent.getStringExtra("position"));
		pager = (ViewPagerFixed) findViewById(R.id.gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < MyImageFolderAdapter.mSelectedImage.size(); i++) {
			initListViews(MyImageFolderAdapter.mSelectedImage.get(i));
		}
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin((int) getResources().getDimensionPixelOffset(
				R.dimen.viewpager_margen));
		int id = intent.getIntExtra("ID", 0);
		pager.setCurrentItem(id);
	}

	private void initListViews(String imgPath) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		BitmapUtilsHelp.getImage(this).display(img, imgPath);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}

	class MyPageAdapter extends PagerAdapter {

		private ArrayList<View> listViews;

		private int size;

		public MyPageAdapter(ArrayList<View> listViews) {
			super();
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		public void setListViews(ArrayList<View> listViews) {
			this.listViews = listViews;
			size = listViews == null ? 0 : listViews.size();
		}

		@Override
		public int getCount() {
			return size;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public Object instantiateItem(View arg0, int arg1) {
			try {
				((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);
			} catch (Exception e) {
			}
			return listViews.get(arg1 % size);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPagerFixed) container).removeView(listViews.get(position
					% size));
		}

		public void finishUpdate(View arg0) {
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
			tv_img_num.setText(arg0 + 1 + "/3");
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gallery_del:
			if (listViews.size() == 1) {
				MyImageFolderAdapter.mSelectedImage.clear();
				Bimp.max = 0;
				tv_img_num.setText(MyImageFolderAdapter.mSelectedImage.size()
						+ "/3");
				Intent intent = new Intent("data.broadcast.action");
				sendBroadcast(intent);
				ActivityManager.getInstance().finishCurrentActivity();
			} else {
				MyImageFolderAdapter.mSelectedImage.remove(location);
				Bimp.max--;
				pager.removeAllViews();
				listViews.remove(location);
				adapter.setListViews(listViews);
				tv_img_num.setText(MyImageFolderAdapter.mSelectedImage.size()
						+ "/3");
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

}

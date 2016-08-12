package sinia.com.linkfarmnew.activity;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyImageFolderAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.PhotoView;
import sinia.com.linkfarmnew.utils.ViewPagerFixed;

public class CurriculumCheckImgActivity extends BaseActivity {
	// 返回按钮
	private RelativeLayout back_layout, title;

	private TextView tv_img_num, tv_img_complete;

	private String currentPath = "";

	// 完成按钮
	private Button send_bt;

	private CheckBox check_select1;
	private CheckBox check_select2;
	private CheckBox check_select3;

	// 当前的位置
	private int location = 0;

	private ViewPagerFixed pager;

	private MyPageAdapter adapter;

	private ArrayList<View> listViews = null;

	private int position1;
	private int position2;
	private int position3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.curriculum_select_gallery);
		initViews();
		setTintColor();
		position3 = position1 = position2 = 0;
		currentPath = MyImageFolderAdapter.mSelectedImage.get(0);
		for (int i = 0; i < MyImageFolderAdapter.mSelectedImage.size(); i++) {
			if (i == 0)
				position1 = 1;
			else if (i == 1)
				position2 = 1;
			else if (i == 2)
				position3 = 1;
		}
	}

	private void setTintColor() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			if (true) {
				winParams.flags |= bits;
			} else {
				winParams.flags &= ~bits;
			}
			win.setAttributes(winParams);
		}

		int stateHeight = AppInfoUtil.getStateHeight(this);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, AppInfoUtil.dip2px(this, 48)
						+ stateHeight);
		title.setLayoutParams(layoutParams);
	}

	private void initViews() {
		back_layout = (RelativeLayout) findViewById(R.id.back_layout);
		title = (RelativeLayout) findViewById(R.id.title);
		back(back_layout);
		tv_img_num = (TextView) findViewById(R.id.tv_img_num);
		tv_img_complete = (TextView) findViewById(R.id.tv_img_complete);
		tv_img_complete.setText("完成("
				+ MyImageFolderAdapter.mSelectedImage.size() + "/3)");
		pager = (ViewPagerFixed) findViewById(R.id.gallery01);
		pager.setOnPageChangeListener(pageChangeListener);
		for (int i = 0; i < MyImageFolderAdapter.mSelectedImage.size(); i++) {
			initListViews(MyImageFolderAdapter.mSelectedImage.get(i));
		}
		adapter = new MyPageAdapter(listViews);
		pager.setAdapter(adapter);
		pager.setPageMargin((int) getResources().getDimensionPixelOffset(
				R.dimen.viewpager_margen));
		check_select1 = (CheckBox) findViewById(R.id.check_select1);
		check_select1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					position1 = 1;
					tv_img_complete.setText("完成("
							+ (position1 + position2 + position3) + "/3)");
					tv_img_complete
							.setBackgroundResource(R.drawable.btn_chooseimg_com);

				} else {
					position1 = 0;
					tv_img_complete.setText("完成("
							+ (position1 + position2 + position3) + "/3)");
				}
			}
		});
		check_select2 = (CheckBox) findViewById(R.id.check_select2);
		check_select2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					position2 = 1;
					tv_img_complete.setText("完成("
							+ (position1 + position2 + position3) + "/3)");
					tv_img_complete
							.setBackgroundResource(R.drawable.btn_chooseimg_com);

				} else {
					position2 = 0;
					tv_img_complete.setText("完成("
							+ (position1 + position2 + position3) + "/3)");
				}
			}
		});
		check_select3 = (CheckBox) findViewById(R.id.check_select3);
		check_select3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					position3 = 1;
					tv_img_complete.setText("完成("
							+ (position1 + position2 + position3) + "/3)");
					tv_img_complete
							.setBackgroundResource(R.drawable.btn_chooseimg_com);

				} else {
					position3 = 0;
					tv_img_complete.setText("完成("
							+ (position1 + position2 + position3) + "/3)");
				}
			}
		});
		tv_img_complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (position1 == 0)
				// MyImageFolderAdapter.mSelectedImage.remove(0);
				// if (position2 == 0)
				// MyImageFolderAdapter.mSelectedImage.remove(1);
				// if (position3 == 0)
				// MyImageFolderAdapter.mSelectedImage.remove(2);
				ActivityManager.getInstance().finishCurrentActivity(
						CurriculumCheckImgActivity.this);
				// startActivityForNoIntent(CurriculumAddCommentActivity.class);
			}
		});
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

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			location = arg0;
			tv_img_num.setText(arg0 + 1 + "/3");
			currentPath = MyImageFolderAdapter.mSelectedImage.get(arg0);
			if (0 == arg0) {
				check_select1.setVisibility(View.VISIBLE);
				check_select2.setVisibility(View.INVISIBLE);
				check_select3.setVisibility(View.INVISIBLE);
			} else if (1 == arg0) {
				check_select1.setVisibility(View.INVISIBLE);
				check_select2.setVisibility(View.VISIBLE);
				check_select3.setVisibility(View.INVISIBLE);
			} else if (2 == arg0) {
				check_select1.setVisibility(View.INVISIBLE);
				check_select2.setVisibility(View.INVISIBLE);
				check_select3.setVisibility(View.VISIBLE);
			}

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	};

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

}

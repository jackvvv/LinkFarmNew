package sinia.com.linkfarmnew.activity;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;

import com.polites.android.GestureImageView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PreViewImageActivity extends BaseActivity {

	@Bind(R.id.big_img)
	GestureImageView big_img;

	private String imgUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_big_image);
		ButterKnife.bind(this);
		hideHeadView();
		initData();
	}

	private void initData() {
		imgUrl = getIntent().getStringExtra("picUrl");
		BitmapUtilsHelp.getImage(this, R.drawable.ic_launcher).display(big_img,
				imgUrl);
		big_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ActivityManager.getInstance().finishCurrentActivity();
			}
		});
	}

}

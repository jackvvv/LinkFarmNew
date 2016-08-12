package sinia.com.linkfarmnew.activity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.MyImageFolderAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.ImageFloder;
import sinia.com.linkfarmnew.utils.ListImageDirPopupWindow;

public class CurriculumChooseImgActivity extends BaseActivity implements
        OnClickListener {
    private GridView mGirdView;

    private RelativeLayout mBottomLy, back_layout, title;

    private TextView mChooseDir;

    public static TextView tv_img_numbers, id_look_pic;

    int totalCount = 0;

    private int mScreenHeight;

    /**
     * 所有的图片
     */
    private List<String> mImgs;

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;

    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;

    private MyImageFolderAdapter mAdapter;

    private ListImageDirPopupWindow mListImageDirPopupWindow;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    private Handler mhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // ShowDialog.getInstance().dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.curriculum_choose_img_main);
        // 注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，
        // 再回到该页面时被取消选中的图片仍处于选中状态
        IntentFilter filter = new IntentFilter("data.broadcast.action");
        registerReceiver(broadcastReceiver, filter);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        initViews();
        setTintColor();
        getImages();
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
        back(back_layout);
        tv_img_numbers = (TextView) findViewById(R.id.tv_img_numbers);
        tv_img_numbers.setOnClickListener(this);
        id_look_pic = (TextView) findViewById(R.id.id_look_pic);
        id_look_pic.setOnClickListener(this);
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mChooseDir.setOnClickListener(this);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
        title = (RelativeLayout) findViewById(R.id.title);
        // MyImageFolderAdapter.mSelectedImage.clear();
        // tv_img_numbers.setText("完成");
        // tv_img_numbers.setBackgroundResource(R.drawable.btn_chooseimg_no);
        // tv_img_numbers.setClickable(false);
        // id_look_pic.setClickable(false);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        // ShowDialog.getInstance().showActivityAnimation(this,
        // "努力加载中，请稍后(^_^)");
        new Thread(new Runnable() {

            @Override
            public void run() {
                String firstImage = null;
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    int picSize = parentFile.list(new FilenameFilter() {

                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;
                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);
                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                mhandler.sendEmptyMessage(0x110);
            }
        }).start();
    }

    private void data2View() {
        if (mImgDir == null) {
            showToast("手机中没有图片");
            return;
        }
        mImgs = Arrays.asList(mImgDir.list());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new MyImageFolderAdapter(this, mImgs,
                R.layout.curriculum_gridimg_item, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
        if (MyImageFolderAdapter.mSelectedImage.size() > 0) {
            id_look_pic.setClickable(true);
        } else {
            id_look_pic.setClickable(false);
        }
        // mImageCount.setText(totalCount + "张");
    }

    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(this).inflate(
                R.layout.curriculum_img_listdir, null));
        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow
                .setOnImageDirSelected(new ListImageDirPopupWindow.OnImageDirSelected() {

                    @Override
                    public void selected(ImageFloder floder) {
                        mImgDir = new File(floder.getDir());
                        mImgs = Arrays.asList(mImgDir
                                .list(new FilenameFilter() {

                                    @Override
                                    public boolean accept(File dir,
                                                          String filename) {
                                        if (filename.endsWith(".jpg")
                                                || filename.endsWith(".png")
                                                || filename.endsWith(".jpeg"))
                                            return true;
                                        return false;
                                    }
                                }));
                        /**
                         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
                         */
                        mAdapter = new MyImageFolderAdapter(
                                CurriculumChooseImgActivity.this, mImgs,
                                R.layout.curriculum_gridimg_item, mImgDir
                                .getAbsolutePath());
                        // MyImageFolderAdapter.mSelectedImage.clear();
                        // tv_img_numbers.setText("完成");
                        // tv_img_numbers
                        // .setBackgroundResource(R.drawable.btn_chooseimg_no);
                        // tv_img_numbers.setClickable(false);
                        // id_look_pic.setClickable(false);
                        mGirdView.setAdapter(mAdapter);
                        // mImageCount.setText(floder.getCount() + "张");
                        mChooseDir.setText(floder.getName());
                        mListImageDirPopupWindow.dismiss();
                    }
                });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_choose_dir:
                mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
                break;
            case R.id.tv_img_numbers:
                // startActivityForNoIntent(CurriculumAddCommentActivity.class);
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.id_look_pic:
                // 预览选择界面
                startActivityForNoIntent(CurriculumCheckImgActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}

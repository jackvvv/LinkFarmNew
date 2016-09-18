package sinia.com.linkfarmnew.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog;
import sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialogUtils;
import sinia.com.linkfarmnew.adapter.MyImageFolderAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.LoginBean;
import sinia.com.linkfarmnew.bean.RefreshBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.CacheUtils;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.PictureUtil;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.view.CircleImageView;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class PersonalCenterActivty extends BaseActivity {

    @Bind(R.id.img_head)
    CircleImageView imgHead;
    @Bind(R.id.tv_changeimg)
    TextView tvChangeimg;
    @Bind(R.id.et_name)
    TextView etName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.rl_sex)
    RelativeLayout rlSex;

    private AsyncHttpClient client = new AsyncHttpClient();
    private String imgPath, dateTime, imageUrl;
    private String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center, "个人中心");
        getDoingView().setVisibility(View.GONE);
        Bmob.initialize(this, Constants.BMOB_KEY);
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "1");
        Log.i("tag", Constants.BASE_URL + "refresh&" + params);
        client.post(Constants.BASE_URL + "refresh", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    RefreshBean bean = gson.fromJson(s, RefreshBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        BitmapUtilsHelp.getImage(PersonalCenterActivty.this, R.drawable
                                .ic_launcher).display(imgHead, bean.getImageUrl());
                        imageUrl = bean.getImageUrl();
                        etName.setText(bean.getNickName());
                        tvSex.setText(bean.getSex());
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    } else {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_changeimg, R.id.rl_sex, R.id.rl_workplace})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_changeimg:
                selectHeadImage();
                break;
            case R.id.rl_sex:
                createSexDialog(this, tvSex);
                break;
            case R.id.rl_workplace:
                Intent intent = new Intent(PersonalCenterActivty.this, ChangeNameActivity.class);
                intent.putExtra("img", imageUrl);
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("sex", tvSex.getText().toString());
                startActivityForResult(intent, 100);
                break;
        }
    }

    private void changeInfo(String img, String sex) {
        showLoad("修改中...");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "1");
        params.put("content", "-1");
        try {
            if (StringUtil.isEmpty(etName.getText().toString().trim())) {
                params.put("name", "-1");
            } else {
                params.put("name", URLEncoder.encode(etName.getText().toString().trim(), "UTF-8"));
            }
            if (StringUtil.isEmpty(img)) {
                params.put("image", "-1");
            } else {
                params.put("image", img);
            }
            if (StringUtil.isEmpty(sex)) {
                params.put("sex", URLEncoder.encode("男", "UTF-8"));
            } else {
                params.put("sex", URLEncoder.encode(sex, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("tag", Constants.BASE_URL + "updateInfo&" + params);
        client.post(Constants.BASE_URL + "updateInfo", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("修改成功");
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("修改失败");
                    } else {
                        showToast("修改失败");
                    }
                }
            }
        });
    }

    private void createSexDialog(Context context, final TextView tv) {
        new sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.SheetItemColor.BLACK,
                        new sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("男");
                                changeInfo(imageUrl, "男");
                            }
                        })
                .addSheetItem("女", sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.SheetItemColor.BLACK,
                        new sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("女");
                                changeInfo(imageUrl, "女");
                            }
                        }).show();
    }

    private void selectHeadImage() {
        new ActionSheetDialog(this)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Date date1 = new Date(System
                                        .currentTimeMillis());
                                dateTime = date1.getTime() + "";
                                getAvataFromCamera();
                            }
                        })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);
                            }
                        }).show();
    }

    protected void getAvataFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(this, true,
                "icon") + dateTime + "avatar.jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 100:
                    String name = data.getStringExtra("name");
                    etName.setText(name);
                    break;
                case 1:
                    String files = CacheUtils.getCacheDirectory(this,
                            true, "icon") + dateTime + "avatar.jpg";
                    //裁剪
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    }
                    //不裁剪
//                    if (files != null) {
//                        int degree = readPictureDegree(files);
//                        /**
//                         * 把图片旋转为正的方向
//                         */
//                        Bitmap temp = PictureUtil.getSmallBitmap(files);
//                        Bitmap newbitmap = rotaingImageView(degree, temp);
//                        imgPath = saveToSdCard(newbitmap);
//                        imgHead.setImageBitmap(newbitmap);
//                        updateIcon(imgPath);
//                    }
                    break;
                case 2:
                    //裁剪
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    //不裁剪
//                    if (data != null) {
////                        Bundle extras = data.getExtras();
////                        if (extras != null) {
////                            Bitmap bitmap = extras.getParcelable("data");
////                            imgPath = saveToSdCard(bitmap);
////                            Log.i("tag", "iconUrl---" + imgPath);
////                            imgHead.setImageBitmap(bitmap);
////                            updateIcon(imgPath);
////                        }
//                        Uri uri = data.getData();
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                            imgHead.setImageBitmap(bitmap);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        ContentResolver cr = this.getContentResolver();
//                        Cursor c = cr.query(uri, null, null, null, null);
//                        c.moveToFirst();
//                        imgPath = c.getString(c.getColumnIndex("_data"));
//                        updateIcon(imgPath);
//                    }

                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            imgPath = saveToSdCard(bitmap);
                            Log.i("tag", "iconUrl---" + imgPath);
                            imgHead.setImageBitmap(bitmap);
                            updateIcon(imgPath);
                        }
                    }
                    break;
            }
        }
    }

    private void updateIcon(String avataPath) {
        if (avataPath != null) {
            showLoad("正在上传头像");
            final BmobFile file = new BmobFile(new File(avataPath));
            file.upload(this, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    dismiss();
                    imgUrl = file.getFileUrl(PersonalCenterActivty.this);
                    // showToast("图片上传成功");
                    changeInfo(imgUrl, tvSex.getText().toString());
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    Log.i("tag", "图片上传失败" + arg1);
                    dismiss();
                }
            });
        }
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils
                .getCacheDirectory(this, true, "icon")
                + dateTime
                + "_11.jpg";
        File file = new File(files);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 黑边
        intent.putExtra("scaleUpIfNeeded", true);// 黑边
        intent.putExtra("return-data", true);// 选择返回数据
        startActivityForResult(intent, 3);
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

package sinia.com.linkfarmnew.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.ValidateCodeBean;
import sinia.com.linkfarmnew.myinterface.OnLoginListener;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.CacheUtils;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.PictureUtil;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.StringUtils;
import sinia.com.linkfarmnew.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class RegisterActivity extends BaseActivity implements AMapLocationListener {

    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    @Pattern(regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message =
            "请输入正确的手机号码")
    @Order(1)
    @Bind(R.id.et_phone)
    EditText etPhone;
    @NotEmpty(message = "请输入验证码")
    @Order(2)
    @Bind(R.id.et_code)
    EditText etCode;
    @Password(sequence = 1, message = "请输入密码")
    @Order(3)
    @Bind(R.id.et_password)
    EditText etPassword;
    @NotEmpty(message = "请输入公司名称")
    @Order(5)
    @Bind(R.id.et_company_name)
    EditText etCompanyName;
    @NotEmpty(message = "请输入公司地址")
    @Order(6)
    @Bind(R.id.et_company_address)
    EditText etCompanyAddress;
    @NotEmpty(message = "请输入联系人姓名")
    @Order(7)
    @Bind(R.id.et_contact_name)
    EditText etContactName;
    @Bind(R.id.et_recommend_code)
    EditText etRecommendCode;
    @Bind(R.id.iv_add)
    ImageView ivAdd;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Order(4)
    @ConfirmPassword(message = "两次输入的密码不一致，请重新输入")
    @Bind(R.id.et_confirm)
    EditText etConfirm;
    @Bind(R.id.tv_bind)
    TextView tvBind;
    @Bind(R.id.tv_protecol)
    TextView tvProtecol;
    private Validator validator;
    private LocationManagerProxy mLocationManagerProxy;
    private int i = 60;
    private String imgPath, dateTime, code;
    private String imgUrl = "";
    private AsyncHttpClient client = new AsyncHttpClient();
    private String city = "南京";
    private String thirdId, userIcon;
    private boolean isThridRegister;

    private static final int INTENT_ACTION_PICTURE = 0;
    private static final int INTENT_ACTION_CAREMA = 1;
    private static final int INTENT_ACTION_CROP = 2;
    private static final int LOAD_USER_ICON = 3;

    private static final String PICTURE_NAME = "UserIcon.jpg";

    private static OnLoginListener tmpRegisterListener;
    private static String tmpPlatform;

    private OnLoginListener registerListener;
    private Platform platform;
    private String platType;//1.QQ,2.WEXIN,3.WEIBO

    public static final void setOnLoginListener(OnLoginListener login) {
        tmpRegisterListener = login;
    }

    public static final void setPlatform(String platName) {
        tmpPlatform = platName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register, "注册");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        Bmob.initialize(this, Constants.BMOB_KEY);
        validator = new Validator(this);
        location();
        initView();
    }

    private void location() {
        platform = ShareSDK.getPlatform(tmpPlatform);
        tmpRegisterListener = null;
        tmpPlatform = null;

        if (platform != null) {
            thirdId = platform.getDb().getUserId();
            if ("QQ".equals(platform.getName())) {
                platType = "1";
            }
            if ("Wechat".equals(platform.getName())) {
                platType = "2";
            }
            if ("SinaWeibo".equals(platform.getName())) {
                platType = "3";
            }
        }
        isThridRegister = getIntent().getBooleanExtra("isThridRegister", false);
        if (isThridRegister) {
            tvBind.setVisibility(View.VISIBLE);
        } else {
            tvBind.setVisibility(View.GONE);
        }
//        thirdId = getIntent().getStringExtra("thirdId");
//        userIcon = getIntent().getStringExtra("userIcon");

        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(true);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
    }

    private void initView() {
        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                if (!etCode.getEditableText().toString().trim().equals(code)) {
                    showToast("验证码不正确");
                    return;
                }
                if (StringUtil.isEmpty(imgUrl)) {
                    showToast("请上传营业执照");
                    return;
                }
                register();
            }
        });
    }

    private void register() {
        showLoad("注册中...");
        RequestParams params = new RequestParams();
        params.put("telephone", etPhone.getEditableText().toString().trim());
        params.put("password", etPassword.getEditableText().toString().trim());
        params.put("company", etCompanyName.getEditableText().toString().trim());
        params.put("address", etCompanyAddress.getEditableText().toString().trim());
        params.put("name", etContactName.getEditableText().toString().trim());
        params.put("city", city);
        if (StringUtil.isEmpty(etRecommendCode.getEditableText().toString().trim())) {
            params.put("content", "-1");
        } else {
            params.put("content", etRecommendCode.getEditableText().toString().trim());
        }
        params.put("image", imgUrl);
        params.put("userId", "-1");
        params.put("type", "1");
        params.put("choose", "-1");
        Log.i("tag", Constants.BASE_URL + "register&" + params);
        client.post(Constants.BASE_URL + "register", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("注册成功");
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("注册失败");
                    } else {
                        showToast("邀请码输入错误，请重新输入");
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_get_code, R.id.tv_submit, R.id.iv_add, R.id.tv_bind, R.id.tv_protecol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (!StringUtils.isMobileNumber(etPhone.getEditableText().toString().trim())) {
                    showToast("请输入正确的手机号码");
                } else {
                    tvGetCode.setClickable(false);
                    tvGetCode.setText("重新发送(" + i + ")");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (; i > 0; i--) {
                                handler.sendEmptyMessage(-9);
                                if (i <= 0) {
                                    break;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(-8);
                        }
                    }).start();
                    getCode();
                }
                break;
            case R.id.tv_submit:
                validator.validate();
                break;
            case R.id.iv_add:
                selectHeadImage();
                break;
            case R.id.tv_bind:
                Intent intent = new Intent();
                intent.putExtra("platType", platType);
                intent.putExtra("thirdId", thirdId);
                startActivityForIntent(BindActivity.class, intent);
//                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.tv_protecol:
                startActivityForNoIntent(ProtecolActivity.class);
                break;
        }
    }

    private void getCode() {
        showLoad("正在发送短信...");
        RequestParams params = new RequestParams();
        params.put("telephone", etPhone.getEditableText().toString().trim());
        params.put("type", "1");
        params.put("choose", "1");
        Log.i("tag", Constants.BASE_URL + "gainValidateCode&" + params);
        client.post(Constants.BASE_URL + "gainValidateCode", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    ValidateCodeBean bean = gson.fromJson(s, ValidateCodeBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        showToast("验证码已发送");
                        code = bean.getValidateCode();
                        showToast(code);
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("该手机号已经被注册");
                    } else {
                        showToast("验证码发送失败");
                    }
                }
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -9) {
                tvGetCode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                tvGetCode.setText("获取验证码");
                tvGetCode.setClickable(true);
                i = 60;
            }
        }
    };

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
                case 1:
                    String files = CacheUtils.getCacheDirectory(this,
                            true, "icon") + dateTime + "avatar.jpg";
//                    File file = new File(files);
//                    if (file.exists() && file.length() > 0) {
//                        Uri uri = Uri.fromFile(file);
//                        startPhotoZoom(uri);
//                    }
                    if (files != null) {
                        int degree = readPictureDegree(files);
                        /**
                         * 把图片旋转为正的方向
                         */
                        Bitmap temp = PictureUtil.getSmallBitmap(files);
                        Bitmap newbitmap = rotaingImageView(degree, temp);
                        imgPath = saveToSdCard(newbitmap);
                        ivAdd.setImageBitmap(newbitmap);
                        updateIcon(imgPath);
                    }
                    break;
                case 2:
//                    if (data == null) {
//                        return;
//                    }
//                    startPhotoZoom(data.getData());
                    //不裁剪
                    if (data != null) {
//                        Bundle extras = data.getExtras();
//                        if (extras != null) {
//                            Bitmap bitmap = extras.getParcelable("data");
//                            imgPath = saveToSdCard(bitmap);
//                            Log.i("tag", "iconUrl---" + imgPath);
//                            imgHead.setImageBitmap(bitmap);
//                            updateIcon(imgPath);
//                        }
                        Uri uri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            ivAdd.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ContentResolver cr = this.getContentResolver();
                        Cursor c = cr.query(uri, null, null, null, null);
                        c.moveToFirst();
                        imgPath = c.getString(c.getColumnIndex("_data"));
                        updateIcon(imgPath);
                    }
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            imgPath = saveToSdCard(bitmap);
                            Log.i("lamp", "iconUrl---" + imgPath);
                            ivAdd.setImageBitmap(bitmap);
                            updateIcon(imgPath);
                        }
                    }
                    break;
            }
        }
    }

    private void updateIcon(String avataPath) {
        if (avataPath != null) {
            showLoad("正在上传营业执照");
            final BmobFile file = new BmobFile(new File(avataPath));
            file.upload(this, new UploadFileListener() {

                @Override
                public void onSuccess() {
                    dismiss();
                    imgUrl = file.getFileUrl(RegisterActivity.this);
                    showToast("图片上传成功");
                }

                @Override
                public void onFailure(int arg0, String arg1) {
                    Log.i("tag", "图片上传失败错误码：" + arg0 + "----" + arg1);
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation.getCity()) {
            city = aMapLocation.getCity().split("市")[0];
            if (!StringUtil.isEmpty(city)) {
            } else {
                city = "南京";
            }
        } else {
            city = "南京";
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mLocationManagerProxy) {
            // 移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mLocationManagerProxy) {
            // 移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
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

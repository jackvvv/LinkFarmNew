package sinia.com.linkfarmnew.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog;
import sinia.com.linkfarmnew.adapter.MyImageFolderAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.Bimp;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.CacheUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/11.
 */
public class ServiceCommentActivity extends BaseActivity {

    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tv_good)
    TextView tvGood;
    @Bind(R.id.tv_zhong)
    TextView tvZhong;
    @Bind(R.id.tv_bad)
    TextView tvBad;
    @NotEmpty(message = "请输入评价内容")
    @Order(1)
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.gridView)
    GridView gridView;

    public static Bitmap bimap;
    private GridAdapter adapter;
    private String dateTime;
    public int screenWidth = 0;
    public int screenHeight = 0;
    private String savePath = "";
    private Validator mValidator;
    private List<String> tempList = new LinkedList<String>();
    private List<String> imgUrlList = new LinkedList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_comment, "服务评价");
        ButterKnife.bind(this);
        mValidator = new Validator(this);
        getDoingView().setText("提交");
        initView();
    }

    private void initView() {
        tvGood.setSelected(true);
        tvZhong.setSelected(false);
        tvBad.setSelected(false);
        adapter = new GridAdapter(this);
        MyImageFolderAdapter.mSelectedImage.clear();
        adapter.update();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // 如果 已选择的图片有5张了，点击图片，删除图片
                if (MyImageFolderAdapter.mSelectedImage.size() == 5) {
                    MyImageFolderAdapter.mSelectedImage.remove(arg2);
                    adapter.update();
                } else {
                    // 点击 选择图片
                    if (arg2 == 0) {
                        new ActionSheetDialog(ServiceCommentActivity.this)
                                .builder()
                                .setCancelable(true)
                                .setCanceledOnTouchOutside(true)
                                .addSheetItem("拍照选择", ActionSheetDialog.SheetItemColor.THEME_COLOR,
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(int which) {
                                                Date date1 = new Date(System
                                                        .currentTimeMillis());
                                                dateTime = date1.getTime() + "";
                                                getAvataFromCamera();
                                            }
                                        })
                                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.THEME_COLOR,
                                        new ActionSheetDialog.OnSheetItemClickListener() {
                                            @Override
                                            public void onClick(int which) {
                                                // startActivityForNoIntent(CurriculumChooseImgActivity.class);
                                                Intent intent = new Intent();
                                                intent.setAction(Intent.ACTION_PICK);
                                                intent.setType("image/*");
                                                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                startActivityForResult(intent,
                                                        2);
                                            }
                                        }).show();
                    } else {
                        MyImageFolderAdapter.mSelectedImage.remove(arg2 - 1);
                        adapter.update();
                    }
                }
            }
        });
    }

    private void getAvataFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(this, true, "icon")
                + dateTime + "avatar.jpg");
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

    private Uri imgUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 相机
                case 1:
                    if (MyImageFolderAdapter.mSelectedImage.size() < 3
                            && resultCode == -1) {
                        String files = CacheUtils.getCacheDirectory(this, true,
                                "icon") + dateTime + "avatar.jpg";
                        File file = new File(files);
                        if (file.exists() && file.length() > 0) {
                            // Uri uri = Uri.fromFile(file);
                            imgUri = Uri.fromFile(file);
                            startPhotoZoom(imgUri, 3);
                        }
                        // if (files != null) {
                        // int degree = readPictureDegree(files);
                        // /**
                        // * 把图片旋转为正的方向
                        // */
                        // Bitmap temp = PictureUtil.getSmallBitmap(files);
                        // Bitmap newbitmap = rotaingImageView(degree, temp);
                        // saveBit(newbitmap);
                        // MyImageFolderAdapter.mSelectedImage.add(savePath);
                        // adapter.update();
                        // }
                    }
                    break;
                // 相册
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData(), 4);
                    break;
                // 相机
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            // Bitmap bitmap = extras.getParcelable("data");
                            Bitmap bitmap = decodeUriAsBitmap(imgUri);
                            saveBit(bitmap);
                            MyImageFolderAdapter.mSelectedImage.add(savePath);
                            adapter.update();
                        }
                    }
                    break;
                // 相册
                case 4:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            saveBit(bitmap);
                            MyImageFolderAdapter.mSelectedImage.add(savePath);
                            adapter.update();
                        }
                    }
                    break;
            }
        }
    }


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private void startPhotoZoom(Uri data, int flag) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        if (flag == 3) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, data);
            // 开始return-data设置了true的话直接返回bitmap，可能会很占内存
            intent.putExtra("return-data", false);
        } else if (flag == 4) {
            intent.putExtra("return-data", true);
        }
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, flag);
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
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
    protected void onResume() {
        super.onResume();
        adapter.update();
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        private int selectedPosition = -1;

        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (MyImageFolderAdapter.mSelectedImage.size() == 5) {
                return 5;
            }
            return (MyImageFolderAdapter.mSelectedImage.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Log.i("tag", "-----图片-----" + MyImageFolderAdapter.mSelectedImage);

            if (MyImageFolderAdapter.mSelectedImage.size() < 5) {
                if (position == 0) {
                    holder.image.setImageBitmap(BitmapFactory.decodeResource(
                            getResources(), R.drawable.icon_upload_img));
                } else if (null != MyImageFolderAdapter.mSelectedImage
                        && null != MyImageFolderAdapter.mSelectedImage
                        .get(position - 1)) {
                    try {
                        BitmapUtilsHelp.getImage(ServiceCommentActivity.this)
                                .display(
                                        holder.image,
                                        MyImageFolderAdapter.mSelectedImage
                                                .get(position - 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (null != MyImageFolderAdapter.mSelectedImage) {
                try {
                    BitmapUtilsHelp.getImage(ServiceCommentActivity.this).display(
                            holder.image,
                            MyImageFolderAdapter.mSelectedImage.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return convertView;
        }

        class ViewHolder {
            ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            if (Bimp.max == MyImageFolderAdapter.mSelectedImage.size()) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            } else {
                Bimp.max += 1;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    }

    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void saveBit(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String saveDir = Environment.getExternalStorageDirectory()
                    + "/temple";
            Date date = new Date(System.currentTimeMillis());
            savePath = saveDir + date.getTime() + ".jpg";
        }
        DeleteTempFile(savePath);
        File file = new File(savePath);// 将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void DeleteTempFile(String filePath) {
        File tmpFile = null;
        if (filePath == null) {
            return;
        }
        tmpFile = new File(filePath);
        if (tmpFile != null) {
            tmpFile.deleteOnExit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void doing() {
        super.doing();
//        mValidator.validate();
    }

    @OnClick({R.id.tv_good, R.id.tv_zhong, R.id.tv_bad})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_good:
                tvGood.setSelected(true);
                tvZhong.setSelected(false);
                tvBad.setSelected(false);
                break;
            case R.id.tv_zhong:
                tvGood.setSelected(false);
                tvZhong.setSelected(true);
                tvBad.setSelected(false);
                break;
            case R.id.tv_bad:
                tvGood.setSelected(false);
                tvZhong.setSelected(false);
                tvBad.setSelected(true);
                break;
        }
    }
}

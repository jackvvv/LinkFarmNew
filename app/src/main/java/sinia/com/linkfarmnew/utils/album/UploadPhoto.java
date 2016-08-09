package sinia.com.linkfarmnew.utils.album;//package sinia.com.linkfarmnew.utils.album;
//
//import android.annotation.SuppressLint;
//import android.graphics.Bitmap;
//
//import com.doing.utils.album.bean.UploadImgEntity;
//import com.doing.utils.base.FileUtils;
//import com.doing.utils.base.GsonUtils;
//import com.doing.utils.base.JodaUtils;
//import com.doing.utils.constant.Constants;
//import com.doing.utils.constant.HttpConstant;
//import com.doing.utils.image.BitmapOpt;
//import com.doing.utils.net.HttpRestClient;
//import com.doing.utils.net.HttpRestClient.ResponseListener;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Date;
//
//public class UploadPhoto {
//
//    public interface onUploadPhotoSuccessListener {
//        public void uploadSuccess(UploadImgEntity entity);
//    }
//
//    private static Bitmap uploadBitmap;
//
//    @SuppressLint("NewApi")
//    public static void setPhoto(final String imgPath, final onUploadPhotoSuccessListener listener) {
//        FileUtils fileUtils = new FileUtils();
//        if (fileUtils.isFile(imgPath)) {
//            String tmpPhotoFilePath = fileUtils.getSDPATH()
//                    + Constants.TAG + "/imgs/tmp/tmp.jpg";
//            final File tmpPhotoFile = new File(tmpPhotoFilePath);
//            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                 FileOutputStream os = new FileOutputStream(tmpPhotoFile);) {
//                uploadBitmap = BitmapOpt.decodeSampledBitmapFromResource(
//                        imgPath, 2000);
//                uploadBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
//                baos.writeTo(os);
//
//                HttpRestClient.getInstance().postimg(
//                        HttpConstant.uploadimgUrl,
//                        tmpPhotoFilePath,
//                        "img_"
//                                + JodaUtils.format(new Date(),
//                                "yyyyMMddHhmmssSSS") + ".jpg",
//                        new ResponseListener<String>("上传失败") {
//
//                            @Override
//                            protected void onProcess(String t) {
//                                // TODO Auto-generated method stub
//                                UploadImgEntity entity = GsonUtils.fromJson(t,
//                                        UploadImgEntity.class);
//                                listener.uploadSuccess(entity);
//                            }
//
//                            @Override
//                            protected void onFinish() {
//                                if (tmpPhotoFile.isFile()) {
//                                    tmpPhotoFile.delete();
//                                }
//                                uploadBitmap.recycle();
//                            }
//
//                            @Override
//                            protected void onProgress(long bytesWritten, long totalSize) {
//
//                            }
//                        });
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//}

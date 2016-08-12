package sinia.com.linkfarmnew.adapter;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.CurriculumChooseImgActivity;

public class MyImageFolderAdapter extends CommonAdapter<String> {
    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage = new LinkedList<String>();

    /**
     * 文件夹路径
     */
    private String mDirPath;

    private Context context;

    public MyImageFolderAdapter(Context context, List<String> mDatas,
                                int itemLayoutId, String dirPath) {
        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, final String item) {
        helper.setImageResource(R.id.id_item_image,
                R.drawable.ic_launcher);
        helper.setImageResource(R.id.id_item_select,
                R.drawable.picture_unselected);
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);
        mImageView.setColorFilter(null);
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.drawable.pictures_selected);
            // CurriculumChooseImgActivity.tv_img_numbers.setText("完成("
            // + mSelectedImage.size() + "/3)");
            CurriculumChooseImgActivity.tv_img_numbers.setText("完成");
        }
        if (mSelectedImage.size() == 0) {
            CurriculumChooseImgActivity.tv_img_numbers.setClickable(false);
            CurriculumChooseImgActivity.id_look_pic.setClickable(false);
        }
        // 设置ImageView的点击事件
        mImageView.setOnClickListener(new OnClickListener() {
            // 选择，则将图片变暗，反之则反之
            @Override
            public void onClick(View v) {
                // 已经选择过该图片
                if (mSelectedImage.contains(mDirPath + "/" + item)) {
                    mSelectedImage.remove(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.picture_unselected);
                    mImageView.setColorFilter(null);
                    if (mSelectedImage.size() == 0) {
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setText("完成");
                        // CurriculumChooseImgActivity.tv_img_numbers
                        // .setBackgroundResource(R.drawable.btn_chooseimg_no);
                        // CurriculumChooseImgActivity.tv_img_numbers
                        // .setClickable(false);
                        // CurriculumChooseImgActivity.id_look_pic
                        // .setClickable(false);
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setClickable(true);
                        CurriculumChooseImgActivity.id_look_pic
                                .setClickable(true);
                    } else if (mSelectedImage.size() <= 3) {
                        // CurriculumChooseImgActivity.tv_img_numbers
                        // .setText("完成(" + mSelectedImage.size() + "/3)");
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setText("完成");
                        // CurriculumChooseImgActivity.tv_img_numbers
                        // .setBackgroundResource(R.drawable.btn_chooseimg_com);
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setClickable(true);
                        CurriculumChooseImgActivity.id_look_pic
                                .setClickable(true);
                    }
                }
                // 未选择该图片
                else if (mSelectedImage.size() < 3) {
                    mSelectedImage.add(mDirPath + "/" + item);
                    mSelect.setImageResource(R.drawable.pictures_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                    if (mSelectedImage.size() == 0) {
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setText("完成");
//						CurriculumChooseImgActivity.tv_img_numbers
//								.setBackgroundResource(R.drawable.btn_chooseimg_no);
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setClickable(true);
                        CurriculumChooseImgActivity.id_look_pic
                                .setClickable(true);
                    } else if (mSelectedImage.size() <= 3) {
                        // CurriculumChooseImgActivity.tv_img_numbers
                        // .setText("完成(" + mSelectedImage.size() + "/3)");
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setText("完成");
//						CurriculumChooseImgActivity.tv_img_numbers
//								.setBackgroundResource(R.drawable.btn_chooseimg_com);
                        CurriculumChooseImgActivity.tv_img_numbers
                                .setClickable(true);
                        CurriculumChooseImgActivity.id_look_pic
                                .setClickable(true);
                    }
                } else {
                    mSelect.setImageResource(R.drawable.picture_unselected);
                    mImageView.setColorFilter(null);
                    Toast.makeText(context, "已超过图片选择上限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.drawable.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }
    }
}

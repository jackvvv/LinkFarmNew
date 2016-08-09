package sinia.com.linkfarmnew.actionsheetdialog;

import android.content.Context;
import android.widget.TextView;

public class ActionSheetDialogUtils {


    public static void createSexDialog(Context context, final TextView tv) {
        new sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.SheetItemColor.BLACK,
                        new sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("男");
                            }
                        })
                .addSheetItem("女", sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.SheetItemColor.BLACK,
                        new sinia.com.linkfarmnew.actionsheetdialog.ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("女");
                            }
                        }).show();
    }

}

package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.RefreshBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;

/**
 * Created by 忧郁的眼神 on 2016/8/15.
 */
public class ChangeNameActivity extends BaseActivity {

    @Bind(R.id.et_nickname)
    EditText et_nickname;
    @Bind(R.id.iv_clear)
    ImageView iv_clear;

    private String imageUrl, sex, name;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name, "修改昵称");
        getDoingView().setText("确定");
        initData();
    }

    private void initData() {
        imageUrl = getIntent().getStringExtra("img");
        sex = getIntent().getStringExtra("sex");
        name = getIntent().getStringExtra("name");
        et_nickname.setText(name);
    }

    @Override
    public void doing() {
        super.doing();
        if (StringUtil.isEmpty(et_nickname.getEditableText().toString().trim())) {
            showToast("请输入昵称");
        } else {
            changeInfo();
        }
    }

    private void changeInfo() {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("type", "1");
        try {
            params.put("name", URLEncoder.encode(et_nickname.getEditableText().toString().trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (StringUtil.isEmpty(imageUrl)) {
            params.put("image", "-1");
        } else {
            params.put("image", imageUrl);
        }
        if (StringUtil.isEmpty(sex)) {
            try {
                params.put("sex", URLEncoder.encode("男", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                params.put("sex", URLEncoder.encode(sex, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
                        showToast("昵称修改成功");
                        Intent intent = new Intent();
                        intent.putExtra("name", et_nickname.getEditableText().toString().trim());
                        setResult(RESULT_OK, intent);
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("修改失败");
                    } else {
                        showToast("修改失败");
                    }
                }
            }
        });
    }

    @OnClick(R.id.iv_clear)
    void clear() {
        et_nickname.setText("");
    }
}

package sinia.com.linkfarmnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.AddOrderBean;
import sinia.com.linkfarmnew.bean.HelpContentBean;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;

/**
 * Created by 忧郁的眼神 on 2016/9/20.
 */
public class HelpDocActivity extends BaseActivity {

    @Bind(R.id.tv_content)
    TextView tvContent;
    private String title;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_help_doc, title);
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        getData();
    }

    private void getData() {
        Log.i("tag", Constants.BASE_URL + "annceAndHelp&");
        client.post(Constants.BASE_URL + "annceAndHelp", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    HelpContentBean bean = gson.fromJson(s, HelpContentBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        if (title.equals("特别声明")) {
                            tvContent.setText(bean.getStatement());
                        } else {
                            tvContent.setText(bean.getUseHelp());
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }
}

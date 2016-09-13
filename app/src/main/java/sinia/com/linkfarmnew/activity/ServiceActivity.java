package sinia.com.linkfarmnew.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.QuestionAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.ServiceBean;
import sinia.com.linkfarmnew.utils.Constants;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class ServiceActivity extends BaseActivity {

    @Bind(R.id.tv_online)
    TextView tvOnline;
    @Bind(R.id.tv_call)
    TextView tvCall;
    @Bind(R.id.listView)
    ListView listView;

    private AsyncHttpClient client = new AsyncHttpClient();
    private MaterialDialog materialDialog;
    private List<ServiceBean.ItemsBean> questionList = new ArrayList<>();
    private QuestionAdapter adapter;
    private String tel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service, "客服");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        getQuestion();
    }

    private void getQuestion() {
        adapter = new QuestionAdapter(this, questionList);
        listView.setAdapter(adapter);

        showLoad("");
        RequestParams params = new RequestParams();
        Log.i("tag", Constants.BASE_URL + "someCall&" + params);
        client.post(Constants.BASE_URL + "someCall", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    ServiceBean bean = gson.fromJson(s, ServiceBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        tel = bean.getTelephone();
                        questionList.clear();
                        questionList.addAll(bean.getItems());
                        adapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ServiceBean.ItemsBean bean = questionList.get(i);
                Intent intent = new Intent();
                intent.putExtra("bean", (Serializable) bean);
                startActivityForIntent(QuetionDetailActivity.class, intent);
            }
        });
    }

    @OnClick({R.id.tv_online, R.id.tv_call})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_online:
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("link", "http://kefu.easemob.com/webim/im.html?tenantId=27590");
                startActivity(intent);
                break;
            case R.id.tv_call:
                callService();
                break;
        }
    }

    private void callService() {
        materialDialog = new MaterialDialog(ServiceActivity.this);
        materialDialog.setTitle("联系客服").setMessage(tel)
                .setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                        if (ActivityCompat.checkSelfPermission(ServiceActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                        materialDialog.dismiss();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        }).show();
    }
}

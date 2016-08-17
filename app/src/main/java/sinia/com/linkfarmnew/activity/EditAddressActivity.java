package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.city.RegionInfo;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.DialogUtils;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.ValidationUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class EditAddressActivity extends BaseActivity {

    @NotEmpty(message = "请输入收货人姓名")
    @Order(1)
    @Bind(R.id.et_name)
    EditText etName;
    @Pattern(regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", message = "请输入正确的手机号码")
    @Order(2)
    @Bind(R.id.et_tel)
    EditText etTel;
    @NotEmpty(message = "请选择所在地区")
    @Order(3)
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @NotEmpty(message = "请输入详细地址")
    @Order(4)
    @Bind(R.id.et_detail)
    EditText etDetail;
    @Bind(R.id.tv_ok)
    TextView tvOk;

    private String addressId, name, tel, area, address;
    private Validator validator;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address, "编辑地址");
        ButterKnife.bind(this);
        getDoingView().setText("删除");
        validator = new Validator(this);
        init();
    }

    private void init() {
        addressId = getIntent().getStringExtra("addressId");
        name = getIntent().getStringExtra("name");
        tel = getIntent().getStringExtra("tel");
        area = getIntent().getStringExtra("area");
        address = getIntent().getStringExtra("address");

        etName.setText(name);
        etTel.setText(tel);
        tvAddress.setText(area);
        etDetail.setText(address);

        validator.setValidationListener(new ValidationUtils.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                super.onValidationSucceeded();
                editAddress();
            }
        });
    }

    private void editAddress() {
        showLoad("");
        RequestParams params = new RequestParams();
        try {
            params.put("userId", MyApplication.getInstance().getStringValue("userId"));
            params.put("otherId", addressId);
            params.put("name", URLEncoder.encode(etName.getEditableText().toString().trim(), "UTF-8"));
            params.put("telephone", etTel.getEditableText().toString().trim());
            params.put("content", URLEncoder.encode(tvAddress.getText().toString().trim(), "UTF-8"));
            params.put("address", URLEncoder.encode(etDetail.getEditableText().toString().trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("tag", Constants.BASE_URL + "addressAddOrUp&" + params);
        client.post(Constants.BASE_URL + "addressAddOrUp", params, new AsyncHttpResponseHandler() {
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
                        showToast("地址修改成功");
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_address, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                DialogUtils.createAddressDialog(EditAddressActivity.this, tvAddress);
                break;
            case R.id.tv_ok:
                validator.validate();
                break;
        }
    }

    @Override
    public void doing() {
        super.doing();
        deleteAddress();
    }

    private void deleteAddress() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", addressId);
        Log.i("tag", Constants.BASE_URL + "delAddress&" + params);
        client.post(Constants.BASE_URL + "delAddress", params, new AsyncHttpResponseHandler() {
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
                        showToast("删除成功");
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }
}

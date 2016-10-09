package sinia.com.linkfarmnew.wxapi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.FillOrderActivity;
import sinia.com.linkfarmnew.activity.MyOrderActivity;
import sinia.com.linkfarmnew.activity.PayActivity;
import sinia.com.linkfarmnew.activity.StandardDialogActivity;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.SharedPreferencesUtils;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.view.loadingview.LoadingView;

public class WXPayEntryActivity extends Activity implements
        IWXAPIEventHandler {

    private IWXAPI api;
    private String orderId, coupleId, norm;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ActivityManager.getInstance().addActivity(this);
        orderId = SharedPreferencesUtils.getShareString(this, "WX_orderid");
        coupleId = SharedPreferencesUtils.getShareString(this, "WX_coupleId");
        norm = SharedPreferencesUtils.getShareString(this, "WX_norm");
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        api.registerApp(Constants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.registerApp(Constants.WX_APPID);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i("tag", "微信回调errCode-------" + resp.errCode);
        Log.i("tag", "微信回调getType()-------" + resp.getType());
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            SharedPreferencesUtils.removeShareValue(this, "WX_orderid");
            SharedPreferencesUtils.removeShareValue(this, "WX_coupleId");
            SharedPreferencesUtils.removeShareValue(this, "WX_norm");
            Log.i("tag", "微信回调-------------" + resp.errCode);
            if (resp.errCode == 0) {
                paySuccess("1");
            } else if (resp.errCode == -1) {
                Log.d("msg",
                        "onPayFinish, errCode = " + resp.errCode
                                + resp.toString());
                paySuccess("2");
                this.finish();
            } else if (resp.errCode == -2) {
                Toast.makeText(WXPayEntryActivity.this, "取消支付", Toast.LENGTH_SHORT).show();
                paySuccess("2");
                this.finish();
            }
        }
    }

    private void paySuccess(String s) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("otherId", orderId);
        params.put("coupleId", coupleId);
        params.put("norm", norm);
        params.put("choose", "2");
        params.put("type", s);
        Log.i("tag", Constants.BASE_URL + "payOrder&" + params);
        client.post(Constants.BASE_URL + "payOrder", params, new AsyncHttpResponseHandler() {
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
                        if (norm.equals("1")) {
                            //1 从填写订单页面跳转，支付完成销毁填写订单页面
                            ActivityManager.getInstance().finishActivity(FillOrderActivity.class);
                        }
                        ActivityManager.getInstance().finishActivity(PayActivity.class);
                        Intent intent = new Intent(WXPayEntryActivity.this, MyOrderActivity.class);
                        intent.putExtra("title", "我的订单");
                        intent.putExtra("type", "4");
                        startActivity(intent);
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else {
                        Toast.makeText(WXPayEntryActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private View mDialogContentView;
    private LoadingView mLoadingView;
    private Dialog dialog;

    public void showLoad(String text) {
        dialog = new Dialog(this, R.style.custom_dialog);
        mDialogContentView = LayoutInflater.from(this).inflate(
                R.layout.layout_loading_dialog, null);
        mLoadingView = (LoadingView) mDialogContentView
                .findViewById(R.id.loadView);
        if (StringUtil.isEmpty(text)) {
            mLoadingView.setLoadingText("加载中...");
        } else {
            mLoadingView.setLoadingText(text);
        }
        Display d = getWindowManager().getDefaultDisplay();
        dialog.show();
        dialog.setContentView(mDialogContentView, new ViewGroup.LayoutParams((int) (d.getWidth() * 0.5),
                (int) (d.getHeight() * 0.3)));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}

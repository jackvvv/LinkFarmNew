//package sinia.com.linkfarmnew.wxapi;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import com.avast.android.butterknifezelezny.common.Utils;
//import com.google.gson.Gson;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.tencent.mm.sdk.constants.ConstantsAPI;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//import sinia.com.linkfarmnew.R;
//import sinia.com.linkfarmnew.base.BaseActivity;
//import sinia.com.linkfarmnew.bean.JsonBean;
//import sinia.com.linkfarmnew.utils.ActivityManager;
//import sinia.com.linkfarmnew.utils.Constants;
//import sinia.com.linkfarmnew.utils.MyApplication;
//
//public class WXPayEntryActivity extends BaseActivity implements
//		IWXAPIEventHandler {
//
//	private IWXAPI api;
//	private String orderid, voucherid, isFromOther, tradeno, dingjinMoney,
//			paydingjin, method;
//	private AsyncHttpClient client = new AsyncHttpClient();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_result, "微信支付");
//		hideHeadView();
//		getDoingView().setVisibility(View.GONE);
//		orderid = SharedPreferencesUtils.getShareString(this, "WX_orderid");
//		voucherid = SharedPreferencesUtils.getShareString(this, "WX_voucherid");
//		tradeno = SharedPreferencesUtils.getShareString(this, "WX_tradeno");
//		dingjinMoney = SharedPreferencesUtils.getShareString(this,
//				"dingjinMoney");
//		paydingjin = SharedPreferencesUtils.getShareString(this, "paydingjin");
//		isFromOther = SharedPreferencesUtils
//				.getShareString(this, "isFromOther");
//		api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
//		api.handleIntent(getIntent(), this);
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		super.onNewIntent(intent);
//		setIntent(intent);
//		api.handleIntent(intent, this);
//	}
//
//	@Override
//	public void onReq(BaseReq req) {
//	}
//
//	@Override
//	public void onResp(BaseResp resp) {
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			SharedPreferencesUtils.removeShareValue(this, "WX_orderid");
//			SharedPreferencesUtils.removeShareValue(this, "WX_voucherid");
//			SharedPreferencesUtils.removeShareValue(this, "isFromOther");
//			SharedPreferencesUtils.removeShareValue(this, "WX_tradeno");
//			SharedPreferencesUtils.removeShareValue(this, "dingjinMoney");
//			SharedPreferencesUtils.removeShareValue(this, "paydingjin");
//			if (resp.errCode == 0) {
//				if ("1".equals(isFromOther)) {
//					// 学院支付
//					collegePay();
//				} else if ("2".equals(isFromOther)) {
//					// vip支付
//					VipPay();
//				} else if ("3".equals(isFromOther)) {
//					// 商城支付
//					MallPay();
//				} else {
//					paySuccess();
//				}
//			} else if (resp.errCode == -1) {
//				showToast(resp.toString());
//				Log.d("msg",
//						"onPayFinish, errCode = " + resp.errCode
//								+ resp.toString());
//				this.finish();
//			} else if (resp.errCode == -2) {
//				showToast("取消支付");
//				this.finish();
//			}
//		}
//	}
//
//	private void MallPay() {
//		RequestParams params = new RequestParams();
//		params.put("orderid", orderid);
//		params.put("userid", MyApplication.getInstance().getUserBean()
//				.getMemberid());
//		params.put("paytype", "2");
//		params.put("payno", tradeno);
//		client.post(Constants.BASE_URL + "goodpaysuccess", params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, String response) {
//						super.onSuccess(arg0, response);
//						String resultStr = Utils
//								.getStrVal(new String(response));
//						JsonBean json = JsonUtil.getJsonBean(resultStr);
//						int rescode = json.getRescode();
//						if (rescode == 0) {
//							showToast((String) json.getRescnt());
//						} else {
//							showToast((String) json.getRescnt());
//						}
//						startActivityForNoIntent(MainActivity.class);
//						ActivityManager.getInstance().finishCurrentActivity();
//					}
//				});
//	}
//
//	private void VipPay() {
//		RequestParams params = new RequestParams();
//		params.put("orderid", orderid);
//		params.put("voucherid", voucherid);
//		params.put("paytype", "2");
//		params.put("payno", tradeno);
//		client.post(Constants.BASE_URL + "servicepaysuccess", params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, String response) {
//						super.onSuccess(arg0, response);
//						String resultStr = Utils
//								.getStrVal(new String(response));
//						JsonBean json = JsonUtil.getJsonBean(resultStr);
//						Gson gson = new Gson();
//						int rescode = json.getRescode();
//						if (rescode == 0) {
//							VoucherBean jsonBean = gson.fromJson(resultStr,
//									VoucherBean.class);
//							Intent it = new Intent();
//							it.putExtra("VoucherBean", jsonBean.getRescnt());
//							it.putExtra("types", "2");
//							startActivityForIntent(CertificateActivity.class,
//									it);
//							ActivityManager.getInstance()
//									.finishCurrentActivity();
//						}
//					}
//				});
//	}
//
//	private void collegePay() {
//		RequestParams params = new RequestParams();
//		params.put("orderid", orderid);
//		params.put("voucherid", voucherid);
//		params.put("paytype", "2");
//		params.put("payno", tradeno);
//		client.post(Constants.BASE_URL + "collegepaysuccess", params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, String response) {
//						super.onSuccess(arg0, response);
//						String resultStr = Utils
//								.getStrVal(new String(response));
//						JsonBean json = JsonUtil.getJsonBean(resultStr);
//						Gson gson = new Gson();
//						int rescode = json.getRescode();
//						if (rescode == 0) {
//							VoucherBean jsonBean = gson.fromJson(resultStr,
//									VoucherBean.class);
//							Intent it = new Intent();
//							it.putExtra("VoucherBean", jsonBean.getRescnt());
//							startActivityForIntent(CertificateActivity.class,
//									it);
//							ActivityManager.getInstance()
//									.finishCurrentActivity();
//						}
//					}
//				});
//	}
//
//	private void paySuccess() {
//		RequestParams params = new RequestParams();
//		if ("1".equals(paydingjin)) {
//			// 支付定金
//			params.put("orderid", orderid);
//			params.put("payno", tradeno);
//			params.put("paytype", "2");
//			params.put("dinjing", dingjinMoney);
//			method = "depositpayment";
//		} else {
//			params.put("orderid", orderid);
//			params.put("payno", tradeno);
//			params.put("paytype", "2");
//			method = "paySuccess";
//		}
//		client.post(Constants.BASE_URL + method, params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, String response) {
//						super.onSuccess(arg0, response);
//						String resultStr = Utils
//								.getStrVal(new String(response));
//						JsonBean json = JsonUtil.getJsonBean(resultStr);
//						int rescode = json.getRescode();
//						if (rescode == 0) {
//							showToast((String) json.getRescnt());
//						} else {
//							showToast((String) json.getRescnt());
//						}
//						startActivityForNoIntent(MainActivity.class);
//						ActivityManager.getInstance().finishCurrentActivity();
//					}
//				});
//	}
//
//}

package sinia.com.linkfarmnew.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.message.proguard.P;
import com.umeng.message.proguard.S;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MD5;
import sinia.com.linkfarmnew.utils.PayResult;
import sinia.com.linkfarmnew.utils.SharedPreferencesUtils;
import sinia.com.linkfarmnew.utils.SignUtils;
import sinia.com.linkfarmnew.utils.Util;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class PayActivity extends BaseActivity {

    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.ic_wx)
    ImageView icWx;
    @Bind(R.id.imageView3)
    ImageView imageView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.ic_alipay)
    ImageView icAlipay;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private String orderId, coupleId, choose = "2";//1支付宝，2微信
    private String payMoney, norm, tradeno;//1.第一次支付 2.订单管理未支付进入
    private AsyncHttpClient client = new AsyncHttpClient();
    private static final int SDK_PAY_FLAG = 111;
    private static final int SDK_CHECK_FLAG = 112;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        resultInfo = resultInfo.replace("\"", "");
                        String[] resultParams = resultInfo.split("&");
                        String orderString = resultParams[2];
//                        payNum = orderString.split("=")[1];

                        paySuccess("1");

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showToast("支付结果确认中");
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            showToast("您已放弃付款，支付失败");
                            paySuccess("2");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            showToast("支付失败");
                            paySuccess("2");
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG:
                    showToast("检查结果为：" + msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay, "支付");
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        orderId = getIntent().getStringExtra("orderId");
        coupleId = getIntent().getStringExtra("coupleId");
        norm = getIntent().getStringExtra("flag");
        payMoney = getIntent().getStringExtra("payMoney");
        tvMoney.setText(payMoney + "元");
    }

    @OnClick({R.id.ic_wx, R.id.ic_alipay, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_wx:
                choose = "2";
                icWx.setImageResource(R.drawable.ic_checked);
                icAlipay.setImageResource(R.drawable.ic_notcheck);
                break;
            case R.id.ic_alipay:
                choose = "1";
                icWx.setImageResource(R.drawable.ic_notcheck);
                icAlipay.setImageResource(R.drawable.ic_checked);
                break;
            case R.id.tv_ok:
                if (choose.equals("2")) {
                    payWithWx();
                    SharedPreferencesUtils.putShareValue(this, "WX_orderid", orderId);
                    SharedPreferencesUtils.putShareValue(this, "WX_coupleId", coupleId);
                    SharedPreferencesUtils
                            .putShareValue(this, "WX_norm", norm);
                } else if (choose.equals("1")) {
                    payWithAliPay();
//                    paySuccess("1");
                }
                break;
        }
    }

    private void payWithWx() {
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    private void paySuccess(String s) {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("otherId", orderId);
        params.put("coupleId", coupleId);
        params.put("norm", norm);
        params.put("choose", choose);
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
//                        showToast("请求成功");
                        if (norm.equals("1")) {
                            //1 从填写订单页面跳转，支付完成销毁填写订单页面
                            ActivityManager.getInstance().finishActivity(FillOrderActivity.class);
                        }
                        Intent intent = new Intent();
                        intent.putExtra("title", "我的订单");
                        intent.putExtra("type", "4");
                        startActivityForIntent(MyOrderActivity.class, intent);
                        ActivityManager.getInstance().finishCurrentActivity();
                    } else {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private IWXAPI api;

    private static final String TAG = "MicroMsg.SDKSample.PayActivity";

    PayReq req = new PayReq();

    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    Map<String, String> resultunifiedorder;

    StringBuffer sb = new StringBuffer();

    private class GetPrepayIdTask extends
            AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(PayActivity.this,
                    getString(R.string.app_tip),
                    getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            // showToast(sb.toString());

            resultunifiedorder = result;
            genPayReq();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String
                    .format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String costs = String.valueOf(Double.valueOf(payMoney) * 100)
                    .split("\\.")[0];
            String entity = genProductArgs(costs);

            Log.e("tag", entity);

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("tag", content);
            Map<String, String> xml = decodeXml(content);
            Log.e("tag", "xml" + xml);
            return xml;
        }
    }

    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("tag", e.toString());
        }
        return null;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genOutTradNo() {
        Random random = new Random();
        tradeno = MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
        return tradeno;
    }

    private String genProductArgs(String costs) {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();

            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid",
                    Constants.WX_APPID));
            packageParams.add(new BasicNameValuePair("body", "链农荟支付"));
            packageParams.add(new BasicNameValuePair("mch_id",
                    Constants.WX_PARTNER_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url",
                    "http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php"));
            packageParams.add(new BasicNameValuePair("out_trade_no", genOutTradNo()));
            packageParams.add(new BasicNameValuePair("spbill_create_ip",
                    getPhoneIp()));
            packageParams.add(new BasicNameValuePair("total_fee", "1"));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);
            // 解决body传中文报签名错误的问题，生成的xml请求参数转为字节数组后，用“ISO8859-1”编码格式进行编码为字符
            return new String(xmlstring.getBytes(), "ISO8859-1");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成签名
     */
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.WX_APIKEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.WX_APIKEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("orion", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }

    private void genPayReq() {
        req.appId = Constants.WX_APPID;
        req.partnerId = Constants.WX_PARTNER_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        // showToast(sb.toString());
        sendPayReq();
        Log.e("orion", signParams.toString());

    }

    private void sendPayReq() {
        msgApi.registerApp(Constants.WX_APPID);
        msgApi.sendReq(req);
    }

    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "127.0.0.1";
    }

    /**
     * 支付宝支付
     */
    private void payWithAliPay() {
        // 订单
        String orderInfo = getOrderInfo("链农荟支付", "链农荟支付", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        Log.i("sign", "sign------" + sign);
        Log.i("sign", "orderInfo------" + orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check() {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(PayActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Constants.ALIPAY_PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constants.ALIPAY_SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, Constants.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}

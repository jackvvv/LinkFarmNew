package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.ClassfyGoodsAdapter;
import sinia.com.linkfarmnew.adapter.SearchGoodsAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.ClassfyGoodsBean;
import sinia.com.linkfarmnew.bean.ClassfyListBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.bean.SearchBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class GoodsListActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.tv_zonghe)
    TextView tvZonghe;
    @Bind(R.id.iv_zonghe)
    ImageView ivZonghe;
    @Bind(R.id.ll_zonghe)
    LinearLayout llZonghe;
    @Bind(R.id.tv_xiaoliang)
    TextView tvXiaoliang;
    @Bind(R.id.img_sellup)
    ImageView imgSellup;
    @Bind(R.id.img_selldown)
    ImageView imgSelldown;
    @Bind(R.id.ll_xiaoliang)
    LinearLayout llXiaoliang;
    @Bind(R.id.tv_jiage)
    TextView tvJiage;
    @Bind(R.id.img_priceup)
    ImageView imgPriceup;
    @Bind(R.id.img_pricedown)
    ImageView imgPricedown;
    @Bind(R.id.ll_price)
    LinearLayout llPrice;
    @Bind(R.id.ll_shaixuan)
    LinearLayout llShaixuan;
    @Bind(R.id.listView)
    ListView listView;
    public static DrawerLayout drawerLayout;
    @Bind(R.id.tv_shaixuan)
    TextView tvShaixuan;
    @Bind(R.id.img_shaixuan)
    ImageView imgShaixuan;
    private ClassfyGoodsAdapter goodsAdapter;
    private String sellFlag = "1";//销量 1上，2下
    private String priceFlag = "1";//价格 1上，2下
    private String secondId;
    public static String startPlace, endPlace;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<ClassfyGoodsBean.ItemsBean> list = new ArrayList<>();
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                getGoodsListByFilters("1", "-1", "-1", startPlace, endPlace);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        initData();
        getGoodsListBySecondId();
    }

    private void initData() {
        secondId = getIntent().getStringExtra("secondId");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvZonghe.setSelected(true);
        ivZonghe.setSelected(true);
        goodsAdapter = new ClassfyGoodsAdapter(this, list);
        listView.setAdapter(goodsAdapter);
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtil.isEmpty(etContent.getEditableText().toString().trim())) {
                        showToast("请输入搜索内容");
                    } else {
                        getGoodsListByFilters("1", "-1", "-1", "-1", "-1");
                    }
                }
                return false;
            }
        });
    }

    /**
     * 根据二级分类查询
     */
    private void getGoodsListBySecondId() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", secondId);
        try {
            params.put("content", URLEncoder.encode(MyApplication.getInstance().getStringValue("city"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("tag", Constants.BASE_URL + "goodListByTypeId&" + params);
        client.post(Constants.BASE_URL + "goodListByTypeId", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    ClassfyGoodsBean bean = gson.fromJson(s, ClassfyGoodsBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        list.clear();
                        list.addAll(bean.getItems());
                        goodsAdapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    /**
     * 筛选
     *
     * @param zonghe
     * @param sell
     * @param price
     * @param proArea
     * @param sendArea
     */
    private void getGoodsListByFilters(String zonghe, String sell, String price, String proArea, String sendArea) {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", secondId);
        params.put("muLiType", zonghe);
        params.put("saleChoose", sell);
        params.put("choose", price);
        try {
            params.put("content", URLEncoder.encode(MyApplication.getInstance().getStringValue("city"), "UTF-8"));
            if (StringUtil.isEmpty(etContent.getEditableText().toString().trim())) {
                params.put("name", "-1");
            } else {
                params.put("name", URLEncoder.encode(etContent.getEditableText().toString().trim(), "UTF-8"));
            }
            params.put("proArea", URLEncoder.encode(proArea, "UTF-8"));
            params.put("sendArea", URLEncoder.encode(sendArea, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("tag", Constants.BASE_URL + "goodSearch&" + params);
        client.post(Constants.BASE_URL + "goodSearch", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Log.i("tag", s);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    ClassfyGoodsBean bean = gson.fromJson(s, ClassfyGoodsBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        list.clear();
                        list.addAll(bean.getItems());
                        goodsAdapter.notifyDataSetChanged();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.ll_zonghe, R.id.ll_xiaoliang, R.id.ll_price, R.id.ll_shaixuan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.ll_zonghe:
                tvZonghe.setSelected(true);
                ivZonghe.setSelected(true);
                tvXiaoliang.setSelected(false);
                tvShaixuan.setSelected(false);
                imgSellup.setSelected(false);
                imgSelldown.setSelected(false);
                tvJiage.setSelected(false);
                imgPricedown.setSelected(false);
                imgPriceup.setSelected(false);
                imgShaixuan.setSelected(false);
                getGoodsListByFilters("1", "-1", "-1", "-1", "-1");
                break;
            case R.id.ll_xiaoliang:
                tvZonghe.setSelected(false);
                ivZonghe.setSelected(false);
                tvXiaoliang.setSelected(true);
                tvShaixuan.setSelected(false);
                imgShaixuan.setSelected(false);
                tvJiage.setSelected(false);
                imgPricedown.setSelected(false);
                imgPriceup.setSelected(false);
                if (sellFlag.equals("1")) {
                    sellFlag = "2";
                    imgSellup.setSelected(true);
                    imgSelldown.setSelected(false);
                    getGoodsListByFilters("-1", "1", "-1", "-1", "-1");
                } else {
                    sellFlag = "1";
                    imgSellup.setSelected(false);
                    imgSelldown.setSelected(true);
                    getGoodsListByFilters("-1", "2", "-1", "-1", "-1");
                }
                break;
            case R.id.ll_price:
                tvZonghe.setSelected(false);
                ivZonghe.setSelected(false);
                tvXiaoliang.setSelected(false);
                tvShaixuan.setSelected(false);
                imgShaixuan.setSelected(false);
                tvJiage.setSelected(true);
                imgSellup.setSelected(false);
                imgSelldown.setSelected(false);
                if (priceFlag.equals("1")) {
                    priceFlag = "2";
                    imgPriceup.setSelected(true);
                    imgPricedown.setSelected(false);
                    getGoodsListByFilters("-1", "-1", "1", "-1", "-1");
                } else {
                    priceFlag = "1";
                    imgPriceup.setSelected(false);
                    imgPricedown.setSelected(true);
                    getGoodsListByFilters("-1", "-1", "2", "-1", "-1");
                }
                break;
            case R.id.ll_shaixuan:
                tvZonghe.setSelected(false);
                ivZonghe.setSelected(false);
                tvXiaoliang.setSelected(false);
                tvJiage.setSelected(false);
                tvShaixuan.setSelected(true);
                imgShaixuan.setSelected(true);
                imgPricedown.setSelected(false);
                imgPriceup.setSelected(false);
                imgSellup.setSelected(false);
                imgSelldown.setSelected(false);
                openRightMenu();
                break;
        }
    }

    private void openRightMenu() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }
}

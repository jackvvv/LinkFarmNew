package sinia.com.linkfarmnew.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import sinia.com.linkfarmnew.adapter.SearchGoodsAdapter;
import sinia.com.linkfarmnew.adapter.SearchShopAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.bean.HomePageBean;
import sinia.com.linkfarmnew.bean.SearchBean;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.StringUtil;

/**
 * Created by 忧郁的眼神 on 2016/8/8.
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.tv_search_type)
    TextView tvSearchType;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.listView)
    ListView listView;

    private PopupWindow popupWindow;
    private SearchGoodsAdapter goodsAdapter;
    private SearchShopAdapter shopAdapter;
    private String search_type = "1";//1.商品，2 商户
    private String city;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<SearchBean.ItemsBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initData();
    }

    private void search() {
        showLoad("搜索中...");
        RequestParams params = new RequestParams();
        try {
            params.put("content", URLEncoder.encode(city, "UTF-8"));
            params.put("name", URLEncoder.encode(etContent.getEditableText().toString().trim(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("type", search_type);
        Log.i("tag", Constants.BASE_URL + "searchMerOrGood&" + params);
        client.post(Constants.BASE_URL + "searchMerOrGood", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    SearchBean bean = gson.fromJson(s, SearchBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        list.clear();
                        list.addAll(bean.getItems());
                        if (search_type.equals("1")) {
                            listView.setAdapter(goodsAdapter);
                        } else {
                            listView.setAdapter(shopAdapter);
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void initData() {
        city = getIntent().getStringExtra("city");
        goodsAdapter = new SearchGoodsAdapter(this, list);
        shopAdapter = new SearchShopAdapter(this, list);
        listView.setAdapter(goodsAdapter);
        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtil.isEmpty(etContent.getEditableText().toString().trim())) {
                        showToast("请输入搜索内容");
                    } else {
                        search();
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.back, R.id.tv_search_type, R.id.img_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                ActivityManager.getInstance().finishCurrentActivity();
                break;
            case R.id.tv_search_type:
                showPopwindow(tvSearchType);
                break;
            case R.id.img_search:
                if (StringUtil.isEmpty(etContent.getEditableText().toString().trim())) {
                    showToast("请输入搜索内容");
                } else {
                    search();
                }
                break;
        }
    }

    private void showPopwindow(final TextView tvSearchType) {
        if (popupWindow == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.pop_search_type, null);
            TextView tv_goods = (TextView) v.findViewById(R.id.tv_goods);
            TextView tv_shop = (TextView) v.findViewById(R.id.tv_shop);
            popupWindow = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams
                    .WRAP_CONTENT);
            tv_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvSearchType.setText("商铺");
                    search_type = "2";
                    popupWindow.dismiss();
                }
            });
            tv_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvSearchType.setText("商品");
                    search_type = "1";
                    popupWindow.dismiss();
                }
            });
        }
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(tvSearchType);
    }


}

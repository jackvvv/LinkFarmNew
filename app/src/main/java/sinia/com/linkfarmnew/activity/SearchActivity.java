package sinia.com.linkfarmnew.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.SearchGoodsAdapter;
import sinia.com.linkfarmnew.adapter.SearchShopAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.ActivityManager;

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
    private String search_type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        goodsAdapter = new SearchGoodsAdapter(this);
        shopAdapter = new SearchShopAdapter(this);
        listView.setAdapter(goodsAdapter);
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
                break;
        }
    }

    private void showPopwindow(final TextView tvSearchType) {
        if (popupWindow == null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.pop_search_type, null);
            TextView tv_goods = (TextView) v.findViewById(R.id.tv_goods);
            TextView tv_shop = (TextView) v.findViewById(R.id.tv_shop);
            popupWindow = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvSearchType.setText("商铺");
                    search_type = "2";
                    listView.setAdapter(shopAdapter);
                    popupWindow.dismiss();
                }
            });
            tv_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvSearchType.setText("商品");
                    search_type = "1";
                    listView.setAdapter(goodsAdapter);
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

package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sinia.zxing.CaptureActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.GoodsDetailActivity;
import sinia.com.linkfarmnew.activity.LocationActivity;
import sinia.com.linkfarmnew.activity.LoginActivity;
import sinia.com.linkfarmnew.activity.MessageActivity;
import sinia.com.linkfarmnew.activity.SearchActivity;
import sinia.com.linkfarmnew.activity.WebViewActivity;
import sinia.com.linkfarmnew.adapter.HomeRecommendAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.HomePageBean;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.view.MyGridView;
import sinia.com.linkfarmnew.view.NetworkImageHolderView;

import static com.amap.api.mapcore2d.v.i;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class HomeFragment extends BaseFragment implements AMapLocationListener {

    @Bind(R.id.tv_locate)
    TextView tvLocate;
    @Bind(R.id.tv_search_type)
    TextView tvSearchType;
    @Bind(R.id.et_content)
    TextView etContent;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.tv_scan)
    TextView tvScan;
    @Bind(R.id.tv_msg)
    TextView tvMsg;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.img_up)
    ImageView imgUp;
    @Bind(R.id.img_down)
    ImageView imgDown;
    @Bind(R.id.llup)
    LinearLayout llup;
    @Bind(R.id.rl_search)
    RelativeLayout rl_search;
    @Bind(R.id.v)
    View v;
    @Bind(R.id.img_left_big)
    ImageView imgLeftBig;
    @Bind(R.id.img_right_top)
    ImageView imgRightTop;
    @Bind(R.id.img_right_bottom)
    ImageView imgRightBottom;
    @Bind(R.id.img_left_top)
    ImageView imgLeftTop;
    @Bind(R.id.img_left_bottom)
    ImageView imgLeftBottom;
    @Bind(R.id.img_right_big)
    ImageView imgRightBig;
    @Bind(R.id.gridView)
    MyGridView gridView;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private View rootView;
    private List<String> picList = new ArrayList<String>();
    private HomeRecommendAdapter adapter;
    private LocationManagerProxy mLocationManagerProxy;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String city = "南京";
    private String link, selectCity;
    private List<HomePageBean.RecarrayItemsBean> recommendList = new ArrayList<>();
    private List<HomePageBean.UpActItemsBean> floorList = new ArrayList<>();
    private String img1 = "", img2 = "", img3 = "", img4 = "", img5 = "", img6 = "";
    private String link1 = "", link2 = "", link3 = "", link4 = "", link5 = "", link6 = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, rootView);
        location();
        initData();
        return rootView;
    }

    private void location() {
        int h = AppInfoUtil.getScreenWidth(getActivity()) * 340 / 750;
        convenientBanner.getLayoutParams().height = h;
        String transforemerName = "DefaultTranformer";
        ABaseTransformer transforemer = null;
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.startTurning(3000).setPageIndicator(new int[]{R.drawable.carousel_point, R.drawable
                    .carousel_point_select})
                    .getViewPager().setPageTransformer(true, transforemer);
            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
        mLocationManagerProxy.setGpsEnable(true);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getHomeData();
    }

    private void getHomeData() {
        RequestParams params = new RequestParams();
        try {
            if (StringUtil.isEmpty(MyApplication.getInstance().getStringValue("city"))) {
                params.put("content", URLEncoder.encode(city, "UTF-8"));
            } else {
                params.put("content", URLEncoder.encode(MyApplication.getInstance().getStringValue("city"), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("tag", Constants.BASE_URL + "cusfirstPage&" + params);
        client.post(Constants.BASE_URL + "cusfirstPage", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                swipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    HomePageBean bean = gson.fromJson(s, HomePageBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        setData(bean);
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void initData() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                R.color.themeColor,
                R.color.colorPrimary,
                R.color.possible_result_points);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData();
            }
        });
        adapter = new HomeRecommendAdapter(getActivity(), recommendList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    String goodId = recommendList.get(i).getGoodId();
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("goodId", goodId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void setData(final HomePageBean bean) {
        if (bean != null) {
            if (bean.getBanitems() != null) {
                picList = new ArrayList<>();
                for (int i = 0; i < bean.getBanitems().size(); i++) {
                    picList.add(bean.getBanitems().get(i).getImage());
                }
                convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, picList).setPageIndicator(new int[]{R.drawable.carousel_point, R.drawable
                        .carousel_point_select}).startTurning(3000);
                convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String link = bean.getBanitems().get(position).getLink();
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("link", link);
                        intent.putExtra("title", "链接详情");
                        startActivity(intent);
                    }
                });
            }
            if (bean.getRecarrayitems() != null) {
                recommendList.clear();
                recommendList.addAll(bean.getRecarrayitems());
                adapter.notifyDataSetChanged();
            }
            if (bean.getUpactitems() != null) {
                setFloorData(bean.getUpactitems());
            }
            if (bean.getAnnitems() != null) {
                setNoticeData(bean.getAnnitems());
            }
        }
    }

    private void setFloorData(List<HomePageBean.UpActItemsBean> list) {
        int itemWidth = AppInfoUtil.getScreenWidth(getActivity()) / 2 - AppInfoUtil.dip2px(getActivity(), 15);
        int itemHeight = AppInfoUtil.getScreenHeight(getActivity()) / 3;
        // 大图片LayoutParams
        LinearLayout.LayoutParams lpB = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpB.width = itemWidth;
        lpB.height = itemHeight;
        // 小图片LayoutParams
        LinearLayout.LayoutParams lpS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lpS.width = itemWidth;
        lpS.height = itemHeight / 2 - AppInfoUtil.dip2px(getActivity(), 6);
        for (int i = 0; i < list.size(); i++) {
            int position = list.get(i).getLocationType();
            int floorType = list.get(i).getType();
            String imgUrl = list.get(i).getImage();
            String link = list.get(i).getLink();
            String id = list.get(i).getUpactId();
            if (1 == floorType) {
                if (position == 1) {
                    Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.load_failed_right).into
                            (imgLeftBig);
                    imgLeftBig.setLayoutParams(lpB);
                    img1 = imgUrl;
                    link1 = link;
                }
                if (position == 2) {
                    Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.load_failed_right).into
                            (imgRightTop);
                    imgRightTop.setLayoutParams(lpS);
                    img2 = imgUrl;
                    link2 = link;
                }
                if (position == 3) {
                    Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.load_failed_right).into
                            (imgRightBottom);
                    imgRightBottom.setLayoutParams(lpS);
                    img3 = imgUrl;
                    link3 = link;
                }
            }
            if (2 == floorType) {
                if (position == 1) {
                    Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.load_failed_right).into
                            (imgLeftTop);
                    imgLeftTop.setLayoutParams(lpS);
                    img4 = imgUrl;
                    link4 = link;
                }
                if (position == 2) {
                    Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.load_failed_right).into
                            (imgLeftBottom);
                    imgLeftBottom.setLayoutParams(lpS);
                    img5 = imgUrl;
                    link5 = link;
                }
                if (position == 3) {
                    Glide.with(getActivity()).load(imgUrl).placeholder(R.drawable.load_failed_right).into
                            (imgRightBig);
                    imgRightBig.setLayoutParams(lpB);
                    img6 = imgUrl;
                    link6 = link;
                }
            }
        }
    }

    private void setNoticeData(List<HomePageBean.AnnItemsBean> list) {
        if (list.size() != 0) {
            viewFlipper.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                HomePageBean.AnnItemsBean task = list.get(i);
                initTask(task, viewFlipper);
            }
        }
    }

    private void initTask(HomePageBean.AnnItemsBean task, ViewFlipper viewFlipper) {
        LinearLayout item = new LinearLayout(getActivity());
        item.setOrientation(LinearLayout.VERTICAL);
        item.setBackgroundColor(Color.WHITE);
        item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        item.setGravity(Gravity.CENTER_VERTICAL);
        // 通知内容
        TextView title = new TextView(getActivity());
        title.setTextColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.CENTER_VERTICAL;
        title.setLayoutParams(lp2);
        title.setSingleLine(true);
        title.setGravity(Gravity.CENTER_VERTICAL);
        title.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        title.setText(task.getContent());
        title.setTextSize(16);
        item.addView(title);
        viewFlipper.addView(item);
    }

    @OnClick({R.id.tv_locate, R.id.tv_scan, R.id.tv_msg, R.id.rl_search, R.id.img_up, R.id.img_down, R.id
            .img_left_big, R.id.img_right_top, R.id.img_right_bottom, R.id.img_right_big, R.id.img_left_top, R.id
            .img_left_bottom})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_locate:
                intent = new Intent(getActivity(), LocationActivity.class);
                intent.putExtra("city", MyApplication.getInstance().getStringValue("city"));
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("city", MyApplication.getInstance().getStringValue("city"));
                startActivity(intent);
                break;
            case R.id.tv_scan:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_msg:
                if (MyApplication.getInstance().getBoolValue("is_login")) {
                    intent = new Intent(getActivity(), MessageActivity.class);
                    intent.putExtra("city", MyApplication.getInstance().getStringValue("city"));
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.img_up:
                viewFlipper.showPrevious();
                break;
            case R.id.img_down:
                viewFlipper.showNext();
                break;
            case R.id.img_left_big:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", link1);
                intent.putExtra("title", "链接详情");
                startActivity(intent);
                break;
            case R.id.img_right_top:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", link2);
                intent.putExtra("title", "链接详情");
                startActivity(intent);
                break;
            case R.id.img_right_bottom:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", link3);
                intent.putExtra("title", "链接详情");
                startActivity(intent);
                break;
            case R.id.img_right_big:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", link6);
                intent.putExtra("title", "链接详情");
                startActivity(intent);
                break;
            case R.id.img_left_top:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", link4);
                intent.putExtra("title", "链接详情");
                startActivity(intent);
                break;
            case R.id.img_left_bottom:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link", link5);
                intent.putExtra("title", "链接详情");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation.getCity()) {
            city = aMapLocation.getCity().split("市")[0];
            if (!StringUtil.isEmpty(city)) {
//                tvLocate.setText(aMapLocation.getCity().split("市")[0]);
                tvLocate.setText("定位");
            } else {
                tvLocate.setText("定位");
                city = "南京";
            }
        } else {
            tvLocate.setText("定位");
            city = "南京";
        }
        MyApplication.getInstance().setStringValue("city", city);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 100) {
                selectCity = data.getStringExtra("selectCity");
                city = selectCity;
                tvLocate.setText("定位");
                MyApplication.getInstance().setStringValue("city", selectCity);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mLocationManagerProxy) {
            // 移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mLocationManagerProxy) {
            // 移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }
    }
}

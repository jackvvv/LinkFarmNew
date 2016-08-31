package sinia.com.linkfarmnew.activity;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.IntegralMallAdapter;
import sinia.com.linkfarmnew.base.BaseActivity;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.view.CircleImageView;
import sinia.com.linkfarmnew.view.ObservableListView;
import sinia.com.linkfarmnew.view.ObservableScrollViewCallbacks;
import sinia.com.linkfarmnew.view.ScrollState;
import sinia.com.linkfarmnew.view.ScrollUtils;

/**
 * Created by 忧郁的眼神 on 2016/8/31.
 */
public class IntegralMallActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    @Bind(R.id.iv_head)
    CircleImageView ivHead;
    @Bind(R.id.img_into)
    TextView tvRecorde;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_jifen)
    TextView tvJifen;
    @Bind(R.id.listView)
    ObservableListView mListView;

    private View mHeaderView;
    private View mToolbarView;
    private int mBaseTranslationY;
    private IntegralMallAdapter integralMallAdapter;
    private String userName, headImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall, "积分商城");
        ButterKnife.bind(this);
        getDoingView().setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        userName = getIntent().getStringExtra("userName");
        headImage = getIntent().getStringExtra("headImage");
        mHeaderView = findViewById(R.id.header);
        ViewCompat.setElevation(mHeaderView, 4);
        mToolbarView = findViewById(R.id.mToolbarView);
        mListView.setScrollViewCallbacks(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View headView = inflater.inflate(R.layout.view_mall_headview, mListView, false);
        mListView.addHeaderView(headView);

        BitmapUtilsHelp.getImage(this, R.drawable
                .ic_launcher).display(ivHead, headImage);
        tvUsername.setText(userName);
        tvJifen.setText("积分：" + MyApplication.getInstance().getLoginBean().getPoint());

        integralMallAdapter = new IntegralMallAdapter(this);
        mListView.setAdapter(integralMallAdapter);
    }

    @OnClick(R.id.img_into)
    public void onClick() {
        startActivityForNoIntent(ExchangeRecordActivity.class);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int toolbarHeight = mToolbarView.getHeight();
            if (firstScroll) {
                float currentHeaderTranslationY = ViewHelper.getTranslationY(mHeaderView);
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewHelper.setTranslationY(mHeaderView, headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;

        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            int toolbarHeight = mToolbarView.getHeight();
            int scrollY = mListView.getCurrentScrollY();
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            if (!toolbarIsShown() && !toolbarIsHidden()) {
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mHeaderView) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mHeaderView) == -mToolbarView.getHeight();
    }

    private void showToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        if (headerTranslationY != 0) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(0).setDuration(200).start();
        }
    }

    private void hideToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        int toolbarHeight = mToolbarView.getHeight();
        if (headerTranslationY != -toolbarHeight) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(-toolbarHeight).setDuration(200).start();
        }
    }
}

package sinia.com.linkfarmnew.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.adapter.StandardPriceAdapter;
import sinia.com.linkfarmnew.adapter.StandardTypeAdapter;
import sinia.com.linkfarmnew.bean.GoodsDetailBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.myinterface.CalculatePriceInterface;
import sinia.com.linkfarmnew.myinterface.SetPriceDataInterface;
import sinia.com.linkfarmnew.utils.ActivityManager;
import sinia.com.linkfarmnew.utils.AppInfoUtil;
import sinia.com.linkfarmnew.utils.BitmapUtilsHelp;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.Utility;
import sinia.com.linkfarmnew.view.MyGridView;
import sinia.com.linkfarmnew.view.loadingview.LoadingView;

/**
 * Created by 忧郁的眼神 on 2016/8/10.
 */
public class StandardDialogActivity extends Activity implements SetPriceDataInterface {

    @Bind(R.id.img_colse)
    ImageView imgColse;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.img_jian)
    ImageView imgJian;
    @Bind(R.id.et_weight)
    EditText etWeight;
    @Bind(R.id.img_jia)
    ImageView imgJia;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_cart)
    TextView tvCart;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    @Bind(R.id.tv_storeNum)
    TextView tv_storeNum;
    @Bind(R.id.ll_root)
    RelativeLayout ll_root;
    @Bind(R.id.lv_price)
    ListView lvPrice;

    private View headerView;
    private MyGridView gv_type;
    private GoodsDetailBean goodsBean;
    private StandardTypeAdapter typeAdapter;
    private StandardPriceAdapter priceAdapter;
    private SetPriceDataInterface priceDataInterface;
    private List<GoodsDetailBean.NormListBean.NormTypeListBean> priceList = new ArrayList<>();
    private String selectType, normId;
    private AsyncHttpClient client = new AsyncHttpClient();
    private List<String> selectGoodsImage = new ArrayList<>();
    private List<GoodsDetailBean.GoodsImageBean> imgList = new ArrayList<>();
    public static String lastNum, goodsUnit;//剩余量s
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_dialog);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        priceDataInterface = (SetPriceDataInterface) this;
        goodsBean = (GoodsDetailBean) getIntent().getSerializableExtra("goodsBean");
        int w = AppInfoUtil.getScreenWidth(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, FrameLayout.LayoutParams.WRAP_CONTENT);
        ll_root.setLayoutParams(lp);

//        lastNum = goodsBean.getLeastKiloGram();
        imgList.clear();
        imgList.addAll(goodsBean.getImageitems());
        if (0 != imgList.size()) {
            Glide.with(this).load(imgList.get(0).getImage()).crossFade().into(img);
//            BitmapUtilsHelp.getImage(this).display(img, imgList.get(0).getImage());
        } else {
//            BitmapUtilsHelp.getImage(this).display(img, "");
            Glide.with(this).load("").placeholder(R.drawable.ic_launcher).crossFade().into(img);
        }

        headerView = LayoutInflater.from(this).inflate(R.layout.view_standard_head, null);
        gv_type = (MyGridView) headerView.findViewById(R.id.gv_type);

        //类别grid adapter
        typeAdapter = new StandardTypeAdapter(this, goodsBean.getNormitems());
        gv_type.setAdapter(typeAdapter);
        typeAdapter.selectPosition = 0;

        //价格区间list adapter
        priceAdapter = new StandardPriceAdapter(this, priceList);
        if (null != goodsBean.getNormitems() && 0 != goodsBean.getNormitems().size()) {
            lastNum = goodsBean.getNormitems().get(0).getNum();
            goodsUnit = goodsBean.getNormitems().get(0).getUnit();
            tv_storeNum.setText("库存量：" + goodsBean.getNormitems().get(0).getNum() + goodsUnit);
            selectType = goodsBean.getNormitems().get(0).getNormName();
            normId = goodsBean.getNormitems().get(0).getNormId();
            priceDataInterface.setPriceList(goodsBean.getNormitems().get(0).getTypeItems(), goodsBean.getNormitems()
                    .get(0).getUnit());
        }

        lvPrice.setAdapter(priceAdapter);
        //加入头部种类gridview
        lvPrice.addHeaderView(headerView);
        Utility.setListViewHeightBasedOnChildren(lvPrice);
        gv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etWeight.setText("");
                tvMoney.setText(0.00 + "");
                imgJian.setImageResource(R.drawable.img_jian_n);
                typeAdapter.selectPosition = i;
                typeAdapter.notifyDataSetChanged();
                selectType = goodsBean.getNormitems().get(i).getNormName();
                normId = goodsBean.getNormitems().get(i).getNormId();
                goodsBean.getNormitems().get(0).getNum();
                lastNum = goodsBean.getNormitems().get(i).getNum();
                goodsUnit = goodsBean.getNormitems().get(i).getUnit();
                tv_storeNum.setText("库存量：" + goodsBean.getNormitems().get(i).getNum() + goodsUnit);
                //根据规格，切换对应的单价区间列表
                priceDataInterface.setPriceList(goodsBean.getNormitems().get(i).getTypeItems(), goodsBean
                        .getNormitems().get(i).getUnit());
            }
        });
        etWeight.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!StringUtil.isEmpty(etWeight.getEditableText().toString().trim()) && !".".equals(etWeight
                    .getEditableText().toString().trim())) {
                int inputWeight = Integer.parseInt(etWeight.getEditableText().toString().trim());
                //当输入的重量小于1的时候，不能再减了
                if (inputWeight <= 1) {
//                    imgJian.setImageResource(R.drawable.img_jian_n);
                } else {
                    imgJian.setImageResource(R.drawable.img_jian);
                }
                calculatePrice(priceList, inputWeight, tvMoney);
            } else {
                tvMoney.setText(0.00 + "");
                imgJian.setImageResource(R.drawable.img_jian_n);
            }
        }
    };

    @OnClick({R.id.img_colse, R.id.img_jian, R.id.img_jia, R.id.tv_cart, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_colse:
                if (!StringUtil.isEmpty(selectType) && !StringUtil.isEmpty(etWeight.getEditableText().toString().trim
                        ()) && 0 != Float.parseFloat(tvMoney.getText().toString().trim()) && 0 != Integer.parseInt
                        (etWeight.getEditableText().toString().trim())) {
                    Intent intent = new Intent();
                    intent.putExtra("type", selectType);
                    intent.putExtra("normId", normId);
                    intent.putExtra("weight", etWeight.getEditableText().toString().trim());
                    intent.putExtra("price", tvMoney.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    finish();
                }
                break;
            case R.id.img_jian:
                if (!StringUtil.isEmpty(etWeight.getEditableText().toString().trim()) && 1 <= Integer.parseInt
                        (etWeight.getEditableText().toString().trim())) {
                    int inputWeight = Integer.parseInt(etWeight.getEditableText().toString().trim()) - 1;
                    etWeight.setText(inputWeight + "");
                    etWeight.setSelection(etWeight.getText().length());
                    calculatePrice(priceList, Integer.parseInt(etWeight.getEditableText().toString().trim()), tvMoney);
                    int inputWeight2 = Integer.parseInt(etWeight.getEditableText().toString().trim());
                    if (inputWeight2 < 1) {
                        imgJian.setImageResource(R.drawable.img_jian_n);
                    } else {
                        imgJian.setImageResource(R.drawable.img_jian);
                    }
                } else {
                    imgJian.setImageResource(R.drawable.img_jian_n);
                }
                break;
            case R.id.img_jia:
                if (!StringUtil.isEmpty(etWeight.getEditableText().toString().trim()) && 0 != Integer.parseInt
                        (etWeight.getEditableText().toString().trim())) {
                    int inputWeight = Integer.parseInt(etWeight.getEditableText().toString().trim()) + 1;
                    etWeight.setText(inputWeight + "");
                    etWeight.setSelection(etWeight.getText().length());
                    calculatePrice(priceList, Integer.parseInt(etWeight.getEditableText().toString().trim()), tvMoney);
                    imgJian.setImageResource(R.drawable.img_jian);
                } else {
                    int inputWeight = 1;
                    etWeight.setText(inputWeight + "");
                    etWeight.setSelection(etWeight.getText().length());
                    calculatePrice(priceList, Integer.parseInt(etWeight.getEditableText().toString().trim()), tvMoney);
                    imgJian.setImageResource(R.drawable.img_jian);
                }
                break;
            case R.id.tv_cart:
                if (!StringUtil.isEmpty(selectType) && !StringUtil.isEmpty(etWeight.getEditableText().toString().trim
                        ()) && 0 != Float.parseFloat(tvMoney.getText().toString().trim()) && 0 != Integer.parseInt
                        (etWeight.getEditableText().toString().trim())) {
                    if (!StringUtil.isEmpty(lastNum) && Integer.parseInt(etWeight.getEditableText().toString().trim()
                    ) - Float
                            .parseFloat(lastNum) > 0) {
                        materialDialog = new MaterialDialog(this);
                        materialDialog.setTitle("提示").setMessage("本商品库存剩余量" + lastNum + goodsUnit + ",如需大量购买，请联系商家")
                                .setPositiveButton("知道了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        materialDialog.dismiss();
                                    }
                                }).show();
                    } else {
                        addInCart();
                    }
                } else {
                    Toast.makeText(StandardDialogActivity.this, "请选择产品规格", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_ok:
                if (!StringUtil.isEmpty(selectType) && !StringUtil.isEmpty(etWeight.getEditableText().toString().trim
                        ()) && 0 != Float.parseFloat(tvMoney.getText().toString().trim()) && 0 != Integer.parseInt
                        (etWeight.getEditableText().toString().trim())) {
                    if (!StringUtil.isEmpty(lastNum) && Integer.parseInt(etWeight.getEditableText().toString().trim()
                    ) - Integer.parseInt(lastNum) > 0) {
                        materialDialog = new MaterialDialog(this);
                        materialDialog.setTitle("提示").setMessage("本商品库存剩余量" + lastNum + goodsUnit + ",如需大量购买，请联系商家")
                                .setPositiveButton("知道了", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        materialDialog.dismiss();
                                    }
                                }).show();
                    } else {
                        Intent intent = new Intent(StandardDialogActivity.this, FillOrderActivity.class);
                        intent.putExtra("norm", selectType);
                        intent.putExtra("num", etWeight.getEditableText().toString().trim());
                        intent.putExtra("price", tvMoney.getText().toString().trim());
                        intent.putExtra("goodId", goodsBean.getId());
                        intent.putExtra("name", normId);
                        //填写订单显示的购买商品的图片集合
                        selectGoodsImage = new ArrayList<>();
                        if (goodsBean.getImageitems() != null && 0 != goodsBean.getImageitems().size()) {
                            selectGoodsImage.add(goodsBean.getImageitems().get(0).getImage());
                        }
                        intent.putExtra("selectGoodsImage", (Serializable) selectGoodsImage);
                        intent.putExtra("otherId", "-1");//购物车
                        intent.putExtra("choose", "-1");//商户id
                        intent.putExtra("type", "1");//1.直接购买 2.购物车
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(StandardDialogActivity.this, "请选择产品规格", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void addInCart() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        params.put("goodId", goodsBean.getId());
        params.put("otherId", normId);
        params.put("num", etWeight.getEditableText().toString().trim());
        params.put("price", tvMoney.getText().toString().trim());
        Log.i("tag", Constants.BASE_URL + "addCar&" + params);
        client.post(Constants.BASE_URL + "addCar", params, new AsyncHttpResponseHandler() {
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
                        Toast.makeText(StandardDialogActivity.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                        MyApplication.getInstance().setStringValue("buy_type", null);
                        MyApplication.getInstance().setStringValue("buy_weight", null);
                        MyApplication.getInstance().setStringValue("buy_price", null);
                        MyApplication.getInstance().setStringValue("buy_normId", null);
                        //加入购物车商品id，如果相等则显示已加入状态
                        MyApplication.getInstance().setStringValue("addcart_goodid", goodsBean.getId());
                        finish();
                    } else if (0 == state && 1 == isSuccessful) {
                        Toast.makeText(StandardDialogActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void setPriceList(List<GoodsDetailBean.NormListBean.NormTypeListBean> list, String unit) {
        priceList.clear();
        priceList.addAll(list);
        priceAdapter.unit = unit;
        priceAdapter.notifyDataSetChanged();
        Utility.setListViewHeightBasedOnChildren(lvPrice);
    }

    /**
     * 计算价格
     *
     * @param list        价格区间列表
     * @param inputWeight 输入的重量
     * @param tvMoney     计算的价格
     */
    public static void calculatePrice(List<GoodsDetailBean.NormListBean.NormTypeListBean> list, int inputWeight,
                                      TextView tvMoney) {
        double money = 0;
        for (int i = 0; i < list.size(); i++) {
            GoodsDetailBean.NormListBean.NormTypeListBean priceBean = list.get(i);
            String kg_start = priceBean.getStKg();
            String kg_end = priceBean.getEnKg();
            if (inputWeight >= Integer.parseInt(kg_start) && inputWeight < Integer.parseInt(kg_end)) {
                money = inputWeight * priceBean.getPrice();
                tvMoney.setText(StringUtil.formatePrice(money));
                return;
            } else {
                if (i == list.size() - 1) {
                    money = inputWeight * priceBean.getPrice();
                    tvMoney.setText(StringUtil.formatePrice(money));
                    return;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!StringUtil.isEmpty(selectType) && !StringUtil.isEmpty(etWeight.getEditableText().toString().trim
                ()) && 0 != Float.parseFloat(tvMoney.getText().toString().trim()) && 0 != Integer.parseInt
                (etWeight.getEditableText().toString().trim())) {
            Intent intent = new Intent();
            intent.putExtra("type", selectType);
            intent.putExtra("weight", etWeight.getEditableText().toString().trim());
            intent.putExtra("price", tvMoney.getText().toString().trim());
            setResult(RESULT_OK, intent);
        }
        finish();
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

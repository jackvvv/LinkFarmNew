package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.FillOrderActivity;
import sinia.com.linkfarmnew.activity.GoodsDetailActivity;
import sinia.com.linkfarmnew.activity.LoginActivity;
import sinia.com.linkfarmnew.activity.StandardDialogActivity;
import sinia.com.linkfarmnew.adapter.CartRecommendAdapter;
import sinia.com.linkfarmnew.adapter.MyExpandableListAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.CartBean;
import sinia.com.linkfarmnew.bean.GoodsBean;
import sinia.com.linkfarmnew.bean.GroupBean;
import sinia.com.linkfarmnew.bean.JsonBean;
import sinia.com.linkfarmnew.myinterface.CheckInterface;
import sinia.com.linkfarmnew.myinterface.ModifyCountInterface;
import sinia.com.linkfarmnew.utils.Constants;
import sinia.com.linkfarmnew.utils.MyApplication;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.view.MyGridView;

/**
 * Created by 忧郁的眼神 on 2016/8/4.
 */
public class ShopCartFragment extends BaseFragment implements CheckInterface, ModifyCountInterface {
    @Bind(R.id.tv_edit)
    TextView tvEdit;
    @Bind(R.id.rlTitleBar)
    RelativeLayout rlTitleBar;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.ivSelectAll)
    CheckBox ivSelectAll;
    @Bind(R.id.a)
    TextView a;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.tv_countMoney)
    TextView tvCountMoney;
    @Bind(R.id.btnSettle)
    TextView btnSettle;
    @Bind(R.id.rlBottomBar)
    RelativeLayout rlBottomBar;
    @Bind(R.id.ivDeleteAll)
    CheckBox ivDeleteAll;
    @Bind(R.id.aa)
    TextView aa;
    @Bind(R.id.btn_delete)
    TextView btnDelete;
    @Bind(R.id.rl_delete)
    RelativeLayout rlDelete;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.rl_cart)
    RelativeLayout rlCart;
    private MyGridView gridView;
    private CartRecommendAdapter recommendAdapter;
    private View rootView, footView;
    private MyExpandableListAdapter adapter;
    private List<CartBean.MerchantitemsBean> groupList = new ArrayList<CartBean.MerchantitemsBean>();
    private List<CartBean.MerchantitemsBean.GoodsItemsBean> childList = new ArrayList<>();
    private boolean checkAll = false, isEdit = false;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private List<CartBean.HisitemsBean> recommendList = new ArrayList<>();
    private AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cart, null);
        ButterKnife.bind(this, rootView);
        inintData();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.getInstance().getBoolValue("is_login")) {
            getCartListData();
            rlCart.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
        } else {
            rlCart.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);
        }
    }

    private void getCartListData() {
        showLoad("加载中...");
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().getStringValue("userId"));
        Log.i("tag", Constants.BASE_URL + "carList&" + params);
        client.post(Constants.BASE_URL + "carList", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, String s) {
                super.onSuccess(i, s);
                dismiss();
                Gson gson = new Gson();
                if (s.contains("isSuccessful")
                        && s.contains("state")) {
                    CartBean bean = gson.fromJson(s, CartBean.class);
                    int state = bean.getState();
                    int isSuccessful = bean.getIsSuccessful();
                    if (0 == state && 0 == isSuccessful) {
                        if (bean != null) {
                            virtualData(bean);
                            recommendList.clear();
                            recommendList.addAll(bean.getHisitems());
                            recommendAdapter.notifyDataSetChanged();
                            if (recommendList.size() == 0) {
                                footView.setVisibility(View.GONE);
                            } else {
                                footView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private void virtualData(CartBean cartBean) {
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        groupList.addAll(cartBean.getMerchantitems());

        for (CartBean.MerchantitemsBean bean : groupList) {
            adapter.groups.add(bean);
            List<CartBean.MerchantitemsBean.GoodsItemsBean> data = new ArrayList<CartBean.MerchantitemsBean
                    .GoodsItemsBean>();
            data.addAll(bean.getItems());
            adapter.childs.put(bean.getMerName(), data);

//            for (int i = 0; i < data.size(); i++) {
//                CartBean.MerchantitemsBean.GoodsItemsBean goodBean = data.get(i);
//                goodBean.setGoodNum(1);
//            }
        }
        adapter.groups = groupList;

        if (checkAll) {
            ivSelectAll.setChecked(true);
            tvCountMoney.setText(StringUtil.formatePrice(allPrice()) + "");
            btnSettle.setText("去结算" + "(" + allSize() + ")");
        } else {
            ivSelectAll.setChecked(false);
            tvCountMoney.setText(0.00 + "");
            btnSettle.setText("去结算" + "(" + 0 + ")");
        }
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }

    private void inintData() {
        footView = LayoutInflater.from(getActivity()).inflate(R.layout.view_cart_recommend, null);
        gridView = (MyGridView) footView.findViewById(R.id.gridView);
        recommendAdapter = new CartRecommendAdapter(getActivity(), recommendList);
        gridView.setAdapter(recommendAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String goodId = recommendList.get(i).getGoodId();
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("goodId", goodId);
                startActivity(intent);
            }
        });
        adapter = new MyExpandableListAdapter(getActivity());
        adapter.setCheckInterface(this);
        adapter.setModifyCountInterface(this);
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.addFooterView(footView);
    }

    private void expandAllGroup() {
        for (int i = 0; i < groupList.size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @OnClick({R.id.tv_edit, R.id.ivSelectAll, R.id.btnSettle, R.id.ivDeleteAll, R.id.btn_delete, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                if (isEdit) {
                    isEdit = false;
                    tvEdit.setText("编辑");
                    rlDelete.setVisibility(View.INVISIBLE);
                    rlBottomBar.setVisibility(View.VISIBLE);
                    if (ivDeleteAll.isChecked()) {
                        ivSelectAll.setChecked(true);
                        doCheckAll();
                    }
                    footView.setVisibility(View.VISIBLE);
                } else {
                    isEdit = true;
                    tvEdit.setText("完成");
                    rlDelete.setVisibility(View.VISIBLE);
                    rlBottomBar.setVisibility(View.INVISIBLE);
//                    if (ivSelectAll.isChecked()) {
//                        ivDeleteAll.setChecked(true);
//                        doDeleteAll();
//                    }
                    footView.setVisibility(View.GONE);
                }
                break;
            case R.id.ivSelectAll:
                doCheckAll();
                break;
            case R.id.btnSettle:
                if (allSize() == 0) {
                    showToast("请选择要结算的商品");
                } else {
                    showToast("选择了" + allSize() + "件商品，共" + "需要支付" + allPrice() + "元");
                    Intent intent = new Intent(getActivity(), FillOrderActivity.class);
                    intent.putExtra("norm", "-1");
                    intent.putExtra("num", connectBuyNum());
                    intent.putExtra("price", allPrice() + "");
                    intent.putExtra("connectPrices", connectPrice());
                    intent.putExtra("goodId", "-1");
                    intent.putExtra("selectGoodsImage", (Serializable) getSelectGoods());
                    intent.putExtra("otherId", connectCartIds());//购物车
                    intent.putExtra("choose", connectShopIds());//商户id
                    intent.putExtra("type", "2");//1.直接购买 2.购物车
                    startActivity(intent);
                }
                break;
            case R.id.ivDeleteAll:
                doDeleteAll();
                break;
            case R.id.tv_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_delete:
                if (totalCount == 0) {
                    showToast("请选择要删除的商品");
                } else {
//                    deleteGoods();
                    deleteCart();
                }
                break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    private void deleteGoods() {
        List<CartBean.MerchantitemsBean> toDelGroups = new ArrayList<>();//待删除的组
        List<CartBean.MerchantitemsBean.GoodsItemsBean> toDelGoods = new ArrayList<>();//待删除的商品
        List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = new ArrayList<>();
        for (int i = 0; i < adapter.groups.size(); i++) {
            CartBean.MerchantitemsBean groupBean = adapter.groups.get(i);
            if (groupBean.isChecked()) {
                toDelGroups.add(groupBean);
            }
            toDelGoods = new ArrayList<>();//待删除的商品
            goodsList = adapter.childs.get(groupBean.getMerName());
            for (int j = 0; j < goodsList.size(); j++) {
                if (goodsList.get(j).isChecked()) {
                    toDelGoods.add(goodsList.get(j));
                }
            }
            goodsList.removeAll(toDelGoods);
        }
        adapter.groups.removeAll(toDelGroups);
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    private void deleteCart() {
        showLoad("");
        RequestParams params = new RequestParams();
        params.put("otherId", connectGoodsId());
        Log.i("tag", Constants.BASE_URL + "delCar&" + params);
        client.post(Constants.BASE_URL + "delCar", params, new AsyncHttpResponseHandler() {
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
                        showToast("删除成功");
                        getCartListData();
                    } else if (0 == state && 1 == isSuccessful) {
                        showToast("请求失败");
                    }
                }
            }
        });
    }

    private List<String> getSelectGoods() {
        List<String> selectGoodsImage = new ArrayList<>();//待删除的商品
        for (int i = 0; i < adapter.groups.size(); i++) {
            CartBean.MerchantitemsBean groupBean = adapter.groups.get(i);
            List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
            for (int j = 0; j < goodsList.size(); j++) {
                if (goodsList.get(j).isChecked()) {
                    selectGoodsImage.add(goodsList.get(j).getGoodImage());
                }
            }
        }
        return selectGoodsImage;
    }

    private String connectGoodsId() {
        List<String> toConnectGoodsIdsList = new ArrayList<>();
        for (int i = 0; i < adapter.childs.size(); i++) {
            String key = adapter.groups.get(i).getMerName();
            List<CartBean.MerchantitemsBean.GoodsItemsBean> data = adapter.childs.get(key);
            for (CartBean.MerchantitemsBean.GoodsItemsBean goodsBean : data) {
                if (goodsBean.isChecked()) {
                    toConnectGoodsIdsList.add(goodsBean.getId());
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int s = 0; s < toConnectGoodsIdsList.size(); s++) {
            sb.append(toConnectGoodsIdsList.get(s)).append(";");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private String connectShopIds() {
        List<String> toConnectShopIdList = new ArrayList<>();//待拼接的商户id
        for (int i = 0; i < adapter.groups.size(); i++) {
            CartBean.MerchantitemsBean groupBean = adapter.groups.get(i);
            if (groupBean.isChecked()) {
                toConnectShopIdList.add(groupBean.getMerchantId());
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int s = 0; s < toConnectShopIdList.size(); s++) {
            sb.append(toConnectShopIdList.get(s)).append(";");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private String connectCartIds() {
        String ids = "";
        for (int i = 0; i < adapter.childs.size(); i++) {
            String key = adapter.groups.get(i).getMerName();
            List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(key);
            boolean flag = false;
            for (CartBean.MerchantitemsBean.GoodsItemsBean bean : goodsList) {
                if (bean.isChecked()) {
                    flag = true;
                    ids = ids + bean.getId() + ";";
                }
            }
            if (flag) {
                ids = ids + "-";
            }
        }
        ids.substring(0, ids.length() - 1);
        return ids;
    }

    private String connectBuyNum() {
        String nums = "";
        for (int i = 0; i < adapter.childs.size(); i++) {
            String key = adapter.groups.get(i).getMerName();
            List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(key);
            boolean flag = false;
            for (CartBean.MerchantitemsBean.GoodsItemsBean bean : goodsList) {
                if (bean.isChecked()) {
                    flag = true;
                    nums = nums + bean.getGoodNum() + ";";
                }
            }
            if (flag) {
                nums = nums + "-";
            }
        }
        return nums;
    }

    private String connectPrice() {
        String prices = "";
        for (int i = 0; i < adapter.childs.size(); i++) {
            String key = adapter.groups.get(i).getMerName();
            List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(key);
            boolean flag = false;
            for (CartBean.MerchantitemsBean.GoodsItemsBean bean : goodsList) {
                if (bean.isChecked()) {
                    flag = true;
                    prices = prices + bean.getGoodNum() * bean.getPrice() + ";";
                }
            }
            if (flag) {
                prices = prices + "-";
            }
        }
        return prices;
    }

    /**
     * 全选与反选---结算
     */
    private void doCheckAll() {
        if (null != adapter.groups && 0 != adapter.groups.size() && null != adapter.childs && 0
                != adapter.childs.size()) {
            for (int i = 0; i < adapter.groups.size(); i++) {
                adapter.groups.get(i).setChecked(ivSelectAll.isChecked());
                CartBean.MerchantitemsBean group = adapter.groups.get(i);
                List<CartBean.MerchantitemsBean.GoodsItemsBean> childs = adapter.childs.get(group.getMerName());
                for (int j = 0; j < childs.size(); j++) {
                    childs.get(j).setChecked(ivSelectAll.isChecked());
                }
            }
            tvCountMoney.setText(allPrice() + "");
            btnSettle.setText("去结算" + "(" + allSize() + ")");
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 全选与反选---删除
     */
    private void doDeleteAll() {
        if (null != adapter.groups && 0 != adapter.groups.size() && null != adapter.childs && 0
                != adapter.childs.size()) {
            for (int i = 0; i < adapter.groups.size(); i++) {
                adapter.groups.get(i).setChecked(ivDeleteAll.isChecked());
                CartBean.MerchantitemsBean group = adapter.groups.get(i);
                List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(group.getMerName());
                for (int j = 0; j < goodsList.size(); j++) {
                    goodsList.get(j).setChecked(ivDeleteAll.isChecked());
                    CartBean.MerchantitemsBean.GoodsItemsBean goods = goodsList.get(j);
                    if (goods.isChecked()) {
                        totalCount++;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    // 选中的价格
    private double allPrice() {
        double allPrice = 0;
        if (null != adapter.childs && 0 != adapter.childs.size() && null != adapter.groups && 0
                != adapter.groups.size()) {
            for (int i = 0; i < adapter.childs.size(); i++) {
                String key = adapter.groups.get(i).getMerName();
                List<CartBean.MerchantitemsBean.GoodsItemsBean> data = adapter.childs.get(key);
                for (CartBean.MerchantitemsBean.GoodsItemsBean bean : data) {
                    if (bean.isChecked()) {
                        allPrice = allPrice + bean.getPrice() * bean.getNum();
                    }
                }
            }
        }
        return allPrice;
    }

    // 选中的数量
    private int allSize() {
        int allSize = 0;
        if (null != adapter.childs && 0 != adapter.childs.size() && null != adapter.groups && 0
                != adapter.groups.size()) {
            for (int i = 0; i < adapter.childs.size(); i++) {
                String key = adapter.groups.get(i).getMerName();
                List<CartBean.MerchantitemsBean.GoodsItemsBean> data = adapter.childs.get(key);
                for (CartBean.MerchantitemsBean.GoodsItemsBean bean : data) {
                    if (bean.isChecked()) {
//                        allSize = allSize + bean.getGoodNum();
                        allSize++;
                    }
                }
            }
        }
        return allSize;
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean
            isChecked) {
        CartBean.MerchantitemsBean.GoodsItemsBean goods = (CartBean.MerchantitemsBean.GoodsItemsBean) adapter
                .getChild(groupPosition, childPosition);
        int currentCount = goods.getNum();
        currentCount++;
        goods.setNum(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean
            isChecked) {
        CartBean.MerchantitemsBean.GoodsItemsBean goods = (CartBean.MerchantitemsBean.GoodsItemsBean) adapter
                .getChild(groupPosition, childPosition);
        int currentCount = goods.getNum();
        if (currentCount == 1)
            return;
        currentCount--;
        goods.setNum(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        CartBean.MerchantitemsBean groupBean = adapter.groups.get(groupPosition);
        List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
        for (int i = 0; i < goodsList.size(); i++) {
            goodsList.get(i).setChecked(isChecked);
        }
        if (isAllCheck()) {
            ivSelectAll.setChecked(true);
        } else {
            ivSelectAll.setChecked(false);
        }
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        CartBean.MerchantitemsBean groupBean = adapter.groups.get(groupPosition);
        List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).isChecked() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState) {
            groupBean.setChecked(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            groupBean.setChecked(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            ivSelectAll.setChecked(true);
        else
            ivSelectAll.setChecked(false);
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    private boolean isAllCheck() {
        for (CartBean.MerchantitemsBean group : adapter.groups) {
            if (!group.isChecked())
                return false;
        }
        return true;
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculateMoneyAndNum() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < adapter.groups.size(); i++) {
            CartBean.MerchantitemsBean groupBean = adapter.groups.get(i);
            List<CartBean.MerchantitemsBean.GoodsItemsBean> goodsList = adapter.childs.get(groupBean.getMerName());
            for (int j = 0; j < goodsList.size(); j++) {
                CartBean.MerchantitemsBean.GoodsItemsBean goods = goodsList.get(j);
                if (goods.isChecked()) {
                    totalCount++;
                    totalPrice += goods.getPrice() * goods.getNum();
                }
            }
        }
        tvCountMoney.setText(totalPrice + "");
        btnSettle.setText("去结算" + "(" + totalCount + ")");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

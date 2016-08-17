package sinia.com.linkfarmnew.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.FillOrderActivity;
import sinia.com.linkfarmnew.activity.GoodsDetailActivity;
import sinia.com.linkfarmnew.adapter.HomeRecommendAdapter;
import sinia.com.linkfarmnew.adapter.MyExpandableListAdapter;
import sinia.com.linkfarmnew.base.BaseFragment;
import sinia.com.linkfarmnew.bean.GoodsBean;
import sinia.com.linkfarmnew.bean.GroupBean;
import sinia.com.linkfarmnew.bean.HomePageBean;
import sinia.com.linkfarmnew.myinterface.CheckInterface;
import sinia.com.linkfarmnew.myinterface.ModifyCountInterface;
import sinia.com.linkfarmnew.utils.Utility;
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
    private MyGridView gridView;
    private HomeRecommendAdapter recommendAdapter;
    private View rootView, footView;
    private MyExpandableListAdapter adapter;
    private List<GroupBean> groupList = new ArrayList<GroupBean>();
    private List<GoodsBean> childList = new ArrayList<GoodsBean>();
    private boolean checkAll = false, isEdit = false;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private List<HomePageBean.RecarrayItemsBean> recommendList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    // 全选按钮是否选中，总价、运费变更
                    for (int i = 0; i < adapter.childs.size(); i++) {
                        String key = adapter.groups.get(i).getShopName();
                        List<GoodsBean> data = adapter.childs.get(key);
                        for (GoodsBean bean : data) {
                            if (bean.isChecked()) {
                            } else {
                                checkAll = false;
                            }
                        }
                    }
                    tvCountMoney.setText(allPrice() + "");
                    btnSettle.setText("去结算" + "(" + allSize() + ")");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_cart, null);
        ButterKnife.bind(this, rootView);
        inintData();
        virtualData();
        return rootView;
    }

    private void virtualData() {
        groupList.clear();
        childList.clear();
        for (int i = 0; i < 2; i++) {
            GroupBean bean = new GroupBean();
            bean.setShopName("第" + i + "蔬菜连锁店");
            groupList.add(bean);
            for (int j = 0; j <= i; j++) {
                GoodsBean goods = new GoodsBean();
                goods.setCarId(j + "");
                goods.setGoodName("iPhone" + j);
                goods.setGoodPrice("1");
                goods.setGoodNum("1");
                childList.add(goods);
            }
            adapter.groups.add(bean);
            adapter.childs.put(groupList.get(i).getShopName(), childList);
        }
        if (checkAll) {
            ivSelectAll.setChecked(true);
            tvCountMoney.setText(allPrice() + "");
            btnSettle.setText("去结算" + "(" + allSize() + ")");
        } else {
            ivSelectAll.setChecked(false);
            tvCountMoney.setText(0.0 + "");
            btnSettle.setText("去结算" + "(" + 0 + ")");
        }
        adapter.notifyDataSetChanged();
        expandAllGroup();
    }

    private void inintData() {
        footView = LayoutInflater.from(getActivity()).inflate(R.layout.view_cart_recommend, null);
        gridView = (MyGridView) footView.findViewById(R.id.gridView);
        recommendAdapter = new HomeRecommendAdapter(getActivity(),recommendList);
        gridView.setAdapter(recommendAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
        adapter = new MyExpandableListAdapter(getActivity(), handler);
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

    @OnClick({R.id.tv_edit, R.id.ivSelectAll, R.id.btnSettle, R.id.ivDeleteAll, R.id.btn_delete})
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
                } else {
                    isEdit = true;
                    tvEdit.setText("完成");
                    rlDelete.setVisibility(View.VISIBLE);
                    rlBottomBar.setVisibility(View.INVISIBLE);
                    if (ivSelectAll.isChecked()) {
                        ivDeleteAll.setChecked(true);
                        doDeleteAll();
                    }
                }
                break;
            case R.id.ivSelectAll:
                doCheckAll();
                break;
            case R.id.btnSettle:
                if (allSize() == 0) {
                    showToast("请选择要支付的商品");
                } else {
                    showToast("选择了" + allSize() + "件商品，共" + "需要支付" + allPrice() + "元");
                    Intent intent = new Intent(getActivity(), FillOrderActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ivDeleteAll:
                doDeleteAll();
                break;
            case R.id.btn_delete:
                if (totalCount == 0) {
                    showToast("请选择要删除的商品");
                } else {
                    deleteGoods();
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
        List<GroupBean> toDelGroups = new ArrayList<>();//待删除的组
        for (int i = 0; i < adapter.groups.size(); i++) {
            GroupBean groupBean = adapter.groups.get(i);
            if (groupBean.isChecked()) {
                toDelGroups.add(groupBean);
            }
            List<GoodsBean> toDelGoods = new ArrayList<>();//待删除的商品
            List<GoodsBean> goodsList = adapter.childs.get(groupBean.getShopName());
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

    /**
     * 全选与反选---结算
     */
    private void doCheckAll() {
        if (null != adapter.groups && 0 != adapter.groups.size() && null != adapter.childs && 0
                != adapter.childs.size()) {
            for (int i = 0; i < adapter.groups.size(); i++) {
                adapter.groups.get(i).setChecked(ivSelectAll.isChecked());
                GroupBean group = adapter.groups.get(i);
                List<GoodsBean> childs = adapter.childs.get(group.getShopName());
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
                GroupBean group = adapter.groups.get(i);
                List<GoodsBean> goodsList = adapter.childs.get(group.getShopName());
                for (int j = 0; j < goodsList.size(); j++) {
                    goodsList.get(j).setChecked(ivDeleteAll.isChecked());
                    GoodsBean goods = goodsList.get(j);
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
                String key = adapter.groups.get(i).getShopName();
                List<GoodsBean> data = adapter.childs.get(key);
                for (GoodsBean bean : data) {
                    if (bean.isChecked()) {
                        allPrice = allPrice + Double.parseDouble(bean.getGoodPrice());
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
                String key = adapter.groups.get(i).getShopName();
                List<GoodsBean> data = adapter.childs.get(key);
                for (GoodsBean bean : data) {
                    if (bean.isChecked()) {
                        allSize = allSize + Integer.parseInt(bean.getGoodNum());
                    }
                }
            }
        }
        return allSize;
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean
            isChecked) {
        GoodsBean goods = (GoodsBean) adapter.getChild(groupPosition, childPosition);
        int currentCount = Integer.parseInt(goods.getGoodNum());
        currentCount++;
        goods.setGoodNum(currentCount + "");
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean
            isChecked) {
        GoodsBean goods = (GoodsBean) adapter.getChild(groupPosition, childPosition);
        int currentCount = Integer.parseInt(goods.getGoodNum());
        if (currentCount == 1)
            return;
        currentCount--;
        goods.setGoodNum(currentCount + "");
        ((TextView) showCountView).setText(currentCount + "");
        adapter.notifyDataSetChanged();
        calculateMoneyAndNum();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        GroupBean groupBean = adapter.groups.get(groupPosition);
        List<GoodsBean> goodsList = adapter.childs.get(groupBean.getShopName());
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
        GroupBean groupBean = adapter.groups.get(groupPosition);
        List<GoodsBean> goodsList = adapter.childs.get(groupBean.getShopName());
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
        for (GroupBean group : adapter.groups) {
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
            GroupBean groupBean = adapter.groups.get(i);
            List<GoodsBean> goodsList = adapter.childs.get(groupBean.getShopName());
            for (int j = 0; j < goodsList.size(); j++) {
                GoodsBean goods = goodsList.get(j);
                if (goods.isChecked()) {
                    totalCount++;
                    totalPrice += Double.parseDouble(goods.getGoodPrice()) * Double.parseDouble
                            (goods.getGoodNum());
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

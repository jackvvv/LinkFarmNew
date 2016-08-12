package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.bean.GoodsBean;
import sinia.com.linkfarmnew.bean.GroupBean;
import sinia.com.linkfarmnew.myinterface.CheckInterface;
import sinia.com.linkfarmnew.myinterface.IsGroupChecked;
import sinia.com.linkfarmnew.myinterface.ModifyCountInterface;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/11.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    public List<GroupBean> groups = new ArrayList<GroupBean>();
    public HashMap<String, List<GoodsBean>> childs = new HashMap<String, List<GoodsBean>>();
    private Handler handler;
    //    private IsGroupChecked isGroupChecked;
    private ModifyCountInterface modifyCountInterface;
    private CheckInterface checkInterface;

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public MyExpandableListAdapter(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childs.get(groups.get(i).getShopName()).size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childs.get(groups.get(i).getShopName()).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cart_group, null);
        }
        CheckBox ivCheckGroup = ViewHolder.get(view, R.id.ivCheckGroup);
        TextView tv_shopname = ViewHolder.get(view, R.id.tv_shopname);
        final GroupBean groupBean = groups.get(i);
        tv_shopname.setText(groupBean.getShopName());
        if (groupBean.isChecked()) {
            ivCheckGroup.setChecked(true);
        } else {
            ivCheckGroup.setChecked(false);
        }
        ivCheckGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (((CheckBox) v).isChecked()) {
//                    groups.get(i).setChecked(true);
//                    isGroupChecked.isChekgroup(i, true);
//                } else {
//                    isGroupChecked.isChekgroup(i, false);
//                }
//                handler.sendEmptyMessage(100);
                groupBean.setChecked(((CheckBox) v).isChecked());
                checkInterface.checkGroup(i, ((CheckBox) v).isChecked());// 暴露组选接口
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_cart_child, null);
        }
        final CheckBox ivCheckChild = ViewHolder.get(view, R.id.ivCheckChild);
        ImageView img_goods = ViewHolder.get(view, R.id.img_goods);
        RelativeLayout rl_jia = ViewHolder.get(view, R.id.rl_jia);
        RelativeLayout rl_jian = ViewHolder.get(view, R.id.rl_jian);
        TextView tv_goodsname = ViewHolder.get(view, R.id.tv_goodsname);
        TextView tv_weight = ViewHolder.get(view, R.id.tv_weight);
        final TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        final TextView tv_num = ViewHolder.get(view, R.id.tv_num);

        final GoodsBean goodsBean = childs.get(groups.get(i).getShopName()).get(i1);
        final double singlePrice = Double.parseDouble(goodsBean.getGoodPrice())
                / Integer.parseInt(goodsBean.getGoodNum());

        tv_goodsname.setText(goodsBean.getGoodName());
        tv_price.setText("¥ " + goodsBean.getGoodPrice());
        tv_num.setText(goodsBean.getGoodNum());
        if (goodsBean.isChecked()) {
            ivCheckChild.setChecked(true);
        } else {
            ivCheckChild.setChecked(false);
        }
        rl_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int num1 = Integer.parseInt(tv_num.getText().toString());
//                if (num1 > 1) {
//                    int num2 = num1 - 1;
//                    tv_num.setText(num2 + "");
//                    goodsBean.setGoodNum(num2 + "");
//                    goodsBean.setGoodPrice(singlePrice * num2 + "");
//                    tv_price.setText("¥ " + goodsBean.getGoodPrice());
//                    handler.sendEmptyMessage(100);
//                }
                modifyCountInterface.doDecrease(i, i1, tv_num, ivCheckChild.isChecked());
            }
        });
        rl_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int num1 = Integer.parseInt(tv_num.getText().toString());
//                int num2 = num1 + 1;
//                tv_num.setText(num2 + "");
//                goodsBean.setGoodNum(num2 + "");
//                goodsBean.setGoodPrice(singlePrice * num2 + "");
//                tv_price.setText("¥ " + goodsBean.getGoodPrice());
//                handler.sendEmptyMessage(100);
                modifyCountInterface.doIncrease(i, i1, tv_num, ivCheckChild.isChecked());
            }
        });
        ivCheckChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (((CheckBox) v).isChecked()) {
//                    //二级分类全部选中，一级分类全选
//                    goodsBean.setChecked(true);
//                    oneClassSetCheck(i);
//                } else {
//                    //二级分类取消选中，一级分类取消选中
//                    goodsBean.setChecked(false);
//                    oneClassSetUnCheck(i);
//                }
//                handler.sendEmptyMessage(100);
                goodsBean.setChecked(((CheckBox) v).isChecked());
                ivCheckChild.setChecked(((CheckBox) v).isChecked());
                checkInterface.checkChild(i, i1,
                        ((CheckBox) v).isChecked());// 暴露子选接口
            }
        });
        return view;
    }

    private void oneClassSetCheck(int groupPosition) {
        boolean isCheckAll = false;
        int j = 0;
        for (int i = 0; i < childs.get(groups.get(groupPosition).getShopName()).size(); i++) {
            GoodsBean goodsBean = childs.get(groups.get(groupPosition).getShopName()).get(i);
            if (goodsBean.isChecked()) {
                j++;
            }
            if (j == childs.get(groups.get(groupPosition).getShopName()).size()) {
                isCheckAll = true;
            }
        }
        if (isCheckAll) {
            groups.get(groupPosition).setChecked(true);
            notifyDataSetChanged();
        }
    }

    private void oneClassSetUnCheck(int groupPosition) {
        boolean isCheckAll = true;
        for (int i = 0; i < childs.get(groups.get(groupPosition).getShopName()).size(); i++) {
            GoodsBean goodsBean = childs.get(groups.get(groupPosition).getShopName()).get(i);
            if (goodsBean.isChecked()) {
                isCheckAll = false;
            }
        }
        if (isCheckAll) {
            groups.get(groupPosition).setChecked(false);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}

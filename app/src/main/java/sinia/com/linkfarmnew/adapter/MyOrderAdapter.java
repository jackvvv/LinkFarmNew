package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.ApplyReturnActivity;
import sinia.com.linkfarmnew.activity.OrderDetailActivity;
import sinia.com.linkfarmnew.activity.PayActivity;
import sinia.com.linkfarmnew.activity.ServiceCommentActivity;
import sinia.com.linkfarmnew.bean.MyOrderListBean;
import sinia.com.linkfarmnew.myinterface.OrderOperatorInterface;
import sinia.com.linkfarmnew.utils.StringUtil;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class MyOrderAdapter extends BaseAdapter {

    private Context context;

    private GoodImageAdapter adapter;
    private List<MyOrderListBean.OrderBean> list;

    public MyOrderAdapter(Context context, List<MyOrderListBean.OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_order, null);
        }
        TextView tv_shopname = ViewHolder.get(view, R.id.tv_shopname);
        TextView tv_orderstatus = ViewHolder.get(view, R.id.tv_orderstatus);
        TextView tv_num = ViewHolder.get(view, R.id.tv_num);
        TextView tv_price = ViewHolder.get(view, R.id.tv_price);
        TextView btn1 = ViewHolder.get(view, R.id.btn1);
        TextView btn2 = ViewHolder.get(view, R.id.btn2);
        LinearLayout item = ViewHolder.get(view, R.id.item);
        GridView gv_goods = ViewHolder.get(view, R.id.gv_goods);

        List<MyOrderListBean.OrderBean.OrderImageitemBean> orderImageList = new ArrayList<>();
        List<String> imgList = new ArrayList<>();
        orderImageList = list.get(i).getOrderImageitem();
        for (int j = 0; j < orderImageList.size(); j++) {
            imgList.add(orderImageList.get(j).getImage());
        }
        adapter = new GoodImageAdapter(context, imgList);
        gv_goods.setAdapter(adapter);
        tv_shopname.setText(list.get(i).getMerName());
        tv_num.setText("共" + list.get(i).getGoodNum() + "件商品 实付款：");
        tv_price.setText("¥ " + StringUtil.formatePrice(list.get(i).getPrice()));
        final String orderId = list.get(i).getOrderId();
        final String merImage = list.get(i).getMerImage();
        final int orderStatus = list.get(i).getOrderStatus();
        final int commentStatus = list.get(i).getComStatus();
        switch (orderStatus) {
            case 1:
                tv_orderstatus.setText("待付款");
                btn1.setText("取消订单");
                btn2.setText("去支付");
                btn2.setBackgroundResource(R.drawable.green_round_edit);
                btn1.setVisibility(View.VISIBLE);
                btn2.setClickable(true);
                break;
            case 2:
                tv_orderstatus.setText("待发货");
                //申请退货
                btn1.setVisibility(View.GONE);
                btn2.setText("申请退货");
                btn2.setBackgroundResource(R.drawable.green_round_edit);
                btn2.setClickable(true);
                break;
            case 3:
                tv_orderstatus.setText("待收货");
                btn1.setText("申请退货");
                btn1.setTextColor(Color.parseColor("#962722"));
                btn1.setBackgroundResource(R.drawable.red_round_edit);
                btn2.setText("确认送达");
                btn2.setBackgroundResource(R.drawable.green_round_edit);
                btn1.setVisibility(View.VISIBLE);
                btn2.setClickable(true);
                break;
            case 4:
                if (1 == commentStatus) {
                    tv_orderstatus.setText("已评价");
                    btn2.setBackgroundResource(R.drawable.gray_round_edit);
                    btn2.setClickable(false);
                } else {
                    tv_orderstatus.setText("待评价");
                    btn2.setBackgroundResource(R.drawable.green_round_edit);
                }
                btn1.setVisibility(View.GONE);
                btn2.setText("服务评价");
                btn2.setClickable(true);
                break;
            case 5:
                tv_orderstatus.setText("审核中");
                //灰色的申请退货
                btn1.setVisibility(View.GONE);
                btn2.setText("申请退货");
                btn2.setBackgroundResource(R.drawable.gray_round_edit);
                btn2.setClickable(false);
                break;
            case 6:
                tv_orderstatus.setText("审核成功");
                //删除订单
                btn2.setText("删除订单");
                btn2.setBackgroundResource(R.drawable.green_round_edit);
                btn1.setVisibility(View.GONE);
                btn2.setClickable(true);
                break;
            case 7:
                tv_orderstatus.setText("审核失败");
                //申请退货
                btn1.setVisibility(View.GONE);
                btn2.setText("再次申请");
                btn2.setBackgroundResource(R.drawable.green_round_edit);
                btn2.setClickable(true);
                break;
        }

        final OrderOperatorInterface orderOperatorInterface = (OrderOperatorInterface) context;

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderStatus == 1) {
                    //取消订单
                    orderOperatorInterface.cancelOrder(orderId, i);
                }
                if (orderStatus == 3) {
                    //申请退货
                    Intent intent = new Intent(context, ApplyReturnActivity.class);
                    intent.putExtra("orderId", orderId);
                    context.startActivity(intent);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderStatus == 1) {
                    //支付
                    Intent intent = new Intent(context, PayActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("payMoney", list.get(i).getPrice() + "");
                    intent.putExtra("coupleId", "-1");
                    intent.putExtra("flag", "2");
                    context.startActivity(intent);
                }
                if (orderStatus == 2) {
                    //申请退货退款
                    Intent intent = new Intent(context, ApplyReturnActivity.class);
                    intent.putExtra("orderId", orderId);
                    context.startActivity(intent);
                }
                if (orderStatus == 3) {
                    //确认送达
                    orderOperatorInterface.confirmOrderSend(orderId, i);
                }
                if (orderStatus == 4 && commentStatus == 2) {
                    //评价
                    Intent intent = new Intent(context, ServiceCommentActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("merImage", merImage);
                    context.startActivity(intent);
                }
                if (orderStatus == 6) {
                    //删除订单
                    orderOperatorInterface.cancelOrder(orderId, i);
                }
                if (orderStatus == 7) {
                    //再次申请退货
                    Intent intent = new Intent(context, ApplyReturnActivity.class);
                    intent.putExtra("orderId", orderId);
                    context.startActivity(intent);
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("merImage", merImage);
                context.startActivity(intent);
            }
        });
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("merImage", merImage);
                context.startActivity(intent);
            }
        });
        gv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("merImage", merImage);
                context.startActivity(intent);
            }
        });
        return view;
    }
}

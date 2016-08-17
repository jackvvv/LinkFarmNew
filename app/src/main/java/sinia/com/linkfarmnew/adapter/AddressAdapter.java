package sinia.com.linkfarmnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sinia.com.linkfarmnew.R;
import sinia.com.linkfarmnew.activity.EditAddressActivity;
import sinia.com.linkfarmnew.bean.AddressListBean;
import sinia.com.linkfarmnew.utils.ViewHolder;

/**
 * Created by 忧郁的眼神 on 2016/8/5.
 */
public class AddressAdapter extends BaseAdapter {

    private Context context;

    private List<AddressListBean.AddressBean> list;

    public AddressAdapter(Context context, List<AddressListBean.AddressBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_address, null);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_tel = ViewHolder.get(view, R.id.tv_tel);
        TextView tv_address = ViewHolder.get(view, R.id.tv_address);
        ImageView img_modify = ViewHolder.get(view, R.id.img_modify);

        final String addressId = list.get(i).getAddId();
        img_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                intent.putExtra("addressId", addressId);
                intent.putExtra("name", list.get(i).getAddName());
                intent.putExtra("tel", list.get(i).getAddTelephone());
                intent.putExtra("address", list.get(i).getAddAddress());
                intent.putExtra("area", list.get(i).getAddArea());
                context.startActivity(intent);
            }
        });
        tv_name.setText(list.get(i).getAddName());
        tv_tel.setText(list.get(i).getAddTelephone());
        tv_address.setText(list.get(i).getAddArea() + list.get(i).getAddAddress());
        return view;
    }
}

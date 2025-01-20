package com.example.applicationminishoplaba3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.applicationminishoplaba3.R;
import com.example.applicationminishoplaba3.models.Good;

import java.util.List;

public class CheckedGoodsAdapter extends BaseAdapter {

    private Context context;
    private List<Good> checkedGoods;

    public CheckedGoodsAdapter(Context context, List<Good> checkedGoods) {
        this.context = context;
        this.checkedGoods = checkedGoods;
    }

    @Override
    public int getCount() {
        return checkedGoods.size();
    }

    @Override
    public Object getItem(int position) {
        return checkedGoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return checkedGoods.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_good, parent, false);
        }

        TextView itemId = convertView.findViewById(R.id.item_id);
        TextView itemName = convertView.findViewById(R.id.item_name);
        TextView itemPrice = convertView.findViewById(R.id.item_price);

        Good good = checkedGoods.get(position);

        itemId.setText(String.valueOf(good.getId()));
        itemName.setText(good.getName());
        itemPrice.setText(good.getPrice());

        // Убираем чекбокс, так как в корзине он не нужен
        convertView.findViewById(R.id.item_checkbox).setVisibility(View.GONE);

        return convertView;
    }

    public void updateCheckedGoods(List<Good> updatedGoods) {
        this.checkedGoods.clear();
        for (Good good : updatedGoods) {
            if (good.isChecked()) {
                this.checkedGoods.add(good);
            }
        }
        notifyDataSetChanged(); // Уведомляем адаптер об изменениях
    }
}
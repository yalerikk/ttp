package com.example.applicationminishoplaba3.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.applicationminishoplaba3.R;
import com.example.applicationminishoplaba3.models.Good;
import com.example.applicationminishoplaba3.interfaces.OnChangeListener;

import java.util.ArrayList;

public class GoodsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Good> goodsList;
    private OnChangeListener onChangeListener;

    public GoodsAdapter(Context context, ArrayList<Good> goodsList, OnChangeListener onChangeListener) {
        this.context = context;
        this.goodsList = goodsList;
        this.onChangeListener = onChangeListener;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_good, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.goodIdTextView = convertView.findViewById(R.id.item_id);
            viewHolder.goodNameTextView = convertView.findViewById(R.id.item_name);
            viewHolder.goodPriceTextView = convertView.findViewById(R.id.item_price);
            viewHolder.goodCheckBox = convertView.findViewById(R.id.item_checkbox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Good good = goodsList.get(position);
        viewHolder.goodIdTextView.setText(String.valueOf(good.getId()));
        viewHolder.goodNameTextView.setText(good.getName());
        viewHolder.goodPriceTextView.setText(good.getPrice());

        // Устанавливаем состояние чекбокса
        viewHolder.goodCheckBox.setChecked(good.isChecked());
        Log.d("GoodsAdapter", "Good " + good.getId() + " isChecked: " + good.isChecked());

        // Устанавливаем слушатель для чекбокса
        viewHolder.goodCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            good.setChecked(isChecked);
            if (onChangeListener != null) {
                onChangeListener.onItemCheckedChange();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView goodIdTextView;
        TextView goodNameTextView;
        TextView goodPriceTextView;
        CheckBox goodCheckBox;
    }
}
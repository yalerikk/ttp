package com.example.applicationminishoplaba3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationminishoplaba3.adapters.GoodsAdapter;
import com.example.applicationminishoplaba3.interfaces.OnChangeListener;
import com.example.applicationminishoplaba3.models.Good;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChangeListener {

    private ListView goodsListView;
    private TextView footerTextView;
    private ArrayList<Good> goodsList;
    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация ListView
        goodsListView = findViewById(R.id.goodsListView);

        // Инициализация списка товаров
        goodsList = generateGoodsList();

        // Добавление заголовка
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_mygoods, null);
        goodsListView.addHeaderView(headerView);

        // Добавление подвала
        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_mygoods, null);
        footerTextView = footerView.findViewById(R.id.footerTextView);
        Button showCheckedItemsButton = footerView.findViewById(R.id.showCheckedItemsButton);
        goodsListView.addFooterView(footerView);

        // Установка адаптера
        goodsAdapter = new GoodsAdapter(this, goodsList, this);
        goodsListView.setAdapter(goodsAdapter);

        // Обработка нажатия кнопки
        showCheckedItemsButton.setOnClickListener(v -> {
            ArrayList<Good> selectedGoods = getSelectedGoods();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putParcelableArrayListExtra("selectedGoods", selectedGoods);
            startActivity(intent);
        });

        // Обновление футера
        updateFooter();
    }

    private ArrayList<Good> generateGoodsList() {
        ArrayList<Good> goods = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            goods.add(new Good(i, " My good №" + i, " $" + (i * 10), false));
        }
        return goods;
    }

    private ArrayList<Good> getSelectedGoods() {
        ArrayList<Good> selectedGoods = new ArrayList<>();
        for (Good good : goodsList) {
            if (good.isChecked()) {
                selectedGoods.add(good);
            }
        }
        return selectedGoods;
    }

    private void updateFooter() {
        int selectedCount = 0;
        for (Good good : goodsList) {
            if (good.isChecked()) {
                selectedCount++;
            }
        }
        footerTextView.setText(getString(R.string.count_of_goods, selectedCount));
    }

    @Override
    public void onItemCheckedChange() {
        updateFooter();
    }
}
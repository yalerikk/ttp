package com.example.applicationminishoplaba3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationminishoplaba3.adapters.CheckedGoodsAdapter;
import com.example.applicationminishoplaba3.models.Good;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private ListView checkedGoodsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        checkedGoodsListView = findViewById(R.id.checkedGoodsListView);

        // Получение выбранных товаров из Intent
        ArrayList<Good> selectedGoods = getIntent().getParcelableArrayListExtra("selectedGoods");

        // Установка адаптера для отображения выбранных товаров
        CheckedGoodsAdapter checkedGoodsAdapter = new CheckedGoodsAdapter(this, selectedGoods);
        checkedGoodsListView.setAdapter(checkedGoodsAdapter);
    }
}
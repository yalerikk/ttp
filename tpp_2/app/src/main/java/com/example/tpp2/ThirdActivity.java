package com.example.tpp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextStreetFrom;
    private EditText editTextHouseFrom;
    private EditText editTextFlatFrom;
    private EditText editTextStreetTo;
    private EditText editTextHouseTo;
    private EditText editTextFlatTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initViews();
    }

    private void initViews() {
        editTextStreetFrom = findViewById(R.id.editTextStreetFrom);
        editTextHouseFrom = findViewById(R.id.editTextHouseFrom);
        editTextFlatFrom = findViewById(R.id.editTextFlatFrom);
        editTextStreetTo = findViewById(R.id.editTextStreetTo);
        editTextHouseTo = findViewById(R.id.editTextHouseTo);
        editTextFlatTo = findViewById(R.id.editTextFlatTo);

        // кнопка "заказать"
        findViewById(R.id.buttonOk).setOnClickListener(v -> ok());
    }

    private void ok() {
        String streetFrom = editTextStreetFrom.getText().toString();
        String houseFrom = editTextHouseFrom.getText().toString();
        String flatFrom = editTextFlatFrom.getText().toString();
        String streetTo = editTextStreetTo.getText().toString();
        String houseTo = editTextHouseTo.getText().toString();
        String flatTo = editTextFlatTo.getText().toString();

        // проверка
        String validationMessage = validateInput(streetFrom, houseFrom, flatFrom, streetTo, houseTo, flatTo);
        if (validationMessage != null) {
            Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show();
            return; // прерываем выполнение, если есть ошибки
        }

        // форматируем названия улиц
        streetFrom = formatStreetName(streetFrom);
        streetTo = formatStreetName(streetTo);

        // создаем Intent для возврата данных
        Intent returnIntent = new Intent();
        returnIntent.putExtra("streetFrom", streetFrom);
        returnIntent.putExtra("houseFrom", houseFrom);
        returnIntent.putExtra("flatFrom", flatFrom);
        returnIntent.putExtra("streetTo", streetTo);
        returnIntent.putExtra("houseTo", houseTo);
        returnIntent.putExtra("flatTo", flatTo);

        setResult(RESULT_OK, returnIntent);
        finish(); // закрываем ThirdActivity
    }

    private String validateInput(String streetFrom, String houseFrom, String flatFrom,
                                 String streetTo, String houseTo, String flatTo) {
        // проверка на пустые поля
        if (TextUtils.isEmpty(streetFrom) || TextUtils.isEmpty(houseFrom) || TextUtils.isEmpty(flatFrom) ||
                TextUtils.isEmpty(streetTo) || TextUtils.isEmpty(houseTo) || TextUtils.isEmpty(flatTo)) {
            return "Пожалуйста, заполните все поля.";
        }

        // проверка чисел в доме и квартире
        if (!isValidHouseOrFlat(houseFrom) || !isValidHouseOrFlat(flatFrom) ||
                !isValidHouseOrFlat(houseTo) || !isValidHouseOrFlat(flatTo)) {
            return "Номера квартиры и дома должны быть больше 0.";
        }

        return null; // все ок
    }

    private boolean isValidHouseOrFlat(String value) {
        // проверка, что значение не пустое и содержит только допустимые символы
        if (TextUtils.isEmpty(value)) return false;

        // попытка парсинга значения
        try {
            // проверяем, что число больше 0
            int number = Integer.parseInt(value.replaceAll("[^\\d]", ""));
            return number > 0;
        } catch (NumberFormatException e) {
            return false; // если не удалось парсить, возвращаем false
        }
    }

    private String formatStreetName(String street) {
        // форматируем строку: первая буква заглавная, остальные строчные
        if (TextUtils.isEmpty(street)) return street;
        return street.substring(0, 1).toUpperCase() + street.substring(1).toLowerCase();
    }
}

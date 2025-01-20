package com.example.tpp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private TextView textViewNameAndSurname;
    private TextView textViewTel;
    private TextView textViewRideData;
    private Button buttonCallTaxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        initViews();

        // Устанавливаем кнопку неактивной и серой
        buttonCallTaxi.setEnabled(false);
        buttonCallTaxi.setAlpha(0.5f); // Установите прозрачность для серого цвета

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String tel = intent.getStringExtra("tel");

        String fullname = name + " " + surname;

        textViewNameAndSurname.setText(fullname);
        textViewTel.setText(tel);
    }

    private void initViews() {
        textViewNameAndSurname = findViewById(R.id.textViewNameAndSurname);
        textViewTel = findViewById(R.id.textViewTel);
        textViewRideData = findViewById(R.id.textViewRideData);
        buttonCallTaxi = findViewById(R.id.buttonCallTaxi);

        // кнопка "куда едем?"
        findViewById(R.id.buttonSetPath).setOnClickListener(v -> setPath());
        // кнопка "вызвать такси"
        findViewById(R.id.buttonCallTaxi).setOnClickListener(v -> callTaxi("Такси успешно отправлено! Ожидайте и хорошей Вам поездки"));
    }

    private void callTaxi(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setPath() {
        // Переход к третьему активити
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        // Можно запустить другое Activity и получить обратно результат
        startActivityForResult(intent, REQUEST_CODE);
        // requestCode – идентификатор для определения, с какого Activity при- шел результат.
    }

    // Обработка результата
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Random rand = new Random();
        int randomTime = rand.nextInt(21);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Получаем данные из Intent
            String streetFrom = data.getStringExtra("streetFrom");
            String houseFrom = data.getStringExtra("houseFrom");
            String flatFrom = data.getStringExtra("flatFrom");
            String streetTo = data.getStringExtra("streetTo");
            String houseTo = data.getStringExtra("houseTo");
            String flatTo = data.getStringExtra("flatTo");

            // Формируем строку маршрута
            String rideData = "Маршрут:\n" +
                    "От: " + streetFrom + ", д. " + houseFrom + ", кв. " + flatFrom + "\n" +
                    "До: " + streetTo + ", д. " + houseTo + ", кв. " + flatTo + "\n" +
                    "Машина приедет через " + randomTime + " минут. Нажмите Вызвать такси, если согласны!";

            // Отображаем информацию о маршруте
            textViewRideData.setText(rideData);
            textViewRideData.setVisibility(View.VISIBLE);
            // Сделать кнопку "Call taxi" активной
            buttonCallTaxi.setEnabled(true);
            buttonCallTaxi.setAlpha(1.0f);
        }
    }
}

package com.example.tpp_lekciya_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    // текущее значение
    private double currentValue = 0;
    // текущий оператор
    private String currentOperator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // устанавливает содержимое Activity из layout-файла

        editText = findViewById(R.id.editText); // R.java класс, в к-м хранятся константы id
        editText.setFocusable(true); // разрешаем редактирование
        editText.setText("0");

        initViews(); // инициализация кнопок
    }

    // установка обработчиков для кнопок
    private void initViews() {
        // кнопка "очистить"
        findViewById(R.id.buttonAC).setOnClickListener(v -> clear());

        // кнопка изменения знака
        findViewById(R.id.buttonPlusMin).setOnClickListener(v -> plusMin());

        // Кнопка процентов
        findViewById(R.id.buttonPercent).setOnClickListener(v -> calcPer());

        // обработчики для цифр
        for (int i = 0; i <= 9; i++) {
            int resId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(resId);
            setNumButListener(button);
        }

        // кнопка дес. дроби
        findViewById(R.id.buttonDot).setOnClickListener(v -> addDecimal());

        // арифм. операции
        findViewById(R.id.buttonPlus).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.buttonMin).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.buttonMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.buttonDivide).setOnClickListener(v -> setOperator("/"));

        // равно
        findViewById(R.id.buttonEqually).setOnClickListener(v -> calculateResult());

        // кнопки функций
        findViewById(R.id.buttonSqrt).setOnClickListener(v -> calculateSqrt());
        findViewById(R.id.buttonSquare).setOnClickListener(v -> calculateSquare());
        findViewById(R.id.buttonSin).setOnClickListener(v -> calculateSin());
        findViewById(R.id.buttonCos).setOnClickListener(v -> calculateCos());
    }

    // кнопка "очистить"
    private void clear() {
        editText.setText("0");
        currentValue = 0;
        currentOperator = "";
    }

    // кнопка изменения знака
    private void plusMin() {
        String currentText = editText.getText().toString();
        if (!currentText.isEmpty()) {
            if (currentText.startsWith("-")) {
                editText.setText(currentText.substring(1)); // убираем знак минуса
            } else {
                editText.setText("-" + currentText); // добавляем знак минуса
            }
        }
    }

    // кнопка процентов
    private void calcPer() {
        String inputText = editText.getText().toString();
        if (inputText.isEmpty()) {
            editText.setText("Недоп. процент");
            return;
        }

        double value = Double.parseDouble(inputText);
        double percentValue = value / 100;
        editText.setText(formatResult(percentValue));
    }

    // устанавливает обработчик для кнопок с цифрами
    private void setNumButListener(Button button) {
        button.setOnClickListener(v -> {
            String buttonText = ((Button) v).getText().toString();
            String currentText = editText.getText().toString();
            if (currentText.equals("0")) {
                editText.setText(buttonText);
            } else {
                editText.setText(currentText + buttonText);
            }
        });
    }

    // дес. дробь
    private void addDecimal() {
        String currentText = editText.getText().toString();
        if (!currentText.contains(".")) {
            editText.setText(currentText + ".");
        }
    }

    // устанавливает оператор
    private void setOperator(String operator) {
        currentValue = Double.parseDouble(editText.getText().toString());
        currentOperator = operator;
        editText.setText("0"); // сбрасываем экран для следующего ввода
    }

    // равно
    private void calculateResult() {
        if (editText.getText().toString().isEmpty() || currentOperator.isEmpty()) {
            return;
        }

        double secondValue = Double.parseDouble(editText.getText().toString());
        double resultValue = 0;

        switch (currentOperator) {
            case "+":
                resultValue = currentValue + secondValue;
                break;
            case "-":
                resultValue = currentValue - secondValue;
                break;
            case "*":
                resultValue = currentValue * secondValue;
                break;
            case "/":
                if (secondValue != 0) {
                    resultValue = currentValue / secondValue;
                } else {
                    editText.setText("Недоп. операция");
                    return;
                }
                break;
            default:
                return;
        }

        editText.setText(formatResult(resultValue));
        currentValue = resultValue; // сохраняем результат для следующей операции
        currentOperator = ""; // сбрасываем оператор
    }

    // функция квадратного корня
    private void calculateSqrt() {
        String inputText = editText.getText().toString();
        if (inputText.isEmpty()) {
            return;
        }

        double value = Double.parseDouble(inputText);
        if (value < 0) {
            editText.setText("Недоп. корень");
            return;
        }

        double resultValue = Math.sqrt(value);
        editText.setText(formatResult(resultValue));
    }

    // функция возведения в квадрат
    private void calculateSquare() {
        String inputText = editText.getText().toString();
        if (inputText.isEmpty()) {
            return;
        }

        double value = Double.parseDouble(inputText);
        double resultValue = value * value;
        editText.setText(formatResult(resultValue));
    }

    // функция синуса
    private void calculateSin() {
        String inputText = editText.getText().toString();
        if (inputText.isEmpty()) {
            return;
        }

        double value = Double.parseDouble(inputText);
        double resultValue = Math.sin(Math.toRadians(value)); // преобразуем в радианы
        editText.setText(formatResult(resultValue));
    }

    // функция косинуса
    private void calculateCos() {
        String inputText = editText.getText().toString();
        if (inputText.isEmpty()) {
            return;
        }

        double value = Double.parseDouble(inputText);
        double resultValue = Math.cos(Math.toRadians(value)); // преобразуем в радианы
        editText.setText(formatResult(resultValue));
    }

    private String formatResult(double currentValue) {
        // Форматируем результат как целое число, если это возможно
        if (currentValue == (int) currentValue) {
            return String.valueOf((int) currentValue);
        } else {
            return String.valueOf(currentValue);
        }
    }
}
package com.example.tpp2;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTel;
    private EditText editTextName;
    private EditText editTextSurname;
    private TextView textError;
    private Button buttonReg;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_TEL = "tel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTel = findViewById(R.id.editTextTel);
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        textError = findViewById(R.id.textError);
        buttonReg = findViewById(R.id.buttonReg);

        initViews();
        loadText();
    }

    private void initViews() {
        // кнопка "регистрация"
        buttonReg.setOnClickListener(v -> registration());

        editTextTel.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            private boolean backspacingFlag = false; // пользователь удаляет или вводит?
            // предотвращает повторное выполнение логики afterTextChanged
            private boolean editedFlag = false;
            // позиция курсора относительно конца строки
            private int cursorComplement;

            // сохраняем позицию курсора и определяем, удаляет ли пользователь символы
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // сколько символов находится после курсора
                cursorComplement = s.length() - editTextTel.getSelectionStart();
                // Если количество символов до изменения больше, значит, удаление
                backspacingFlag = count > after;
            }

            /*Параметры beforeTextChanged:
                s: текущее содержимое EditText.
                start: индекс, с которого начинается изменение.
                count: количество символов до изменения.
                after: количество символов, которые будут после изменения.
            */

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ниче тут =D
            }

            /*Параметры:
                s: текущее содержимое EditText.
                start: индекс, с которого начинается изменение.
                before: количество символов, которые были в EditText до изменения.
                count: количество символов, которые были добавлены.
            */

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                // оставляем только цифры
                String phone = string.replaceAll("[^\\d]", "");

                // трушка - текст отредачен - вызова нет
                // ложь - исходник юзера - форматируем
                if (!editedFlag) {
                    // сырой номер - 999999999
                    if (phone.length() >= 10 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "+375 (" + phone.substring(3, 5) + ") " + // Код оператора
                                phone.substring(5, 8) + "-" + // Первая часть номера
                                phone.substring(8, 10) + "-" + // Вторая часть номера
                                phone.substring(10); // Последняя часть номера
                        editTextTel.setText(ans);
                        editTextTel.setSelection(ans.length() - cursorComplement);
                    } else if (phone.length() >= 8 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "+375 (" + phone.substring(3, 5) + ") " +
                                phone.substring(5,8) + "-" + phone.substring(8);
                        editTextTel.setText(ans);
                        editTextTel.setSelection(editTextTel.getText().length() - cursorComplement);
                    } else if (phone.length() >= 5 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "+375 (" + phone.substring(3, 5) + ") " + phone.substring(5);
                        editTextTel.setText(ans);
                        editTextTel.setSelection(editTextTel.getText().length() - cursorComplement);
                    }
                } else {
                    editedFlag = false;
                }
            }
        });
    }

    private void saveText(String name, String surname, String tel) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, surname);
        editor.putString(KEY_TEL, tel);
        // асинхронно сохраняет
        editor.apply();
    }

    private void loadText() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String surname = sharedPreferences.getString(KEY_SURNAME, null);
        String tel = sharedPreferences.getString(KEY_TEL, null);

        if (name != null && surname != null && tel != null) {
            // если данные существуют, заполняем поля
            editTextName.setText(name);
            editTextSurname.setText(surname);
            editTextTel.setText(tel);
            buttonReg.setText(R.string.butLogIn);
        }
    }

    private void registration() {
        String tel = editTextTel.getText().toString();
        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();

        // Проверка номера телефона
        if (!isValidPhone(tel)) {
            showError(getString(R.string.errorTel));
            return;
        }

        // Проверка имени и фамилии
        if (!isValidNameOrSurname(name) || !isValidNameOrSurname(surname)) {
            showError(getString(R.string.errorNameOrSurname));
            return;
        }

        // Убираем ошибку, если все проверки пройдены
        textError.setVisibility(TextView.GONE);

        // Сохраняем данные в SharedPreferences
        saveText(name, surname, tel);

        // Преобразуем имена
        name = capitalizeFirstLetter(name);
        surname = capitalizeFirstLetter(surname);

        // Переход ко второму активити
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("tel", tel);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        startActivity(intent);
    }

    private boolean isValidPhone(String tel) {
        String phone = tel.replaceAll("[^\\d]", "");
        if (phone.length() < 12) { // (например, +375 XX XXX XX XX)
            return false;
        }
        String prefix = phone.substring(3, 5);
        return prefix.equals("44") || prefix.equals("25") || prefix.equals("33") || prefix.equals("29");
    }

    private boolean isValidNameOrSurname(String text) {
        return text.matches("[А-Яа-яЁё]+");
    }

    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    private void showError(String message) {
        textError.setText(message);
        textError.setVisibility(TextView.VISIBLE);
    }
}
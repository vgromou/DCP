package com.example.dcp;


import actions.Differentiation;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.transition.*;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.ViewGroupUtils;
import androidx.core.view.MotionEventCompat;
import textManipulators.Analyzer;
import tree.GenericTree;

import java.util.Arrays;

import static android.os.VibrationEffect.*;
import static android.view.View.*;
//TODO: Сделать нормальное скрытие ненужных клав. Убрать нажатие недействительных клавиш в more
//TODO: Разобраться со структурой программы. Может какие-то методы можно будет вытащить в отдельный класс
public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //Только портретный режим
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //Прячет шапку с названием приложения
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); //Иконки статус бара темные
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); //От бара навигации только сама кнопка

        ViewGroup keyboard = findViewById(R.id.keyboard);
        keyboard.bringChildToFront(findViewById(R.id.keyboard_main));
        Button main = (Button) findViewById(R.id.switch_main);
        main.setTextColor(getColor(R.color.switchButtonOn));

        editText = findViewById(R.id.edittext);
        hideAndroidKeyboard(editText); //Отвечает за скрытие клавиатуры при щелчке на поле ввода
        editText.setGravity(Gravity.END);

        textView = findViewById(R.id.textView);
        textView.setGravity(Gravity.END);

        swipe(); //Отвечает за все свайпы

        //Долгое нажатие удаления для полной очистки
        View main_delete = (View) findViewById(R.id.main_include_delete);
        main_delete.setOnLongClickListener(this);
        View digit_delete = (View) findViewById(R.id.digit_include_delete);
        digit_delete.setOnLongClickListener(this);
        View more_delete = (View) findViewById(R.id.more_include_delete);
        more_delete.setOnLongClickListener(this);
    }
    void hideAndroidKeyboard(final EditText editText){
        editText.requestFocus();
        editText.setShowSoftInputOnFocus(false);
    }
    void swipe(){
        final ViewGroup keyboardPanel = (RelativeLayout) findViewById(R.id.keyboard_panel);
        final View swipeButton = (View) findViewById(R.id.swipe_button);
        ConstraintLayout mainLayout = (ConstraintLayout)  findViewById(R.id.main);
        final RelativeLayout keyboard = (RelativeLayout) findViewById(R.id.keyboard);
        keyboardPanel.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            ObjectAnimator swipeButtonAnimation;
            ObjectAnimator keyboardAnimation;
            public void onSwipeTop() {
                if ((int) keyboardPanel.getTranslationY() != 0) {
                    swipeButtonAnimation = ObjectAnimator.ofFloat(swipeButton, "scaleX", 1f);
                    swipeButton.setBackground(getDrawable(R.drawable.swipe_button_top));
                    ObjectAnimator keyboardAnimation = ObjectAnimator.ofFloat(keyboardPanel, "translationY", 0f);
                    keyboardAnimation.setDuration(300);
                    swipeButtonAnimation.setDuration(300);
                    swipeButtonAnimation.start();
                    keyboardAnimation.start();
                }
            }
            public void onSwipeRight() {
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                if ((int) keyboardPanel.getTranslationY() == 0) {
                    swipeButtonAnimation = ObjectAnimator.ofFloat(swipeButton, "scaleX", 2f);
                    swipeButton.setBackground(getDrawable(R.drawable.swipe_button_bottom));
                    keyboardAnimation = ObjectAnimator.ofFloat(keyboardPanel, "translationY", 1500f);
                    keyboardAnimation.setDuration(300);
                    swipeButtonAnimation.setDuration(300);
                    swipeButtonAnimation.start();
                    keyboardAnimation.start();
                }
            }
        });
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            ObjectAnimator swipeButtonAnimation;
            public void onSwipeTop() {
                if ((int) keyboardPanel.getTranslationY() != 0) {
                    swipeButtonAnimation = ObjectAnimator.ofFloat(swipeButton, "scaleX", 1f);
                    swipeButton.setBackground(getDrawable(R.drawable.swipe_button_top));
                    ObjectAnimator keyboardAnimation = ObjectAnimator.ofFloat(keyboardPanel, "translationY", 0f);
                    keyboardAnimation.setDuration(300);
                    swipeButtonAnimation.setDuration(300);
                    swipeButtonAnimation.start();
                    keyboardAnimation.start();
                }
            }
        });
    }

    public void keyboardSwitcher(View v){
        Button digits = (Button) findViewById(R.id.switch_digits);
        Button main = (Button) findViewById(R.id.switch_main);
        Button more = (Button) findViewById(R.id.switch_more);

        ViewGroup keyboard = findViewById(R.id.keyboard);
        String id = v.getResources().getResourceName(v.getId());
        String switcher = id.substring(id.lastIndexOf("_")+1);

        if(switcher.equals("digits")) {
          keyboard.bringChildToFront(findViewById(R.id.keyboard_digits));
          main.setTextColor(getColor(R.color.switchButtonOff));
          digits.setTextColor(getColor(R.color.switchButtonOn));
          more.setTextColor(getColor(R.color.switchButtonOff));
        }
        else if (switcher.equals("main")){
           keyboard.bringChildToFront(findViewById(R.id.keyboard_main));
           main.setTextColor(getColor(R.color.switchButtonOn));
           digits.setTextColor(getColor(R.color.switchButtonOff));
           more.setTextColor(getColor(R.color.switchButtonOff));
        }
        else{
            keyboard.bringChildToFront(findViewById(R.id.keyboard_more));
            main.setTextColor(getColor(R.color.switchButtonOff));
            digits.setTextColor(getColor(R.color.switchButtonOff));
            more.setTextColor(getColor(R.color.switchButtonOn));
        }
    }

    //Все, что связано с вводом текста
    void delete() {
        //TODO: Можно добавить удаление сразу целых слов (cos, arctg, log и т.п.)
        StringBuilder text = new StringBuilder(editText.getText().toString());
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start == end && start != 0) {
            text.deleteCharAt(start - 1);
        } else {
            text.delete(start, end);
        }
        editText.setText(text);
        if (start == 0 || start != end) {
            editText.setSelection(start);
        } else {
            editText.setSelection(start - 1);
        }
    }

    void putIntoText(String symbol, boolean isSpecialSymbol){
        //TODO: Можно сделать подстановку скобок у cos, ln и т.п. (по типу "sin(" или там "arctg(")
        int position = editText.getSelectionStart();
        StringBuilder text = new StringBuilder(editText.getText().toString());
        if (isSpecialSymbol){
            boolean isAtTheBeginning = (position == 0);
            Character char1 = (isAtTheBeginning) ? '#' : text.charAt(position - 1);
            String beforePosition = char1.toString();
            Character char2 = (position == editText.getText().length()) ? '#' : text.charAt(position);
            String afterPosition = char2.toString();
            boolean repeat = (beforePosition.equals(symbol)) || (afterPosition.equals(symbol));
            if (isAtTheBeginning && !symbol.equals("−")){
                return;
            }
            if (symbol.equals(".") && !canPointBeEntered(position, beforePosition, afterPosition, text.toString())){
                return;
            }
            if(repeat) {
                return;
            }
        }
        if (symbol.equals("power")){
            symbol = "^"; //TODO: Можно заморочиться над верхним индексом
        }
        if (symbol.equals("e")){
            symbol = "e^";
        }
        text.insert(position, symbol);
        editText.setText(text);
        editText.setSelection(position + symbol.length());


    }
    boolean canPointBeEntered(int position, String charBefore, String charAfter, String expression){
        //TODO: сделать так, чтобы точку можно было нажимать только один раз за число
        //Проверка на наличие рядом с позицией знаков, отличающихся от цифр
        if (!charBefore.equals("#")){
            try{
                Integer.parseInt(charBefore);
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        if (!charAfter.equals("#")){
            try{
                Integer.parseInt(charAfter);
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        //Проверка на наличие в числе других точек (не реализовано)
        return true;
    }
    @Override
    public void onClick(View v) {
        String id = v.getResources().getResourceName(v.getId());
        String action = id.substring(id.lastIndexOf("_")+1);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(createOneShot(1, 150));
        switch (action) {
            case "delete":
                delete();
                break;
            case "add":
                putIntoText("+", true);
                break;
            case "multiply":
                putIntoText("·", true);
                break;
            case "divide":
                putIntoText("÷", true);
                break;
            case "subtract":
                putIntoText("−", true);
                break;
            case "open":
                putIntoText("(", false);
                break;
            case "close":
                putIntoText(")", false);
                break;
            case "x":
                putIntoText("x", false);
                break;
            case "calculate":
                calculate();
                break;
            case "point":
                putIntoText(".", true);
                break;
            default:
                putIntoText(action, false);
        }
    }
    @Override
    public boolean onLongClick(View v) {
        //TODO: Можно сделать анимацию полного удаления текста
        editText.setText("");
        textView.setText("");
        return true;
    }

    private void calculate(){
        String strExpression = editText.getText().toString();
        StringBuilder strBuildExpression = new StringBuilder(strExpression);

        StringBuilder answer;
        //TODO: Когда с основными ошибками разберетесь, сделать тут try-catch, чтобы выводить Error в случае неправильной записи
        answer = Differentiation.difExpression(strBuildExpression);
        editText.setText(answer);
        editText.setSelection(answer.length());
        textView.setText(strExpression);
    }
}

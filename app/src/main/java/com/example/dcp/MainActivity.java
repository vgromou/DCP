package com.example.dcp;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.transition.*;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.ViewGroupUtils;
import androidx.core.view.MotionEventCompat;

import static android.os.VibrationEffect.*;
import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        editText = findViewById(R.id.edittext);
        hideAndroidKeyboard(editText);
        editText.setGravity(Gravity.END);

        swipe();
        type(editText);
    }
    public void hideAndroidKeyboard(final EditText editText){
        editText.requestFocus();
        editText.setShowSoftInputOnFocus(false);
    }
    public void swipe(){
        final ViewGroup keyboardPanel = (RelativeLayout) findViewById(R.id.keyboard_panel);
        final View swipeButton = (View) findViewById(R.id.swipe_button);
        ConstraintLayout mainLayout = (ConstraintLayout)  findViewById(R.id.main);
        RelativeLayout keyboard = (RelativeLayout) findViewById(R.id.keyboard);
        keyboardPanel.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            ObjectAnimator swipeButtonAnimation;
            ObjectAnimator keyboardAnimation;
            public void onSwipeTop() {
                if ((int) keyboardPanel.getTranslationY() != 0) {
                    swipeButtonAnimation = ObjectAnimator.ofFloat(swipeButton, "scaleX", 1f);
                    ObjectAnimator keyboardAnimation = ObjectAnimator.ofFloat(keyboardPanel, "translationY", 0f);
                    keyboardAnimation.setDuration(300);
                    swipeButtonAnimation.setDuration(300);
                    swipeButtonAnimation.start();
                    keyboardAnimation.start();
                    //keyboardPanel.addView(findViewById(R.id.keyboard));
                }
            }
            public void onSwipeRight() {
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                if ((int) keyboardPanel.getTranslationY() == 0) {
                    swipeButtonAnimation = ObjectAnimator.ofFloat(swipeButton, "scaleX", 2f);
                    keyboardAnimation = ObjectAnimator.ofFloat(keyboardPanel, "translationY", 1500f);
                    keyboardAnimation.setDuration(300);
                    swipeButtonAnimation.setDuration(300);
                    swipeButtonAnimation.start();
                    keyboardAnimation.start();
                    //keyboardPanel.removeView(findViewById(R.id.keyboard));
                }
            }
        });
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            ObjectAnimator swipeButtonAnimation;
            ObjectAnimator keyboardAnimation;
            public void onSwipeTop() {
                if ((int) keyboardPanel.getTranslationY() != 0) {
                    swipeButtonAnimation = ObjectAnimator.ofFloat(swipeButton, "scaleX", 1f);
                    ObjectAnimator keyboardAnimation = ObjectAnimator.ofFloat(keyboardPanel, "translationY", 0f);
                    keyboardAnimation.setDuration(300);
                    swipeButtonAnimation.setDuration(300);
                    swipeButtonAnimation.start();
                    keyboardAnimation.start();
                    //keyboardPanel.addView(findViewById(R.id.keyboard));
                }
            }
        });
    }
    public void delete() {
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
    public void putIntoText(String symbol, boolean isSpecialSymbol){
        //TODO: Можно сделать подстановку скобок у cos, ln и т.п. (по типу "sin(" или там "arctg(")
        int position = editText.getSelectionStart();
        StringBuilder text = new StringBuilder(editText.getText().toString());
        if (isSpecialSymbol){
            Character char1 = (position == 0) ? '#' : text.charAt(position - 1);
            String beforePosition = char1.toString();
            Character char2 = (position == editText.getText().length()) ? '#' : text.charAt(position);
            String afterPosition = char2.toString();
            boolean repeat = (beforePosition.equals(symbol)) || (afterPosition.equals(symbol));
            if(repeat) {
                return;
            }
        }
        if (symbol.equals("power")){
            symbol = "^"; //TODO: Можно заморочиться над верзним индексом
        }
        text.insert(position, symbol);
        editText.setText(text);
        editText.setSelection(position + symbol.length());


    }

    @Override
    public void onClick(View v) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(createOneShot(1, 150));
        switch (v.getId()) {
            case R.id.digit_include_delete:
            case R.id.main_include_delete:
            case R.id.more_include_delete:
                delete();
                break;
            case R.id.digit_include_add:
            case R.id.main_include_add:
            case R.id.more_include_add:
                putIntoText("+", true);
                break;
            case R.id.digit_include_multiply:
            case R.id.main_include_multiply:
            case R.id.more_include_multiply:
                putIntoText("·", true);
                break;
            case R.id.digit_include_divide:
            case R.id.main_include_divide:
            case R.id.more_include_divide:
                putIntoText("÷", true);
                break;
            case R.id.digit_include_subtract:
            case R.id.main_include_subtract:
            case R.id.more_include_subtract:
                putIntoText("−", true);
                break;
            case R.id.digit_include_open:
            case R.id.main_include_open:
            case R.id.more_include_open:
                putIntoText("(", false);
                break;
            case R.id.digit_include_close:
            case R.id.main_include_close:
            case R.id.more_include_close:
                putIntoText(")", false);
                break;
            case R.id.digit_include_x:
            case R.id.main_include_x:
            case R.id.more_include_x:
                putIntoText("x", true);
            case R.id.digit_include_calculate:
            case R.id.main_include_calculate:
            case R.id.more_include_calculate:
                //code;
                break;
            case R.id.button_point:
                //TODO: сделать так, чтобы точку можно было нажимать только один раз за число
                putIntoText(".", false);
                break;
            default:
                String id = v.getResources().getResourceName(v.getId());
                String action = id.substring(id.lastIndexOf("_")+1);
                putIntoText(action, false);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        //TODO: Можно сделать анимацию полного удаления текста
        editText.setText("");
        return true;
    }

    public void type(final EditText editText){
        final Button digit_0 = (Button) findViewById(R.id.digit_0);
        digit_0.setOnClickListener(this);
        final Button digit_1 = (Button) findViewById(R.id.digit_1);
        digit_1.setOnClickListener(this);
        final Button digit_2 = (Button) findViewById(R.id.digit_2);
        digit_2.setOnClickListener(this);
        final Button digit_3 = (Button) findViewById(R.id.digit_3);
        digit_3.setOnClickListener(this);
        final Button digit_4 = (Button) findViewById(R.id.digit_4);
        digit_4.setOnClickListener(this);
        final Button digit_5 = (Button) findViewById(R.id.digit_5);
        digit_5.setOnClickListener(this);
        final Button digit_6 = (Button) findViewById(R.id.digit_6);
        digit_6.setOnClickListener(this);
        final Button digit_7 = (Button) findViewById(R.id.digit_7);
        digit_7.setOnClickListener(this);
        final Button digit_8 = (Button) findViewById(R.id.digit_8);
        digit_8.setOnClickListener(this);
        final Button digit_9 = (Button) findViewById(R.id.digit_9);
        digit_9.setOnClickListener(this);
        final Button button_point = (Button) findViewById(R.id.button_point);
        button_point.setOnClickListener(this);
        final Button button_cos = (Button) findViewById(R.id.button_cos);
        button_cos.setOnClickListener(this);
        final Button button_sin = (Button) findViewById(R.id.button_sin);
        button_sin.setOnClickListener(this);
        final Button button_tg = (Button) findViewById(R.id.button_tg);
        button_tg.setOnClickListener(this);
        final Button button_ctg = (Button) findViewById(R.id.button_ctg);
        button_ctg.setOnClickListener(this);
        final Button button_arccos = (Button) findViewById(R.id.button_arccos);
        button_arccos.setOnClickListener(this);
        final Button button_arcsin = (Button) findViewById(R.id.button_arcsin);
        button_arcsin.setOnClickListener(this);
        final Button button_arctg = (Button) findViewById(R.id.button_arctg);
        button_arctg.setOnClickListener(this);
        final Button button_arcctg = (Button) findViewById(R.id.button_arcctg);
        button_arcctg.setOnClickListener(this);
        final Button button_ln = (Button) findViewById(R.id.button_ln);
        button_ln.setOnClickListener(this);
        final Button button_e = (Button) findViewById(R.id.button_e);
        button_e.setOnClickListener(this);
        final Button button_power = (Button) findViewById(R.id.button_power);
        button_power.setOnClickListener(this);
        final Button button_sh = (Button) findViewById(R.id.button_sh);
        button_sh.setOnClickListener(this);
        final Button button_ch = (Button) findViewById(R.id.button_ch);
        button_ch.setOnClickListener(this);
        final Button button_th = (Button) findViewById(R.id.button_th);
        button_th.setOnClickListener(this);
        final Button button_cth = (Button) findViewById(R.id.button_cth);
        button_cth.setOnClickListener(this);
        final Button button_log = (Button) findViewById(R.id.button_log);
        button_log.setOnClickListener(this);

        View digit_delete = (View) findViewById(R.id.digit_include_delete);
        digit_delete.setOnClickListener(this);
        digit_delete.setOnLongClickListener(this);
        View digit_divide = (View) findViewById(R.id.digit_include_divide);
        digit_divide.setOnClickListener(this);
        View digit_multiply = (View) findViewById(R.id.digit_include_multiply);
        digit_multiply.setOnClickListener(this);
        View digit_subtract = (View) findViewById(R.id.digit_include_subtract);
        digit_subtract.setOnClickListener(this);
        View digit_add = (View) findViewById(R.id.digit_include_add);
        digit_add.setOnClickListener(this);
        View digit_calculate= (View) findViewById(R.id.digit_include_calculate);
        digit_calculate.setOnClickListener(this);
        View digit_x = (View) findViewById(R.id.digit_include_x);
        digit_x.setOnClickListener(this);
        View digit_open = (View) findViewById(R.id.digit_include_open);
        digit_open.setOnClickListener(this);
        View digit_close = (View) findViewById(R.id.digit_include_close);
        digit_close.setOnClickListener(this);

        View main_delete = (View) findViewById(R.id.main_include_delete);
        main_delete.setOnClickListener(this);
        main_delete.setOnLongClickListener(this);
        View main_divide = (View) findViewById(R.id.main_include_divide);
        main_divide.setOnClickListener(this);
        View main_multiply = (View) findViewById(R.id.main_include_multiply);
        main_multiply.setOnClickListener(this);
        View main_subtract = (View) findViewById(R.id.main_include_subtract);
        main_subtract.setOnClickListener(this);
        View main_add = (View) findViewById(R.id.main_include_add);
        main_add.setOnClickListener(this);
        View main_calculate= (View) findViewById(R.id.main_include_calculate);
        main_calculate.setOnClickListener(this);
        View main_x = (View) findViewById(R.id.main_include_x);
        main_x.setOnClickListener(this);
        View main_open = (View) findViewById(R.id.main_include_open);
        main_open.setOnClickListener(this);
        View main_close = (View) findViewById(R.id.main_include_close);
        main_close.setOnClickListener(this);

        View more_delete = (View) findViewById(R.id.more_include_delete);
        more_delete.setOnClickListener(this);
        more_delete.setOnLongClickListener(this);
        View more_divide = (View) findViewById(R.id.more_include_divide);
        more_divide.setOnClickListener(this);
        View more_multiply = (View) findViewById(R.id.more_include_multiply);
        more_multiply.setOnClickListener(this);
        View more_subtract = (View) findViewById(R.id.more_include_subtract);
        more_subtract.setOnClickListener(this);
        View more_add = (View) findViewById(R.id.more_include_add);
        more_add.setOnClickListener(this);
        View more_calculate= (View) findViewById(R.id.more_include_calculate);
        more_calculate.setOnClickListener(this);
        View more_x = (View) findViewById(R.id.more_include_x);
        more_x.setOnClickListener(this);
        View more_open = (View) findViewById(R.id.more_include_open);
        more_open.setOnClickListener(this);
        View more_close = (View) findViewById(R.id.more_include_close);
        more_close.setOnClickListener(this);
    }
}

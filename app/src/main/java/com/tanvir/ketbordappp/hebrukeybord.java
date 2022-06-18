package com.tanvir.ketbordappp;


import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;

import android.provider.UserDictionary;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class hebrukeybord extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean isCaps = false;
    private boolean ishebrew=true;
    private LinearLayout linearLayout;
    InputConnection ic;



    //Press Ctrl+O



    @Override
    public View onCreateInputView() {
        View kv = getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboardView= kv.findViewById(R.id.keyboard);

        ImageView imoji = kv.findViewById(R.id.ivimoji);
        ImageView lang = kv.findViewById(R.id.ivlang);
        TextView number=kv.findViewById(R.id.tvnumber);
        TextView space=kv.findViewById(R.id.tvspace);
        TextView returnkey=kv.findViewById(R.id.tvreturn);


        number.setOnClickListener(view -> {
            keyboardView.setKeyboard(new Keyboard(this,R.xml.numbers));

        });
        returnkey.setOnClickListener(view -> {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        });

        space.setOnClickListener(view -> {
            ic.commitText(" ",1);


        });

        keyboard = new Keyboard(this, R.xml.qwerty);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);

        lang.setOnClickListener(view -> {
            if (ishebrew){
                keyboardView.setKeyboard(new Keyboard(this,R.xml.quetytwo));
                ishebrew=false;

            }else {
                keyboardView.setKeyboard(new Keyboard(this,R.xml.qwerty));
                ishebrew=true;
            }

        });
        imoji.setOnClickListener(view -> {
            //oopenimoji activity
        });




        return kv;

    }



    @Override
    public void onPress(int i) {


    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

         ic = getCurrentInputConnection();
        playClick(i);
        switch (i) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                isCaps = !isCaps;
                keyboard.setShifted(isCaps);
                keyboardView.invalidateAllKeys();
                break;


           /* case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -82:
                keyboardView.setKeyboard(new Keyboard(this,R.xml.numbers));
                break;*/

            /*case -12:
                keyboardView.setKeyboard(new Keyboard(this,R.xml.imoji));
                break;

            case -15:
                if (ishebrew){
                    keyboardView.setKeyboard(new Keyboard(this,R.xml.quetytwo));
                    ishebrew=false;
                    break;
                }else {
                    keyboardView.setKeyboard(new Keyboard(this,R.xml.qwerty));
                    ishebrew=true;
                    break;
                }*/



            default:
                char code = (char) i;
                if (Character.isLetter(code) && isCaps)
                    code = Character.toUpperCase(code);
                ic.commitText(String.valueOf(code), 1);


        }

    }

    private void playClick(int i) {

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (i) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
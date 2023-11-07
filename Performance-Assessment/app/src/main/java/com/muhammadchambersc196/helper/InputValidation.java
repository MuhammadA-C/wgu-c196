package com.muhammadchambersc196.helper;

import android.widget.EditText;

public class InputValidation {
    public static boolean isInputFieldEmpty(EditText editText) {
        if(editText.getText().toString().length() == 0) {
            return true;
        }
        return false;
    }
}

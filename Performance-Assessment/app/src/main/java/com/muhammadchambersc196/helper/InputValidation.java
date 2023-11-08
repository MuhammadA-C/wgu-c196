package com.muhammadchambersc196.helper;

import android.widget.EditText;
import android.widget.Spinner;

public class InputValidation {
    public static boolean isInputFieldEmpty(EditText editText) {
        if(editText.getText().toString().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isInputFieldEmpty(Spinner spinner) {
        if(spinner.getSelectedItem() != null) {
            return false;
        }
        return true;
    }
}

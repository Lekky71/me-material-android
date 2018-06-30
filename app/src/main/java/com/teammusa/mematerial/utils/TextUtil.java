package com.teammusa.mematerial.utils;

import android.text.TextUtils;
import android.widget.EditText;

public class TextUtil {
    public static boolean isEmpty(EditText edt) {
        return TextUtils.isEmpty(getString(edt));
    }

    public static String getString(EditText edt) {
        return edt.getText().toString().trim();
    }
}

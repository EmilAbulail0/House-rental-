package com.example.houserentals;

import android.content.Context;
import android.widget.Toast;

public class PrintToast {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

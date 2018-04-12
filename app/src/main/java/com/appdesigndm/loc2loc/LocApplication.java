package com.appdesigndm.loc2loc;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.appdesigndm.loc2loc.Models.ErrorModel;

public class LocApplication extends Application {
    
    public static final String MATCH_LOWERCASE_CHARS = ".*[a-z].*";
    public static final String MATCH_UPPERCASE_CHARS = ".*[A-Z].*";
    public static final String MATCH_NUMBERS = ".*[0-9].*";
    public static final int MIN_WORD_LENGTH = 3;
    public static final int MIN_PASSWORD_LENGTH = 4;

    public LocApplication() {
    }

    public static void printShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void printShort(Context context, int textId) {
        String text = context.getResources().getString(textId);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void printLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void printLong(Context context, int textId) {
        String text = context.getResources().getString(textId);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}

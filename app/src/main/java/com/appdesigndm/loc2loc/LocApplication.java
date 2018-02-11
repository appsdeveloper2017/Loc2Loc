package com.appdesigndm.loc2loc;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LocApplication extends Application {
    public static final int MIN_WORD_LENGTH = 3;
    public static final String MATCH_LOWERCASE_CHARS = ".*[a-z].*";
    public static final String MATCH_UPPERCASE_CHARS = ".*[A-Z].*";
    public static final String MATCH_NUMBERS = ".*[0-9].*";

    public static FirebaseAuth fAuth;
    public static FirebaseUser fCurrentUser;
    public static FirebaseDatabase fDatabase;

    // Childs nodes for database
    public static String USERS = "Users";

    public LocApplication() {
    }

    public static void printShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void printLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

}

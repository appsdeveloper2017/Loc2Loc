package com.appdesigndm.loc2loc;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHelper {

    public static final String CHILD_USERS="Users";

    private static DatabaseReference sDatabase;

    public DBHelper() {
        init();
    }

    public static void init() {
        sDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getInstance() {
        sDatabase = FirebaseDatabase.getInstance().getReference();
        return sDatabase;
    }

    public void setValue(String child, Object value) {
        sDatabase.child(child).setValue(value);
    }
}

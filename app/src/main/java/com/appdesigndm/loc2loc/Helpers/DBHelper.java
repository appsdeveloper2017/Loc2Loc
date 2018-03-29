package com.appdesigndm.loc2loc.Helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHelper {

    public static final String USERS ="Users";
    public static final String LINKS ="Links";

    private static DatabaseReference sDatabase;

    public static DatabaseReference getInstance() {
        getDatabaseReference();
        return sDatabase;
    }

    private static void getDatabaseReference() {
        sDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Set the value at database children passed by children vector
     * @param children
     * @param value
     */
    public static void setValue(String[] children, Object value) {
        getDatabaseReference();
        for (String child : children) {
            sDatabase = sDatabase.child(child);
        }
        sDatabase.setValue(value);
        getDatabaseReference(); // Reset database children
    }
}

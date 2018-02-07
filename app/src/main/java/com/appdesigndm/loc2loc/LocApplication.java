package com.appdesigndm.loc2loc;

import android.app.Application;

/**
 * Created by davidmendano on 5/2/18.
 */

public class LocApplication extends Application {
    public static final int MIN_WORD_LENGTH = 3;
    public static final String MATCH_LOWERCASE_CHARS = ".*[a-z].*";
    public static final String MATCH_UPPERCASE_CHARS = ".*[A-Z].*";
    public static final String MATCH_NUMBERS = ".*[0-9].*";

}

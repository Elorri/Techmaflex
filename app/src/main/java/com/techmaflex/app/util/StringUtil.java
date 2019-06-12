package com.techmaflex.app.util;

import java.util.ArrayList;

public class StringUtil {


    public String delim(ArrayList<String> array, String delim) {
        String arrayStringDelimited = "";
        int i = 0;
        for (String aString : array) {
            arrayStringDelimited += aString;
            if (i < array.size() - 1) {
                arrayStringDelimited += delim;
            }
            i++;
        }
        return arrayStringDelimited;
    }

    public String delim(String[] array, String delim) {
        String arrayStringDelimited = "";
        int i = 0;
        for (String aString : array) {
            arrayStringDelimited += aString;
            if (i < array.length - 1) {
                arrayStringDelimited += delim;
            }
            i++;
        }
        return arrayStringDelimited;
    }
}

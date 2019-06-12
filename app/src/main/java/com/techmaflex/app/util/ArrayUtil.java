package com.techmaflex.app.util;

import java.util.ArrayList;

public class ArrayUtil {

    public String[] fromArrayListToArray(ArrayList<String> arrayList) {
        //Ca ca devrait marcher aussi en mettant T à la place de DatastoreBundle
        //eventsBundleList.toArray(new DatastoreBundle[]{});

        String[] array = new String[arrayList.size()];
        int i = 0;
        for (String anElement : arrayList) {
            array[i] = anElement;
            i++;
        }
        return array;
    }

    public <T> ArrayList<T> fromArrayToArrayList(T[] arrayList) {

        ArrayList<T> array = new ArrayList<>(arrayList.length);
        int i = 0;
        for (T anElement : arrayList) {
            array.add(i, anElement);
            i++;
        }
        return array;
    }

}

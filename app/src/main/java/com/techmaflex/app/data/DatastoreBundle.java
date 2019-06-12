package com.techmaflex.app.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Elorri on 04/06/2018.
 */

public final class DatastoreBundle implements Parcelable {

    public static final String DATAS = "DATAS";
    public static final String NULL = "null";
    public static final String COMBINAISONS = "combinaisons";

    protected Bundle bundle;

    public DatastoreBundle() {
        bundle = new Bundle();
    }

    public DatastoreBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    protected DatastoreBundle(Parcel in) {
        bundle = in.readBundle();
    }


    /*Parcelable methods*/

    /**
     * Je ne suis pas sure de cette methode. Peut-etre serait il mieux de copier/coller carrement
     * la classe Bundle et d'ajouter les methodes qui m'intéressent.
     */
    public static final Creator<DatastoreBundle> CREATOR = new Creator<DatastoreBundle>() {
        @Override
        public DatastoreBundle createFromParcel(Parcel in) {
            Bundle bundle = in.readBundle(getClass().getClassLoader());
            return fromBundleToDatastoreBundle(bundle);
        }

        @Override
        public DatastoreBundle[] newArray(int size) {
            return new DatastoreBundle[size];
        }
    };

    public DatastoreBundle(ContentValues values) {
        bundle = new Bundle();
        for (String key : values.keySet()) {
            bundle.putString(key, values.getAsString(key));
        }
    }

    public DatastoreBundle(Cursor cursor) {
        bundle = new Bundle();

        ArrayList<DatastoreBundle> datas = new ArrayList<>();
        while (cursor.moveToNext()) {
            DatastoreBundle aBundle = new DatastoreBundle();
            for (String columnName : cursor.getColumnNames()) {
                aBundle.putString(columnName, cursor.getString(cursor.getColumnIndex(columnName)));
            }
            datas.add(aBundle);
        }

        if (datas.isEmpty()) {
            putString("documents", NULL);
        } else {
            putDatastoreArrayList("documents", datas);
        }
    }

    public void putDatastoreArrayList(String label, ArrayList<DatastoreBundle> datas) {
        ArrayList<Bundle> bundleList = new ArrayList<>();
        for (DatastoreBundle aDatastoreBundle : datas) {
            bundleList.add(aDatastoreBundle.toBundle());
        }
        bundle.putParcelableArrayList(label, bundleList);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(bundle);
    }



    /*Bundle methods*/

    public Object clone() {
        return new DatastoreBundle((Bundle)bundle.clone());
    }

    public Bundle deepCopy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return bundle.deepCopy();
        }
        return null;
    }

    public void clear() {
        bundle.clear();
    }

    public void remove(String key) {
        bundle.remove(key);
    }

    public void putAll(Bundle bundle) {
        bundle.putAll(bundle);
    }

    public boolean hasFileDescriptors() {
        return bundle.hasFileDescriptors();
    }

    public void putByte(String key, byte value) {
        bundle.putByte(key, value);
    }

    public void putChar(String key, char value) {
        bundle.putChar(key, value);
    }

    public void putShort(String key, short value) {
        bundle.putShort(key, value);
    }

    public void putFloat(String key, float value) {
        bundle.putFloat(key, value);
    }

    public void putCharSequence(String key, CharSequence value) {
        bundle.putCharSequence(key, value);
    }

//    public void putParcelable(String key, Parcelable value) {
//        bundle.putParcelable(key, value);
//    }

    public void putSize(String key, Size value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle.putSize(key, value);
        }
    }

    public void putSizeF(String key, SizeF value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle.putSizeF(key, value);
        }
    }

//    public void putParcelableArray(String key, Parcelable[] value) {
//        bundle.putParcelableArray(key, value);
//    }

//    public void putParcelableArrayList(String key, ArrayList<? extends Parcelable> value) {
//        bundle.putParcelableArrayList(key, value);
//    }

    public void putSparseParcelableArray(String key, SparseArray<? extends Parcelable> value) {
        bundle.putSparseParcelableArray(key, value);
    }

    public void putIntegerArrayList(String key, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(key, value);
    }

    public void putStringArrayList(String key, ArrayList<String> value) {
        bundle.putStringArrayList(key, value);
    }

    public void putCharSequenceArrayList(String key, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(key, value);
    }

    public void putSerializable(String key, Serializable value) {
        bundle.putSerializable(key, value);
    }

    public void putByteArray(String key, byte[] value) {
        bundle.putByteArray(key, value);
    }

    public void putShortArray(String key, short[] value) {
        bundle.putShortArray(key, value);
    }

    public void putCharArray(String key, char[] value) {
        bundle.putCharArray(key, value);
    }

    public void putFloatArray(String key, float[] value) {
        bundle.putFloatArray(key, value);
    }

    public void putCharSequenceArray(String key, CharSequence[] value) {
        bundle.putCharSequenceArray(key, value);
    }

    public void putDatastoreBundle(String key, DatastoreBundle value) {
        bundle.putBundle(key, value.toBundle());
    }

    public void putBinder(String key, IBinder value) {
        bundle.putBinder(key, value);
    }

    public void putString(String key, String value) {
        bundle.putString(key, value);
    }

    public void putInt(String key, Integer value) {
        bundle.putInt(key, value);
    }


    public void putStringArray(String key, String[] value) {
        bundle.putStringArray(key, value);
    }


    public String getString(String key) {
        return bundle.getString(key);
    }

    public String[] getStringArray(String key) {
        return bundle.getStringArray(key);
    }

    public byte getByte(String key) {
        return bundle.getByte(key);
    }

    public Byte getByte(String key, byte defaultValue) {
        return bundle.getByte(key, defaultValue);
    }

    public char getChar(String key) {
        return bundle.getChar(key);
    }

    public char getChar(String key, char defaultValue) {
        return bundle.getChar(key, defaultValue);
    }

    public short getShort(String key) {
        return bundle.getShort(key);
    }

    public short getShort(String key, short defaultValue) {
        return bundle.getShort(key, defaultValue);
    }

    public float getFloat(String key) {
        return bundle.getFloat(key);
    }

    public float getFloat(String key, float defaultValue) {
        return bundle.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return bundle.getBoolean(key);
    }

    public CharSequence getCharSequence(String key) {
        return bundle.getCharSequence(key);
    }

    public CharSequence getCharSequence(String key, CharSequence defaultValue) {
        return bundle.getCharSequence(key, defaultValue);
    }

    public Size getSize(String key) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return bundle.getSize(key);
        }
        return null;
    }

    public SizeF getSizeF(String key) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return bundle.getSizeF(key);
        }
        return null;
    }

    public DatastoreBundle getDatastoreBundle(String key) {
        return fromBundleToDatastoreBundle(bundle.getBundle(key));
    }

//    public <T extends Parcelable> T getParcelable(String key) {
//        return bundle.getParcelable(key);
//    }

//    public Parcelable[] getParcelableArray(String key) {
//        return bundle.getParcelableArray(key);
//    }

//    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String key) {
//        return bundle.getParcelableArrayList(key);
//    }


    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(String key) {
        return bundle.getSparseParcelableArray(key);
    }

    public Serializable getSerializable(String key) {
        return bundle.getSerializable(key);
    }

    public ArrayList<Integer> getIntegerArrayList(String key) {
        return bundle.getIntegerArrayList(key);
    }

    public ArrayList<String> getStringArrayList(String key) {
        return bundle.getStringArrayList(key);
    }

    public ArrayList<CharSequence> getCharSequenceArrayList(String key) {
        return bundle.getCharSequenceArrayList(key);
    }

    public byte[] getByteArray(String key) {
        return bundle.getByteArray(key);
    }

    public short[] getShortArray(String key) {
        return bundle.getShortArray(key);
    }

    public char[] getCharArray(String key) {
        return bundle.getCharArray(key);
    }

    public float[] getFloatArray(String key) {
        return bundle.getFloatArray(key);
    }

    public CharSequence[] getCharSequenceArray(String key) {
        return bundle.getCharSequenceArray(key);
    }

    public IBinder getBinder(String key) {
        return bundle.getBinder(key);
    }

    public synchronized String toString() {
        return bundle.toString();
    }

    public Set<String> keySet() {
        return bundle.keySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DatastoreBundle)) {
            return false;
        }
        for (String aKey : ((DatastoreBundle) obj).keySet()) {
            if (!bundle.containsKey(aKey)) {
                return false;
            }

            ArrayList<DatastoreBundle> array = bundle.getParcelableArrayList(aKey);
            if (array != null) {
                int i = 0; //On considère que les element des 2 tableaux sont rangés dans le m^me
                // ordre, on compare donc les bundle.
                for (DatastoreBundle aBundle : array) {
                    ArrayList<DatastoreBundle> objArray = ((DatastoreBundle) obj).getDatastoreBundleArrayList(aKey);
                    if (objArray != null) {
                        if (!aBundle.equals(objArray.get(i))) {
                            return false;
                        }
                    }
                    i++;
                }
            } else {
                if (!bundle.getString(aKey).equals(((DatastoreBundle) obj).getString(aKey))) {
                    return false;
                }
            }

        }
        return true;
    }

    public ArrayList<DatastoreBundle> getDatastoreBundleArrayList(String aKey) {
        ArrayList<DatastoreBundle> datastoreBundles = new ArrayList<>();
        if ((bundle.get(aKey) instanceof String) && (bundle.get(aKey).equals("null"))) {
            return datastoreBundles;
        }
        ArrayList<Bundle> bundles = (ArrayList<Bundle>) bundle.get(aKey);
        for (Bundle aBundle : bundles) {
            datastoreBundles.add(fromBundleToDatastoreBundle(aBundle));
        }
        return datastoreBundles;
    }

    @Override
    public int hashCode() {
        int hashSum = 0;
        for (String aKey : this.keySet()) {
            hashSum += aKey.hashCode();
        }
        return hashSum;
    }


    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        for (String aKey : keySet()) {
            contentValues.put(aKey, getString(aKey));
        }
        return contentValues;
    }

    public Bundle toBundle() {
        return bundle;
    }

    public static DatastoreBundle fromBundleToDatastoreBundle(Bundle bundle) {
        DatastoreBundle dataStoreBundle = new DatastoreBundle();
        for (String label : bundle.keySet()) {
            Object object = bundle.get(label);
            if (object instanceof Bundle) {
                dataStoreBundle.putDatastoreBundle(label, fromBundleToDatastoreBundle((Bundle) object));
            } else if (object instanceof List) {
                if(!((List) object).isEmpty()){
                    Object anObject = ((List) object).get(0);
                    if(anObject instanceof String){
                        ArrayList<String> datastoreList = new ArrayList<>();
                        ArrayList<String> bundleList = (ArrayList<String>) object;
                        for (String aString : bundleList) {
                            datastoreList.add(aString);
                        }
                        dataStoreBundle.putStringArrayList(label, datastoreList);
                    }else if(anObject instanceof Bundle){
                        ArrayList<DatastoreBundle> datastoreList = new ArrayList<>();
                        ArrayList<Bundle> bundleList = (ArrayList<Bundle>) object;
                        for (Bundle aBundle : bundleList) {
                            datastoreList.add(fromBundleToDatastoreBundle(aBundle));
                        }
                        dataStoreBundle.putDatastoreArrayList(label, datastoreList);
                    }else{

                    }
                }

            } else {
                dataStoreBundle.putString(label, (String) object);
            }
        }
        return dataStoreBundle;
    }

    public void putNull(String key) {
        //putNull doesn't exist ?!
    }

    public int getInt(String key) {
        return bundle.getInt(key);
    }

    public long getLong(String key) {
        return bundle.getLong(key);
    }

    public boolean containsKey(String key) {
        return bundle.containsKey(key);
    }

    @Nullable
    public Object get(String key) {
        Object object = bundle.get(key);
        if (object instanceof List) {
            ArrayList<DatastoreBundle> datastoreBundles = new ArrayList<>();
            ArrayList<Bundle> bundleList = (ArrayList<Bundle>) object;
            for (Bundle aBundle : bundleList) {
                datastoreBundles.add(fromBundleToDatastoreBundle(aBundle));
            }
            return datastoreBundles;
        } else if (object instanceof Bundle) {
            return fromBundleToDatastoreBundle((Bundle) object);
        } else {
            return object;
        }
    }

    public void putBoolean(@Nullable String key, boolean value) {
        bundle.putBoolean(key, value);
    }

    public void putLong(@Nullable String key, long value) {
        bundle.putLong(key, value);
    }

    public void putDouble(@Nullable String key, double value) {
        bundle.putDouble(key, value);
    }

     public void putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        bundle.putBooleanArray(key, value);
    }

    public void putIntArray(@Nullable String key, @Nullable int[] value) {
        bundle.putIntArray(key, value);
    }

    public void putLongArray(@Nullable String key, @Nullable long[] value) {
        bundle.putLongArray(key, value);
    }

    public void putDoubleArray(@Nullable String key, @Nullable double[] value) {
        bundle.putDoubleArray(key, value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return bundle.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return bundle.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return bundle.getLong(key, defaultValue);
    }

    public double getDouble(String key) {
        return bundle.getDouble(key);
    }

    public double getDouble(String key, double defaultValue) {
        return bundle.getDouble(key, defaultValue);
    }

    public String getString(@Nullable String key, String defaultValue) {
        return bundle.getString(key, defaultValue);
    }

    @Nullable
    public boolean[] getBooleanArray(@Nullable String key) {
        return bundle.getBooleanArray(key);
    }

    @Nullable
    public int[] getIntArray(@Nullable String key) {
        return bundle.getIntArray(key);
    }

    @Nullable
    public long[] getLongArray(@Nullable String key) {
        return bundle.getLongArray(key);
    }

    @Nullable
    public double[] getDoubleArray(@Nullable String key) {
        return bundle.getDoubleArray(key);
    }
}

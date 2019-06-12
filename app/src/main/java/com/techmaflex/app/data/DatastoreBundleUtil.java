package com.techmaflex.app.data;

import com.techmaflex.app.util.DateUtilsImpl;
import com.techmaflex.app.util.StacktraceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatastoreBundleUtil {

    public DatastoreBundle fromJSONStringToBundle(String jsonObjectString) {
        DatastoreBundle resultBundle = null;
        try {
            resultBundle = fromJSONObjectToBundle(new JSONObject(jsonObjectString));
        } catch (JSONException e) {
            DatastoreBundle warning = new DatastoreBundle();
            warning.putString("id", String.valueOf(new DateUtilsImpl().now()));
            warning.putString("message", e.getMessage());
            warning.putString("stacktrace", new StacktraceUtil().printStack(e.getStackTrace()));
            ArrayList<DatastoreBundle> warnings = new ArrayList<>();
            warnings.add(warning);
            resultBundle.putDatastoreArrayList("warnings", warnings);
        }
        return resultBundle;
    }

    public DatastoreBundle fromJSONObjectToBundle(JSONObject jsonObject) {
        DatastoreBundle bundle = new DatastoreBundle();
        try {
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String label = iterator.next();
                Object object = jsonObject.get(label);

                if (object instanceof JSONObject) {
                    bundle.putDatastoreBundle(label, fromJSONObjectToBundle((JSONObject) object));
                } else if (object instanceof JSONArray) {
                    ArrayList<DatastoreBundle> bundleList = new ArrayList<>();
                    for (int i = 0; i < ((JSONArray) object).length(); i++) {
                        JSONObject anElement = ((JSONArray) object).getJSONObject(i);
                        bundleList.add(fromJSONObjectToBundle(anElement));
                    }
                    bundle.putDatastoreArrayList(label, bundleList);
                } else {
                    bundle.putString(label, (String) object);
                }
            }
            return bundle;
        } catch (JSONException e) {
            DatastoreBundle warning = new DatastoreBundle();
            warning.putString("id", String.valueOf(new DateUtilsImpl().now()));
            warning.putString("message", e.getMessage());
            warning.putString("stacktrace", new StacktraceUtil().printStack(e.getStackTrace()));
            ArrayList<DatastoreBundle> warnings = new ArrayList<>();
            warnings.add(warning);
            DatastoreBundle result = new DatastoreBundle();
            result.putDatastoreArrayList("warnings", warnings);
            return result;
        }
    }

    public Object fromBundleToJSONObject(DatastoreBundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (String label : bundle.keySet()) {
                Object object = bundle.get(label);
                if (object instanceof DatastoreBundle) {
                    jsonObject.put(label, fromBundleToJSONObject((DatastoreBundle) object));
                } else if (object instanceof List) {
                    ArrayList<DatastoreBundle> bundleList = (ArrayList<DatastoreBundle>) object;
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < bundleList.size(); i++) {
                        DatastoreBundle aBundle = bundleList.get(i);
                        jsonArray.put(i, fromBundleToJSONObject(aBundle));
                    }
                    jsonObject.put(label, jsonArray);
                } else {
                    jsonObject.put(label, object);
                }
            }
            return jsonObject;
        } catch (JSONException e) {
            DatastoreBundle warning = new DatastoreBundle();
            warning.putString("id", String.valueOf(new DateUtilsImpl().now()));
            warning.putString("message", e.getMessage());
            warning.putString("stacktrace", new StacktraceUtil().printStack(e.getStackTrace()));
            ArrayList<DatastoreBundle> warnings = new ArrayList<>();
            warnings.add(warning);
            DatastoreBundle result = new DatastoreBundle();
            result.putDatastoreArrayList("warnings", warnings);
            return result;
        }
    }
}

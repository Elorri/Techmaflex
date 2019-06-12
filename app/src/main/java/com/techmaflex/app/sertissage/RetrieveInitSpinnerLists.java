package com.techmaflex.app.sertissage;

import com.techmaflex.app.data.Datastore;
import com.techmaflex.app.data.DatastoreAndroidSQLite;
import com.techmaflex.app.data.DatastoreBundle;
import com.techmaflex.app.data.RetrieveDataInterface;

import java.util.ArrayList;

class RetrieveInitSpinnerLists implements RetrieveDataInterface {


    @Override
    public DatastoreBundle retrieveData(Datastore datastore, DatastoreBundle datastoreBundle) {
        DatastoreBundle b=new DatastoreBundle();

        DatastoreBundle tuyaux=datastore.read(true,
                DatastoreAndroidSQLite.TECHMAFLEX,
                new String[]{"tuyau"}, null, null, null, null, "tuyau asc", null);
        b.putStringArrayList("tuyaux", fromDatastoreBundleToString(tuyaux.getDatastoreBundleArrayList("documents"), "tuyau"));

        DatastoreBundle jupes=datastore.read(true,
                DatastoreAndroidSQLite.TECHMAFLEX,
                new String[]{"jupe"}, null, null, null, null, "jupe asc", null);
        b.putStringArrayList("jupes", fromDatastoreBundleToString(jupes.getDatastoreBundleArrayList("documents"), "jupe"));

        DatastoreBundle embouts=datastore.read(true,
                DatastoreAndroidSQLite.TECHMAFLEX,
                new String[]{"embout"}, null, null, null, null, "embout asc", null);
        b.putStringArrayList("embouts", fromDatastoreBundleToString(embouts.getDatastoreBundleArrayList("documents"), "embout"));

        return b;
    }

    private ArrayList<String>  fromDatastoreBundleToString(ArrayList<DatastoreBundle> documents, String key) {
        ArrayList<String> strings=new ArrayList<>();

        for(DatastoreBundle aDatastoreBundle : documents){
            strings.add(aDatastoreBundle.getString(key));
        }

        return strings;
    }
}

package com.techmaflex.app.sertissage;

import com.techmaflex.app.data.Datastore;
import com.techmaflex.app.data.DatastoreAndroidSQLite;
import com.techmaflex.app.data.DatastoreBundle;
import com.techmaflex.app.data.RetrieveDataInterface;
import com.techmaflex.app.util.ArrayUtil;
import com.techmaflex.app.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class RetrieveSertissageBundleAfterSelection implements RetrieveDataInterface {


    @Override
    public DatastoreBundle retrieveData(Datastore datastore, DatastoreBundle bundle) {


        DatastoreBundle tuyauSpinnerBundle = findBundleViewByNameId(bundle, "diametre_de_sertissage_tuyau");
        DatastoreBundle jupeSpinnerBundle = findBundleViewByNameId(bundle, "diametre_de_sertissage_jupe");
        DatastoreBundle emboutSpinnerBundle = findBundleViewByNameId(bundle, "diametre_de_sertissage_embout");

        String tuyauSelection = tuyauSpinnerBundle.getString("selection")
                .equals("Selectionner un tuyau") ? null : tuyauSpinnerBundle.getString("selection");

        String jupeSelection = jupeSpinnerBundle.getString("selection")
                .equals("Selectionner un jupe") ? null : jupeSpinnerBundle.getString("selection");

        String emboutSelection = emboutSpinnerBundle.getString("selection")
                .equals("Selectionner un embout") ? null : emboutSpinnerBundle.getString("selection");

        DatastoreBundle result;
        if (tuyauSelection == null && jupeSelection == null && emboutSelection == null) {
            //L'utilisateur a fait un reset, on remet les menus tels qu'ils étaient lors de l'init
            result = new RetrieveInitSpinnerLists().retrieveData(datastore, null);
            bundle.putStringArrayList("tuyaux", result.getStringArrayList("tuyau"));
            bundle.putStringArrayList("jupes", result.getStringArrayList("jupe"));
            bundle.putStringArrayList("embouts", result.getStringArrayList("embout"));
            return bundle;
        }

        //On détermine la nouvelle sélection
        ArrayList<String> selection = new ArrayList<>();
        ArrayList<String> selectionArgs = new ArrayList<>();
        if (tuyauSelection != null) {
            selection.add("tuyau=?");
            selectionArgs.add(tuyauSelection);
        }
        if (tuyauSelection != null) {
            selection.add("jupe=?");
            selectionArgs.add(jupeSelection);
        }
        if (tuyauSelection != null) {
            selection.add("embout=?");
            selectionArgs.add(emboutSelection);
        }
        result = datastore.read(
                DatastoreAndroidSQLite.TECHMAFLEX,
                null, new StringUtil().delim(selection, " and "),
                new ArrayUtil().fromArrayListToArray(selectionArgs), null, null, null, null);

        LinkedHashSet<String> tuyauOptions = new LinkedHashSet<>();
        LinkedHashSet<String> jupeOptions = new LinkedHashSet<>();
        LinkedHashSet<String> emboutOptions = new LinkedHashSet<>();
        LinkedHashSet<String> diametreDeSertissage = new LinkedHashSet<>();
        for (DatastoreBundle aResult : result.getDatastoreBundleArrayList("documents")) {
            tuyauOptions.add(aResult.getString("tuyau"));
            jupeOptions.add(aResult.getString("jupe"));
            emboutOptions.add(aResult.getString("embout"));
            diametreDeSertissage.add(aResult.getString("diametre_de_sertissage"));
        }

        bundle.putStringArrayList("tuyaux", new ArrayList<>(tuyauOptions));
        bundle.putStringArrayList("jupes", new ArrayList<>(jupeOptions));
        bundle.putStringArrayList("embouts", new ArrayList<>(emboutOptions));
        ArrayList<String> diametreDeSertissageArray = new ArrayList<>(diametreDeSertissage);
        bundle.putStringArrayList("diametre_de_sertissage", diametreDeSertissageArray);


        //Si on a un diameètre :on l'affiche avec le bouton envoyer
        DatastoreBundle diametre_de_sertissage_value = findBundleViewByNameId(bundle, "diametre_de_sertissage_value");
        diametre_de_sertissage_value.putString("visibility", diametreDeSertissageArray.size() == 1 ? "visible" : "invisible");
        diametre_de_sertissage_value.putString("text", diametreDeSertissageArray.size() == 1 ? diametreDeSertissageArray.get(0) : "");

        DatastoreBundle diametre_de_sertissage_send = findBundleViewByNameId(bundle, "diametre_de_sertissage_send");
        diametre_de_sertissage_send.putString("enabled", diametreDeSertissageArray.size() == 1 ? "true" : "false");

        return bundle;

    }

    private DatastoreBundle findBundleViewByNameId(DatastoreBundle viewBundle, String nameId) {
        for (DatastoreBundle aViewBundle : viewBundle.getDatastoreBundleArrayList("views")) {
            if (aViewBundle.getString("id").equals(nameId)) {
                return aViewBundle;
            }
        }
        return null;
    }
}

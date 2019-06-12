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


        //On recrée les listes de données qui seront dans les menus déroulants
        DatastoreBundle tuyauSpinner =
                bundle.getDatastoreBundle("views").getDatastoreBundle("diametre_de_sertissage_tuyau");
        DatastoreBundle jupeSpinner =
                bundle.getDatastoreBundle("views").getDatastoreBundle("diametre_de_sertissage_jupe");
        DatastoreBundle emboutSpinner =
                bundle.getDatastoreBundle("views").getDatastoreBundle("diametre_de_sertissage_embout");

        String tuyauSelection;
        String jupeSelection;
        String emboutSelection;
        if (bundle.getBoolean("is_spinner_init_selection")) {
            tuyauSelection = tuyauSpinner.getString("selection")
                    .equals("Selectionner un tuyau") ? null : tuyauSpinner.getString("selection");

            jupeSelection = jupeSpinner.getString("selection")
                    .equals("Selectionner un jupe") ? null : jupeSpinner.getString("selection");

            emboutSelection = emboutSpinner.getString("selection")
                    .equals("Selectionner un embout") ? null : emboutSpinner.getString("selection");
        } else {
            tuyauSelection = tuyauSpinner.containsKey("selection_user") ?
                    tuyauSpinner.getString("selection_user") : null;

            jupeSelection = jupeSpinner.containsKey("selection_user")?
                    jupeSpinner.getString("selection_user") : null;

            emboutSelection = emboutSpinner.containsKey("selection_user")?
                    emboutSpinner.getString("selection_user"): null;
        }

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
        if (jupeSelection != null) {
            selection.add("jupe=?");
            selectionArgs.add(jupeSelection);
        }
        if (emboutSelection != null) {
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

        //On mets à jour les vues
        DatastoreBundle views = bundle.getDatastoreBundle("views");

        //On mets à jours les vues Spinner avec les nouvelles listes
        ArrayList<String> tuyauSpinnerList = new ArrayList<>();
        tuyauSpinnerList.addAll(removeNulls(bundle.getStringArrayList("tuyaux")));
        tuyauSpinner.putString("id", "diametre_de_sertissage_tuyau");
        tuyauSpinner.putStringArrayList("list", tuyauSpinnerList);
        //TODO s'assurer qu'il y a au moins 1 élément
        tuyauSpinner.putString("selection", tuyauSpinnerList.get(0)); //On selectionne le premier élément de la liste
        views.putDatastoreBundle("diametre_de_sertissage_tuyau", tuyauSpinner);

        ArrayList<String> jupeSpinnerList = new ArrayList<>();
        jupeSpinnerList.addAll(removeNulls(bundle.getStringArrayList("jupes")));
        jupeSpinner.putString("id", "diametre_de_sertissage_jupe");
        jupeSpinner.putStringArrayList("list", jupeSpinnerList);
        jupeSpinner.putString("selection", jupeSpinnerList.get(0));
        views.putDatastoreBundle("diametre_de_sertissage_jupe", jupeSpinner);

        ArrayList<String> emboutSpinnerList = new ArrayList<>();
        emboutSpinnerList.addAll(removeNulls(bundle.getStringArrayList("embouts")));
        emboutSpinner.putString("id", "diametre_de_sertissage_embout");
        emboutSpinner.putStringArrayList("list", emboutSpinnerList);
        emboutSpinner.putString("selection", emboutSpinnerList.get(0));
        views.putDatastoreBundle("diametre_de_sertissage_embout", emboutSpinner);

        //Si on a un diamètre :on l'affiche avec le bouton envoyer
        DatastoreBundle diametre_de_sertissage_value = views.getDatastoreBundle("diametre_de_sertissage_value");
        diametre_de_sertissage_value.putString("visibility", diametreDeSertissageArray.size() == 1 ? "visible" : "invisible");
        diametre_de_sertissage_value.putString("text",
                diametreDeSertissageArray.size() == 1 ? diametreDeSertissageArray.get(0) : "");
        views.putDatastoreBundle("diametre_de_sertissage_value", diametre_de_sertissage_value);

        DatastoreBundle diametre_de_sertissage_send = views.getDatastoreBundle("diametre_de_sertissage_send");
        diametre_de_sertissage_send.putString("enabled", diametreDeSertissageArray.size() == 1 ? "true" : "false");
        views.putDatastoreBundle("diametre_de_sertissage_send", diametre_de_sertissage_send);

        bundle.putDatastoreBundle("views", views);

        return bundle;

    }

    private ArrayList<String> removeNulls(ArrayList<String> list) {
        ArrayList<String> listWithoutNulls = new ArrayList<>();
        for (String aString : list) {
            listWithoutNulls.add(aString.equals("null") ? "" : aString);
        }
        return listWithoutNulls;
    }

}

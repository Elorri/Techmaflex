package com.techmaflex.app.sertissage;

import com.techmaflex.app.data.Datastore;
import com.techmaflex.app.data.DatastoreAndroidSQLite;
import com.techmaflex.app.data.DatastoreBundle;
import com.techmaflex.app.data.RetrieveDataInterface;

import java.util.ArrayList;

public class RetrieveSertissageBundleInit implements RetrieveDataInterface {

    @Override
    public DatastoreBundle retrieveData(Datastore datastore, DatastoreBundle datastoreBundle) {
        DatastoreBundle b = new DatastoreBundle();

        //Vérif si la db a importé un csv
        DatastoreBundle result = datastore.read(
                DatastoreAndroidSQLite.TECHMAFLEX,
                null, null, null, null, null, null, "1");

        if (result.getDatastoreBundleArrayList("documents").isEmpty()) {

            //Le db n'a pas importé de csv
            DatastoreBundle views = new DatastoreBundle();

            DatastoreBundle noDataViewBundle = new DatastoreBundle();
            noDataViewBundle.putString("id", "diametre_de_sertissage_no_data");
            noDataViewBundle.putString("visibility", "visible");
            views.putDatastoreBundle("diametre_de_sertissage_no_data", noDataViewBundle);

            DatastoreBundle diametreSertissageViewBundle = new DatastoreBundle();
            diametreSertissageViewBundle.putString("id", "diametre_de_sertissage_main");
            diametreSertissageViewBundle.putString("visibility", "gone");
            views.putDatastoreBundle("diametre_de_sertissage_main", diametreSertissageViewBundle);

            DatastoreBundle messageViewBundle = new DatastoreBundle();
            messageViewBundle.putString("id", "diametre_de_sertissage_message");
            messageViewBundle.putString("visibility", "gone");
            views.putDatastoreBundle("diametre_de_sertissage_message", messageViewBundle);

            //TODO Ajouter le texte des boutons et autres ici plutôt que dans le xml

            b.putDatastoreBundle("views", views);
            return b;
        }

        //Les données ont été importées

        //On récupère les différentes listes
        b = new RetrieveInitSpinnerLists().retrieveData(datastore, null);

        //On prépare les bundleView
        DatastoreBundle views = new DatastoreBundle();

        //On affiche la vue main et on cache la vue no_data
        DatastoreBundle noDataViewBundle = new DatastoreBundle();
        noDataViewBundle.putString("id", "diametre_de_sertissage_no_data");
        noDataViewBundle.putString("visibility", "gone");
        views.putDatastoreBundle("diametre_de_sertissage_no_data", noDataViewBundle);

        DatastoreBundle get_diametre_de_sertissage_view = new DatastoreBundle();
        get_diametre_de_sertissage_view.putString("id", "diametre_de_sertissage_main");
        get_diametre_de_sertissage_view.putString("visibility", "visible");
        views.putDatastoreBundle("diametre_de_sertissage_main", get_diametre_de_sertissage_view);

        DatastoreBundle messageViewBundle = new DatastoreBundle();
        messageViewBundle.putString("id", "diametre_de_sertissage_message");
        messageViewBundle.putString("visibility", "gone");
        views.putDatastoreBundle("diametre_de_sertissage_message", messageViewBundle);

        //On affiche le diametre et le bouton envoyer

        DatastoreBundle diametreTextview = new DatastoreBundle();
        diametreTextview.putString("id", "diametre_de_sertissage_value");
        diametreTextview.putString("visibility", "invisible");
        diametreTextview.putString("text", "");
        views.putDatastoreBundle("diametre_de_sertissage_value", diametreTextview);

        DatastoreBundle diametreButton = new DatastoreBundle();
        diametreButton.putString("id", "diametre_de_sertissage_send");
        diametreButton.putString("enabled", "false");
        views.putDatastoreBundle("diametre_de_sertissage_send", diametreButton);

        //On prépare les données que devront contenir les menus déroulants
        DatastoreBundle tuyauSpinner = new DatastoreBundle();
        ArrayList<String> tuyauSpinnerList = new ArrayList<>();
        tuyauSpinnerList.add("Sélectionner le tuyau");
        tuyauSpinnerList.addAll(removeNulls(b.getStringArrayList("tuyaux")));
        tuyauSpinner.putString("id", "diametre_de_sertissage_tuyau");
        tuyauSpinner.putStringArrayList("list", tuyauSpinnerList);
        tuyauSpinner.putString("selection", "Sélectionner le tuyau"); //TODO est-ce vraiment nécessaire ?
        views.putDatastoreBundle("diametre_de_sertissage_tuyau", tuyauSpinner);

        DatastoreBundle jupeSpinner = new DatastoreBundle();
        ArrayList<String> jupeSpinnerList = new ArrayList<>();
        jupeSpinnerList.add("Sélectionner la jupe");
        jupeSpinnerList.addAll(removeNulls(b.getStringArrayList("jupes")));
        jupeSpinner.putString("id", "diametre_de_sertissage_jupe");
        jupeSpinner.putStringArrayList("list", jupeSpinnerList);
        jupeSpinner.putString("selection", "Sélectionner la jupe"); //TODO est-ce vraiment nécessaire ?
        jupeSpinner.putString("enabled", "false");
        views.putDatastoreBundle("diametre_de_sertissage_jupe", jupeSpinner);

        DatastoreBundle emboutSpinner = new DatastoreBundle();
        ArrayList<String> emboutSpinnerList = new ArrayList<>();
        emboutSpinnerList.add("Sélectionner l'embout");
        emboutSpinnerList.addAll(removeNulls(b.getStringArrayList("embouts")));
        emboutSpinner.putString("id", "diametre_de_sertissage_embout");
        emboutSpinner.putStringArrayList("list", emboutSpinnerList);
        emboutSpinner.putString("selection", "Sélectionner l'embout"); //TODO est-ce vraiment nécessaire ?
        emboutSpinner.putString("enabled", "false");
        views.putDatastoreBundle("diametre_de_sertissage_embout", emboutSpinner);

        b.putDatastoreBundle("views", views);

        return b;
    }

    private ArrayList<String> removeNulls(ArrayList<String> list) {
        ArrayList<String> listWithoutNulls = new ArrayList<>();
        for (String aString : list) {
            listWithoutNulls.add(aString.equals("null") ? "" : aString);
        }
        return listWithoutNulls;
    }
}

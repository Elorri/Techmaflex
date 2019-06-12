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
            ArrayList<DatastoreBundle> views = new ArrayList<>();
            DatastoreBundle no_csv_for_diametre_de_sertissage_view = new DatastoreBundle();
            no_csv_for_diametre_de_sertissage_view.putString("id", "no_csv_for_diametre_de_sertissage");
            no_csv_for_diametre_de_sertissage_view.putString("visibility", "visible");
            views.add(no_csv_for_diametre_de_sertissage_view);
            DatastoreBundle get_diametre_de_sertissage_view = new DatastoreBundle();
            get_diametre_de_sertissage_view.putString("id", "get_diametre_de_sertissage");
            get_diametre_de_sertissage_view.putString("visibility", "gone");
            views.add(get_diametre_de_sertissage_view);

            //TODO Ajouter le texte des boutons et autres ici plutôt que dans le xml

            b.putDatastoreArrayList("views", views);
            return b;
        }

        //Les données ont été importées

        //On récupère les différentes listes
        b = new RetrieveInitSpinnerLists().retrieveData(datastore, null);

        //On prépare les bundleView
        ArrayList<DatastoreBundle> views = new ArrayList<>();

        //On affiche la vue main et on cache la vue no_data
        DatastoreBundle no_csv_for_diametre_de_sertissage_view = new DatastoreBundle();
        no_csv_for_diametre_de_sertissage_view.putString("id", "diametre_de_sertissage_no_data");
        no_csv_for_diametre_de_sertissage_view.putString("visibility", "gone");
        views.add(no_csv_for_diametre_de_sertissage_view);
        DatastoreBundle get_diametre_de_sertissage_view = new DatastoreBundle();
        get_diametre_de_sertissage_view.putString("id", "diametre_de_sertissage_main");
        get_diametre_de_sertissage_view.putString("visibility", "visible");
        views.add(get_diametre_de_sertissage_view);

        //On affiche le diametre et le bouton envoyer
        DatastoreBundle diametre_de_sertissage_value = new DatastoreBundle();
        diametre_de_sertissage_value.putString("id", "diametre_de_sertissage_value");
        diametre_de_sertissage_value.putString("visibility", "invisible");
        diametre_de_sertissage_value.putString("text", "");
        views.add(diametre_de_sertissage_value);
        DatastoreBundle diametre_de_sertissage_send = new DatastoreBundle();
        diametre_de_sertissage_send.putString("id", "diametre_de_sertissage_send");
        diametre_de_sertissage_send.putString("enabled", "false");
        views.add(diametre_de_sertissage_send);

        //On prépare les données que devront contenir les menus déroulants
        ArrayList<String> tuyauSpinnerList = new ArrayList<>();
        tuyauSpinnerList.add("Selectionner un tuyau");
        tuyauSpinnerList.addAll(b.getStringArrayList("tuyaux"));
        DatastoreBundle tuyauSpinner = new DatastoreBundle();
        tuyauSpinner.putString("id", "diametre_de_sertissage_tuyau");
        tuyauSpinner.putStringArrayList("list", tuyauSpinnerList);
        tuyauSpinner.putString("selection", "Selectionner un tuyau"); //TODO est-ce vraiment nécessaire ?
        views.add(tuyauSpinner);


        ArrayList<String> jupeSpinnerList = new ArrayList<>();
        jupeSpinnerList.add("Selectionner un jupe");
        jupeSpinnerList.addAll(b.getStringArrayList("jupes"));
        DatastoreBundle jupeSpinner = new DatastoreBundle();
        jupeSpinner.putString("id", "diametre_de_sertissage_jupe");
        jupeSpinner.putStringArrayList("list", jupeSpinnerList);
        jupeSpinner.putString("selection", "Selectionner un jupe"); //TODO est-ce vraiment nécessaire ?
        views.add(jupeSpinner);

        ArrayList<String> emboutSpinnerList = new ArrayList<>();
        emboutSpinnerList.add("Selectionner un embout");
        emboutSpinnerList.addAll(b.getStringArrayList("embouts"));
        DatastoreBundle emboutSpinner = new DatastoreBundle();
        emboutSpinner.putString("id", "diametre_de_sertissage_embout");
        emboutSpinner.putStringArrayList("list", emboutSpinnerList);
        emboutSpinner.putString("selection", "Selectionner un embout"); //TODO est-ce vraiment nécessaire ?
        views.add(emboutSpinner);

        b.putDatastoreArrayList("views", views);
        return b;
    }
}

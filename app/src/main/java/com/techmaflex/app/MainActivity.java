package com.techmaflex.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.techmaflex.app.data.DatastoreBundle;
import com.techmaflex.app.data.DatastoreBundleUtil;
import com.techmaflex.app.navigation.DisplayViewWithFragment;
import com.techmaflex.app.navigation.LoadViewImpl;
import com.techmaflex.app.navigation.LoadViewInterfaceCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadViewInterfaceCallback {

    public static String INTENT_BUNDLE_TAG = "intent_bundle_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openPage(getIntent());
    }

    public void openPage(Intent intent) {
        //Recup bundle donné dans l'intent
        Uri uri = intent.getData();
        if (uri == null) {
            //Cas si on ouvre l'appli sans passer par un test, l'intent n'a pas d'extras
            //On charge la page par défault
            uri = Uri.parse(LoadViewImpl.DIAMETRE_DE_SERTISSAGE);
            DatastoreBundle intent_bundle = new DatastoreBundleUtil().fromJSONStringToBundle("{\n" +
                    "  \"documents\": [\n" +
                    "    {\n" +
                    "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                    "      \"jupe\": \"M00120-04\",\n" +
                    "      \"embout\": \"MF\",\n" +
                    "      \"diametre_de_sertissage\": \"12,4\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                    "      \"jupe\": \"M00120-04\",\n" +
                    "      \"embout\": \"MF-KML\",\n" +
                    "      \"diametre_de_sertissage\": \"12,8\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"tuyau\": \"ETERNITY/2\",\n" +
                    "      \"jupe\": \"M00120-04KM\",\n" +
                    "      \"embout\": \"MF\",\n" +
                    "      \"diametre_de_sertissage\": \"18\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                    "      \"jupe\": \"null\",\n" +
                    "      \"embout\": \"null\",\n" +
                    "      \"diametre_de_sertissage\": \"24\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}");
            intent.putExtra(MainActivity.INTENT_BUNDLE_TAG, intent_bundle);
            intent.setData(uri);
            openPage(intent);
        } else {
            DatastoreBundle bundle = getParcelableExtra(intent);

            //Charge la vue correspondant à l'intent
            View pageView = new LoadViewImpl().loadView(this, uri.toString(), bundle, this);

            //affiche la vue dans le conteneur specifié
            ViewGroup mainContainer = findViewById(R.id.main_container);
            new DisplayViewWithFragment(this).show(mainContainer, pageView);
        }
    }


    private DatastoreBundle getParcelableExtra(Intent intent) {
        DatastoreBundle bundle = new DatastoreBundle();

        Bundle intentExtras = intent.getExtras();
        if (intentExtras == null) {
            return bundle;
        }
        Object intentData = intentExtras.get(MainActivity.INTENT_BUNDLE_TAG);
        if (intentData != null && !(intentData instanceof ArrayList)) {
            return (DatastoreBundle) intentData;
        }

        if (intentData == null) {
            bundle.putDatastoreArrayList(MainActivity.INTENT_BUNDLE_TAG, new ArrayList<DatastoreBundle>());
            return bundle;
        }

        ArrayList<DatastoreBundle> parcelableArrayList = new ArrayList<>();
        for (DatastoreBundle aBundle : (ArrayList<DatastoreBundle>) intentData) {
            parcelableArrayList.add(aBundle);
        }
        bundle.putDatastoreArrayList(MainActivity.INTENT_BUNDLE_TAG, (ArrayList<DatastoreBundle>) intentData);
        return bundle;
    }

    @Override
    public void onPageOpeningRequested(String page) {
        Uri uri = Uri.parse(page);
        Intent intent = new Intent();
        intent.setData(uri);
        openPage(intent);
    }
}

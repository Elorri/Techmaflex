package com.techmaflex.app.navigation;

import android.content.Context;
import android.view.View;

import com.techmaflex.app.NotFoundView;
import com.techmaflex.app.data.Datastore;
import com.techmaflex.app.data.DatastoreAndroidSQLite;
import com.techmaflex.app.data.DatastoreBundle;
import com.techmaflex.app.sertissage.DiametreDeSertissageView;

public class LoadViewImpl implements LoadViewInterface {

    //App
    public static final String DIAMETRE_DE_SERTISSAGE = "/diametre_de_sertissage";

    @Override
    public View loadView(Context context, String path, DatastoreBundle bundle, LoadViewInterfaceCallback callback) {
        //Tous les écrans de l'appli utilisent la même source de donnée : le datastore
        //Décommenter ça si l'on a besoin de choisir la location de la db
//        FileStore osFileProvider = new AndroidFileController(context);
//        String datastoreFilePath = osFileProvider.getPublicAppDir().getPath();
        Datastore datastore = new DatastoreAndroidSQLite(context, null /*datastoreFilePath*/);

        View pageView = null;
        switch (path) {
            case DIAMETRE_DE_SERTISSAGE: {
                pageView = new DiametreDeSertissageView(context);
                ((DiametreDeSertissageView) pageView).setData(datastore, bundle, null);
                break;
            }
            default: {
                pageView = new NotFoundView(context);
            }
        }
        return pageView;
    }

}

package com.techmaflex.app.navigation;

import android.content.Context;
import android.view.View;

import com.techmaflex.app.data.DatastoreBundle;

public interface LoadViewInterface {

     /**
      * Charge une page/écran étant donnée une adresse
      */
     View loadView(Context context, String path, DatastoreBundle bundleView, LoadViewInterfaceCallback callback);
}

package com.techmaflex.app.navigation;

import com.techmaflex.app.data.Datastore;
import com.techmaflex.app.data.DatastoreBundle;

public interface ViewInterface {

    void setData(Datastore datastore, DatastoreBundle bundle, ViewCallback callback) ;
}

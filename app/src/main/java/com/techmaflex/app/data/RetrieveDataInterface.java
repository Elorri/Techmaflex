package com.techmaflex.app.data;

public interface RetrieveDataInterface {

     /**
      * Interface permettant de reprsenter sous forme de classe Java une requete la db.
      * Permet de faire facilement des sous-requêtes ou d'integrer du code Java au milieu des requêtes.
      */
     DatastoreBundle retrieveData(Datastore datastore, DatastoreBundle datastoreBundle);
}

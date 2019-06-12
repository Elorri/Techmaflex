package com.techmaflex.app.data;

import java.util.ArrayList;


/**
 * Permet d'ajouter, supprimer, modifier des données dans une database, ou un système de fichier.
 * Cette interface permet des appels indépendant du type de db utilisée. Ce qui permet de changer facilement de db si nécessaire.
 */
public interface Datastore {

    /**
     * Ajoute une ligne à la table spécifiée en paramètre
     * @param dataSingle doit contenir la clé 'table' avec le nom de la tables,
     * et la clé 'value' contenant l'ensembles des couples clé-valeurs cad colonne-valeurs.
     * @return Un bundle contenant 2 clés minimum.
     *  - added_item : le bundle representant l'item ajouté.
     *  - report : string contenant
     *      soit 'OK' quand l'opération s'est déroulée sans problèmes.
     *      soit 'WARNING' quand il y a eu une exception qui n'est pas une erreur ou autre
     *      soit 'ERROR' quand il y a eu une exception qui est une erreur
     *      soit 'WARNING_ERROR' quand il y a à la fois des exceptions et des erreurs.
     *  - warnings : une liste de bundle contenant :
     *      un id : la date ou l'erreur est apparue
     *      un message : le message du catch ou celui qui nous convient
     *  - errors : idem warnings
     */
    DatastoreBundle addUnique(DatastoreBundle dataSingle);

    /**
     * Ajoute une ligne à la table spécifiée en paramètre
     * cf above
     * @param table
     * @param dataSingle
     * @return
     */
    DatastoreBundle addUnique(String table, DatastoreBundle dataSingle);

    /**
     * Ajoute des lignes dans les tables spécifiées dans les bundles
     * @param dataList Chaque bundle doit contenir la clé 'table' parmi toute ses autre clés
     * @return Un bundle contenant 2 clés minimum.
     * - added_items (avec un -s) contenant la liste des items ajoutés.
     * - report
     */
    DatastoreBundle add(ArrayList<DatastoreBundle> dataList);

    /**
     * Ajoute des lignes dans les tables spécifiées dans les bundles
     * @param dataList Chaque bundle représenter des données de la table spécifiée.
     * Cad il faut vérifier qu'on a les bonnes colonnes.
     * @return
     */
    DatastoreBundle add(String table, ArrayList<DatastoreBundle> dataList);

    /**
     * Retourne un bundle ayant au moins 2 clés
     * - result : le bundle résultat retourné par la query. NULL si la requête ne retourne rien.
     * - report : string contenant cf méthode addUnique
     * @param query bundle devant contenir les clés correspondantes aux paramètres de la méthode qui suit cad
     *              distinct, table, projection, selection, selectionArgs, groupBy, having, orderBy
     * @return
     */
    DatastoreBundle readUnique(DatastoreBundle query);

    DatastoreBundle readUnique(String table, String[] projection, String selection,
                         String[] selectionArgs, String groupBy, String having,
                         String orderBy);

    DatastoreBundle readUnique(String table, String[] columns, String selection,
                         String[] selectionArgs, String groupBy, String having,
                         String orderBy, String limit);

    DatastoreBundle readUnique(boolean distinct, String table, String[] columns,
                         String selection, String[] selectionArgs, String groupBy,
                         String having, String orderBy, String limit);

    DatastoreBundle read(DatastoreBundle query);

    DatastoreBundle read(String table, String[] projection, String selection,
                                    String[] selectionArgs, String groupBy, String having,
                                    String orderBy);

    DatastoreBundle read(String table, String[] columns, String selection,
                                    String[] selectionArgs, String groupBy, String having,
                                    String orderBy, String limit);

    DatastoreBundle read(boolean distinct, String table, String[] columns,
                                    String selection, String[] selectionArgs, String groupBy,
                                    String having, String orderBy, String limit);

    /**
     * Retourne un bundle ayant au moins 1 clé
     * - report : string contenant cf méthode addUnique
     * @param dataSingle bundle devant contenir les clés correspondantes aux paramètres de la méthode qui suit cad
     *              table, value, selection, selectionArgs
     * @param dataSingle
     * @return
     */
    DatastoreBundle updateUnique(DatastoreBundle dataSingle);
    DatastoreBundle updateUnique(String table, DatastoreBundle value, String selection, String[] selectionArgs);

    /**
     * Retourne un bundle ayant au moins 1 clé
     * - report : string contenant cf méthode addUnique
     * @param dataList liste de bundle devant contenir chacun les clés correspondantes aux paramètres de la méthode qui suit cad
     *              table, value, selection, selectionArgs
     * @return report : string contenant cf méthode addUnique. Contient la syntèse de toutes les erreurs.
     */
    DatastoreBundle update(ArrayList<DatastoreBundle> dataList);

    /**
     * Retourne un bundle ayant au moins 1 clé
     * - report : string contenant cf méthode addUnique
     * @param dataSingle bundle devant contenir les clés correspondantes aux paramètres de la méthode qui suit cad
     *              table, value, selection, selectionArgs
     * @return
     */
    DatastoreBundle deleteUnique(DatastoreBundle dataSingle);
    DatastoreBundle deleteUnique(String table, String selection, String[] selectionArgs);

    /**
     * Retourne un bundle ayant au moins 1 clé
     * - report : string contenant cf méthode addUnique
     * @param dataList liste de bundle devant contenir chacun les clés correspondantes aux paramètres de la méthode qui suit cad
     *              table, value, selection, selectionArgs
     * @return report : string contenant cf méthode addUnique. Contient la syntèse de toutes les erreurs.
     */
    DatastoreBundle delete(ArrayList<DatastoreBundle> dataList);

}

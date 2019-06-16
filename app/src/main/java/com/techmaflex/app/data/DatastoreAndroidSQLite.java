package com.techmaflex.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatastoreAndroidSQLite extends SQLiteOpenHelper implements Datastore {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "techmaflex.db";


    public static final String TECHMAFLEX = "techmaflex";
    private static String ID = "id";
    private static String TUYAU = "tuyau";
    private static String JUPE = "jupe";
    private static String EMBOUT = "embout";
    private static String DIAMETRE_DE_SERTISSAGE = "diametre_de_sertissage";


    private static final String CREATE_TABLE_TECHMAFLEX = "CREATE TABLE IF NOT EXISTS "
            + TECHMAFLEX +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TUYAU + " TEXT NOT NULL, "
            + JUPE + " TEXT NOT NULL, "
            + EMBOUT + " TEXT NOT NULL, "
            + DIAMETRE_DE_SERTISSAGE + " TEXT NOT NULL "
            + ")";


    private SQLiteDatabase mDb;

    public DatastoreAndroidSQLite(Context context, String datasotoreFilePath) {
        //Note ici on ignore datasotoreFilePath car le SQLiteOpenHelper stoke la mDb localement
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        init(context, datasotoreFilePath);
    }

    protected void init(Context context, String datastoreFilePath) {
        mDb = getWritableDatabase(); //Ceci va appeler le onCreate ou onUpgrade
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        mDb = db;

        //Creation des tables indispensable
        mDb.execSQL(CREATE_TABLE_TECHMAFLEX);


        //Inserts
        DatastoreBundle documents = new DatastoreBundleUtil().fromJSONStringToBundle("{\n" +
                "  \"documents\": [\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"12,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"12,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"13,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"13,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"14\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"14,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"14,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"null\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"15,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"15,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"16\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"16,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"16,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"17,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"17,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"17,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"24\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"18\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"18,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"18,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"18,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"19,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"19,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"20,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"20,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"21,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"21,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"22\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"ETERNITY/2\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"22,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"22,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"23,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"23,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"24\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"24,4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"M00120-04\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"24,8\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"25,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF\",\n" +
                "      \"diametre_de_sertissage\": \"25,6\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"GOLDENISO/21 ANTIWEAR\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"26\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (RED)\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"27,1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (PINK)\",\n" +
                "      \"jupe\": \"null\",\n" +
                "      \"embout\": \"null\",\n" +
                "      \"diametre_de_sertissage\": \"19\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-05KM\",\n" +
                "      \"embout\": \"MF-KML\",\n" +
                "      \"diametre_de_sertissage\": \"27,2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"tuyau\": \"EQUATOR/1 (BLUE)\",\n" +
                "      \"jupe\": \"M00120-04KM\",\n" +
                "      \"embout\": \"MF-KML1\",\n" +
                "      \"diametre_de_sertissage\": \"27,3\"\n" +
                "    }\n" +
                "  ]\n" +
                "}");

        for (DatastoreBundle aBundle : documents.getDatastoreBundleArrayList("documents")) {
            mDb.insert(TECHMAFLEX, null, aBundle.toContentValues());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mDb = db;

        //Vu que la table est très petite, à chaque changement de structure on peut supprimer la table et la recréer.
        mDb.execSQL("DROP TABLE IF EXISTS " + TECHMAFLEX);
        onCreate(mDb);
    }

    @Override
    public DatastoreBundle addUnique(DatastoreBundle dataSingle) {
        return null;
    }

    @Override
    public DatastoreBundle addUnique(String table, DatastoreBundle dataSingle) {
        ArrayList<DatastoreBundle> aList = new ArrayList<>();
        aList.add(dataSingle);
        return add(table, aList);
    }

    @Override
    public DatastoreBundle add(ArrayList<DatastoreBundle> dataList) {
        return null;
    }

    @Override
    public DatastoreBundle add(String table, ArrayList<DatastoreBundle> dataList) {
        ArrayList<DatastoreBundle> addedItems = new ArrayList<>();
        for (DatastoreBundle aData : dataList) {
            if (mDb.insert(table, null, aData.toContentValues()) != -1) {
                addedItems.add(aData);
            }
        }
        DatastoreBundle b = new DatastoreBundle();
        b.putDatastoreArrayList("documents", addedItems);
        return b;
    }

    @Override
    public DatastoreBundle readUnique(DatastoreBundle query) {
        return null;
    }

    @Override
    public DatastoreBundle readUnique(String table, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        DatastoreBundle results=read( table, projection, selection, selectionArgs, groupBy, having, orderBy);
        if(results.getDatastoreBundleArrayList("documents").isEmpty()){
            return new DatastoreBundle();
        }
        return results.getDatastoreBundleArrayList("documents").get(0);
    }

    @Override
    public DatastoreBundle readUnique(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        DatastoreBundle results=read( table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if(results.getDatastoreBundleArrayList("documents").isEmpty()){
            return new DatastoreBundle();
        }
        return results.getDatastoreBundleArrayList("documents").get(0);
    }

    @Override
    public DatastoreBundle readUnique(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        DatastoreBundle results=read(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if(results.getDatastoreBundleArrayList("documents").isEmpty()){
            return new DatastoreBundle();
        }
        return results.getDatastoreBundleArrayList("documents").get(0);
    }

    @Override
    public DatastoreBundle read(DatastoreBundle query) {
        return null;
    }

    @Override
    public DatastoreBundle read(String table, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return new DatastoreBundle(mDb.query(table, projection, selection, selectionArgs, groupBy, having, orderBy));
    }

    @Override
    public DatastoreBundle read(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return new DatastoreBundle(mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit));
    }

    @Override
    public DatastoreBundle read(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return new DatastoreBundle(mDb.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit));
    }

    @Override
    public DatastoreBundle updateUnique(DatastoreBundle dataSingle) {
        return null;
    }

    @Override
    public DatastoreBundle updateUnique(String table, DatastoreBundle value, String selection, String[] selectionArgs) {
        return null;
    }

    @Override
    public DatastoreBundle update(ArrayList<DatastoreBundle> dataList) {
        return null;
    }

    @Override
    public DatastoreBundle deleteUnique(DatastoreBundle dataSingle) {
        return null;
    }

    @Override
    public DatastoreBundle deleteUnique(String table, String selection, String[] selectionArgs) {
        return null;
    }

    @Override
    public DatastoreBundle delete(ArrayList<DatastoreBundle> dataList) {
        return null;
    }

    public void deleteAll(String table) {
        mDb.delete(table, null, null);
    }
}

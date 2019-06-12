package com.techmaflex.app.sertissage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techmaflex.app.R;
import com.techmaflex.app.data.Datastore;
import com.techmaflex.app.data.DatastoreBundle;
import com.techmaflex.app.navigation.ViewCallback;
import com.techmaflex.app.navigation.ViewInterface;

import java.util.ArrayList;

public class DiametreDeSertissageView extends LinearLayout implements ViewInterface {

    private final Context mContext;
    private DatastoreBundle mBundle;

    public DiametreDeSertissageView(@NonNull Context context) {
        this(context, null);
    }

    public DiametreDeSertissageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflate(context, R.layout.diametre_de_sertissage, this);

    }

    @Override
    public void setData(Datastore datastore, DatastoreBundle bundle, ViewCallback callback) {

        if (datastore == null && !bundle.keySet().isEmpty()) {
            //Cas d'un bundle donné par les tests
            //On affiche directement la vue
            return;
        }

        //Le bundle n'est pas crée on le crée à partir de la db.
        onInit(datastore, bundle);
    }

    private void onInit(final Datastore datastore, final DatastoreBundle bundleQuery) {

        //On génère le bundle représentant la vue
        //il est important ici que ce soit la variable d'instance bundle qui soit initialisée
        // car les méthodes qui suivent font appel à updateViewFromBundle qui ne fonctionne qu'avec le bundle d'instance.
        mBundle = new RetrieveSertissageBundleInit().retrieveData(datastore, bundleQuery);


        //Init des vues indiquées visibles par le bundle, et init avec les valeurs du bundle.
         
        for (DatastoreBundle aViewBundle : mBundle.getDatastoreBundleArrayList("views")) {

            switch (aViewBundle.getString("id")) {
                case "diametre_de_sertissage_message": {
                    View diametre_de_sertissage_message = findViewByName("diametre_de_sertissage_message");
                    diametre_de_sertissage_message.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    if (diametre_de_sertissage_message.getVisibility() == VISIBLE) {
                        initMessageView();
                    }
                    break;
                }
                case "diametre_de_sertissage_no_data": {
                    View diametre_de_sertissage_no_data = findViewByName("diametre_de_sertissage_no_data");
                    diametre_de_sertissage_no_data.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    if (diametre_de_sertissage_no_data.getVisibility() == VISIBLE) {
                        initDiametreDeSertissageNoDataView();
                    }
                    break;
                }
                case "diametre_de_sertissage_main": {
                    View diametre_de_sertissage_main = findViewByName("diametre_de_sertissage_main");
                    diametre_de_sertissage_main.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    if (diametre_de_sertissage_main.getVisibility() == VISIBLE) {
                        initDiametreDeSertissageMainView(datastore);
                    }
                    break;
                }
                default:
            }
        }
    }


    private void initMessageView() {

    }

    private void initDiametreDeSertissageNoDataView() {
        View diametre_de_sertissage_synchronisation = findViewByName("diametre_de_sertissage_synchronisation");
        diametre_de_sertissage_synchronisation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Synchro demanded", Toast.LENGTH_LONG).show();
                //onConfigureSynchronisationDemanded();
            }
        });
        View diametre_de_sertissage_import = findViewByName("diametre_de_sertissage_import");
        diametre_de_sertissage_import.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onImportDemanded();
            }
        });
    }

    private void initDiametreDeSertissageMainView(final Datastore datastore) {
        //Init des vues

        //Partie avec diametre et bouton send machine
        View sendButton = findViewByName("diametre_de_sertissage_send");
        sendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Envoyer diamètre à machine via blutooth", Toast.LENGTH_LONG).show();
            }
        });


        //Spinner tuyau
        final Spinner tuyauSpinner = (Spinner) findViewByName("diametre_de_sertissage_tuyau");
        final StringSpinnerAdapter tuyauAdapter = new StringSpinnerAdapter(mContext, new ArrayList<String>());
        tuyauSpinner.setAdapter(tuyauAdapter);
        tuyauSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onTuyauSelected(datastore, tuyauSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner jupe
        final Spinner jupeSpinner = (Spinner) findViewByName("diametre_de_sertissage_jupe");
        StringSpinnerAdapter jupeAdapter =
                new StringSpinnerAdapter(mContext, new ArrayList<String>());
        jupeSpinner.setAdapter(jupeAdapter);
        jupeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onJupeSelected(datastore, jupeSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner embout
        final Spinner emboutSpinner = (Spinner) findViewByName("diametre_de_sertissage_embout");
        StringSpinnerAdapter emboutAdapter = new StringSpinnerAdapter(mContext, new ArrayList<String>());
        emboutSpinner.setAdapter(emboutAdapter);
        emboutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onEmboutSelected(datastore, emboutSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Bouton reset
        View resetButton = findViewByName("diametre_de_sertissage_reset");
        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onInit(datastore, null);
            }
        });

        //Mise à jour des vues à partir du bundle
        updateViewFromBundle(datastore);
    }

    private void onTuyauSelected(Datastore datastore, Spinner spinnerView, int position) {
        onSpinnerItemSelected(datastore, spinnerView, position);
    }

    private void onJupeSelected(Datastore datastore, Spinner spinnerView, int position) {
        onSpinnerItemSelected(datastore, spinnerView, position);
    }

    private void onEmboutSelected(Datastore datastore, Spinner spinnerView, int position) {
        onSpinnerItemSelected(datastore, spinnerView, position);
    }


    private void onSpinnerItemSelected(Datastore datastore, Spinner spinnerView, int position) {
        String selection = (String) spinnerView.getAdapter().getItem(position);
        spinnerView.setSelection(position);
//      TODO  L'une ou l'autre de ces commandes ne mets pas à jour l'arraylist view du bundle, il faudraudrait que views ne soit pas un arraylist'
        //DatastoreBundle spinnerViewBundle = (DatastoreBundle) spinnerView.getTag();
        DatastoreBundle spinnerViewBundle = findBundleViewByNameId(mBundle, getRessourceName(spinnerView.getId()));
        if (selection.equals(spinnerViewBundle.getString("selection"))) {
            return; //Il n'a rien sélectionné, on sort.
        }
        //Cette selection peut affecter les 2 autres listes, on recalcule donc le bundle de la vue.
        spinnerViewBundle.putString("selection", selection);
        mBundle = new RetrieveSertissageBundleAfterSelection().retrieveData(datastore, mBundle);

        //On mets à jours les vues avec les données du bundle
        updateViewFromBundle(datastore);
    }


    private void updateViewFromBundle(Datastore datastore) {
        for (DatastoreBundle aViewBundle : mBundle.getDatastoreBundleArrayList("views")) {

            switch (aViewBundle.getString("id")) {
                case "diametre_de_sertissage_message": {
                    View diametre_de_sertissage_message = findViewByName("diametre_de_sertissage_message");
                    int lastVisibility = diametre_de_sertissage_message.getVisibility();
                    diametre_de_sertissage_message.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    if (lastVisibility != diametre_de_sertissage_message.getVisibility()
                            && diametre_de_sertissage_message.getVisibility() == VISIBLE) {
                        //Si la vues n'étaitt pas visible et que maintenant elle l'est.
                        // Il est possible qu'elle n'aie pas été initialisée. Donc on l'intialise.
                        initMessageView();
                    }
                    break;
                }
                case "diametre_de_sertissage_no_data": {
                    View diametre_de_sertissage_no_data = findViewByName("diametre_de_sertissage_no_data");
                    int lastVisibility = diametre_de_sertissage_no_data.getVisibility();
                    diametre_de_sertissage_no_data.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    if (lastVisibility != diametre_de_sertissage_no_data.getVisibility()
                            && diametre_de_sertissage_no_data.getVisibility() == VISIBLE) {
                        //Si la vues n'étaitt pas visible et que maintenant elle l'est.
                        // Il est possible qu'elle n'aie pas été initialisée. Donc on l'intialise.
                        initDiametreDeSertissageNoDataView();
                    }
                    break;
                }
                case "diametre_de_sertissage_main": {
                    View diametre_de_sertissage_main = findViewByName("diametre_de_sertissage_main");
                    int lastVisibility = diametre_de_sertissage_main.getVisibility();
                    diametre_de_sertissage_main.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    if (lastVisibility != diametre_de_sertissage_main.getVisibility()
                            && diametre_de_sertissage_main.getVisibility() == VISIBLE) {
                        //Si la vues n'étaitt pas visible et que maintenant elle l'est.
                        // Il est possible qu'elle n'aie pas été initialisée. Donc on l'intialise.
                        initDiametreDeSertissageMainView(datastore);
                    }
                    break;
                }

                case "diametre_de_sertissage_tuyau": {
                    Spinner diametre_de_sertissage_tuyau = (Spinner) findViewByName("diametre_de_sertissage_tuyau");
                    StringSpinnerAdapter tubeAdapter =
                            ((StringSpinnerAdapter) diametre_de_sertissage_tuyau.getAdapter());
                    tubeAdapter.swapData(aViewBundle.getStringArrayList("list"));
                    diametre_de_sertissage_tuyau
                            .setSelection(tubeAdapter.getPosition(aViewBundle.getString("selection")));
                    diametre_de_sertissage_tuyau.setTag(aViewBundle);
                    break;
                }
                case "diametre_de_sertissage_jupe": {
                    Spinner diametre_de_sertissage_jupe = (Spinner) findViewByName("diametre_de_sertissage_jupe");
                    StringSpinnerAdapter jupeAdapter =
                            ((StringSpinnerAdapter) diametre_de_sertissage_jupe.getAdapter());
                    jupeAdapter.swapData(aViewBundle.getStringArrayList("list"));
                    diametre_de_sertissage_jupe
                            .setSelection(jupeAdapter.getPosition(aViewBundle.getString("selection")));
                    diametre_de_sertissage_jupe.setTag(aViewBundle);
                    break;
                }
                case "diametre_de_sertissage_embout": {
                    Spinner diametre_de_sertissage_embout = (Spinner) findViewByName("diametre_de_sertissage_embout");
                    StringSpinnerAdapter emboutAdapter =
                            ((StringSpinnerAdapter) diametre_de_sertissage_embout.getAdapter());
                    emboutAdapter.swapData(aViewBundle.getStringArrayList("list"));
                    diametre_de_sertissage_embout
                            .setSelection(emboutAdapter.getPosition(aViewBundle.getString("selection")));
                    diametre_de_sertissage_embout.setTag(aViewBundle);
                    break;
                }
                case "diametre_de_sertissage_value": {
                    TextView diametre_de_sertissage_value = (TextView) findViewByName("diametre_de_sertissage_value");
                    diametre_de_sertissage_value.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    diametre_de_sertissage_value.setText(aViewBundle.getString("text"));
                    break;
                }
                case "diametre_de_sertissage_send": {
                    View diametre_de_sertissage_send = findViewByName("diametre_de_sertissage_send");
                    diametre_de_sertissage_send.setEnabled(aViewBundle.getString("enabled").equals("true"));
                    break;
                }
                case "diametre_de_sertissage_reset": {
                    break;
                }
                default:
            }
        }
    }

    private void onImportDemanded() {
        Toast.makeText(mContext, "Import demanded", Toast.LENGTH_LONG).show();
    }

    private int getIntVisibility(String visibility) {
        switch (visibility) {
            case "invisible":
                return INVISIBLE;
            case "gone":
                return GONE;
            default:
            case "visible":
                return VISIBLE;
        }
    }

    private View findViewByName(String nameId) {
        //Note : passer par un id sous forme de string comme ça permettra de restaurer la vue à partir du bundle facilement.
        //A voir si impact sur les perfs. Sinon passer par un int R.id.int
        return findViewById(mContext.getResources().getIdentifier(nameId, "id", mContext.getPackageName()));
    }

    private DatastoreBundle findBundleViewByNameId(DatastoreBundle viewBundle, String nameId) {
        //TODO ne vaudrait il pas mieux trouver les bundle par des tags
        for (DatastoreBundle aViewBundle : viewBundle.getDatastoreBundleArrayList("views")) {
            if (aViewBundle.getString("id").equals(nameId)) {
                return aViewBundle;
            }
        }
        return null;
    }

    private String getRessourceName(int ressourceId) {
        return getResources().getResourceEntryName(ressourceId);
    }

}

package com.techmaflex.app.sertissage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
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
import com.techmaflex.app.util.LayoutUtil;

import java.util.ArrayList;

public class DiametreDeSertissageView extends LinearLayout implements ViewInterface {

    private final Context mContext;
    private DatastoreBundle mBundle;
    private int tuyauSelectCount;
    private int jupeSelectCount;
    private int emboutSelectCount;

    private Spinner mTuyauSpinner;
    private Spinner mJupeSpinner;
    private Spinner mEmboutSpinner;

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

        View messageView = findViewByName("diametre_de_sertissage_message");
        DatastoreBundle messageViewBundle =
                mBundle.getDatastoreBundle("views").getDatastoreBundle("diametre_de_sertissage_message");
        messageView.setVisibility(getIntVisibility(messageViewBundle.getString("visibility")));
        if (messageView.getVisibility() == VISIBLE) {
            initMessageView();
        }
        View noDataView = findViewByName("diametre_de_sertissage_no_data");
        DatastoreBundle noDataViewBundle =
                mBundle.getDatastoreBundle("views").getDatastoreBundle("diametre_de_sertissage_no_data");
        noDataView.setVisibility(getIntVisibility(noDataViewBundle.getString("visibility")));
        if (noDataView.getVisibility() == VISIBLE) {
            initDiametreDeSertissageNoDataView();
        }
        View diametreSertissageView = findViewByName("diametre_de_sertissage_main");
        DatastoreBundle diametreSertissageViewBundle =
                mBundle.getDatastoreBundle("views").getDatastoreBundle("diametre_de_sertissage_main");
        diametreSertissageView.setVisibility(getIntVisibility(diametreSertissageViewBundle.getString("visibility")));
        if (diametreSertissageView.getVisibility() == VISIBLE) {
            initDiametreDeSertissageMainView(datastore);
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
        mTuyauSpinner = (Spinner) findViewByName("diametre_de_sertissage_tuyau");
        final StringSpinnerAdapter tuyauAdapter = new StringSpinnerAdapter(mContext, new ArrayList<String>(), new StringSpinnerAdapter.Callback() {
            @Override
            public void onLargestItemViewPxChanged(float largestItemViewPxSize) {
                Log.e("Tech", Thread.currentThread().getStackTrace()[2] +
                        "spinner : " + "tuyau" +
                        " largestItemViewPxSize : " + largestItemViewPxSize);
                adjustSpinnersWidth(largestItemViewPxSize);
            }
        }, "tuyau");
        mTuyauSpinner.setAdapter(tuyauAdapter);
        mTuyauSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onTuyauSelected(datastore, mTuyauSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner jupe
        mJupeSpinner = (Spinner) findViewByName("diametre_de_sertissage_jupe");
        StringSpinnerAdapter jupeAdapter =
                new StringSpinnerAdapter(mContext, new ArrayList<String>(), new StringSpinnerAdapter.Callback() {
                    @Override
                    public void onLargestItemViewPxChanged(float largestItemViewPxSize) {
                        Log.e("Tech", Thread.currentThread().getStackTrace()[2] +
                                "spinner : " + "jupe" +
                                " largestItemViewPxSize : " + largestItemViewPxSize);
                        adjustSpinnersWidth(largestItemViewPxSize);
                    }
                }, "jupe");
        mJupeSpinner.setAdapter(jupeAdapter);
        mJupeSpinner.setEnabled(mBundle.getDatastoreBundle("views")
                .getDatastoreBundle("diametre_de_sertissage_jupe").getString("enabled").equals("true"));
        mJupeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onJupeSelected(datastore, mJupeSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner embout
        mEmboutSpinner = (Spinner) findViewByName("diametre_de_sertissage_embout");
        StringSpinnerAdapter emboutAdapter = new StringSpinnerAdapter(mContext, new ArrayList<String>(), new StringSpinnerAdapter.Callback() {
            @Override
            public void onLargestItemViewPxChanged(float largestItemViewPxSize) {
                Log.e("Tech", Thread.currentThread().getStackTrace()[2] +
                        "spinner : " + "embout" +
                        " largestItemViewPxSize : " + largestItemViewPxSize);
                adjustSpinnersWidth(largestItemViewPxSize);
                adjustSpinnersWidth(largestItemViewPxSize);
            }
        }, "embout");
        mEmboutSpinner.setAdapter(emboutAdapter);
        mEmboutSpinner.setEnabled(mBundle.getDatastoreBundle("views")
                .getDatastoreBundle("diametre_de_sertissage_embout").getString("enabled").equals("true"));
        mEmboutSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onEmboutSelected(datastore, mEmboutSpinner, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Mise à jour des vues à partir du bundle
        updateViewFromBundle(datastore);
    }

    private void adjustSpinnersWidth(float largestItemViewDpSize) {
        //La taille est trop petite si on met en dp et trop grande en Px. Pas très concluant.
        mTuyauSpinner.getLayoutParams().width =  (int) new LayoutUtil().toPx(mContext, largestItemViewDpSize);
        ((StringSpinnerAdapter) mTuyauSpinner.getAdapter()).setAllItemsWidth((int) largestItemViewDpSize);

        mJupeSpinner.getLayoutParams().width = (int) new LayoutUtil().toPx(mContext, largestItemViewDpSize);
        ((StringSpinnerAdapter) mJupeSpinner.getAdapter()).setAllItemsWidth((int) largestItemViewDpSize);

        mEmboutSpinner.getLayoutParams().width = (int) new LayoutUtil().toPx(mContext, largestItemViewDpSize);
        ((StringSpinnerAdapter) mEmboutSpinner.getAdapter()).setAllItemsWidth((int) largestItemViewDpSize);
    }

    private void onTuyauSelected(Datastore datastore, Spinner spinnerView, int position) {
        //Verif si la modif est demandée par l'utilisateur ou non
        tuyauSelectCount++;
        boolean isFirstTuyauFirstSelection = tuyauSelectCount <= 1;

        //Si l'utilisateur change d'item dans ce menu (qui contient toujours toutes les options),
        // on supprime les selections qu'il aurait pu faire dans les autres menus.
        DatastoreBundle views = mBundle.getDatastoreBundle("views");
        DatastoreBundle tuyauBundle = views.getDatastoreBundle("diametre_de_sertissage_tuyau");
        String selection = (String) spinnerView.getAdapter().getItem(position);
        if (!isFirstTuyauFirstSelection && !selection.equals(tuyauBundle.getString("selection"))) {
            DatastoreBundle jupeBundle = views.getDatastoreBundle("diametre_de_sertissage_jupe");
            if (jupeBundle.containsKey("selection_user")) {
                jupeBundle.remove("selection_user");
                //TODO modifier le datastore de façon a éviter d'avoir à faire ça à chaque fois
                views.putDatastoreBundle("diametre_de_sertissage_jupe", jupeBundle);
                mBundle.putDatastoreBundle("views", views);
            }
            DatastoreBundle emboutBundle = views.getDatastoreBundle("diametre_de_sertissage_embout");
            if (emboutBundle.containsKey("selection_user")) {
                emboutBundle.remove("selection_user");
                //TODO modifier le datastore de façon a éviter d'avoir à faire ça à chaque fois
                views.putDatastoreBundle("diametre_de_sertissage_embout", emboutBundle);
                mBundle.putDatastoreBundle("views", views);
            }
        }
        onSpinnerItemSelected(datastore, spinnerView, position, !isFirstTuyauFirstSelection);
    }

    private void onJupeSelected(Datastore datastore, Spinner spinnerView, int position) {
        jupeSelectCount++;
        boolean isFirstJupeFirstSelection = jupeSelectCount <= 1;
        onSpinnerItemSelected(datastore, spinnerView, position, !isFirstJupeFirstSelection);
    }

    private void onEmboutSelected(Datastore datastore, Spinner spinnerView, int position) {
        emboutSelectCount++;
        boolean isFirstEmboutFirstSelection = emboutSelectCount <= 1;
        onSpinnerItemSelected(datastore, spinnerView, position, !isFirstEmboutFirstSelection);
    }

    private void onSpinnerItemSelected(Datastore datastore, Spinner spinnerView, int position, boolean isUserSelection) {
        String selection = (String) spinnerView.getAdapter().getItem(position);
        spinnerView.setSelection(position);
        DatastoreBundle spinnerViewBundle = (DatastoreBundle) spinnerView.getTag();
        //DatastoreBundle spinnerViewBundle = findBundleViewByNameId(mBundle, getRessourceName(spinnerView.getId()));
        if (selection.equals(spinnerViewBundle.getString("selection"))) {
            return; //Il n'a rien sélectionné, on sort.
        }
        //Cette selection peut affecter les 2 autres listes, on recalcule donc le bundle de la vue.
        DatastoreBundle views = mBundle.getDatastoreBundle("views");
        spinnerViewBundle.putString(isUserSelection ? "selection_user" : "selection", selection.equals("") ? "null" : selection);
        views.putDatastoreBundle(getRessourceName(spinnerView.getId()), spinnerViewBundle);
        mBundle.putDatastoreBundle("views", views);
        mBundle.putBoolean("is_spinner_init_selection", !isUserSelection);
        mBundle = new RetrieveSertissageBundleAfterSelection().retrieveData(datastore, mBundle);

        //On mets à jours les vues avec les données du bundle
        updateViewFromBundle(datastore);
    }


    private void updateViewFromBundle(Datastore datastore) {
        DatastoreBundle views = mBundle.getDatastoreBundle("views");
        for (String aViewBundleKey : views.keySet()) {

            switch (aViewBundleKey) {
                case "diametre_de_sertissage_message": {
                    View diametre_de_sertissage_message = findViewByName("diametre_de_sertissage_message");
                    int lastVisibility = diametre_de_sertissage_message.getVisibility();
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_message");
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
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_no_data");
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
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_main");
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
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_tuyau");
                    tubeAdapter.swapData(aViewBundle.getStringArrayList("list"));
                    diametre_de_sertissage_tuyau
                            .setSelection(tubeAdapter.getPosition(aViewBundle.getString("selection")));
                    diametre_de_sertissage_tuyau.setTag(aViewBundle);
                    break;
                }
                case "diametre_de_sertissage_jupe": {
                    Spinner jupeSpinner = (Spinner) findViewByName("diametre_de_sertissage_jupe");
                    StringSpinnerAdapter jupeAdapter =
                            ((StringSpinnerAdapter) jupeSpinner.getAdapter());
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_jupe");
                    jupeAdapter.swapData(aViewBundle.getStringArrayList("list"));
                    jupeSpinner
                            .setSelection(jupeAdapter.getPosition(aViewBundle.getString("selection")));
                    jupeSpinner.setEnabled(aViewBundle.getString("enabled").equals("true"));
                    jupeSpinner.setTag(aViewBundle);
                    break;
                }
                case "diametre_de_sertissage_embout": {
                    Spinner emboutSpinner = (Spinner) findViewByName("diametre_de_sertissage_embout");
                    StringSpinnerAdapter emboutAdapter =
                            ((StringSpinnerAdapter) emboutSpinner.getAdapter());
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_embout");
                    emboutAdapter.swapData(aViewBundle.getStringArrayList("list"));
                    emboutSpinner
                            .setSelection(emboutAdapter.getPosition(aViewBundle.getString("selection")));
                    emboutSpinner.setEnabled(aViewBundle.getString("enabled").equals("true"));
                    emboutSpinner.setTag(aViewBundle);
                    break;
                }
                case "diametre_de_sertissage_value": {
                    TextView diametre_de_sertissage_value = (TextView) findViewByName("diametre_de_sertissage_value");
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_value");
                    diametre_de_sertissage_value.setVisibility(getIntVisibility(aViewBundle.getString("visibility")));
                    diametre_de_sertissage_value.setText(aViewBundle.getString("text"));
                    break;
                }
                case "diametre_de_sertissage_send": {
                    View diametre_de_sertissage_send = findViewByName("diametre_de_sertissage_send");
                    DatastoreBundle aViewBundle = views.getDatastoreBundle("diametre_de_sertissage_send");
                    diametre_de_sertissage_send.setEnabled(aViewBundle.getString("enabled").equals("true"));
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

    private String getRessourceName(int ressourceId) {
        return getResources().getResourceEntryName(ressourceId);
    }

}

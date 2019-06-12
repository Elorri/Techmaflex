package com.techmaflex.app.navigation;

import android.view.View;
import android.view.ViewGroup;

/**
 * Selon la façon où cette interface est implémentée, la vue se charge à l'interieur d'un
 * fragment ou non.
 */
public interface DisplayView {

    void show(ViewGroup container, View view);
}

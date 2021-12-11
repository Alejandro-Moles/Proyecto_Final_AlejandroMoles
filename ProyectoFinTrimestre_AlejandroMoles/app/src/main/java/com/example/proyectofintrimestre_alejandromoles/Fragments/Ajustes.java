package com.example.proyectofintrimestre_alejandromoles.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectofintrimestre_alejandromoles.R;


public class Ajustes extends PreferenceFragmentCompat {

    public Ajustes() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferencia, rootKey);
    }


}
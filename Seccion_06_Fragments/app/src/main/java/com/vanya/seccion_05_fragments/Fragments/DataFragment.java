package com.vanya.seccion_05_fragments.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vanya.seccion_05_fragments.R;


public class DataFragment extends Fragment {


    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Cambiamos el return para devolver un simple view
        // y tener todo mucho mas facil.

        View view = inflater.inflate(R.layout.fragment_data, container, false) ;

        // Inflate the layout for this fragment
        return view ;
    }

}

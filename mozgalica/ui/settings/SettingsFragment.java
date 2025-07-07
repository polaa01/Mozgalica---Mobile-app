package com.example.mozgalica.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mozgalica.LocaleHelper;
import com.example.mozgalica.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Spinner spinner = view.findViewById(R.id.spinner);
        String[] languages = {getString(R.string.english), getString(R.string.serbian)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, languages);
        spinner.setAdapter(adapter);



        SharedPreferences prefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        //String username = prefs.getString("username", null);

        String lang = prefs.getString("my_Lang", "en");
        spinner.setSelection(lang.equals("sr") ? 1 : 0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean first = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (first) {
                    first = false;
                    return;
                }

                String selectedLang = position == 0 ? "en" : "sr";
                LocaleHelper.setLocale(requireContext(), selectedLang);

                Intent intent = requireActivity().getIntent();
                requireActivity().finish();
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

}
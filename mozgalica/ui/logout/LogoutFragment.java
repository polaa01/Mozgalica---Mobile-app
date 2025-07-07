package com.example.mozgalica.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mozgalica.LoginActivity;
import com.example.mozgalica.MainMenuActivity;
import com.example.mozgalica.R;

public class LogoutFragment extends Fragment {

    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_confirmation))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {

                    processLogout();

                }).setNegativeButton(getString(R.string.no), (d, w) ->
                {
                    NavHostFragment.findNavController(this).popBackStack();
                }).show();

        return view;
    }

    private void processLogout()
    {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
    }

}
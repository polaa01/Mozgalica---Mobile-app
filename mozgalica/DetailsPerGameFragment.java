package com.example.mozgalica;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsPerGameFragment extends Fragment {

    private DetailsPerGameViewModel mViewModel;

    public static DetailsPerGameFragment newInstance() {
        return new DetailsPerGameFragment();
    }

    /*
    public static DetailsPerGameFragment newInstance(GameDetails details) {
        Bundle args = new Bundle();
        args.putSerializable("details", details);
        DetailsPerGameFragment fragment = new DetailsPerGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

     */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_per_game, container, false);
        TextView txtDescription = view.findViewById(R.id.txtDescription);
        TextView txtRules = view.findViewById(R.id.txtRules);
        TextView txtScoring = view.findViewById(R.id.txtScoring);
        TextView txtYoutube = view.findViewById(R.id.txtYoutubeLink);

      Bundle args = getArguments();
      if(args != null)
      {
          txtDescription.setText(args.getString("description", ""));
          txtRules.setText(args.getString("rules", ""));
          txtScoring.setText(args.getString("scoring", ""));
          txtYoutube.setText(args.getString("youtubeUrl", ""));
      }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsPerGameViewModel.class);
        // TODO: Use the ViewModel
    }

}
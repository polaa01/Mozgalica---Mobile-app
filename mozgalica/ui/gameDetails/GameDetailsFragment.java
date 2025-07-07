package com.example.mozgalica.ui.gameDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mozgalica.DetailsPerGameFragment;
import com.example.mozgalica.GameDetails;
import com.example.mozgalica.R;
import com.example.mozgalica.databinding.FragmentGameDetailsBinding;

public class GameDetailsFragment extends Fragment {

    private FragmentGameDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GameDetailsViewModel slideshowViewModel =
                new ViewModelProvider(this).get(GameDetailsViewModel.class);

        binding = FragmentGameDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        root.findViewById(R.id.button4).setOnClickListener(v -> openDetails(getMathQuiz()));
        root.findViewById(R.id.button5).setOnClickListener(v -> openDetails(getAnagrams()));
        root.findViewById(R.id.button6).setOnClickListener(v -> openDetails(getMemoryGame()));

        //final TextView textView = binding.textGameDetails;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void openDetails(GameDetails details)
    {
        /*
        //Fragment fragment = GameDetailsFragment.newInstance(details);
        DetailsPerGameFragment fragment = DetailsPerGameFragment.newInstance(details);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_game_details, fragment)
                .addToBackStack(null)
                .commit();


         */
        Bundle bundle = new Bundle();
        bundle.putString("title", details.getTitle());
        bundle.putString("description", details.getDescription());
        bundle.putString("rules", details.getRules());
        bundle.putString("scoring", details.getScoring());
        bundle.putString("youtubeUrl", details.getYoutubeUrl());

        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_nav_game_details_to_detailsPerGameFragment, bundle);


    }

    private GameDetails getMathQuiz()
    {
            return new GameDetails(getString(R.string.math), getString(R.string.math_desc),
                    getString(R.string.math_rules), getString(R.string.math_scoring),
                    "https://www.youtube.com/watch?v=f9sX7tQePNE");
    }

    private GameDetails getAnagrams()
    {
        return new GameDetails(getString(R.string.anagrams), getString(R.string.anagrams_desc),
                getString(R.string.anagrams_rules), getString(R.string.anagrams_scoring),
                "https://www.youtube.com/watch?v=trMl6AVOuV0");
    }

    private GameDetails getMemoryGame()
    {
        return new GameDetails(getString(R.string.memory), getString(R.string.memory_desc), getString(R.string.memory_rules),
                getString(R.string.memory_rules), "https://www.youtube.com/watch?v=s_RBgTqK_2g");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
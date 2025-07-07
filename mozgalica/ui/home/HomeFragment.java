package com.example.mozgalica.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mozgalica.R;
import com.example.mozgalica.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button mathQuizBtn = root.findViewById(R.id.btn_math_quiz);
        Button anagramsBtn = root.findViewById(R.id.btn_anagrams);
        Button memoryGameBtn = root.findViewById(R.id.btn_memory_game);

        NavController navController = NavHostFragment.findNavController(HomeFragment.this);

        anagramsBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_nav_home_to_anagramFragment);
        });

        memoryGameBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_nav_home_to_memoryGameFragment);
        });

        mathQuizBtn.setOnClickListener(v ->{
            navController.navigate(R.id.action_nav_home_to_mathQuizFragment);
        });




        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
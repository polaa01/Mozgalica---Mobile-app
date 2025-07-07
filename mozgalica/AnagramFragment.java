package com.example.mozgalica;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.example.mozgalica.db.DBHelper;
import com.example.mozgalica.db.model.GameResult;

import java.util.*;


public class AnagramFragment extends Fragment {

    private AnagramViewModel mViewModel;

    private List<String> allWords = Arrays.asList(
            "android", "studio", "kotlin", "java", "igra", "logika", "mobilni", "aplikacija",
            "fragment", "aktivnost", "navigacija", "baza", "sqlite", "korisnik", "rezultat",
            "dizajn", "program", "layout", "ekran", "dugme", "tekst", "unos", "hint",
            "tema", "jezik", "meni", "toolbar", "ikonica", "naslov", "ocjena", "tabela", "igrica",
            "memorija", "tacno", "netacno", "etf", "banjaluka", "zadatak", "pitanje", "odgovor", "shuffle", "broj"
    );

    private List<String> gameWords = null;
   private static final int TOTAL_WORDS = 10;
   private int currentQuestion = 0;
   private int totalQuestions = 10;
    private int score=0;
    private String currentWord;

    private int currentIndex=0;
    private Button btnConfirm;
    private EditText editGuess;
   private TextView tvWord, tvOutcome;

    public static AnagramFragment newInstance() {
        return new AnagramFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anagram, container, false);

        btnConfirm = view.findViewById(R.id.btn_Confirm);
        editGuess = view.findViewById(R.id.edit_Guess);
        tvWord = view.findViewById(R.id.tV_Word);
        tvOutcome = view.findViewById(R.id.tV_outcome);

        /*
        SharedPreferences prefs = getActivity().getSharedPreferences("MozgalicaPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
         */

      this.prepareCollection();
      this.loadNextWord();

  btnConfirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          String answer = editGuess.getText().toString().trim().toLowerCase();
          if (answer.equals(currentWord)) {
              score++;
              tvOutcome.setText(getString(R.string.correct) + "!");
          } else {
              tvOutcome.setText(getString(R.string.wrong) + ". " + getString(R.string.correct) + ": " + currentWord);
          }

          //tvScore.setText("Poeni: " + score);
          editGuess.setText("");
          currentQuestion++;

          btnConfirm.postDelayed(() -> {
              tvOutcome.setText("");

              /*
              if(currentQuestion >= totalQuestions)
              {
                  DBHelper db = new DBHelper(getContext());
                  GameResult gameResult = new GameResult(MainMenuActivity.ANAGRAMS_NAME, username, score);
                  db.insertGameResult(gameResult);
              }
              */

                  loadNextWord();



          }, 1200);

      }
  });


        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AnagramViewModel.class);
        // TODO: Use the ViewModel
    }


    private void prepareCollection()
    {
        gameWords = new ArrayList<>(allWords);
        Collections.shuffle(gameWords);
        gameWords = gameWords.subList(0, Math.min(TOTAL_WORDS, gameWords.size()));
    }

    private void loadNextWord() {
        if (currentIndex >= gameWords.size()) {

            SharedPreferences prefs = getActivity().getSharedPreferences("MozgalicaPrefs", Context.MODE_PRIVATE);
            String username = prefs.getString("username", "");
            DBHelper db = new DBHelper(getContext());
            GameResult gameResult = new GameResult(MainMenuActivity.ANAGRAMS_NAME, username, score);
            db.insertGameResult(gameResult);

            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.res))
                    .setMessage(getString(R.string.won) +  " " + score + " " + getString(R.string.points) + " "  + getString(R.string.outOf) + " " + gameWords.size())
                    .setPositiveButton("OK", (dialog, which) -> {
                        new AlertDialog.Builder(requireContext())
                                .setTitle(getString(R.string.new_game)).setMessage(R.string.question)
                                .setPositiveButton(getString(R.string.yes), (d, w) ->
                                {
                                    score = 0;
                                    currentIndex=0;
                                    currentQuestion=0;
                                    prepareCollection();
                                    loadNextWord();
                                }).setNegativeButton(getString(R.string.no), (d,w)->
                                {
                                    NavHostFragment.findNavController(this).popBackStack();
                                }).show();

                    }).show();
                        //NavHostFragment.findNavController(this).popBackStack();



            return;
        }

        currentWord = gameWords.get(currentIndex);
        tvWord.setText(currentIndex+1 + ". " + shuffleWord(currentWord));
        currentIndex++;
    }


    private String shuffleWord(String word) {
        List<Character> chars = new ArrayList<>();
        for (char c : word.toCharArray()) chars.add(c);
        Collections.shuffle(chars);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        return sb.toString();
    }

}
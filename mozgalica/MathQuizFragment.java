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


public class MathQuizFragment extends Fragment {

    private MathQuizViewModel mViewModel;

    private Button button, button2, button3;
    private TextView textView;

    private List<MathQuestion> allQuestions = null;
    private List<MathQuestion> quizQuestions = null;
    private int score = 0;
    private int currentQuestionIndex = 0;


    public static MathQuizFragment newInstance() {
        return new MathQuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_math_quiz, container, false);

        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        textView = view.findViewById(R.id.textView);

        this.allQuestions = MathQuestionRepository.getAllQuestions();
        Collections.shuffle(this.allQuestions);
        this.quizQuestions = this.allQuestions.subList(0, 10);

        this.loadQuestions();

        return view;
    }


    private void loadQuestions()
    {
        if (currentQuestionIndex >= quizQuestions.size()) {
            showResultDialog();
            return;
        }

        MathQuestion q = quizQuestions.get(currentQuestionIndex);
        textView.setText(currentQuestionIndex + 1 + ". " + q.getText());

        List<String> answers = q.getAnswers();
        button.setText(answers.get(0));
        button2.setText(answers.get(1));
        button3.setText(answers.get(2));

        View.OnClickListener listener = v -> {
            int chosen = -1;
            if (v == button) chosen = 0;
            else if (v == button2) chosen = 1;
            else if (v == button3) chosen = 2;

            if (chosen == q.getCorrectIndex()) {
                score++;
            }

            currentQuestionIndex++;
            this.loadQuestions();
        };

        button.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
    }

    private void showResultDialog()
    {

        SharedPreferences prefs = getActivity().getSharedPreferences("MozgalicaPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        DBHelper db = new DBHelper(getContext());
        GameResult gameResult = new GameResult(MainMenuActivity.MATH_QUIZ_NAME, username, score);
        db.insertGameResult(gameResult);
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.res))
                .setMessage(getString(R.string.won) +  " " + score + " " + getString(R.string.points) + " "  + getString(R.string.outOf) + " " + quizQuestions.size())
                .setPositiveButton("OK", (dialog, which) -> {
                    new AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.new_game)).setMessage(R.string.question)
                            .setPositiveButton(getString(R.string.yes), (d, w) ->
                            {
                                     score = 0;
                                     currentQuestionIndex=0;
                                     Collections.shuffle(allQuestions);
                                     quizQuestions = allQuestions.subList(0, 10);
                                     loadQuestions();
                            }).setNegativeButton(getString(R.string.no), (d,w)->
                            {
                                NavHostFragment.findNavController(this).popBackStack();
                            }).show();

                }).show();

    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MathQuizViewModel.class);
        // TODO: Use the ViewModel
    }

}
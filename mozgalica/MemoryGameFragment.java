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

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mozgalica.db.DBHelper;
import com.example.mozgalica.db.model.GameResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryGameFragment extends Fragment {

    private MemoryGameViewModel mViewModel;

    private GridLayout gridLayout;
    private List<ImageButton> buttons = new ArrayList<>();
    private ImageButton firstPick = null;
    private ImageButton secondPick = null;

    private boolean isBusy = false;



    private int matchedPairs = 0;
    private static final int TOTAL_PAIRS = 6;
    private CountDownTimer timer;
    private TextView tvTimer;
    private TextView tvScore;




    private List<Integer> icons = Arrays.asList(
            R.drawable.ic_star, R.drawable.ic_star,
            R.drawable.ic_square, R.drawable.ic_square,
            R.drawable.ic_circle, R.drawable.ic_circle,
            R.drawable.ic_keyboard, R.drawable.ic_keyboard,
            R.drawable.ic_network_wifi, R.drawable.ic_network_wifi,
            R.drawable.ic_ac_unit, R.drawable.ic_ac_unit
    );


    public static MemoryGameFragment newInstance() {
        return new MemoryGameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory_game, container, false);

        this.tvTimer = view.findViewById(R.id.tvTimer);
        this.tvScore = view.findViewById(R.id.tvScore);
        this.gridLayout = view.findViewById(R.id.gridMemory);


        this.prepareMemoryGame();
        this.startTimer();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MemoryGameViewModel.class);
        // TODO: Use the ViewModel
    }


   private void  prepareMemoryGame() {
        Collections.shuffle(icons);
        gridLayout.removeAllViews();
        buttons.clear();

        for (int i = 0; i < icons.size(); i++) {
            ImageButton button = new ImageButton(getContext());
           // button.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width=0;
            params.height=0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(16, 16, 16, 16);
            button.setLayoutParams(params);

            button.setBackgroundResource(R.drawable.card_back);
            int imageId = icons.get(i);
            button.setTag(imageId);
            int finalI = i;

            button.setOnClickListener(v -> {
                if (isBusy || button == firstPick || button == secondPick) return;

                button.setImageResource(imageId);
                button.setScaleType(ImageView.ScaleType.CENTER_CROP);
                button.setAdjustViewBounds(true);

                if (firstPick == null) {
                    firstPick = button;
                } else {
                    secondPick = button;
                    isBusy = true;

                    new Handler().postDelayed(() -> {
                        if (firstPick.getTag().equals(secondPick.getTag())) {
                            firstPick.setEnabled(false);
                            secondPick.setEnabled(false);

                            this.matchedPairs++;
                            tvScore.setText(getString(R.string.res) + ": " + matchedPairs);

                            if(this.matchedPairs == MemoryGameFragment.TOTAL_PAIRS)
                            {
                                this.timer.cancel();
                                this.endGame();
                            }
                        } else {
                            firstPick.setImageResource(0);
                            secondPick.setImageResource(0);
                        }
                        firstPick = null;
                        secondPick = null;
                        isBusy = false;
                    }, 1200);
                }
            });

            buttons.add(button);
            gridLayout.addView(button);
        }

    }


    private void startTimer() {
        timer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(getString(R.string.time) + ": " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvTimer.setText(getString(R.string.time) + ": 0");
                endGame();
            }
        }.start();
    }

    private void endGame() {

        for (ImageButton btn : buttons) {
            btn.setEnabled(false);
        }

        SharedPreferences prefs = getActivity().getSharedPreferences("MozgalicaPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        DBHelper db = new DBHelper(getContext());
        GameResult gameResult = new GameResult(MainMenuActivity.MEMORY_GAME_NAME, username, matchedPairs);
        db.insertGameResult(gameResult);

        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.res))
                .setMessage(getString(R.string.won) +  " " + matchedPairs + " " + getString(R.string.points) + " "  + getString(R.string.outOf) + " " + MemoryGameFragment.TOTAL_PAIRS)
                .setPositiveButton("OK", (dialog, which) -> {

                    new AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.new_game)).setMessage(R.string.question)
                            .setPositiveButton(getString(R.string.yes), (d, w) ->
                            {
                               matchedPairs = 0;
                               tvScore.setText(getString(R.string.res) + ": 0");
                               startTimer();
                               prepareMemoryGame();
                            }).setNegativeButton(getString(R.string.no), (d,w)->
                            {
                                NavHostFragment.findNavController(this).popBackStack();
                            }).show();

                }).show();


    }

}
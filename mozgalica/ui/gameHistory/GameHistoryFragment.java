package com.example.mozgalica.ui.gameHistory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mozgalica.GameResultAdapter;
import com.example.mozgalica.R;
import com.example.mozgalica.databinding.FragmentGameHistoryBinding;
import com.example.mozgalica.db.DBHelper;
import com.example.mozgalica.db.model.GameResult;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameHistoryFragment extends Fragment {

    private FragmentGameHistoryBinding binding;

    //private final ExecutorService executor = Executors.newSingleThreadExecutor();
    //private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private RecyclerView recyclerView;
    private GameResultAdapter adapter;
    private EditText editSearch;
    private Button btnSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*GameHistoryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GameHistoryViewModel.class);*/

        View view = inflater.inflate(R.layout.fragment_game_history, container, false);

         recyclerView = view.findViewById(R.id.recyclerHistory);
         editSearch = view.findViewById(R.id.editSearch);
         btnSearch = view.findViewById(R.id.btnSearch);

         adapter = new GameResultAdapter();
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         recyclerView.setAdapter(adapter);


      new GameResultsTask().execute();

        btnSearch.setOnClickListener(v -> {
            String filter = editSearch.getText().toString().trim();
            new GameResultsTask(filter).execute();
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    new GameResultsTask().execute();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        //View root = binding.getRoot();

        //final TextView textView = binding.textGameHistory;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return view;
    }


    private class GameResultsTask extends AsyncTask<Void, Void, List<GameResult>>
    {

        private String filter;

        public GameResultsTask()
        {
            this.filter = null;
        }

        public GameResultsTask(String filter)
        {
            this.filter = filter;
        }
        @Override
        protected List<GameResult> doInBackground(Void... voids)
        {
            DBHelper db = new DBHelper(getContext());
            List<GameResult> list = null;
            if(filter == null || filter.isEmpty())
            {
                list = db.getAllResults();

            }

            else if(filter.matches("\\d+"))
            {
                list = db.getResultsByScore(Integer.parseInt(filter));
            }

            else {
                list = db.getResultsByUsernameOrGameName(filter);
            }


            list.removeIf(result ->
                    result.getUsername() == null || result.getUsername().trim().isEmpty());

            return list;

        }

        @Override
        protected void onPostExecute(List<GameResult> results) {
            adapter.setResults(results);
        }




    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
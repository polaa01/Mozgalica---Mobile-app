package com.example.mozgalica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mozgalica.db.model.GameResult;

import java.util.ArrayList;
import java.util.List;

public class GameResultAdapter extends RecyclerView.Adapter<GameResultAdapter.ViewHolder> {

    private List<GameResult> list = new ArrayList<>();


    public void setResults(List<GameResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView textResult;
         ViewHolder(View itemView)
         {
                super(itemView);
                textResult = itemView.findViewById(R.id.textResult);
         }

         public void bind(GameResult result)
         {
             textResult.setText(result.getUsername() + " - " + result.getGame() + " - " + result.getScore());
         }


    }
}

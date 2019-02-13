package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    Context mContext;
    List<String> mRecipes;
    public RecipeAdapter(Context context, List<String> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, int i) {
        recipeViewHolder.mTextView.setText(mRecipes.get(i));

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder
    {

        TextView mTextView;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}

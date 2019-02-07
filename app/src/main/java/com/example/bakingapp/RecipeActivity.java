package com.example.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity {

    ArrayAdapter adapter;
    ListView ingredientsList;
    List<Recipe> recipes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("recipe_id",-1);

        ingredientsList = findViewById(R.id.ingredients_list);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/").addConverterFactory(GsonConverterFactory.create()).build();
        Baking baking = retrofit.create(Baking.class);
        retrofit2.Call<List<Recipe>> call = baking.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();
                for(int i = 0 ; i < recipes.size() ; i++)
                    if(recipes.get(i).getId() == id)
                    {
                        Recipe current = recipes.get(i);
                        List<String> ingredients = current.getIngredients();
                        adapter = new ArrayAdapter(RecipeActivity.this,android.R.layout.simple_list_item_1,ingredients);
                        ingredientsList.setAdapter(adapter);
                        break;
                    }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(RecipeActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

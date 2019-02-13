package com.example.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipeActivity extends AppCompatActivity {

    RecipeAdapter ingredientsAdapter;
    ArrayAdapter stepsAdapter;
    RecyclerView ingredientsList;
    ListView stepsList;
    int recipeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        recipeId = intent.getIntExtra("recipe_id",-1);

        ingredientsList = findViewById(R.id.ingredients_list);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        stepsList = findViewById(R.id.steps_list);
        //stepsList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        getIngredientsAndSteps();

        stepsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(RecipeActivity.this,StepsActivity.class);
                 intent.putExtra("step", position);
                 intent.putExtra("recipe_id", recipeId);
                 startActivity(intent);
             }
        });
    }

    public void getIngredientsAndSteps(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/").addConverterFactory(GsonConverterFactory.create()).build();
        Baking baking = retrofit.create(Baking.class);
        Call<List<Recipe>> call = baking.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                Recipe current = recipes.get(recipeId);
                List<Ingredient> ingredients = current.getIngredients();
                List<String> ingredientsString = new ArrayList<>();
                for (int j = 0; j < ingredients.size(); j++) {
                    ingredientsString.add(ingredients.get(j).getQuantity() + " " + ingredients.get(j).getMeasure() + " " + ingredients.get(j).getIngredient());
                }
                List<Step> steps = current.getSteps();
                List<String> stepsDescription = new ArrayList<>();
                for (int j = 0; j < steps.size(); j++)
                    stepsDescription.add(steps.get(j).getShortDescription());

                ingredientsAdapter = new RecipeAdapter(RecipeActivity.this, ingredientsString);
                stepsAdapter = new ArrayAdapter(RecipeActivity.this,android.R.layout.simple_list_item_1,stepsDescription);
                ingredientsList.setAdapter(ingredientsAdapter);
                stepsList.setAdapter(stepsAdapter);
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
}

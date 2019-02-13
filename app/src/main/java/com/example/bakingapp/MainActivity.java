package com.example.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    ListView recipesList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesList = findViewById(R.id.recipes_list);

        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,RecipeActivity.class);
                intent.putExtra("recipe_id", position);
                startActivity(intent);
            }
        });
        getRecipesNames();
    }
    void getRecipesNames()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/").addConverterFactory(GsonConverterFactory.create()).build();
        Baking baking = retrofit.create(Baking.class);
        Call<List<Recipe>> call = baking.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                List<String> recipesNames = new ArrayList<>();
                for(int i = 0 ; i < recipes.size() ; i++)
                    recipesNames.add(recipes.get(i).getName());
                adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,recipesNames);
                recipesList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

    }
}

package com.example.bakingapp;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StepsActivity extends AppCompatActivity {

    ViewPager viewPager;
    StepsAdapter adapter;
    int stepIndex;
    int recipeId;
    List<Step> steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Intent intent = getIntent();
        stepIndex = intent.getIntExtra("step", -1);
        recipeId = intent.getIntExtra("recipe_id",-1);
        getSteps();

        viewPager = findViewById(R.id.view_pager);
    }
    void getSteps()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/").addConverterFactory(GsonConverterFactory.create()).build();
        Baking baking = retrofit.create(Baking.class);
        Call<List<Recipe>> call = baking.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                Recipe current = recipes.get(recipeId);

                steps = current.getSteps();
                List<Fragment> stepsFragments= new ArrayList<>();

                for(int i = 0 ; i < steps.size() ; i++)
                {
                    Fragment stepFragment = new StepFragment();
                    Bundle args = new Bundle();
                    args.putString("step_description",steps.get(i).getDescription());
                    args.putString("video_url",steps.get(i).getVideoUrl());
                    stepFragment.setArguments(args);
                    stepsFragments.add(stepFragment);
                }

                adapter = new StepsAdapter(StepsActivity.this,getSupportFragmentManager(),stepsFragments);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(stepIndex);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }
}

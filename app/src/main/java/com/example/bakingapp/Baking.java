package com.example.bakingapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface Baking
{
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
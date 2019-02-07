package com.example.bakingapp;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients()
    {
        List<String> ingredientsString = new ArrayList<>();
        for(int i = 0 ; i < ingredients.size() ; i++)
        {
            ingredientsString.add(ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure() + " " + ingredients.get(i).getIngredient());
        }
        return ingredientsString;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}

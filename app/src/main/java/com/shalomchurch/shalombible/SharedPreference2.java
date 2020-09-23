package com.shalomchurch.shalombible;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference2 {

    public static final String PREFS_NAME = "PRODUCT_APP2";
    public static final String FAVORITES = "Product_Favorite2";

    public SharedPreference2() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<String> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();

    }

    public void addFavorite(Context context, String product) {
        List<String> favorites = getFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<String>();
            favorites.add(product);
            Toast.makeText(context, "Bookmark Added Successfully", Toast.LENGTH_SHORT).show();
        }
       else if (favorites.contains(product)){
            Toast.makeText(context, "Already Bookmark Added", Toast.LENGTH_SHORT).show();
        }else {
            favorites.add(product);
            Toast.makeText(context, "Bookmark Added Successfully", Toast.LENGTH_SHORT).show();
        }

        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, String product) {
        ArrayList<String> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);

            saveFavorites(context, favorites);
        }
    }

    public ArrayList<String> getFavorites(Context context) {
        SharedPreferences settings;
        List<String> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites,
                    String[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<String>(favorites);
        } else
            return null;

        return (ArrayList<String>) favorites;
    }
}
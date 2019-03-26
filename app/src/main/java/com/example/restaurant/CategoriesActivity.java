package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {
    private String categories[] = {"appetizers", "entrees"};

    /** create the activity in case it was killed */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the action bar at the top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_categories);
        //Gets categories with HTTP request
        CategoriesRequest request = new CategoriesRequest(this);
        request.getCategories(this);
    }

    //** fill the list with information */
    @Override
    public void gotCategories(ArrayList<String> categories) {
        // Sets adapter and listener for listView filled with data from HTTP request
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        ListView menuList = findViewById(R.id.categoriesList);
        menuList.setAdapter(categoryAdapter);
        menuList.setOnItemClickListener(new OnItemItemClick());
    }

    //** check for errors */
    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //** a listener class for when the user clicks on listView items */
    private class OnItemItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // find the category from the view and starts new CategoriesActivity
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
            TextView categoryText = view.findViewById(R.id.categoryText);
            // pass the category into the intent
            intent.putExtra("category", categories[position]);
            startActivity(intent);
        }
    }
}

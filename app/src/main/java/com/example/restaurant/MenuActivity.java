package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

class MenuActivity extends AppCompatActivity implements MenuRequest.Callback{
    private ArrayList<MenuItem> menu;   // arraylist with the menu items

    /** create the activity in case it was killed */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the action bar at the top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        // request the menu items through api
        String category = getIntent().getStringExtra("category");
        MenuRequest itemRequest = new MenuRequest(this, category);
        itemRequest.getMenu(this);
    }

    //** link the menu items to a listview with an adapter */
    @Override
    public void gotMenu(ArrayList<MenuItem> menu) {
        // make an adapter and a listView
        ListView menuList = findViewById(R.id.menuList);
        MenuAdapter adapter = new MenuAdapter(this, R.layout.menu_item, menu);
        // fill the list with the items
        menuList.setOnItemClickListener(new MenuActivity.menuItemClick());
        menuList.setAdapter(adapter);
    }

    //** when the request failed give an error message */
    @Override
    public void gotMenuError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    //** a listener for when listView items are clicked */
    class menuItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // send the menuItem that was clicked to the activity through the intent
            Intent intent = new Intent(MenuActivity.this, DetailActivity.class);
            intent.putExtra("menuItem", menu.get(position));
            startActivity(intent);
        }
    }
}
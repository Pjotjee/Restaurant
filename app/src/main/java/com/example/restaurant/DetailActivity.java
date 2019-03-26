package com.example.restaurant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.Objects;


class DetailActivity extends AppCompatActivity {
    //** create the detail activity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the action bar at the top
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        // get the right item from the intent
        MenuItem menuItem = (MenuItem) getIntent().getSerializableExtra("menuItem");
        // get the view element from the layout
        TextView itemTitle = findViewById(R.id.detailTitle);
        TextView itemPrice = findViewById(R.id.detailPrice);
        TextView itemBio = findViewById(R.id.detailBio);
        ImageView itemImg = findViewById(R.id.detailImg);
        // set the right text and image url
        itemPrice.setText(menuItem.getPrice());
        itemTitle.setText(menuItem.getName());
        itemBio.setText(menuItem.getBio());
        Picasso.with(this).load(menuItem.getImageURL()).into(itemImg);
    }
}
package com.example.restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

class MenuAdapter extends ArrayAdapter<MenuItem> {
    private ArrayList<MenuItem> menu;      // array list for the menu items

    //** the constructor for the MenuAdapter */
    MenuAdapter(Context context, int resource, ArrayList<MenuItem> objects) {
        super(context, resource, objects);
        this.menu = objects;
    }

    //** link the item details in the list */
    @Override @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // set a default convertView from menu_item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, parent,false);
        }
        // make the views for the items
        ImageView menuImg = convertView.findViewById(R.id.menuImg);
        TextView menuName = convertView.findViewById(R.id.menuName);
        TextView menuPrice = convertView.findViewById(R.id.menuPrice);
        // sets the information in the views
        Picasso.with(this.getContext()).load(menu.get(position).getImageURL()).into(menuImg);
        menuName.setText(menu.get(position).getName());
        menuPrice.setText(menu.get(position).getPrice());
        return convertView;
    }
}
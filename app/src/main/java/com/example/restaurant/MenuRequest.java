package com.example.restaurant;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

class MenuRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;        //  context object to send internet requests
    private Callback activity;      // callback to notify the activity
    private String category;        // category for the menu

    //** predefine methods in an interface */
    interface Callback {
        void gotMenu(ArrayList<MenuItem> menu);
        void gotMenuError(String message);
    }

    //** the constructor of the class */
    MenuRequest(Context context, String category) {
        this.context = context;
        this.category = category;
    }

    //** retrieve the categories from the API, and if succesful, notify the activity that
    // instantiated the request that it is done through the callback */
    void getMenu(Callback activity) {
        // saves the activity as instance variable
        this.activity = activity;
        // create a request queue
        RequestQueue queue = Volley.newRequestQueue(context);
        // creates a JsonObjectRequest and add it to queue
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://resto.mprog.nl/" +
                "menu?category=" + category, null, this, this);
        queue.add(jsonObjectRequest);
    }

    //** error parameter containing a VolleyError, which contains the error message */
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotMenuError(error.getMessage());
    }

    //** the response parameter containing a JSONObject with the document */
    @Override
    public void onResponse(JSONObject response) {
        try {
            // get JSON array
            JSONArray menuJSON = response.getJSONArray("items");
            // create an ArrayList to store menu items
            ArrayList<MenuItem> menu = new ArrayList<>();
            // for each item in JSON array add the item to the ArrayList
            for (int i = 0; i < menuJSON.length(); i++) {
                String name = menuJSON.getJSONObject(i).getString("name");
                String bio = menuJSON.getJSONObject(i).getString("description");
                String price = menuJSON.getJSONObject(i).getString("price");
                String imgURL = menuJSON.getJSONObject(i).getString("image_url");
                menu.add(new MenuItem(name, bio, imgURL, price));
            }
            activity.gotMenu(menu);
        }
        // error method on the JSONException
        catch(JSONException error){
            activity.gotMenuError(error.getMessage());
        }
    }
}
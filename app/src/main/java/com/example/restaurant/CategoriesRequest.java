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

class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;                                    // context to send internet requests
    private Callback activity;                                  // callback to notify the activity
    private ArrayList<String> categories = new ArrayList<>();   // arrayList for the JSON elements

    //** declaration of callback to notify the activity */
    interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }
    CategoriesRequest(Context context) {
        this.context = context;
    }

    //** retrieve the categories from the API and notify the activity
    // that instantiated the request that it is done through the callback*/
    void getCategories(Callback activity) {
        // Saves activity as instance variable
        this.activity = activity;
        // Create new request queue for API requests
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://resto.mprog.nl/categories";
        // Creates new request and adds it to queue
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    //** message in case of an error */
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotCategoriesError(error.getMessage());
    }

    //** put the response JSONObject into an arraylist if succesfull */
    @Override
    public void onResponse(JSONObject response) {
        try {
            // initialize the JSON array and create a new ArrayList of strings
            JSONArray categoriesJSON = response.getJSONArray("categories");
            ArrayList<String> categories = new ArrayList<>();
            // put  the elements from the JSON array into an ArrayList
            for (int i = 0; i < categoriesJSON.length(); i++) {
                categories.add(categoriesJSON.getString(i));
            }
        }
        // call the error method when JSONException occur
        catch(JSONException error){
            activity.gotCategoriesError(error.getMessage());
        }
        activity.gotCategories(categories);
    }
}
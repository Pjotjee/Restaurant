package com.example.restaurant;

import java.io.Serializable;

class MenuItem implements Serializable {
    private String name, bio, imageURL, price; // variables for the menu items

    //** constructor for the class */
    MenuItem(String name, String bio, String imageURL, String price) {
        this.name = name;
        this.bio = bio;
        this.imageURL = imageURL;
        this.price = "€" + price;
    }

    //** get methods for the class */
    String getName() {
        return name;
    }

    String getBio() {
        return bio;
    }

    String getImageURL() {
        return imageURL;
    }

    String getPrice() {
        return price;
    }
}
package com.harry.finkers.FragmentPost;

public class HomeItemTranding {
    private String title;
    private String imageUrl;

    public HomeItemTranding() {
        //
    }

    public HomeItemTranding(String title, String imageUrl) {
        if (title.trim().equals("")) {
            title = "No Title";
        }
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

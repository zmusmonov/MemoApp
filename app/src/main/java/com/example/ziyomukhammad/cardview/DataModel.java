package com.example.ziyomukhammad.cardview;



/**
 * Created by ZiyoMukhammad on 8/3/17.
 */
//
// Data model for our database
// We have notes table and inside
// we have 4 parameters: date, subtitle, title and image
//
// Constructor, all setters and getters are created
public class DataModel {

    String title;
    String subtitle;
    String date;
    String pushID;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    String imageUrl;


    public DataModel() {
    }

    public DataModel(String title, String subtitle, String date,String imageUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.pushID = pushID;
        this.imageUrl = imageUrl;
    }
    public DataModel(String title, String subtitle, String date) {
        this.title = title;
        this.subtitle = subtitle;
        this.date = date;
        this.pushID = pushID;
        this.imageUrl = imageUrl;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

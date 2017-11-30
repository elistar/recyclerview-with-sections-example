package edu.wayne.cs.discovery.example;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private String title;
    private String tag;

    public ImageItem(Bitmap image, String title, String tag) {
        super();
        this.image = image;
        this.title = title;
        this.tag = tag;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

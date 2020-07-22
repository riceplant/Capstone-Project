
package com.riceplant.capstoneproject.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cover {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("url")
    @Expose
    private String url;

    public Cover(String imageId) {
        this.imageId = imageId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

package com.gkhnnl.howweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gkhnnl on 03/09/16.
 */
public class Clouds implements Serializable {

    @SerializedName("all")
    @Expose
    private int all;

    /**
     * @return The all
     */
    public int getAll() {
        return all;
    }

    /**
     * @param all The all
     */
    public void setAll(int all) {
        this.all = all;
    }
}

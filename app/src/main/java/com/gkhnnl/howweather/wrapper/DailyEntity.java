package com.gkhnnl.howweather.wrapper;

/**
 * Created by gkhnnl on 22/10/2016.
 */

public class DailyEntity {

    private String temperature;
    private String description;
    private int iconId;
    private String iconSet;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconSet() {
        return iconSet;
    }

    public void setIconSet(String iconSet) {
        this.iconSet = iconSet;
    }
}

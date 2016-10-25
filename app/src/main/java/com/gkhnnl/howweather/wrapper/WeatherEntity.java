package com.gkhnnl.howweather.wrapper;

/**
 * Created by gkhnnl on 22/10/2016.
 */

public class WeatherEntity {

    private int cityId;
    private String cityName;
    private String country;
    private String description;
    private String temperature;
    private String minDegree;
    private String maxDegree;
    private String humidity;
    private String pressure;
    private String windSpeed;
    private String windDegree;
    private String sunrise;
    private String sunset;
    private int iconId;
    private String iconSet;
    private HourlyEntity[] hourlyEntities;
    private DailyEntity[] dailyEntities;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
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

    public String getMinDegree() {
        return minDegree;
    }

    public void setMinDegree(String minDegree) {
        this.minDegree = minDegree;
    }

    public String getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(String maxDegree) {
        this.maxDegree = maxDegree;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(String windDegree) {
        this.windDegree = windDegree;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public HourlyEntity[] getHourlyEntities() {
        return hourlyEntities;
    }

    public void setHourlyEntities(HourlyEntity[] hourlyEntities) {
        this.hourlyEntities = hourlyEntities;
    }

    public DailyEntity[] getDailyEntities() {
        return dailyEntities;
    }

    public void setDailyEntities(DailyEntity[] dailyEntities) {
        this.dailyEntities = dailyEntities;
    }
}

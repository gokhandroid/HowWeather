package com.gkhnnl.howweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gkhnnl on 03/09/16.
 */
public class Main implements Serializable {

    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;

    /**
     * @return The temp
     */
    public double getTemp() {
        return temp;
    }

    /**
     * @param temp The temp
     */
    public void setTemp(double temp) {
        this.temp = temp;
    }

    /**
     * @return The pressure
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * @param pressure The pressure
     */
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    /**
     * @return The humidity
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * @param humidity The humidity
     */
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    /**
     * @return The tempMin
     */
    public double getTempMin() {
        return tempMin;
    }

    /**
     * @param tempMin The temp_min
     */
    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    /**
     * @return The tempMax
     */
    public double getTempMax() {
        return tempMax;
    }

    /**
     * @param tempMax The temp_max
     */
    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

}

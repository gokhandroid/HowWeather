package com.gkhnnl.howweather.wrapper;

/**
 * Created by gkhnnl on 23/10/2016.
 */

public class CityEntity {

    private int id;
    private String name;
    private String country;

    public CityEntity() {
    }

    public CityEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityEntity(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

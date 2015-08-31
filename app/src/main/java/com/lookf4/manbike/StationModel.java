package com.lookf4.manbike;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jarbar on 15/8/30.
 */
public class StationModel {
    private int id;
    private String name;
    private double lng;
    private double lat;
    private int capacity;
    private int available;
    private String address;

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

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StationModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", capacity=" + capacity +
                ", available=" + available +
                ", address='" + address + '\'' +
                '}';
    }

    public static StationModel parse(JSONObject obj){
        StationModel model = new StationModel();

        try {
            model.setId(obj.getInt("id"));
            model.setName(obj.getString("name"));
            model.setLat(obj.getDouble("lat"));
            model.setLng(obj.getDouble("lng"));
            model.setCapacity(Integer.parseInt(obj.getString("capacity")));
            model.setAvailable(Integer.parseInt(obj.getString("availbike")));
            model.setAddress(obj.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }
}

package com.lookf4.manbike;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jarbar on 15/8/30.
 */
public class RequestEntity {
    public static final String TAG = RequestEntity.class.getSimpleName();

    private String key;
    private ArrayList<Double> coord;
    private int radius;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Double> getCoord() {
        return coord;
    }

    public void setCoord(ArrayList<Double> coord) {
        this.coord = coord;
    }

    private void fetchKey(){
        //location=116.35785296575,40.078150094176
        String location = TextUtils.join(",", this.coord);
        Document doc = null;
        try {
            //city beijing
            doc = Jsoup.connect("http://ws.uibike.com/map.php?location=" + location + "&city=%E5%8C%97%E4%BA%AC%E5%B8%82").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements scriptTag = doc.select("script");
        String src = scriptTag.attr("src").toString();
        String key = src.split("\\?")[1].split("&")[2].split("=")[1];

        this.setKey(key);
    };

    public List<StationModel> getBikeStationsAround(){
        String url = this.getUrl();
        Document ibike = null;
        try {
            ibike = Jsoup.connect(url).get();
        } catch (IOException e) {
            Log.d(TAG, "IO exception error");
            e.printStackTrace();
        }

        String response = ibike.toString();
        JSONArray array = new JSONArray();
        HashMap<Integer, StationModel> idStationMap = new HashMap<Integer, StationModel>();
        try {
            JSONObject json = new JSONObject(response.split("=")[1]);
            array = json.getJSONArray("station");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                idStationMap.put(item.getInt("id"), StationModel.parse(item));
            }
        } catch (JSONException e) {
            Log.d(TAG, "json parse error");
            e.printStackTrace();
        }

        List<StationModel> stationModels = new ArrayList<StationModel>(idStationMap.values());
        return stationModels;
    }

    private String getUrl(){
        String baseUrl = "http://ws.uibike.com/wx.station.php?";
        String location = "myloc=" + TextUtils.join(",", this.coord);
        String keyParam = "&k=" + this.key;
        String radius = "&d=" + this.radius;
        String other = "&e=1";
        return baseUrl + location + keyParam + radius + other;
    }

    public void init(ArrayList<Double> coord){
        this.init(coord, 2);//default 2
    }

    public void init(ArrayList<Double> coord, int radius){
        this.setRadius(radius);
        this.setCoord(coord);
        this.fetchKey();
    }
}

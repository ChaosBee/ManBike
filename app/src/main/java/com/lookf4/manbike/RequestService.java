package com.lookf4.manbike;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class RequestService extends Service{
    private static final String TAG = RequestService.class.getSimpleName();
    private RequestEntity req = new RequestEntity();
    private List<StationModel> bikes = new ArrayList<StationModel>();
    public Mbinder mBinder;

    public class Mbinder extends Binder {
        public RequestService getService() {
            return RequestService.this;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new Mbinder();
        return mBinder;
    }

    protected List<StationModel> getStationsAroundData(ArrayList<Double> coord) {
//        ArrayList<Double> coord = new ArrayList<Double>();
//        if (intent != null) {
////            double [] arr = intent.getDoubleArrayExtra("coord");
////            coord.add(arr[0]);
////            coord.add(arr[1]);
//            double lng = intent.getDoubleExtra("lng", 0);
//            double lat = intent.getDoubleExtra("lat", 0);
//            coord.add(lng);
//            coord.add(lat);
//        }

        Log.d(TAG, coord.toString());
        req.init(coord, 10);//2 or 10
        bikes = req.getBikeStationsAround();
        Log.d(TAG, bikes.toString());
        return bikes;
    }
}

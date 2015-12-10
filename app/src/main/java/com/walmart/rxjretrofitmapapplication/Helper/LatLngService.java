package com.walmart.rxjretrofitmapapplication.Helper;

import com.walmart.rxjretrofitmapapplication.model1.GoogleLatLng;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by sgovind on 12/9/15.
 */
public interface LatLngService {
    @GET("json")
    Observable<GoogleLatLng> getLatLng(@Query("address") String postcode);
}

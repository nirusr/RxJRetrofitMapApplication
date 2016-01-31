package com.walmart.rxjretrofitmapapplication;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.walmart.rxjretrofitmapapplication.Helper.LatLngService;
import com.walmart.rxjretrofitmapapplication.model1.DeliveryGeoCode;
import com.walmart.rxjretrofitmapapplication.model1.GoogleLatLng;

import java.util.Observable;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sgovind on 12/31/15.
 */
public class FindLatLng extends AsyncTask<String, Void, Boolean> {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    @Override
    protected Boolean doInBackground(String... postcodes) {
        String postcode = postcodes[0];
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor  = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(client).build();
        LatLngService apiService = retrofit.create(LatLngService.class);
        rx.Observable<GoogleLatLng> call =apiService.getLatLng(postcode);
        call.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Subscriber<GoogleLatLng>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GoogleLatLng googleLatLng) {
                        double lat = googleLatLng.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = googleLatLng.getResults().get(0).getGeometry().getLocation().getLng();
                        Log.v("Lat:", googleLatLng.getResults().get(0).getGeometry().getLocation().getLat() + "");
                        Log.v("Lng:", googleLatLng.getResults().get(0).getGeometry().getLocation().getLng()+"");
                        DeliveryGeoCode deliveryGeoCode = new DeliveryGeoCode();
                        deliveryGeoCode.setLatValue(lat);
                        deliveryGeoCode.setLngValue(lng);
                        deliveryGeoCode.setPostalCode("94555");
                        deliveryGeoCode.save();

                    }
                }
        );



        return null;
    }
}

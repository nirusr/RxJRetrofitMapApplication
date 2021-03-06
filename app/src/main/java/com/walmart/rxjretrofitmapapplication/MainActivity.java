package com.walmart.rxjretrofitmapapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.walmart.rxjretrofitmapapplication.Helper.LatLngService;
import com.walmart.rxjretrofitmapapplication.model1.DeliveryGeoCode;
import com.walmart.rxjretrofitmapapplication.model1.GoogleLatLng;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OkHttpClient dClient = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        dClient.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(dClient).build();

        LatLngService apiService = retrofit.create(LatLngService.class);

        Observable<GoogleLatLng> call = apiService.getLatLng("94555");

        call.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).
                subscribe(new Subscriber<GoogleLatLng>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("Error=", e.getMessage());
                    }

                    @Override
                    public void onNext(GoogleLatLng googleLatLng) {
                        double lat = googleLatLng.getResults().get(0).getGeometry().getLocation().getLat();
                        double lng = googleLatLng.getResults().get(0).getGeometry().getLocation().getLng();
                        Log.v("Lat:", googleLatLng.getResults().get(0).getGeometry().getLocation().getLat()+"");
                        Log.v("Lng:", googleLatLng.getResults().get(0).getGeometry().getLocation().getLng()+"");
                        DeliveryGeoCode deliveryGeoCode = new DeliveryGeoCode();
                        deliveryGeoCode.setLatValue(lat);
                        deliveryGeoCode.setLngValue(lng);
                        deliveryGeoCode.setPostalCode("94555");
                        deliveryGeoCode.save();
                    }
                });



    /*Log.v("Lat:", googleLatLng.getResults().get(0).getGeometry().getLocation().getLat()+"");
    Log.v("Lng:", googleLatLng.getResults().get(0).getGeometry().getLocation().getLng()+"");*/
        new FindLatLng().execute("94555");
        DeliveryGeoCode tDGC = DeliveryGeoCode.getPostalCode("postalcode", "94555");
        Log.v("Saved Entry:", tDGC.getLatValue() + "");

    }
}

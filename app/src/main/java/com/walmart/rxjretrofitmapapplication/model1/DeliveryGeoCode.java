package com.walmart.rxjretrofitmapapplication.model1;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by sgovind on 12/31/15.
 */
@Table(name = "deliverygeocode")
public class DeliveryGeoCode extends Model {

    @Column(name ="postalcode", unique=true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String postalCode;
    @Column(name = "latvalue")
    private double latValue;
    @Column(name = "lngvalue")
    private double lngValue;



    public DeliveryGeoCode() {
        super();
    }

    public DeliveryGeoCode(String postalCode, double latValue, double lngValue) {
        this.postalCode = postalCode;
        this.latValue = latValue;
        this.lngValue = lngValue;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getLatValue() {
        return latValue;
    }

    public void setLatValue(double latValue) {
        this.latValue = latValue;
    }

    public double getLngValue() {
        return lngValue;
    }

    public void setLngValue(double lngValue) {
        this.lngValue = lngValue;
    }

    public static DeliveryGeoCode getPostalCode(String fieldName, String fieldValue) {
        return new Select().from(DeliveryGeoCode.class).where( fieldName + " = ?", fieldValue).executeSingle();






    }
}


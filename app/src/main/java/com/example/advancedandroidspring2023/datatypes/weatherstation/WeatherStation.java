
package com.example.advancedandroidspring2023.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class WeatherStation {

    @SerializedName("d")
    @Expose
    private D d;
    @SerializedName("ts")
    @Expose
    private String ts;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherStation() {
    }

    /**
     * 
     * @param d
     * @param ts
     */
    public WeatherStation(D d, String ts) {
        super();
        this.d = d;
        this.ts = ts;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

}

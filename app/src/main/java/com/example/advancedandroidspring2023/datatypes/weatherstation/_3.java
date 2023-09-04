
package com.example.advancedandroidspring2023.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class _3 {

    @SerializedName("v")
    @Expose
    private double v;

    /**
     * No args constructor for use in serialization
     * 
     */
    public _3() {
    }

    /**
     * 
     * @param v
     */
    public _3(double v) {
        super();
        this.v = v;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

}

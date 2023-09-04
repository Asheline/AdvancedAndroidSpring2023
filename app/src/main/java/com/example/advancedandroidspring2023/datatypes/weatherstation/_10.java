
package com.example.advancedandroidspring2023.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class _10 {

    @SerializedName("v")
    @Expose
    private int v;

    /**
     * No args constructor for use in serialization
     * 
     */
    public _10() {
    }

    /**
     * 
     * @param v
     */
    public _10(int v) {
        super();
        this.v = v;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

}

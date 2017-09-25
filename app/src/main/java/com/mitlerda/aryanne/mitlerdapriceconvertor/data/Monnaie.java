package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

/**
 * Created by goulliarts on 25/09/2017.
 */

public interface Monnaie {
    public String getNom();

    public float getToCouronne();

    public float toCouronne(float value);

    public float fromCouronne(float value);
}

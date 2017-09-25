package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum MarkType implements Monnaie {
    MarkPrartien("mark prartien", 0.02f),
    MarkVennesien("mark vennesien", 0.08f),
    MarkDen("mark den", 0.02f);

    private String nom;

    private float toCouronne;

    MarkType(String nom, float toCouronne) {

        this.nom = nom;
        this.toCouronne = toCouronne;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public float getToCouronne() {
        return toCouronne;
    }

    @Override
    public float toCouronne(float value) {
        return value * getToCouronne();
    }

    @Override
    public float fromCouronne(float value) {
        return value / getToCouronne();
    }
}

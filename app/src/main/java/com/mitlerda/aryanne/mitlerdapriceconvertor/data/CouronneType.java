package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum CouronneType implements Monnaie {
    Couronnes("Couronnes", 1f);

    private String nom;
    private float toCouronne;

    CouronneType(String nom, float toCouronne) {

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

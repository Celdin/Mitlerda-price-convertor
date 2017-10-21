package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum CouronneType implements Monnaie {
    Couronnes("Couronnes", 1f, 0),
    Or("Or", 1f, 1);

    private String nom;
    private BigDecimal toCouronne;
    private int indexNom;

    CouronneType(String nom, float toCouronne, int indexNom) {
        this.nom = nom;
        this.toCouronne = BigDecimal.valueOf(toCouronne);
        this.indexNom = indexNom;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public BigDecimal getToCouronne() {
        return toCouronne;
    }

    @Override
    public int getIndexNom() {
        return indexNom;
    }

    @Override
    public BigDecimal toCouronne(BigDecimal value) {
        return value.multiply(getToCouronne());
    }

    @Override
    public BigDecimal fromCouronne(BigDecimal value) {
        return value.divide(getToCouronne(), 5, BigDecimal.ROUND_HALF_UP);
    }
}

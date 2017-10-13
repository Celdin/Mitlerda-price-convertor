package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum CouronneType implements Monnaie {
    Couronnes("Couronnes", 1f);

    private String nom;
    private BigDecimal toCouronne;

    CouronneType(String nom, float toCouronne) {

        this.nom = nom;
        this.toCouronne = BigDecimal.valueOf(toCouronne);
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
    public BigDecimal toCouronne(BigDecimal value) {
        return value.multiply(getToCouronne());
    }

    @Override
    public BigDecimal fromCouronne(BigDecimal value) {
        return value.divide(getToCouronne(), 5, BigDecimal.ROUND_HALF_UP);
    }
}

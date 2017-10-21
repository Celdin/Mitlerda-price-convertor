package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

public enum LireDivision implements Division {
    Lire(1, new String[] {"Lire", "Livre"}),
    Sou(1d/20, new String[] {"Sou", "Shilling"}),
    Denier(1d/240, new String[] {"Denier", "Pence"});

    private BigDecimal toReference;
    private String[] noms;

    LireDivision(double toReference, String[] noms) {
        this.toReference = BigDecimal.valueOf(toReference);
        this.noms = noms;
    }

    @Override
    public BigDecimal toReference(BigDecimal value){
        return value.multiply(toReference);
    }

    @Override
    public BigDecimal fromReference(BigDecimal value) {
        return value.divide(toReference, 5, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String getNom(int index) {
        return noms[index];
    }
}

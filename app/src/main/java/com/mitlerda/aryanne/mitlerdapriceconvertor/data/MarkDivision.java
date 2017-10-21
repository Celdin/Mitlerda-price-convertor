package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

public enum MarkDivision implements Division {
    Schwer(1000d, new String[] {"Schwer"}),
    Mark(1d, new String[] {"Mark"});

    private BigDecimal toReference;
    private String[] noms;

    MarkDivision(double toReference, String[] noms) {
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

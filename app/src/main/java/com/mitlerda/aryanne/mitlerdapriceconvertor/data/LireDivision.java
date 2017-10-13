package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

public enum LireDivision implements Division {
    Lire(1),
    Sou(1d/20),
    Denier(1d/240);

    private BigDecimal toReference;

    LireDivision(double toReference) {
        this.toReference = BigDecimal.valueOf(toReference);
    }

    @Override
    public BigDecimal toReference(BigDecimal value){
        return value.multiply(toReference);
    }

    @Override
    public BigDecimal fromReference(BigDecimal value) {
        return value.divide(toReference, 5, BigDecimal.ROUND_HALF_UP);
    }
}

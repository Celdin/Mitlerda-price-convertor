package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

public enum CouronneDivision implements Division {
    Eude(10d),
    Couronne(1d),
    Déci(0.1d),
    Cent(0.01d);

    private BigDecimal toReference;

    CouronneDivision(double toReference) {
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

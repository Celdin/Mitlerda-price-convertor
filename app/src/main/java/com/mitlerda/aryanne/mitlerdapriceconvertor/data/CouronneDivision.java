package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

public enum CouronneDivision implements Division {
    Eude(10d, new String[] {"Eude", "Platine"}),
    Couronne(1d, new String[] {"Couronne", "Or"}),
    Déci(0.1d, new String[] {"Déci", "Cuivre"}),
    Cent(0.01d, new String[] {"Cent", "Bronze"});

    private BigDecimal toReference;
    private String[] noms;

    CouronneDivision(double toReference, String[] noms) {
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

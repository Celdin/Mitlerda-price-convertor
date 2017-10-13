package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 25/09/2017.
 */

public interface Monnaie {
    public String getNom();

    public BigDecimal getToCouronne();

    public BigDecimal toCouronne(BigDecimal value);

    public BigDecimal fromCouronne(BigDecimal value);
}

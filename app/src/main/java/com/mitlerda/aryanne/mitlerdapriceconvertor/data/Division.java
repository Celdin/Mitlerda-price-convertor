package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 26/09/2017.
 */

public interface Division {
    public BigDecimal toReference(BigDecimal value);
    public BigDecimal fromReference(BigDecimal value);
}

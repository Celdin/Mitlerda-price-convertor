package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum MonnaieType {
    Couronne(CouronneDivision.Couronne),
    Mark(MarkDivision.Mark),
    Lire(LireDivision.Lire);

    Division baseDivision;

    MonnaieType(Division division) {
        this.baseDivision = division;
    }

    public Division getBaseDivision() {
        return baseDivision;
    }
}

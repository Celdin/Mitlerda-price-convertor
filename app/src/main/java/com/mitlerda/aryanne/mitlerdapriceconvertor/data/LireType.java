package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

import java.math.BigDecimal;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum LireType implements Monnaie {
    LireMonavidienne("Lire monavidienne", 1.5f, 0),
    LivreSiennate("Livre siennate", 1.2f, 1),
    LivreGatz("Livre de Gatz", 1f, 1),
    LivreYorkbury("Livre de Yorkbury", 0.8f, 1),
    LivreHighwall("Livre de Highwall", 0.7f, 1),
    LivreCortezienne("Livre cortezienne", 1.1f, 1);

    private String nom;
    private BigDecimal toCouronne;
    private int indexNom;


    LireType(String nom, float toCouronne, int indexNom) {
        this.nom = nom;
        this.toCouronne = BigDecimal.valueOf(toCouronne);
        this.indexNom = indexNom;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public int getIndexNom() {
        return indexNom;
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

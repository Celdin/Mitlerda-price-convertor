package com.mitlerda.aryanne.mitlerdapriceconvertor.data;

/**
 * Created by goulliarts on 25/09/2017.
 */

public enum LireType implements Monnaie {
    LireMonavidienne("Lire monavidienne", 1.5f),
    LivreSiennate("Livre siennate", 1.2f),
    LivreGatz("Livre de Gatz", 1f),
    LivreYorkbury("Livre de Yorkbury", 0.8f),
    LivreHighwall("Livre de Highwall", 0.7f),
    LivreCortezienne("Livre cortezienne", 1.1f);

    private String nom;
    private float toCouronne;

    LireType(String nom, float toCouronne) {

        this.nom = nom;
        this.toCouronne = toCouronne;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public float getToCouronne() {
        return toCouronne;
    }

    @Override
    public float toCouronne(float value) {
        return value * getToCouronne();
    }

    @Override
    public float fromCouronne(float value) {
        return value / getToCouronne();
    }
}

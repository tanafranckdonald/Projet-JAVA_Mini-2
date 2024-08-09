package com.ecole;

class Note {
    private double valeur;

    public Note(double valeur) {
        this.valeur = valeur;
    }

    public double getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return String.valueOf(valeur);
    }
}

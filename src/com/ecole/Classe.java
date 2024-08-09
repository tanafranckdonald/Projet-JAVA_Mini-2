package com.ecole;


class Classe {
	    private String nom;

	    public Classe(String nom) {
	        this.nom = nom;
	    }

	    public String getNom() {
	        return nom;
	    }

	    @Override
	    public String toString() {
	        return nom;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;

	        Classe classe = (Classe) obj;

	        return nom != null ? nom.equals(classe.nom) : classe.nom == null;
	    }

	    @Override
	    public int hashCode() {
	        return nom != null ? nom.hashCode() : 0;
	    }
	}

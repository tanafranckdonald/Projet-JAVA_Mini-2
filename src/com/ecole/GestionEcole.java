package com.ecole;

import java.util.*;

public class GestionEcole {
    private List<Classe> classes;
    private Map<Classe, List<Etudiant>> etudiantsParClasse;
    private Set<Matiere> matieres;
    private Map<Matiere, Map<Etudiant, List<Note>>> notes;

    public GestionEcole() {
        classes = new ArrayList<>();
        etudiantsParClasse = new HashMap<>();
        matieres = new HashSet<>();
        notes = new HashMap<>();
    }

    // Gérer les Classes : Les méthodes pour la gestion des classes

    public void ajouterClasse(String nomClasse) {
        classes.add(new Classe(nomClasse));
    }

    public List<Classe> getClasses() {
        return classes;
    }

    public void afficherClasses() {
        for (Classe classe : classes) {
            System.out.println(classe.getNom());
        }
    }

    // Gérer les Étudiants : Les méthodes pour la gestion des étudiants

    public Classe trouverClasseParNom(String nomClasse) {
        for (Classe classe : classes) {
            if (classe.getNom().equalsIgnoreCase(nomClasse)) {
                return classe;
            }
        }
        return null;
    }

    public void ajouterEtudiant(String nomEtudiant, Classe classe) {
        etudiantsParClasse.computeIfAbsent(classe, k -> new ArrayList<>()).add(new Etudiant(nomEtudiant));
    }

    public List<Etudiant> getEtudiants(Classe classe) {
        return etudiantsParClasse.getOrDefault(classe, new ArrayList<>());
    }

    public void afficherEtudiantsParClasse() {
        for (Map.Entry<Classe, List<Etudiant>> entry : etudiantsParClasse.entrySet()) {
            Classe classe = entry.getKey();
            List<Etudiant> etudiants = entry.getValue();
            System.out.println("Classe: " + classe.getNom());
            for (Etudiant etudiant : etudiants) {
                System.out.println("  Etudiant: " + etudiant.getNom());
            }
        }
    }

    // Gérer les Matières : Les méthodes pour la gestion des matières

    public void ajouterMatiere(String nomMatiere) {
        matieres.add(new Matiere(nomMatiere));
    }

    public Set<Matiere> getMatieres() {
        return matieres;
    }

    public void afficherMatieres() {
        for (Matiere matiere : matieres) {
            System.out.println(matiere.getNom());
        }
    }

    // Enregistrer les Notes : Les méthodes pour la gestion des notes

    public Matiere trouverMatiereParNom(String nomMatiere) {
        for (Matiere matiere : matieres) {
            if (matiere.getNom().equalsIgnoreCase(nomMatiere)) {
                return matiere;
            }
        }
        return null;
    }

    public void enregistrerNote(Classe classe, Matiere matiere, Etudiant etudiant, double valeur) {
        notes.computeIfAbsent(matiere, k -> new HashMap<>())
                .computeIfAbsent(etudiant, k -> new ArrayList<>())
                .add(new Note(valeur));
    }

    public Map<Etudiant, List<Note>> getNotes(Matiere matiere) {
        return notes.getOrDefault(matiere, new HashMap<>());
    }

    public void afficherNotes() {
        for (Map.Entry<Matiere, Map<Etudiant, List<Note>>> entry : notes.entrySet()) {
            Matiere matiere = entry.getKey();
            Map<Etudiant, List<Note>> etudiantNotes = entry.getValue();
            System.out.println("Matière: " + matiere.getNom());
            for (Map.Entry<Etudiant, List<Note>> etudiantEntry : etudiantNotes.entrySet()) {
                Etudiant etudiant = etudiantEntry.getKey();
                List<Note> notes = etudiantEntry.getValue();
                System.out.print("  Étudiant: " + etudiant.getNom() + " - Notes: ");
                for (Note note : notes) {
                    System.out.print(note.getValeur() + " ");
                }
                System.out.println();
            }
        }
    }
    
 // Calculer la moyenne de chaque étudiant pour chaque matière
    public Map<Etudiant, Double> calculerMoyenneParMatiere(Matiere matiere) {
        Map<Etudiant, List<Note>> etudiantNotes = getNotes(matiere);
        Map<Etudiant, Double> moyennes = new HashMap<>();
        for (Map.Entry<Etudiant, List<Note>> entry : etudiantNotes.entrySet()) {
            Etudiant etudiant = entry.getKey();
            List<Note> notes = entry.getValue();
            double somme = 0;
            for (Note note : notes) {
                somme += note.getValeur();
            }
            double moyenne = notes.isEmpty() ? 0 : somme / notes.size();
            moyennes.put(etudiant, moyenne);
        }
        return moyennes;
    }

    // Calculer la moyenne générale de chaque étudiant basée sur toutes les matières enseignées
    
    public Map<Etudiant, Double> calculerMoyenneGenerale() {
        Map<Etudiant, List<Double>> toutesLesNotes = new HashMap<>();
        for (Matiere matiere : matieres) {
            Map<Etudiant, Double> moyennesParMatiere = calculerMoyenneParMatiere(matiere);
            for (Map.Entry<Etudiant, Double> entry : moyennesParMatiere.entrySet()) {
                Etudiant etudiant = entry.getKey();
                Double moyenne = entry.getValue();
                toutesLesNotes.computeIfAbsent(etudiant, k -> new ArrayList<>()).add(moyenne);
            }
        }
        Map<Etudiant, Double> moyennesGenerales = new HashMap<>();
        for (Map.Entry<Etudiant, List<Double>> entry : toutesLesNotes.entrySet()) {
            Etudiant etudiant = entry.getKey();
            List<Double> moyennes = entry.getValue();
            double somme = 0;
            for (Double moyenne : moyennes) {
                somme += moyenne;
            }
            double moyenneGenerale = moyennes.isEmpty() ? 0 : somme / moyennes.size();
            moyennesGenerales.put(etudiant, moyenneGenerale);
        }
        return moyennesGenerales;
    }

    // Afficher les moyennes des étudiants
    
    public void afficherMoyennes()  {
        System.out.println("Moyennes par matière :");
        for (Matiere matiere : matieres) {
            System.out.println("Matière: " + matiere.getNom());
            Map<Etudiant, Double> moyennesParMatiere = calculerMoyenneParMatiere(matiere);
            for (Map.Entry<Etudiant, Double> entry : moyennesParMatiere.entrySet()) {
                Etudiant etudiant = entry.getKey();
                Double moyenne = entry.getValue();
                System.out.println("  Étudiant: " + etudiant.getNom() + " - Moyenne: " + moyenne);
            }
        }
        System.out.println("Moyennes générales :");
        Map<Etudiant, Double> moyennesGenerales = calculerMoyenneGenerale();
        for (Map.Entry<Etudiant, Double> entry : moyennesGenerales.entrySet()) {
            Etudiant etudiant = entry.getKey();
            Double moyenne = entry.getValue();
            System.out.println("  Étudiant: " + etudiant.getNom() + " - Moyenne générale: " + moyenne);
        }
    }
    
 // Afficher le classement des étudiants pour chaque matière en ordre décroissant de moyenne
    
    public void afficherClassementParMatiere() {
        for (Matiere matiere : matieres) {
            System.out.println("Classement pour la matière: " + matiere.getNom());
            Map<Etudiant, Double> moyennesParMatiere = calculerMoyenneParMatiere(matiere);
            List<Map.Entry<Etudiant, Double>> listeMoyennes = new ArrayList<>(moyennesParMatiere.entrySet());
            listeMoyennes.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
            for (Map.Entry<Etudiant, Double> entry : listeMoyennes) {
                Etudiant etudiant = entry.getKey();
                Double moyenne = entry.getValue();
                System.out.println("  Étudiant: " + etudiant.getNom() + " - Moyenne: " + moyenne);
            }
        }
    }
}


package com.ecole;

import java.util.*;

public class Ecole {
	
    private static GestionEcole gestionEcole = new GestionEcole();
    private static Authentification auth = new Authentification("admin", "password");
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean authenticated = false;

        while (!authenticated) {
            System.out.println("Nom d'utilisateur :");
            String username = scanner.nextLine();
            System.out.println("Mot de passe :");
            String password = scanner.nextLine();

            authenticated = auth.seConnecter(username, password);
            if (!authenticated) {
                System.out.println("Identifiants incorrects. Veuillez réessayer.");
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("1. Ajouter une classe");
            System.out.println("2. Ajouter un étudiant");
            System.out.println("3. Ajouter une matière");
            System.out.println("4. Enregistrer des notes");
            System.out.println("5. Afficher les moyennes");
            System.out.println("6. Afficher le classement");
            System.out.println("7. Quitter");

            int choix = scanner.nextInt();
            scanner.nextLine();  // Consommer la nouvelle ligne

            switch (choix) {
                case 1: // Ajouter de nouvelles classes à l'école.
                    System.out.println("Nom de la classe :");
                    String nomClasse = scanner.nextLine();
                    gestionEcole.ajouterClasse(nomClasse);
                    break;
                case 2: // Ajouter de nouveaux étudiants à une classe existante.
                	System.out.println("Nom de l'etudiant :");
                    String nomEtudiant = scanner.nextLine();
                    System.out.println("Nom de la classe :");
                    String nomClasseEtudiant = scanner.nextLine();
                    Classe classe = gestionEcole.trouverClasseParNom(nomClasseEtudiant);
                    if (classe != null) {
                        gestionEcole.ajouterEtudiant(nomEtudiant, classe);
                    } else {
                        System.out.println("Classe non trouvée !");
                    }
                    break;
                case 3: // Ajouter une classe
                    System.out.println("Nom de la matière :");
                    String nomMatiere = scanner.nextLine();
                    gestionEcole.ajouterMatiere(nomMatiere);
                    break;
                case 4: // Sélectionner une classe et une matière.
                			//Enregistrer les notes de tous les étudiants de cette classe pour la matière Sélectionnée.
                	 System.out.println("Nom de la classe :");
                     String nomClasseNote = scanner.nextLine();
                     Classe classeNote = gestionEcole.trouverClasseParNom(nomClasseNote);
                     if (classeNote == null) {
                         System.out.println("Classe non trouvée !");
                         break;
                     }
                     System.out.println("Nom de la matière :");
                     String nomMatiereNote = scanner.nextLine();
                     Matiere matiereNote = gestionEcole.trouverMatiereParNom(nomMatiereNote);
                     if (matiereNote == null) {
                         System.out.println("Matière non trouvée !");
                         break;
                     }
                     for (Etudiant e : gestionEcole.getEtudiants(classeNote)) {
                         System.out.println("Note pour " + e.getNom() + " :");
                         double valeur = Double.parseDouble(scanner.nextLine());
                         gestionEcole.enregistrerNote(classeNote, matiereNote, e, valeur);
                     }
                    break;
                case 5:	// Afficher les étudiants pour chaque classes et les notes de tous les étudiants pour la matière Sélectionnée.
                	gestionEcole.afficherEtudiantsParClasse();
                	gestionEcole.afficherNotes();
                    break;
                case 6:  // Afficher le classement des étudiants pour chaque matière en ordre décroissant de moyenne.
                	gestionEcole.afficherMoyennes();
                    break;
                case 7:	 // Afficher le classement des étudiants pour chaque matière en ordre décroissant de moyenne.
                	gestionEcole.afficherClassementParMatiere();
                    break;
                 case 8:
                    running = false;
                    break;
            }
            
        }

        scanner.close();
    }
}
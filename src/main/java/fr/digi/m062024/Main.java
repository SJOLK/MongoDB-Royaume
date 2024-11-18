package fr.digi.m062024;

public class Main {
    public static void main(String[] args) {
        // Créer une instance de connexion
        ConnexionMongoDB connexionMongoDB = new ConnexionMongoDB();

        // Récupérer la base de données
        GestionRessources gestionRessources = new GestionRessources(connexionMongoDB.getDatabase());
        GestionCitoyen gestionCitoyen = new GestionCitoyen(connexionMongoDB.getDatabase());

        // Ajouter une ressource appelée "Or" avec une quantité de 500
        gestionRessources.ajouterRessource("Or", 500);

        // Afficher toutes les ressources
        System.out.println("Ressources après ajout :");
        gestionRessources.afficherRessources();

        // Mettre à jour la quantité de "Or" à 700
        gestionRessources.mettreAJourRessource("Or", 700);

        // Afficher toutes les ressources après mise à jour
        System.out.println("Ressources après mise à jour :");
        gestionRessources.afficherRessources();

        // Supprimer la ressource "Or"
        gestionRessources.supprimerRessource("Or");

        // Afficher toutes les ressources après suppression
        System.out.println("Ressources après suppression :");
        gestionRessources.afficherRessources();


        // 1. Ajouter des citoyens
        gestionCitoyen.ajouterCitoyen("Citoyen1", 100, "production de nourriture");
        gestionCitoyen.ajouterCitoyen("Citoyen2", 50, "protection");

        // 2. Afficher tous les citoyens
        System.out.println("Citoyens après ajout :");
        gestionCitoyen.afficherCitoyens();

        // 3. Mettre à jour un citoyen
        gestionCitoyen.mettreAJourCitoyen("Citoyen1", 150, "construction");

        // 4. Afficher les citoyens après mise à jour
        System.out.println("Citoyens après mise à jour :");
        gestionCitoyen.afficherCitoyens();

        // 5. Supprimer un citoyen
        gestionCitoyen.supprimerCitoyen("Citoyen1");
        gestionCitoyen.supprimerCitoyen("Citoyen2");

        // 6. Afficher les citoyens après suppression
        System.out.println("Citoyens après suppression :");
        gestionCitoyen.afficherCitoyens();

        // Vérifier le nombre total de citoyens par rôle
        int totalPaysans = gestionCitoyen.verifierCitoyenParRole("production de nourriture");
        System.out.println("Nombre total de citoyens pour le rôle 'production de nourriture' : " + totalPaysans);

        int totalSoldats = gestionCitoyen.verifierCitoyenParRole("protection");
        System.out.println("Nombre total de citoyens pour le rôle 'protection' : " + totalSoldats);

        int totalArtisants = gestionCitoyen.verifierCitoyenParRole("construction");
        System.out.println("Nombre total de citoyens pour le rôle 'construction' : " + totalArtisants);

        // Fermer la connexion
        connexionMongoDB.fermerConnexion();
    }
}

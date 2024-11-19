package fr.digi.m062024;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class GestionBatiments {
    private MongoCollection<Document> batiments;
    private GestionRessources gestionRessources; // Pour vérifier les ressources nécessaires

    public GestionBatiments(MongoCollection<Document> batiments, GestionRessources gestionRessources) {
        this.batiments = batiments;
        this.gestionRessources = gestionRessources;
    }

    // Méthode pour ajouter un bâtiment sans condition
    public void ajouterBatiment(String type, int niveau, String fonction) {
        Document batiment = new Document("type", type)
                .append("niveau", niveau)
                .append("fonction", fonction);
        batiments.insertOne(batiment);
        System.out.println("Bâtiment ajouté : " + type);
    }

    // Méthode pour construire un bâtiment avec vérification des ressources
    public void construireBatiment(String type, int coutBois, int coutPierre, String fonction) {
        if (gestionRessources.verifierRessource("Bois", coutBois) &&
                gestionRessources.verifierRessource("Pierre", coutPierre)) {

            // Déduire les ressources pour la construction
            gestionRessources.mettreAJourRessource("Bois", -coutBois);
            gestionRessources.mettreAJourRessource("Pierre", -coutPierre);

            // Ajouter le bâtiment
            ajouterBatiment(type, 1, fonction);
            System.out.println("Bâtiment construit : " + type);
        } else {
            System.out.println("Ressources insuffisantes pour construire le bâtiment.");
        }
    }

    // Méthode pour afficher les bâtiments
    public void afficherBatiments() {
        for (Document batiment : batiments.find()) {
            System.out.println(batiment.toJson());
        }
    }

    // Méthode pour mettre à jour le niveau d'un bâtiment
    public void mettreAJourBatiment(String type, int nouveauNiveau) {
        batiments.updateOne(new Document("type", type), new Document("$set", new Document("niveau", nouveauNiveau)));
        System.out.println("Niveau mis à jour pour : " + type);
    }

    // Méthode pour supprimer un bâtiment
    public void supprimerBatiment(String type) {
        batiments.deleteOne(new Document("type", type));
        System.out.println("Bâtiment supprimé : " + type);
    }

    // Méthode pour améliorer le niveau d'un bâtiment
    public void ameliorerBatiment(String type) {
        // Récupérer le bâtiment à partir de son type
        Document batiment = batiments.find(new Document("type", type)).first();

        if (batiment != null) {
            int niveauActuel = batiment.getInteger("niveau", 1);
            int niveauSuivant = niveauActuel + 1;

            // Définir les coûts pour chaque niveau (exemple : coût augmente avec le niveau)
            int coutBois = niveauSuivant * 100; // Exemple : 100 unités de bois par niveau
            int coutPierre = niveauSuivant * 50; // Exemple : 50 unités de pierre par niveau

            // Vérifier si les ressources sont suffisantes
            if (gestionRessources.verifierRessource("Bois", coutBois) &&
                    gestionRessources.verifierRessource("Pierre", coutPierre)) {

                // Déduire les ressources nécessaires
                gestionRessources.mettreAJourRessource("Bois", -coutBois);
                gestionRessources.mettreAJourRessource("Pierre", -coutPierre);

                // Mettre à jour le niveau du bâtiment
                mettreAJourBatiment(type, niveauSuivant);
                System.out.println("Bâtiment amélioré : " + type + " au niveau " + niveauSuivant);
            } else {
                System.out.println("Ressources insuffisantes pour améliorer le bâtiment " + type);
            }
        } else {
            System.out.println("Bâtiment introuvable : " + type);
        }
    }
}


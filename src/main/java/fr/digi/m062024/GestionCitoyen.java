package fr.digi.m062024;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GestionCitoyen {
    private MongoCollection<Document> collection;

    public GestionCitoyen(MongoDatabase database) {
        // Connecte la classe à la collection "Citoyens"
        this.collection = database.getCollection("Citoyens");
    }

    // Méthode pour ajouter un citoyen
    public void ajouterCitoyen(String nom, int quantite, String role) {
        Document citoyen = new Document("nom", nom)
                .append("quantite", quantite)
                .append("role", role);
        collection.insertOne(citoyen);
        System.out.println("Citoyen ajouté : " + nom);
    }

    // Méthode pour afficher tous les citoyens
    public void afficherCitoyens() {
        for (Document citoyen : collection.find()) {
            System.out.println(citoyen.toJson());
        }
    }

    // Méthode pour mettre à jour un citoyen
    public void mettreAJourCitoyen(String nom, int nouvelleQuantite, String nouveauRole) {
        Document filtre = new Document("nom", nom); // Recherche par nom
        Document miseAJour = new Document("$set", new Document("quantite", nouvelleQuantite).append("role", nouveauRole));
        collection.updateOne(filtre, miseAJour);
        System.out.println("Citoyen mis à jour : " + nom);
    }

    // Méthode pour supprimer un citoyen
    public void supprimerCitoyen(String nom) {
        Document filtre = new Document("nom", nom); // Recherche par nom
        collection.deleteOne(filtre);
        System.out.println("Citoyen supprimé : " + nom);
    }

    // Méthode pour vérifier le nombre total de citoyens par rôle
    public int verifierCitoyenParRole(String role) {
        int total = 0;
        // Filtrer par rôle
        for (Document citoyen : collection.find(new Document("role", role))) {
            total += citoyen.getInteger("quantite");
        }
        return total;
    }
}

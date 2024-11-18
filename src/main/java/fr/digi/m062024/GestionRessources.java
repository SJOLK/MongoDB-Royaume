package fr.digi.m062024;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class GestionRessources {
    private MongoCollection<Document> collection;

    public GestionRessources(MongoDatabase database) {
        this.collection = database.getCollection("Ressources");
    }

    // Méthode pour ajouter une ressource
    public void ajouterRessource(String type, int quantite) {
        Document ressource = new Document("type", type).append("quantite", quantite);
        collection.insertOne(ressource);
        System.out.println("Ressource ajoutée : " + type);
    }

    // Méthode pour afficher toutes les ressources
    public void afficherRessources() {
        for (Document ressource : collection.find()) {
            System.out.println(ressource.toJson());
        }
    }

    // Méthode pour mettre à jour une ressource
    public void mettreAJourRessource(String type, int nouvelleQuantite) {
        collection.updateOne(new Document("type", type), new Document("$set", new Document("quantite", nouvelleQuantite)));
        System.out.println("Quantité mise à jour pour : " + type);
    }

    // Méthode pour supprimer une ressource
    public void supprimerRessource(String type) {
        collection.deleteOne(new Document("type", type));
        System.out.println("Ressource supprimée : " + type);
    }
}


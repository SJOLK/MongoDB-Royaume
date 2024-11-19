package fr.digi.m062024;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import org.bson.Document;

public class GestionRessources {
    private MongoCollection<Document> collection;

    public GestionRessources(MongoDatabase database) {
        this.collection = database.getCollection("Ressources");
    }

    public GestionRessources(MongoCollection<Document> ressource) {
        this.collection = ressource;
    }

    // Méthode pour ajouter une ressource
    public void ajouterRessource(String type, int quantite) {
        Document ressource = collection.find(new Document("type", type)).first();
        if (ressource != null) {
            int nouvelleQuantite = quantite + ressource.getInteger("quantite", 0);
            mettreAJourRessource(type, nouvelleQuantite);
        } else {
            collection.insertOne(new Document("type", type).append("quantite", quantite));
            System.out.println("Ressource ajoutée: " + type + " quantite " + quantite);
        }
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



    public boolean verifierRessource(String type, int quantiteNecessaire) {
        Document ressource = collection.find(new Document("type", type))
                .sort(Sorts.descending("_id")) // Récupérer le document le plus récent
                .first();
        if (ressource != null) {
            int quantiteDisponible = ressource.getInteger("quantite", 0);
            return quantiteDisponible >= quantiteNecessaire;
        }
        return false;
    }
}

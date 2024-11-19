package fr.digi.m062024;

import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class GestionCitoyens {
    private MongoCollection<Document> citoyens;

    public GestionCitoyens(MongoCollection<Document> citoyens) {
        this.citoyens = citoyens;
    }

    // Méthode pour ajouter un citoyen
    public void ajouterCitoyen(String nom, int quantite, String role) {
        Document citoyen = citoyens.find(new Document("role", role)).first();
        if (citoyen != null) {
            mettreAJourCitoyen(role, citoyen.getInteger("quantite", 0) + quantite);
        } else {
            Document nouveauCitoyen = new Document("nom", nom).append("role", role).append("quantite", quantite);
            citoyens.insertOne(nouveauCitoyen);
        }
    }

    // Méthode pour afficher tous les citoyens
    public void afficherCitoyens() {
        for (Document citoyen : citoyens.find()) {
            System.out.println(citoyen.toJson());
        }
    }

    // Méthode pour mettre à jour la quantité d'un citoyen (ajout ou suppression)
    public void mettreAJourCitoyen(String role, int changementQuantite) {
        System.out.println("quantité à mettre : " + changementQuantite);
        citoyens.updateOne(new Document("role", role), new Document("$set", new Document("quantite", changementQuantite)));
        System.out.println("Quantité mise à jour pour le rôle : " + role);
    }

    // Méthode pour mettre à jour le rôle d'un citoyen
    public void mettreAJourCitoyen(String nom, String ancienRole, String nouveauRole) {
        Document citoyenNouveauRole = citoyens.find(new Document("role", nouveauRole)).first();
        if (citoyenNouveauRole != null) {
            int qteNouveauRole = citoyenNouveauRole.getInteger("quantite") + 1;
            mettreAJourCitoyen(nouveauRole, qteNouveauRole);
        } else {
            Document citoyen = new Document("nom", nom)
                    .append("quantite", 1)
                    .append("role", ancienRole);
            citoyens.insertOne(citoyen);
        }
        Document citoyenAncienRole = citoyens.find(new Document("role", ancienRole)).first();
        int qteAncienRole = citoyenAncienRole.getInteger("quantite", 0) - 1;
        mettreAJourCitoyen(nouveauRole, qteAncienRole);

        System.out.println("Role mis à jour pour le citoyen : " + nom + " (" + ancienRole + " -> " + nouveauRole + ")");
    }

    // mettreAJourCitoyen("soldat", "defenseur", "attaquant");
    // -> attaquant ?
    // si oui:
    //     - recupere mon nb attaquant
    //     attaquant +1
    // sinon
    //     - attaquant 1
    // defenseur - 1


    // Méthode pour supprimer un citoyen
    public void supprimerCitoyen(String nom) {
        citoyens.deleteOne(new Document("nom", nom));
        System.out.println("Citoyen supprimé : " + nom);
    }

    public boolean verifierCitoyens(String role, int quantiteNecessaire) {
        Document citoyen = citoyens.find(new Document("role", role)).first();
        if (citoyen != null) {
            int quantiteDisponible = citoyen.getInteger("quantite", 0);
            return quantiteDisponible >= quantiteNecessaire;
        }
        return false;
    }
}
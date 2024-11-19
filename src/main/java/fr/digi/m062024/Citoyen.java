package fr.digi.m062024;

public class Citoyen {
    private String nom;
    private int quantite;
    private String role;

    public Citoyen(String nom, int quantite, String role) {
        this.nom= nom;
        this.quantite= quantite;
        this.role= role;
    }

    public void afficherDetails() {
        System.out.println("Citoyen: " + nom+ ", Quantité: " + quantite+ ", Rôle: " + role);
    }
}

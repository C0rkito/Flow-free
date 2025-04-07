package fr.iutfbleau.projet;


import java.io.Serializable;

public class Puzzle implements Serializable {


    private String nom;
    private boolean estValide;

    private int taille;

    private Case[][] grille;

    public Puzzle() {
    }


    public void setNom(String nomd){
        this.nom = nomd;
    }

    public Case[][] getGrille() {
        return this.grille;
    }

    public void setTaille(int taille){
        this.taille = taille;
        this.grille = new Case[taille][taille];

        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille ; j++) {
                this.grille[i][j] = new Case();
            }
        }

    }




    public String getNom(){
        return this.nom;
    }


    public void setValidite(boolean bool){
        this.estValide = bool;
    }

    public boolean getValidite(){
        return this.estValide;
    }

    public int getTaille(){
        return this.taille;
    }

    public boolean estResolue(){
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille ; j++) {
                if(this.grille[i][j].getValeur() == null){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");

        for (int i = 0; i < this.taille; i++) {
            s.append("[");
            for (int j = 0; j < this.taille; j++) {
                s.append(this.grille[i][j]);
                if (j < this.taille - 1) {
                    s.append(", ");
                }
            }
            s.append("]");
            if (i < this.taille - 1) {
                s.append("\n ");
            }
        }

        s.append("]");
        return s.toString();
    }

}

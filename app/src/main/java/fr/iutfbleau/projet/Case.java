package fr.iutfbleau.projet;


import java.io.Serializable;

public class Case implements Serializable {

    private Element valeur;

    public void setValeur(Element v){
        this.valeur = v;
    }


    public Element getValeur(){
        return this.valeur;
    }

    @Override
    public String toString() {
        return ""+this.valeur;
    }
}

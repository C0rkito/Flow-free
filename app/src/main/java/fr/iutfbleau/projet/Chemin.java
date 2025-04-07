package fr.iutfbleau.projet;



import java.io.Serializable;

public class Chemin extends Element implements Serializable {

    private int color;
    private Point depart;
    private Point arriver;

    public Chemin(int c,Point p,Point p2){
        this.color = c;
        this.depart = p;
        this.arriver = p2;
    }
    public int getCouleur() {
        return  this.color;
    }

    public Point getDepart(){
        return this.depart;
    }

    public Point getArriver(){
        return this.arriver;
    }

    @Override
    public String toString() {
        return "Chemin:";
    }
}

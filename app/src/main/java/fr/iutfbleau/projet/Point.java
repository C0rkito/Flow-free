package fr.iutfbleau.projet;

import java.io.Serializable;

public class Point extends Element implements Serializable {


    private int color;
    private boolean estlier;



    public Point(int c){
        this.color = c;
        this.estlier = false;
    }
    public int getCouleur() {
        return  this.color;
    }

    public void setEstLier(boolean l){
        this.estlier = l;
    }
    public boolean getEstLier(){
        return this.estlier;
    }


    @Override
    public String toString() {
        return this.color +"";
    }
}

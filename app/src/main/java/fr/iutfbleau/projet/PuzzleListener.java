package fr.iutfbleau.projet;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class PuzzleListener implements View.OnTouchListener {


    private  Puzzle puzzle;
    private CaseVue[][] grille;
    private TableLayout terrain;
    private boolean isDrawing;
    private List<CaseVue> cacheChemin;
    private CaseVue pointDepart;
    private ResoudreActivity resoudreActivity;

    public PuzzleListener(Puzzle puzzle, TableLayout terrain, ResoudreActivity resoudreActivity){
        this.puzzle = puzzle;
        this.terrain = terrain;
        this.cacheChemin = new ArrayList<>();
        this.resoudreActivity = resoudreActivity;



        this.grille = new CaseVue[this.puzzle.getTaille()][this.puzzle.getTaille()];

        for (int tr = 0; tr < this.terrain.getChildCount(); tr++) {
            View enfant = this.terrain.getChildAt(tr);

            if (enfant instanceof ViewGroup) {
                ViewGroup ligne = (ViewGroup) enfant;

                for (int cv = 0; cv < ligne.getChildCount(); cv++) {
                    CaseVue view = (CaseVue) ligne.getChildAt(cv);
                    this.grille[tr][cv] = view;
                }
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int typeEvent = event.getActionMasked();
        if (typeEvent == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < this.puzzle.getTaille(); i++) {
                for (int j = 0; j < this.puzzle.getTaille(); j++) {


                    CaseVue vue = this.grille[i][j];
                    int[] location = new int[2];
                    vue.getLocationOnScreen(location);
                    int caseX = location[0];
                    int caseY = location[1];

                    if (event.getRawX() >= caseX && event.getRawX() <= caseX + vue.getWidth() &&
                            event.getRawY() >= caseY && event.getRawY() <= caseY + vue.getHeight()) {

                        if (vue.getCase().getValeur() instanceof Point) {


                                for (int i2 = 0; i2 < this.puzzle.getTaille(); i2++) {
                                    for (int j2 = 0; j2 < this.puzzle.getTaille(); j2++) {

                                        Element caseElt = this.grille[i2][j2].getCase().getValeur();

                                        if (caseElt instanceof Chemin){
                                            Chemin caseChemin = (Chemin) caseElt;

                                            if(caseChemin.getDepart() == vue.getCase().getValeur()
                                                    || caseChemin.getArriver() == vue.getCase().getValeur()){

                                                this.grille[i2][j2].getCase().setValeur(null);
                                            }

                                        }
                                    }
                                }


                                this.reset();
                                this.isDrawing = true;
                                this.pointDepart = vue;
                                this.cacheChemin.add(pointDepart);

                        }
                    }
                }
            }
        }

        if (typeEvent == MotionEvent.ACTION_MOVE && this.isDrawing) {


            for (int i = 0; i < this.puzzle.getTaille(); i++) {
                for (int j = 0; j < this.puzzle.getTaille(); j++) {
                    CaseVue vue = this.grille[i][j];
                    int[] location = new int[2];
                    vue.getLocationOnScreen(location);
                    int caseX = location[0];
                    int caseY = location[1];

                    if (event.getRawX() >= caseX && event.getRawX() <= caseX + vue.getWidth() &&
                            event.getRawY() >= caseY && event.getRawY() <= caseY + vue.getHeight()) {



                        if (vue.getCase().getValeur() instanceof Point) {


                            if(vue == this.pointDepart && this.cacheChemin.size()>1){
                                this.reset();
                            }

                            if((vue.getCase().getValeur()).getCouleur() == this.pointDepart.getCase().getValeur().getCouleur()){
                                if( vue != this.pointDepart){



                                    for(CaseVue cv: this.cacheChemin){
                                        if(cv.getCase().getValeur() == null){
                                            cv.getCase().setValeur(new Chemin(this.pointDepart.getCase().getValeur().getCouleur(),
                                                    (Point) this.pointDepart.getCase().getValeur(), (Point) vue.getCase().getValeur()));
                                        }

                                    }

                                    if(this.puzzle.estResolue()){
                                        this.resoudreActivity.pausePuzzleResout();
                                        return true;
                                    }
                                }
                            }
                            else{

                                this.reset();
                            }

                        }

                        if (vue.getCase().getValeur() instanceof Chemin) {

                            this.reset();

                        }





                        if (vue.getCase().getValeur() == null) {
                            if(!(this.cacheChemin.contains(vue))){
                                this.cacheChemin.add(vue);
                            }

                            vue.setCouleurChemin(this.pointDepart.getCase().getValeur().getCouleur());

                        }



                        if(this.cacheChemin.size() > 1) {

                            // peut pas se croiser
                            if (this.cacheChemin.subList(0, this.cacheChemin.size() - 1).contains(vue)) {
                                this.reset();
                                return true;
                            }

                            //pas diagonale
                            int[] location2 = new int[2];

                            CaseVue dernier = this.cacheChemin.get(this.cacheChemin.size() - 2);

                            dernier.getLocationOnScreen(location2);

                            int dernierX = location2[0];
                            int dernierY = location2[1];

                            if (caseX != dernierX && caseY != dernierY) {
                                this.reset();
                                return true;
                            }

                            //case forcement adjacente a la derniere
                            if(caseX > dernierX + vue.getWidth() || caseY > dernierY + vue.getHeight()){
                                this.reset();
                                return true;

                            }




                        }




                    }
                }
            }
        }



        if (typeEvent == MotionEvent.ACTION_UP) {
            this.reset();
        }


        //sortie du terrain
        if (event.getX() < 0 || event.getX() > this.terrain.getWidth() || event.getY() < 0 || event.getY() > this.terrain.getHeight()) {
            this.reset();
        }






        return true;
    }




    private void reset(){

        for (int i = 0; i < this.puzzle.getTaille(); i++) {
            for (int j = 0; j < this.puzzle.getTaille(); j++) {
                CaseVue vue = this.grille[i][j];
                if (vue.getCase().getValeur() == null) {
                    vue.refresh();
                    vue.clearCouleurChemin();
                }
            }
        }
        this.cacheChemin.removeAll(this.cacheChemin);
        this.isDrawing = false;

    }



}
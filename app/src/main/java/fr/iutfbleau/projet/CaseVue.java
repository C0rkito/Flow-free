package fr.iutfbleau.projet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.io.Serializable;

public class CaseVue extends View implements Serializable {


    private Paint pinceau;
    private Case laCase;
    private Integer couleurChemin = null;
    private int taille;


    public CaseVue(Context context,Case c) {
        super(context);
        this.pinceau = new Paint();
        this.laCase = c;


        this.setBackgroundColor(this.getResources().getColor(R.color.CaseFond));



    }


    public void refresh(){
        this.setBackgroundColor(this.getResources().getColor(R.color.CaseFond));


    }
    public Case getCase(){
        return this.laCase;
    }

    public void setCouleurChemin(int color) {
        this.couleurChemin = color;
        invalidate();
    }

    public void clearCouleurChemin() {
        this.couleurChemin = null;
        invalidate();
    }


    @Override
    public String toString() {
        return laCase.toString();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        this.taille = (this.getHeight()+this.getHeight())/8;


        this.pinceau.setColor(getResources().getColor(R.color.bordure));
        this.pinceau.setStrokeWidth(5F);
        this.pinceau.setStyle( Paint.Style.STROKE );
        canvas.drawRect(0, 0, getWidth(), getHeight(), this.pinceau);




        if(this.laCase.getValeur() != null) {

            if (this.laCase.getValeur() instanceof Point) {
                this.pinceau.setStyle(Paint.Style.FILL);
                this.pinceau.setColor(this.laCase.getValeur().getCouleur());
                canvas.drawCircle((float) this.getWidth() / 2, (float) this.getHeight() / 2, (float) (this.getHeight() + this.getWidth()) / 5, this.pinceau);

            }

            if (this.laCase.getValeur() instanceof Chemin) {
                this.pinceau.setStyle(Paint.Style.FILL);
                this.pinceau.setColor(this.laCase.getValeur().getCouleur());
                canvas.drawRect(
                        (float) this.getWidth() / 2 - this.taille,
                        (float) this.getHeight() / 2 - this.taille,
                        (float) this.getWidth() / 2 + this.taille,
                        (float) this.getHeight() / 2 + this.taille,
                        this.pinceau
                );
            }
        }


        if (couleurChemin != null) {
            this.pinceau.setStyle(Paint.Style.FILL);
            pinceau.setColor(couleurChemin);
            canvas.drawRect(
                    (float) (getWidth()) / 2 - this.taille,
                    (float) getHeight() / 2 - this.taille,
                    (float) getWidth() / 2 + this.taille,
                    (float) getHeight() / 2 + this.taille,
                    this.pinceau
            );


        }

    }


}

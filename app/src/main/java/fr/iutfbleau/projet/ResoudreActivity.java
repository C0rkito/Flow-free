package fr.iutfbleau.projet;

import static java.lang.Math.floor;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ResoudreActivity extends Activity {


    private Button buttonRetour;
    private RetourListener retourObs;

    private Puzzle puzzle;
    private PuzzleListener puzzleListener;
    private  TableLayout terrain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resoudre);


        TextView nomText = this.findViewById(R.id.titre);
        TextView tailleText = this.findViewById(R.id.taille);


        this.terrain = findViewById(R.id.jeu);
        this.terrain.removeAllViews();

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;



        if (savedInstanceState != null && savedInstanceState.containsKey("puzzle")) {
            this.puzzle = (Puzzle) savedInstanceState.getSerializable("puzzle");
        } else {
            this.puzzle = (Puzzle) getIntent().getSerializableExtra("puzzle");
        }

        nomText.setText(this.puzzle.getNom());
        tailleText.setText(this.puzzle.getTaille() + "x" + this.puzzle.getTaille());

        int tailleCaseWidth = (int) floor(screenWidth / puzzle.getTaille());



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tailleCaseWidth = (int) floor((screenHeight-300) / puzzle.getTaille());
        }

        int tailleCaseheight = tailleCaseWidth;

        TableRow ligne;

        for (int i = 0; i < puzzle.getTaille(); i++) {

            ligne = new TableRow(this);
            terrain.addView(ligne);

            for (int j = 0; j < puzzle.getTaille(); j++) {
                CaseVue casevue = new CaseVue(this.getBaseContext(),this.puzzle.getGrille()[i][j]);

                if (casevue.getParent() != null) {
                    ((ViewGroup) casevue.getParent()).removeView(casevue);
                }

                casevue.setLayoutParams(new TableRow.LayoutParams(tailleCaseWidth, tailleCaseheight));

                ligne.addView(casevue);

            }
        }

    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        this.puzzle = (Puzzle) savedInstanceState.getSerializable("puzzle");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("puzzle",this.puzzle);

    }

    //reactiver controlleur
    @Override
    protected void onResume() {
        super.onResume();





        if(this.puzzleListener == null){
            this.retourObs = new RetourListener();
            this.buttonRetour = findViewById(R.id.retour);
            this.puzzleListener = new PuzzleListener(this.puzzle,this.terrain,this);

        }


        this.buttonRetour.setOnClickListener(this.retourObs);
        this.terrain.setOnTouchListener(this.puzzleListener);



        if(this.puzzle.estResolue()){
            this.pausePuzzleResout();
        }


    }

    //desactiver controleur
    @Override
    protected void onPause() {
        super.onPause();
        this.buttonRetour.setOnClickListener(null);
        this.terrain.setOnTouchListener(null);

    }

    public void pausePuzzleResout(){
        this.terrain.setOnTouchListener(null);
        Toast.makeText(getApplicationContext(), "Gagné", Toast.LENGTH_LONG).show();
    }


    //relancer animation etc.
    @Override
    protected void onStart() {
        super.onStart();
    }



    //stop animation etc.
    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
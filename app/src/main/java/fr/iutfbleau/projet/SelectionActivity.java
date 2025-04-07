package fr.iutfbleau.projet;



import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionActivity extends Activity {



    private List<Puzzle> puzzleListe;
    private Map<Integer, Puzzle> puzzleDicoCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_selection);


        Button retour = findViewById(R.id.retour);
        retour.setOnClickListener(new RetourListener());

        AssetManager manager = getAssets();

        Map<Integer, Integer> difficulte = new HashMap<>();



        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean mode = prefs.getBoolean(this.getResources().getString(R.string.achromate_key),this.getResources().getBoolean(R.bool.valeur_def_achromate));

        difficulte.put(0,getResources().getColor(R.color.gris_35));

        int text_color = getResources().getColor(R.color.white);

        if(mode){
            text_color = getResources().getColor(R.color.black);
            difficulte.put(14,getResources().getColor(R.color.gris_3));
            difficulte.put(13,getResources().getColor(R.color.gris_4));
            difficulte.put(12,getResources().getColor(R.color.gris_5));
            difficulte.put(11,getResources().getColor(R.color.gris_7));
            difficulte.put(10,getResources().getColor(R.color.gris_10));
            difficulte.put(9,getResources().getColor(R.color.gris_12));
            difficulte.put(8,getResources().getColor(R.color.gris_15));
            difficulte.put(7,getResources().getColor(R.color.gris_18));
            difficulte.put(6,getResources().getColor(R.color.gris_22));
            difficulte.put(5,getResources().getColor(R.color.gris_25));

        }
        else{
            difficulte.put(5,getResources().getColor(R.color.vert_claire));
            difficulte.put(6,getResources().getColor(R.color.vert));
            difficulte.put(7,getResources().getColor(R.color.bleu));
            difficulte.put(8,getResources().getColor(R.color.bleu_vert));
            difficulte.put(9,getResources().getColor(R.color.magenta));
            difficulte.put(10,getResources().getColor(R.color.violet));
            difficulte.put(11,getResources().getColor(R.color.pourpre));
            difficulte.put(12,getResources().getColor(R.color.marron));
            difficulte.put(13,getResources().getColor(R.color.orange));
            difficulte.put(14,getResources().getColor(R.color.rouge));
        }






        if(savedInstanceState != null && savedInstanceState.containsKey("liste")) {
            this.puzzleListe = (List<Puzzle>) savedInstanceState.getSerializable("liste");
        }else{

            this.puzzleListe = new ArrayList<>();



            try {
                PuzzleParser parser = new PuzzleParser(this.getBaseContext());
                for (String pfile : manager.list("puzzles")) {

                    Puzzle puzzle = parser.readPuzzle(pfile);
                    this.puzzleListe.add(puzzle);
                }
            } catch (IOException e) {

            }

        }
        this.puzzleDicoCache = new HashMap<>();











        TableLayout liste = findViewById(R.id.listePuzzle);
        int nombre = 0;
        TableRow ligne = null;

        int puzzlesparligne = getResources().getInteger(R.integer.PuzzlesParLigne);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            puzzlesparligne += 2;
        }


        /*
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.puzzle_element);
        adapter.setViewBinder()
*/

        puzzleListe.sort((a, b) -> {
            if (a.getTaille() > 0 && b.getTaille() == 0) return -1;
            if (a.getTaille() == 0 && b.getTaille() > 0) return 1;
            return Integer.compare(a.getTaille(), b.getTaille());
        });


        for (Puzzle p : this.puzzleListe) {

            if (nombre % puzzlesparligne == 0) {
                ligne = new TableRow(this);
                ligne.setGravity(Gravity.CENTER);
                liste.addView(ligne);
            }

            LinearLayout lePuzzle = new LinearLayout(this);
            lePuzzle.setOrientation(LinearLayout.VERTICAL);
            lePuzzle.setGravity(Gravity.CENTER);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            layoutParams.setMargins(10, 10, 10, 10);
            lePuzzle.setLayoutParams(layoutParams);

            Button bouton = new Button(this);

            bouton.setBackgroundColor(difficulte.get(p.getTaille()));
            bouton.setLayoutParams(new LinearLayout.LayoutParams(300, 300));

            bouton.setId(TextView.generateViewId());
            this.puzzleDicoCache.put(bouton.getId(),p);




            TextView text = new TextView(this);
            text.setText(p.getNom());
            int size = 20 - (p.getNom().length()/3);


            text.setTextSize(size);
            text.setGravity(Gravity.CENTER);
            text.setLayoutParams(new LinearLayout.LayoutParams(
                    300,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));




            lePuzzle.addView(bouton);
            lePuzzle.addView(text);

            if(!p.getValidite()){
                text.setTextColor( getResources().getColor(R.color.CaseFond));

            }
            else{

                bouton.setText(p.getTaille()+"x"+p.getTaille());
                bouton.setTextColor(text_color);
                bouton.setTextSize(30);


            }

            ligne.addView(lePuzzle);

            nombre++;
        }



    }




    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("liste", (Serializable) this.puzzleListe);

    }

    //relancer animation etc.
    @Override
    protected void onStart() {
        super.onStart();
    }

    //reactiver controlleur
    @Override
    protected void onResume() {
        super.onResume();

        for(int id:puzzleDicoCache.keySet()){
            Button bouton = this.findViewById(id);
            if(puzzleDicoCache.get(id).getValidite()){
                bouton.setOnClickListener(new ButtonResoudreListener(puzzleDicoCache.get(id)));
            }
        }


    }

    //desactiver controleur
    @Override
    protected void onPause() {
        super.onPause();
        for(int id:puzzleDicoCache.keySet()){
            Button bouton = this.findViewById(id);
            bouton.setOnClickListener(null);
        }


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

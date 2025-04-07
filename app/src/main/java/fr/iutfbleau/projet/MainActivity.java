package fr.iutfbleau.projet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;


public class MainActivity extends Activity {


    private Button jouer;
    private Button parametres;
    private ButtonMenuListener menuObs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        this.jouer =  findViewById(R.id.jouer);
        this.parametres = findViewById(R.id.parametres);






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
        if(this.menuObs == null){
            this.menuObs = new ButtonMenuListener(this);
        }

        this.jouer.setOnClickListener(this.menuObs);
        this.parametres.setOnClickListener(this.menuObs);
    }

    //desactiver controleur
    @Override
    protected void onPause() {
        super.onPause();
        this.jouer.setOnClickListener(null);
        this.parametres.setOnClickListener(null);
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
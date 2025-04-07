package fr.iutfbleau.projet;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ButtonMenuListener implements View.OnClickListener {



    private Activity activity;
    private Intent resoudre;
    private Intent parametres;


    public ButtonMenuListener(Activity activity){
        this.activity = activity;
        this.resoudre = new Intent(this.activity.getBaseContext(), SelectionActivity.class);
        this.parametres = new Intent(this.activity.getBaseContext(), SettingsActivity.class);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.jouer){
            this.activity.startActivity(this.resoudre);
        }

        else{
            if(v.getId() == R.id.parametres){
                this.activity.startActivity(this.parametres);
            }

        }

    }
}

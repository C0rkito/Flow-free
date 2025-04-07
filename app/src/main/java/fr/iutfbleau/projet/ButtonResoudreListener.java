package fr.iutfbleau.projet;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ButtonResoudreListener implements View.OnClickListener {

    private Puzzle puzzle;
    public ButtonResoudreListener(Puzzle p){
        this.puzzle = p;
    }

    @Override
    public void onClick(View v) {
        Activity activity = (Activity) v.getContext();
        Intent i = new Intent(activity,ResoudreActivity.class);
        i.putExtra("puzzle",this.puzzle);

        activity.startActivity(i);


    }
}

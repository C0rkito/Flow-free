package fr.iutfbleau.projet;

import android.app.Activity;
import android.view.View;

public class RetourListener implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        Activity activity = (Activity) v.getContext();
        activity.finish();
    }
}

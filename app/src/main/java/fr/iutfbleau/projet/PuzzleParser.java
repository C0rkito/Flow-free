package fr.iutfbleau.projet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.preference.PreferenceManager;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PuzzleParser {


    private static Context context;
    private final String puzzlePath;

    public PuzzleParser(Context ctx) {
        this.context = ctx;
        this.puzzlePath = this.context.getString(R.string.puzzle_path);
    }


    public  Puzzle readPuzzle(String puzzleName) {


        Puzzle puzzle = new Puzzle();
        AssetManager manager = this.context.getAssets();
        String nom = puzzleName.replaceAll("\\..*", "");
        boolean validite = true;



        int taille = 5;

        PreferenceManager.setDefaultValues(this.context,R.xml.preferences,false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        boolean mode = prefs.getBoolean(this.context.getResources().getString(R.string.achromate_key),this.context.getResources().getBoolean(R.bool.valeur_def_achromate));

        int[] colors = this.context.getResources().getIntArray(R.array.point_couleur);
        if(mode){
            colors = this.context.getResources().getIntArray(R.array.point_couleur_achromate);
        }







        try {
            InputStream is = manager.open(this.puzzlePath+puzzleName);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();


            int numPoint = -1;

            int pointsDansPaire = 0;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (parser.getName() != null) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if ("puzzle".equals(parser.getName())) {
                            if (parser.getAttributeValue(null, "nom") != null) {
                                nom = parser.getAttributeValue(null, "nom");
                            }

                            if (parser.getAttributeValue(null, "size") != null) {
                                taille = Integer.parseInt(parser.getAttributeValue(null, "size"));
                                puzzle.setTaille(taille);
                                if (taille < 5 || taille > 14) {
                                    validite = false;
                                    break;
                                }

                            } else {
                                validite = false;
                                break;
                            }
                        }

                        if ("paire".equals(parser.getName())) {
                            numPoint++;
                            pointsDansPaire = 0;

                            if (parser.getDepth() != 2) {
                                validite = false;
                                break;
                            }
                        }

                        if ("point".equals(parser.getName())) {
                            pointsDansPaire++;

                            if (parser.getAttributeValue(null, "colonne") != null && parser.getAttributeValue(null, "ligne") != null) {
                                int colonne = Integer.parseInt(parser.getAttributeValue(null, "colonne"));
                                int ligne = Integer.parseInt(parser.getAttributeValue(null, "ligne"));

                                if ((colonne < 0 || ligne < 0) || (colonne > taille - 1 || ligne > taille - 1)) {
                                    validite = false;
                                    break;
                                }
                                puzzle.getGrille()[ligne][colonne].setValeur(new Point(colors[numPoint]));
                            } else {
                                validite = false;
                                break;
                            }
                        }
                    }

                    if (eventType == XmlPullParser.END_TAG && "paire".equals(parser.getName())) {
                        if (pointsDansPaire != 2) {
                            validite = false;
                            break;
                        }
                    }
                }

                eventType = parser.next();
            }

            is.close();



        } catch (IOException | XmlPullParserException e) {
            validite = false;
        }


        puzzle.setNom(nom);
        puzzle.setValidite(validite);
        if(!validite){
            taille = 0;
            puzzle.setTaille(taille);
        }

        return puzzle;

    }

    private ArrayList<Integer> GenereCouleurs(int taille) {
        ArrayList<Integer> colors2 = new ArrayList<>();
        float minDistance = 1.0f / taille;

        for (int i = 0; i < taille; i++) {

            float hue = i * minDistance;


            int color = Color.HSVToColor(new float[]{hue * 360, 0.9f, 1.0f});
            colors2.add(color);
        }

        return colors2;
    }

}

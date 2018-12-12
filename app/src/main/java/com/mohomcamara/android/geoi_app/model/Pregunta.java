package com.adriangutierrez.android.geoi_app.model;

/**
 * Created by Alumnos on 16/10/2018.
 */

public class Pregunta {
    private int mIdResText;
    private boolean mVerdadera;

    public Pregunta (int idResText, boolean verdadera){
        mIdResText = idResText;
        mVerdadera = verdadera;
    }

    public int getIdResText(){return mIdResText;}

    public void setIdResText(int idResText){mIdResText = idResText;}

    public boolean isVerdadera(){return mVerdadera;}

    public void serVerdadera(boolean verdadera){mVerdadera = verdadera;}
}

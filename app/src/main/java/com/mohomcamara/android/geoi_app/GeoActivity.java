package com.adriangutierrez.android.geoi_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adriangutierrez.android.geoi_app.model.BancoDePreguntas;
import com.adriangutierrez.android.geoi_app.model.Pregunta;

public class GeoActivity extends AppCompatActivity {
    private final String KEY_POSICION_ACUTAL = "posicion_actual";
    private Button mBotonPregunta;
    private Button mBotonFalso;
    private Button mBotonAnterior;
    private Button mBotonSiguiente;
    private Button mBotonCierto;
    private TextView mTextPregunta;
    private Button mBotonVerRespuesta;
    private final int REQUEST_CODE_SE_MOSTRO_RESPUESTA = 0;

    private BancoDePreguntas banco;
    private Pregunta mPreguntaActual;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int posicion = banco.getPosicionActual();
        outState.putInt(KEY_POSICION_ACUTAL, posicion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        crearBancoDePreguntas();
        mPreguntaActual = banco.get(0);
        /*
        if (savedInstanceState != null) {
            mPreguntaActual = savedInstanceState.getInt(KEY_PREGUNTAACTUAL)
        }
        */



        mTextPregunta = (TextView) findViewById(R.id.texto_pregunta);
        mBotonCierto = (Button) findViewById(R.id.boton_verdadero);
        mBotonFalso = (Button) findViewById(R.id.boton_falso);
        mBotonAnterior = (Button) findViewById(R.id.boton_anterior);
        mBotonSiguiente = (Button)  findViewById(R.id.boton_siguiente);
        mBotonVerRespuesta = (Button) findViewById(R.id.boton_ver_respuesta) ;

        crearBancoDePreguntas();
        if (savedInstanceState != null){
            int posicion = savedInstanceState.getInt(KEY_POSICION_ACUTAL);
            mPreguntaActual = banco.get(posicion);
        }

        actualizarPregunta();

        mBotonCierto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarRespuesta(true);
            }
        });

        mBotonFalso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarRespuesta(false);
            }
        });

        mBotonAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPreguntaActual = banco.previous();
                actualizarPregunta();
            }
        });

        mBotonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPreguntaActual = banco.next();
                actualizarPregunta();
            }
        });

        mBotonVerRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeoActivity.this, TrampaActivity.class);
                startActivity(intent);
                boolean laRespustaEsCorrecta = mPreguntaActual.isVerdadera();
                intent.putExtra(TrampaActivity.EXTRA_RESPUESTA_ES_CORRECTA, laRespustaEsCorrecta);

                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_SE_MOSTRO_RESPUESTA);
            }
        });
    }


    private void crearBancoDePreguntas() {
        banco = new BancoDePreguntas();
        banco.add(new Pregunta(R.string.texto_pregunta1, false));
        banco.add(new Pregunta(R.string.texto_pregunta2, true));
        banco.add(new Pregunta(R.string.texto_pregunta3, true));
        banco.add(new Pregunta(R.string.texto_pregunta4, true));
        banco.add(new Pregunta(R.string.texto_pregunta5, false));
    }


    private void actualizarPregunta() {
        mTextPregunta.setText(mPreguntaActual.getIdResText());
    }

    private void verificarRespuesta(boolean botonOprimido) {

        boolean respuestaEsVerdadera = mPreguntaActual.isVerdadera();
        if (botonOprimido == respuestaEsVerdadera) {

            Toast.makeText(GeoActivity.this,
                    R.string.texto_correcto,
                    Toast.LENGTH_SHORT)
                    .show();
        }
        else {
            Toast.makeText(GeoActivity.this,
                    R.string.texto_incorrecto,
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }
}

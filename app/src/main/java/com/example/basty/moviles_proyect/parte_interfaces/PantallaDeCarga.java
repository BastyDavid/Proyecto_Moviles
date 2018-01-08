package com.example.basty.moviles_proyect.parte_interfaces;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.basty.moviles_proyect.R;

import java.util.Timer;
import java.util.TimerTask;



public class PantallaDeCarga extends Activity {

    // duracion de la pantalla de carga
    private static final long SPLASH_SCREEN_DELAY = 2500; //3 segundos para que carge

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // orientacion de la imagen
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //esconder la barra de titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.pantalla_carga);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // inicia la actividad
                Intent mainIntent = new Intent().setClass(
                        PantallaDeCarga.this, MainActivity.class);
                startActivity(mainIntent);

                // cierra la actividad para que el usuario no regrese al presionar el retorno

                finish();
            }
        };

        //se encarga del proceso de retraso
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}

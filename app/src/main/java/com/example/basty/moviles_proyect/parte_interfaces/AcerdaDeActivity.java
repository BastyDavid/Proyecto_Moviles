package com.example.basty.moviles_proyect.parte_interfaces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.basty.moviles_proyect.R;


//creada para mostrar la parte de ayuda o "Acerca De... "

public class AcerdaDeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_menu_layout);
        setActionBar();
        setHelp();
    }



    public void setActionBar() {
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acerca_de, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

        //pequeña metodo que funciona como descripción del funcionamiento de la applicacion
    private void setHelp() {
        String title = "Proyecto Final Moviles :3";
        String content = "una App que tiene como proposito registrar actividades que el usuario ingrese \n" +
                "1) Creas una tareas y le asignas una tarea, puedes eliminarla, Buscarla entre las demas tareas \n" +
                "2) puedes Terminar la tarea que escribiste una vez que la realices :) ";
        com.example.basty.moviles_proyect.Datos.AcerdaDe help = new com.example.basty.moviles_proyect.Datos.AcerdaDe(title, content);
        TextView titleText = (TextView) findViewById(R.id.helpTitle);
        titleText.setText(help.getTitle());
        TextView contentText = (TextView) findViewById(R.id.helpContent);
        contentText.setText(help.getContent());
    }
}

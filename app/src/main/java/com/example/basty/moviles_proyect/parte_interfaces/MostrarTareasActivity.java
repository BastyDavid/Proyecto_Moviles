package com.example.basty.moviles_proyect.parte_interfaces;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basty.moviles_proyect.R;
import com.example.basty.moviles_proyect.Datos.Menu_opcion;

import java.util.HashMap;
import java.util.Map;


public class MostrarTareasActivity extends AppCompatActivity {
  //actividad para mostrar el contenido especifico de la tarea

    public static final String EXTRA_ID = "com.example.basty.moviles_proyect.EXTRA_ID";
    public static final String EXTRA_COMMAND = "com.example.basty.moviles_proyect.EXTRA_COMMAND";
    public static final String EXTRA_TITLE = "com.example.basty.moviles_proyect.EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "com.example.basty.moviles_proyect.EXTRA_CONTENT";
    private final String[] extraCommands = {"Terminar", "Borrar", "Actualizar"};

    private Map<Integer, Menu_opcion> commandMap;
    public static boolean showed = false;
    private EditText title, content;
    private int ID;  //Id de la tarea que se abrira


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_tarea);
        registerComponents();
        setActionBar();
        showTask();
        createCommands();
    }

    public void createCommands() {
        commandMap = new HashMap<>();

        commandMap.put(R.id.finishButton, new Menu_opcion() {
            @Override
            public boolean execute() {
                openMainActivity(ID, extraCommands[0]);
                return true;
            }
        });
        commandMap.put(android.R.id.home, new Menu_opcion() {
            @Override
            public boolean execute()  {//al retroceder se guardaran los cambios siendo confirmado dichos cambios

                if (getWindow().getCurrentFocus() != findViewById(R.id.focusedLayout)) {
                    createConfirmationDialog("Confirme", "Quiere guardar los cambios?");
                    return true;
                }
                openMainActivity();
                return true;
            }
        });
        commandMap.put(R.id.deleteButton, new Menu_opcion() {
            @Override
            public boolean execute() {
                openMainActivity(ID, extraCommands[1]);
                return true;
            }
        });
        commandMap.put(R.id.CompartirTarea, new Menu_opcion() {
            @Override
            public boolean execute() {
                shareOn();
                return true;
            }
        });
    }

    private void setActionBar() {
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mostrar_actividad_, menu);
        return true;
    }

    //se verifica cual es el elemento del menu presionado y retornamos si presionamos en algo
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return (commandMap.get(item.getItemId()).execute() || super.onOptionsItemSelected(item));
    }


    //registramos todas las vistas de esta actividad
    private void registerComponents() {
        title = (EditText) findViewById(R.id.titleText);
        content = (EditText) findViewById(R.id.contentText);
    }



    //usando un intent para poder compartir
    private void shareOn() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);  //enviando el texto a compartir
            intent.setType("text/plain");

            intent.putExtra(Intent.EXTRA_TEXT, getShareText());
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "No hay applicacion para compartir", Toast.LENGTH_LONG).show();
        }


    }


    //obtenemos el texto que se compartira :)
    private String getShareText() {
        return this.title.getText() + "\n-------\n"  //<-- linea separadora entre titulo de la tarea y el contenido
                + this.content.getText();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //lanzara el evento cuando se presione el boton
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled() && getWindow().getCurrentFocus() != findViewById(R.id.focusedLayout)) {
            createConfirmationDialog("Confirmacion", "Quiere guardar los cambios ?:3 ");
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking() && !event.isCanceled()) {
            openMainActivity();
        }
        return super.onKeyUp(keyCode, event);
    }


    //creado de un cuadro de dialogo y creacion de botones
    public void createConfirmationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MostrarTareasActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity(ID, extraCommands[2]);
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //al abrir la actividad principal se pasa los datos de nuestras tareas y para la activity main
    public void openMainActivity(int id, String cmd) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            Bundle extras = new Bundle();
            intent.putExtra(EXTRA_ID, id); //tareas que vemos
            extras.putString(EXTRA_CONTENT, content.getText().toString());
            extras.putString(EXTRA_TITLE, title.getText().toString());
            extras.putString(EXTRA_COMMAND, cmd); //para la activity main
            intent.putExtras(extras);
            showed = true;
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getBaseContext(), "Problemas con el inicio de la actividad", Toast.LENGTH_LONG).show();
        }

    }



    public void openMainActivity() { //abrimos la actividad mientras pasamos los datos mediante inten
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getBaseContext(), "Problemas al iniciar la actividad ): ", Toast.LENGTH_LONG).show();
        }

    }

    //obteniendo los datos de la actividad principal mostrando el titulo y contenido
    private void showTask() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString(MainActivity.TITLE_EXTRA));
            content.setText(extras.getString(MainActivity.CONTENT_EXTRA));
            ID = extras.getInt(MainActivity.ID_EXTRA);
        }
    }
}

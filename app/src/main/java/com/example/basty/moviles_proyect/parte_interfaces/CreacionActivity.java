package com.example.basty.moviles_proyect.parte_interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.basty.moviles_proyect.R;


 // Activity encargada del creado de nuevas Tareas


public class CreacionActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.basty.moviles_proyect.EXTRA_T";
    public static final String EXTRA_CONTENT = "com.example.basty.moviles_proyect.EXTRA_C";
    public static final String EXTRA_PRIORITY = "com.example.basty.moviles_proyect.EXTRA_P";
    public static boolean create = false;

    private EditText title, content;
    private int priority = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creacion_tareas);
        registerComponents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creacion_menu, menu);
        setActionBar(menu);
        return true;
    }



    public void setActionBar(Menu menu) {
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPopupBackgroundResource(R.color.actionBarColor);
        spinner.setAdapter(adapter);
        getSupportActionBar().setTitle("");
        setSpinnerListener(spinner);
    }



    //metodo que nos ayuda al establecer la prioridad de la actividad
    public void setSpinnerListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPriority(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setPriority(0);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.finishCreation) {
            createTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //buscara todas las vistas usadas en esta actividad
    private void registerComponents() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        content.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }


    public int getPriority() {
        return priority;
    }


    //Titulo de entrada
    public String getTaskTitle() {
        return title.getText().toString();
    }


    //Cuadro de contenido para que escriba el usuario
    public String getContent() {
        return content.getText().toString();
    }


    //crea una nueva tarea y regresa a la actividad principal
    public void createTask() {
        create = true;
        changeActivity(getPriority(), getTaskTitle(), getContent());
    }

    //Establece la prioridad para la tarea que se crea
    private void setPriority(int priority) {
        this.priority = priority;
    }


    //cambia la actividad mientras envia los datos
    public void changeActivity(int priority, String title, String content) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_PRIORITY, priority);
        extras.putString(EXTRA_TITLE, title);
        extras.putString(EXTRA_CONTENT, content);
        intent.putExtras(extras);
        startActivity(intent);
    }

}

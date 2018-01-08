package com.example.basty.moviles_proyect.parte_interfaces;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.basty.moviles_proyect.R;


//se maneja tareas activas y tareas eliminadas solo cambia las actividades
public class MainActivity extends AppCompatActivity implements TareasActivasFragment.OnClickedListener {

    public static final String TITLE_EXTRA = "com.example.basty.moviles_proyect.EXTRA_TITLE";
    public static final String CONTENT_EXTRA = "com.example.basty.moviles_proyect.EXTRA_CONTENT";
    public static final String ID_EXTRA = "com.example.basty.moviles_proyect.EXTRA_ID";

    private ViewPager viewPager;
    private TabLayout tablayout;


    //registrado de activities y adapters
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerComponents();
        registerPagerAdapter();
        setCustomActionBar();
    }



    //boton de regreso o también llamado back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getRepeatCount() == 0) {
                event.startTracking();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


  //si se presiona para salir de la app se pedira una confirmación al usuario
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            createConfirmationDialog("Confirmacion :)", "Quiere salir de la App ): ?");
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //dialogo de confirmación basico y creacion de botones listeners cuando cerremos la app
    public void createConfirmationDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("si ):",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //toma el contexto del archivo xml mostrandolo en la ventana emergente
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menu nuestro contexto, v la vista de los componentes, menuinfo da informacion adicional
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.contextual_menu, menu);
    }



    //al dar presionar "acerca de..." abre la activity de ayuda
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.helpButton) {
            openHelpActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //barra de accion personalizada
    public void setCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(
                    getLayoutInflater().inflate(R.layout.actionbar_layout, null),
                    new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                    )
            );
        }
    }


    //registrando vistas en esta activity
    private void registerComponents() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tablayout = (TabLayout) findViewById(R.id.tabLayout);
        setSupportActionBar((Toolbar)findViewById(R.id.toolBar));
    }



    //inicializar the adaptador para mostrar un fragment
    private void registerPagerAdapter() {
        final String activeTasksFragment = "Tareas Activas";
        final String finishedTasksFragment = "Tareas Finalizadas";
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments( new TareasActivasFragment(), activeTasksFragment);
        pagerAdapter.addFragments(new TareasFinalizadasFragment(), finishedTasksFragment);
        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    //se habre la creacion de la actividad al presionar el boton
    public void openCreationActivity() {
        Intent intent = new Intent(this, CreacionActivity.class);
        startActivity(intent);
    }

    //abre la activitad para obtener informacion de la app
    public void openHelpActivity() {
        Intent intent = new Intent(this, AcerdaDeActivity.class);
        startActivity(intent);
    }



    public void openShowTaskActivity(String title, String content, int id) {
        Intent intent = new Intent(this, MostrarTareasActivity.class);
        Bundle extras = new Bundle();
        extras.putString(TITLE_EXTRA, title);
        extras.putString(CONTENT_EXTRA, content);
        extras.putInt(ID_EXTRA, id);
        intent.putExtras(extras);
        startActivity(intent);
    }


    //al llamar de la activity tareas activas fragmen, cambia la actividad para mostrar los datos de la tarea
    @Override
    public void onTaskClick(String content, String title, int id) {
        openShowTaskActivity(content, title, id);
    }

    @Override
    public void onCreateClick() {
        openCreationActivity();
    }

}






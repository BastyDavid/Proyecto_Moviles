package com.example.basty.moviles_proyect.parte_interfaces;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.basty.moviles_proyect.R;
import com.example.basty.moviles_proyect.Datos.Comandoss;

import java.util.HashMap;
import java.util.Map;



 // Se maneja toda la lógica detrás del fragmento de la tarea
 // Maneja la comunicación entre TareaAdapter (vista de lista)
  //Maneja la comunicación a Tareas

public class TareasActivasFragment extends PageFragment {

    private ListView listView;
    private ImageButton taskButton;
    private Map<String, Comandoss> commandMap;



    private OnClickedListener tCallBack;

    interface OnClickedListener {
        void onTaskClick(String title, String content, int id);
        void onCreateClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tareas_layout, container, false);
        registerComponents(view);
        registerForContextMenu(listView);
        setAdapter();
        createButtonListeners();
        getPassedData();
        final int notFinished = 0;
        fillListView(notFinished);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCommands();


    }

    //crea los comandos a ejecutar, terminar, borrar o actualizar
    public void createCommands() {
        commandMap = new HashMap<>();
        commandMap.put("Terminar", new Comandoss() {
            @Override
            public void execute(int id, String title, String content) {
                finishTask(id, title, content);
            }
        });
        commandMap.put("Borrar", new Comandoss() {
            @Override
            public void execute(int id, String title, String content) {
                delete(id);
            }
        });
        commandMap.put("Actualizar", new Comandoss() {
            @Override
            public void execute(int id, String title, String content) {
                updateTask(id, title, content);
            }
        });
    }


    //registrando listas y botones
    private void registerComponents(View view) {
        setListView(view);
        taskButton = (ImageButton) view.findViewById(R.id.addTaskButton);
    }


    //revisar si la interface esta implementada antes de llamarla
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try { //verifica si la interface esta creada
            tCallBack = (OnClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }



    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try { //si la interface esta implementada
                tCallBack = (OnClickedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString());
            }
        }
    }


    @Override
    public void onDetach() {
        tCallBack = null;
        super.onDetach();
    }


    //si se ha pasado un dato de la activity creacion activity
    //verifica si los datos son validos
    public void getPassedData() {

        getCreationData();
        getUpdateData();
    }


    //registrar los datos que se pasaran a la creacion de actividades
    private void getCreationData() {
        if (CreacionActivity.create) {
            Bundle extras = getActivity().getIntent().getExtras();
            createTask(extras.getInt(CreacionActivity.EXTRA_PRIORITY),
                    extras.getString(CreacionActivity.EXTRA_TITLE),
                    extras.getString(CreacionActivity.EXTRA_CONTENT));
            CreacionActivity.create = false;
        }
    }


    //registran los datos que se pasaran a mostrar la tarea
    private void getUpdateData() {
        if (MostrarTareasActivity.showed) {
            int id = getActivity().getIntent().getIntExtra(MostrarTareasActivity.EXTRA_ID, -1);
            Bundle extras = getActivity().getIntent().getExtras();
            String cmd = extras.getString(MostrarTareasActivity.EXTRA_COMMAND);
            commandMap.get(cmd).execute(id, extras.getString(MostrarTareasActivity.EXTRA_TITLE), extras.getString(MostrarTareasActivity.EXTRA_CONTENT));
            MostrarTareasActivity.showed = false;
        }
    }


    private void finishTask(int extra, String title, String content) {
        getAdapter().finish(getContext(), extra, title, content);
    }


    //actualiza nuestra tarea,
    private void updateTask(int id, String title, String content) {
        getAdapter().updateTask(getContext(), id, title, content);
    }


    @Override
    protected void setListView(View view) {
        listView = (ListView) view.findViewById(R.id.lvTasks);
    }
    //al inicializar la actividad se inicializa el listview de las tareas configurando el listener

    public void setAdapter() {
        listView.setAdapter(getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tCallBack.onTaskClick(
                        getAdapter().find(position).getTitle(),
                        getAdapter().find(position).getContent(),
                        getAdapter().find(position).getID());
            }
        });
    }

    public void createButtonListeners() {
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tCallBack.onCreateClick();
            }
        });
    }


    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    //se pasan del fragment creacion de tareas verifica si los datos creados son correctos
    public void createTask(int priority, String title, String content) {

        if (title.length() < 17 && content.length() < 1000) {
            getAdapter().newTask(getContext(), title, content, priority);
            return;
        }
        Toast.makeText(getContext(), "Creación invalida", Toast.LENGTH_LONG).show();
    }
}

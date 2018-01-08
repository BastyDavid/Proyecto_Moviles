package com.example.basty.moviles_proyect.parte_interfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basty.moviles_proyect.Datos.Tarea;
import com.example.basty.moviles_proyect.R;
import com.example.basty.moviles_proyect.Datos.Tareas;

import java.util.ArrayList;
import java.util.List;



//conexion entre la clases tareas y los fragments que usan el adaptar
public class TareaAdapter extends BaseAdapter {

    private Tareas tasks = new Tareas();
    private int[] priorityImages = {R.drawable.con_calma, R.drawable.atareado, R.drawable.con_mucha_prisa};


    //clase para el manejos de las vistas de las filas
    private static class DataHandler {
        private ImageView priorityImage;
        private TextView title;
        private TextView content;
        private TextView date;
    }


    //nueva tarea con su contexto titulo contenido y prioridad
    public Tarea newTask(Context context, String title, String content, int priority) {
        return tasks.newTask(context, title, content, priority);
    }


    //acualizar la tarea con su contexto titulo contenido y prioridad
    public void updateTask(Context context, int id, String title, String content) {
        tasks.updateTask(context, id, title, content);
    }


    //leemos todas las tareas con el estado que queramos
    public void readAllOfState(Context context, int state) {
        tasks.readAllOfState(state, context);
    }


    //buscamos las tareas por su indice
    public Tarea find(int i) {
        return tasks.find(i);
    }


    public void delete(Context context, int id) {
        tasks.delete(context, id);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return tasks.size();
    }


    @Nullable
    @Override
    public Object getItem(int position) {
        return tasks.find(position);
    }


    @Override
    public long getItemId(int position) {
        return tasks.find(position).getID();
    }


    //terminamos la tarea
    public void finish(Context context, int id, String title, String content) {
        tasks.finish(context, id, title, content);
        notifyDataSetChanged();
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }



    //se muestra como se manejan las vistas
    //position posicion
    //convertView la vista que estamos viendo
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DataHandler handler;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.filas, parent, false);
            handler = new DataHandler();
            getListComponents(handler, row);
            row.setTag(handler);
        } else {
            handler = (DataHandler) row.getTag();
        }
        Tarea task = (Tarea) this.getItem(position);
        setListComponents(handler, task);
        return row;
        //retornamos la fila
    }


    //obtenemos los componentes de una fila para mostrar en la lista
    public void getListComponents(DataHandler handler, View row) //handler mantener las vistas
    {
        handler.priorityImage = (ImageView) row.findViewById(R.id.priorityImage);
        handler.title = (TextView) row.findViewById(R.id.taskTitle);
        handler.content = (TextView) row.findViewById(R.id.taskContent);
        handler.date = (TextView) row.findViewById(R.id.finishDate);
    }

    //obtenemos los datos de las tareas y los mostramos en la fila
    public void setListComponents(DataHandler handler, Tarea task) //taras que sera mostrada en la fila
     {
        handler.title.setText(task.getTitle());
        handler.content.setText(task.getContent());
        handler.priorityImage.setImageResource(priorityImages[task.getPriority()]);
        handler.date.setText(task.getDate());
    }

    public void setMatches(ArrayList<Tarea> taskList) {
        tasks.addAll(taskList);
        notifyDataSetChanged();

    }

    public List<Tarea> getAll() {
        return tasks.getAll();
    }


}

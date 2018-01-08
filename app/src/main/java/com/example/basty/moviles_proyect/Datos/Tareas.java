package com.example.basty.moviles_proyect.Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


//realizamos todas las tareas que se van a crear como iniciadas y finalizadas
public class Tareas {

    private List<Tarea> tasks = new ArrayList<>();
    private BaseDeDatos userDbHelper;
    private SQLiteDatabase sqLiteDatabase;


    //escribimos una nueva tarea en la base de datos
    private void writeDb(Tarea task, Context context) throws SQLException {
        userDbHelper = new BaseDeDatos(context);

        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.addTask(task.getTitle(), task.getContent(), Integer.toString(task.getPriority()),
                Integer.toString(task.getID()), Integer.toString(task.getState()), task.getDate(), sqLiteDatabase);
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Tarea Creada", Toast.LENGTH_LONG).show();
        //creamos la tarea que escribimos en la base de datos
    }

    //leemos todas las tareas de la base de datos que tienen un estado terminado
    //state 0=falso 1= verdadero
    public void readAllOfState(int state, Context context) throws SQLException {
        userDbHelper = new BaseDeDatos(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        Cursor cursor = userDbHelper.getTasksOfState(state, sqLiteDatabase);

        if (cursor.moveToFirst()) {
            do {
                Tarea task = new TareasNoProgramadas(
                        cursor.getString(0),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)));
                task.setID(Integer.parseInt(cursor.getString(3)));
                task.setState(Integer.parseInt(cursor.getString(4)));
                task.setDate(cursor.getString(5));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        close(userDbHelper, sqLiteDatabase);
        //ordenando para tener las tareas de mayor prioridad primero
        Collections.sort(tasks);
    }


    //se actualiza el estado cuando la tarea es finalizada
    private void updateState(Context context, int id, int state, String date) throws SQLException {
        userDbHelper = new BaseDeDatos(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.updateTaskState(Integer.toString(id), state, date, sqLiteDatabase);
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Tarea Finalizada", Toast.LENGTH_LONG).show();
    }


    //actualizamos la tarea obteniendola de la base de datos con base al id y se actualiza
    public void updateTask(Context context, int id, String title, String content) throws SQLException {
        userDbHelper = new BaseDeDatos(context);
        sqLiteDatabase = userDbHelper.getWritableDatabase();
        userDbHelper.updateTask(Integer.toString(id), title, content, sqLiteDatabase);
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Tarea Actulizada", Toast.LENGTH_LONG).show();
    }


    //se borran las tareas en base al id
    public void delete(Context context, int id) throws SQLException {
        userDbHelper = new BaseDeDatos(context);
        sqLiteDatabase = userDbHelper.getReadableDatabase();
        userDbHelper.deleteTask(Integer.toString(id), sqLiteDatabase);
        tasks.remove(findByID(id));
        close(userDbHelper, sqLiteDatabase);
        Toast.makeText(context, "Tarea Borrada", Toast.LENGTH_LONG).show();
    }


    //cerramos la base de datos despues de la lectura o escritura
    private void close(BaseDeDatos helper, SQLiteDatabase db) {
        helper.close();
        db.close();
    }


    public Tarea findByID(int id) {
        for (Tarea task : tasks) {
            if (task.getID() == id)
                return task;
        }
        return null;
    }


    //obtenemos la fecha de hoy
    private String date() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date today = new Date();
        return dateFormat.format(today);
    }

    //se busca una tarea por su indice en la lista
    public Tarea find(int i) { //i es el index
        return tasks.get(i);
    }


    //agregamos las tareas deseadas a las tareas actualizadas
    public void addAll(ArrayList<Tarea> taskList) {
        tasks.clear();
        for (Tarea task: taskList) { //tasklist se agregara a las tareas
            tasks.add(task);
            task.register();
        }
    }


    public List<Tarea> getAll() {
        return tasks;
    }

    public int size() {
        return tasks.size(); //retornamos la cantidad de la tarea
    }


    //terminan las tareas actualizandola primero si ha habido un cambio
    public void finish(Context context, int id, String title, String content) {
        updateTask(context, id, title, content);
        updateState(context, id, 1, date());
    }


    //creamos una nueva tarea y escribimos en la base de datos
    public Tarea newTask(Context context, String title, String content, int priority) {
        Tarea task = new TareasNoProgramadas(title, content, priority);
        task.register();
        writeDb(task, context);
        return task; //retornamos una nueva tarea
    }
}

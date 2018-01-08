package com.example.basty.moviles_proyect.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BaseDeDatos extends SQLiteOpenHelper {

    //clase para mantener guardadas todas las filas

    public static class UserContract {
        static abstract class UserTaskInfo {
            private static final String TITLE = "task_title"; //titulo de la tarea
            private static final String CONTENT = "task_content"; //contenido de la misma
            private static final String PRIORITY = "task_priority"; //prioridad
            private static final String ID = "task_ID"; //el id de la misma
            private static final String STATE = "task_state"; //estado de la tarea (creada o terminada)
            private static final String DATE = "finish_date"; //fecha final
            private static final String TABLE_NAME = "task_info"; //informacion de la tarea
        }

    }
//creacion de la base de datos de las tareas
    private static final String DATABASE_NAME = "TASKS_DB6";
    private static final int DATABASE_VERSION = 6;
    private static final String CREATE_QUERY = "CREATE TABLE " +
            UserContract.UserTaskInfo.TABLE_NAME +
            "(" + UserContract.UserTaskInfo.TITLE + " TEXT," +
            UserContract.UserTaskInfo.CONTENT + " TEXT," +
            UserContract.UserTaskInfo.PRIORITY + " TEXT," +
            UserContract.UserTaskInfo.STATE + " TEXT," +
            UserContract.UserTaskInfo.DATE + " TEXT," +
            UserContract.UserTaskInfo.ID + " TEXT);";


    public BaseDeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    //al crear la base datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE ", "Table created...");
    }

    private String[] getProjections() {
        return new String[]{
                UserContract.UserTaskInfo.TITLE, //titulo
                UserContract.UserTaskInfo.CONTENT, //contenido
                UserContract.UserTaskInfo.PRIORITY, //prioridad
                UserContract.UserTaskInfo.ID,
                UserContract.UserTaskInfo.STATE, //estado
                UserContract.UserTaskInfo.DATE}; //fecha
    }



    //obtenemos una tarea especifica de la base de datos retornando la tarea como un objeto de tipo cursor
    @SuppressWarnings("unused") //para que no haya errores en tiempo de ejecucion
    public Cursor getTask(String ID, SQLiteDatabase db) {
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        return db.query(UserContract.UserTaskInfo.TABLE_NAME, getProjections(), selection, selection_args, null, null, null);
    }



    //obtenemos una tarea por su estado sea finalizado o no terminado
    public Cursor getTasksOfState(int state, SQLiteDatabase db) { //state= condicion de busqueda
        String selection = UserContract.UserTaskInfo.STATE + " LIKE ?";
        String[] selection_args = {Integer.toString(state)};
        return db.query(UserContract.UserTaskInfo.TABLE_NAME, getProjections(), selection, selection_args, null, null, null);
       //retornamos la tareas con el estado
    }

    //agregamos la tarea a la base de datos
    public void addTask(String title, String content, String priority, String id, String state, String date, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.TITLE, title);
        contentValues.put(UserContract.UserTaskInfo.CONTENT, content);
        contentValues.put(UserContract.UserTaskInfo.PRIORITY, priority);
        contentValues.put(UserContract.UserTaskInfo.ID, id);
        contentValues.put(UserContract.UserTaskInfo.STATE, state);
        contentValues.put(UserContract.UserTaskInfo.DATE, date);
        db.insert(UserContract.UserTaskInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE: ", "Row inserted...");
    }

    //borrar las tareas de la base de datos por el ID
    public void deleteTask(String ID, SQLiteDatabase sqLiteDatabase) {
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        sqLiteDatabase.delete(UserContract.UserTaskInfo.TABLE_NAME, selection, selection_args);
    }

    //actualizamos las tareas de la base de datos
    public int updateTask(String ID, String newTitle, String newContent, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.TITLE, newTitle); //nuevo titulo de la tarea
        contentValues.put(UserContract.UserTaskInfo.CONTENT, newContent); //nuevo contenido
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        return sqLiteDatabase.update(UserContract.UserTaskInfo.TABLE_NAME, contentValues, selection, selection_args);
        //filas seran cambiadas
    }


    //al terminar la tarea se actualizara la base de datos, y se le da la fecha a dicha tarea tarea al terminarla
    public int updateTaskState(String ID, int newState, String finishDate, SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserTaskInfo.STATE, Integer.toString(newState));
        contentValues.put(UserContract.UserTaskInfo.DATE, finishDate);
        String selection = UserContract.UserTaskInfo.ID + " LIKE ?";
        String[] selection_args = {ID};
        return sqLiteDatabase.update(UserContract.UserTaskInfo.TABLE_NAME, contentValues, selection, selection_args);
        //reregresamos cuantas filas se actualizaron
    }

    //cuando se actualiza la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("DATABASE UPDATE", "Upgrading database from version" + oldVersion + "to" + newVersion + "which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);

    }
}

package com.example.basty.moviles_proyect.Datos;

import android.support.annotation.NonNull;


public abstract class Tarea implements Comparable<Tarea> {

    private String title, content;
    private String date = "";
    private  int priority; //0-2
    private int ID;
    private boolean done;

    private static int nextN = 1;

    //comparamo la tarea y mostramos el orden de más alta prioridad
    @Override
    public int compareTo(@NonNull Tarea t2) {
        return t2.getPriority() - this.getPriority();
    }


    //Constructor de la tarea
    public Tarea(String title, String content, int priority) {
        this.title = title;
        this.content = content;
        this.priority = setPriority(priority);
        this.done = false;
    }

    //nos aseguramos que la prioridad de la tarea no sea tan alta o baja
    private int setPriority(int priority) {
        int maxPriority = 2;
        int minPriority = 0;
        if (priority > maxPriority)
            return maxPriority;

        return Math.max(minPriority, priority);
    }

    //se registra la tarea dandole un id
    public int register() {
        if (ID != 0)
            return ID;

        this.ID = nextN++;
        return this.ID;

        //retornamos el id de la tarea
    }

    //establece el id y hace que se actualize
    public int setID(int n) {
        this.ID = n;
        if (this.ID >= nextN)
            nextN = this.ID + 1;
        return this.ID;
    }

    //obtenemos el estado y devolvera un valor entero
    public int getState() {
        return isFinished() ? 1 : 0; //operador ternario
        // 0 =falso 1=verdadero
    }

    //establecemos el estado de entero a booleano
    public void setState(int state) {
        if(state == 1) {
            done = true;
            return;
        }
        done = false;
    }

    //obtenemos la fecha de finalizacion de la tarea si es que la tiene
    public String getDate() {
        return this.date;
    }

    //lee la fecha de la base de datos
    public void setDate(String date) {
       this.date = date;
    }

    //obtenemos el id de la tarea actual
    public int getID() {
        return this.ID;
    }


    @Override
    public String toString() {
        return this.title;
    }

    //valida si la tarea esta finalizada
    public boolean isFinished() {
        return done;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public int getPriority() {
        return this.priority;
    }
}

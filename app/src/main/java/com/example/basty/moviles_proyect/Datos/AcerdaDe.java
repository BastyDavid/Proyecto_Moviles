package com.example.basty.moviles_proyect.Datos;



public class AcerdaDe {
//clase destinada para que brinde conocimiento de la app

    private String helpTitle;
    private String helpContent;

    public AcerdaDe(String helpTitle, String helpContent) {
        this.helpTitle = helpTitle;
        this.helpContent = helpContent;
    }

    public String getTitle() {
        return this.helpTitle;
    }
    public String getContent() {
        return this.helpContent;
    }
}

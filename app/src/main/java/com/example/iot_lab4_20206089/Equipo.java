package com.example.iot_lab4_20206089;

public class Equipo {
    private String nombre;
    private int posicion;
    private int victorias;
    private int empates;
    private int derrotas;
    private int golesFavor;
    private int golesContra;
    private int diferenciaGoles;
    private String badge;

    public Equipo(String nombre, int posicion, int victorias, int empates, int derrotas,
                  int golesFavor, int golesContra, int diferenciaGoles, String badge) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.victorias = victorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.diferenciaGoles = diferenciaGoles;
        this.badge = badge;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferenciaGoles() {
        return diferenciaGoles;
    }

    public String getBadge() {
        return badge;
    }
}

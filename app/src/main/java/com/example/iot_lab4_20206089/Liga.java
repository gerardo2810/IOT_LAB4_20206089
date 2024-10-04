package com.example.iot_lab4_20206089;

public class Liga {
    private String nombre;
    private String id;
    private String altName1;
    private String altName2;

    public Liga(String nombre, String id, String altName1, String altName2) {
        this.nombre = nombre;
        this.id = id;
        this.altName1 = altName1;
        this.altName2 = altName2;
    }
    public String getNombre() {
        return nombre;
    }
    public String getId() {
        return id;
    }

    public String getAltName1() {
        return altName1;
    }

    public String getAltName2() {
        return altName2;
    }
}


package com.example.iot_lab4_20206089;


public class Resultado {
    private String nombreCompetencia;
    private String fechaEncuentro;
    private String equipoLocal;
    private String equipoVisitante;
    private String resultado;
    private String logoCompetenciaUrl;
    private int espectadores;

    public Resultado(String nombreCompetencia, String fechaEncuentro, String equipoLocal, String equipoVisitante, String resultado, String logoCompetenciaUrl, int espectadores) {
        this.nombreCompetencia = nombreCompetencia;
        this.fechaEncuentro = fechaEncuentro;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.resultado = resultado;
        this.logoCompetenciaUrl = logoCompetenciaUrl;
        this.espectadores = espectadores;
    }

    public String getNombreCompetencia() {
        return nombreCompetencia;
    }

    public String getFechaEncuentro() {
        return fechaEncuentro;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public String getResultado() {
        return resultado;
    }

    public String getLogoCompetenciaUrl() {
        return logoCompetenciaUrl;
    }

    public int getEspectadores() {
        return espectadores;
    }
}

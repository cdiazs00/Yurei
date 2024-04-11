package com.example.yurei;

import java.util.List;

public class Enemies {
    private String nombre;
    private int PV;
    private List<String> habilidades;

    public Enemies(String nombre, int PV, List<String> habilidades) {
        this.nombre = nombre;
        this.PV = PV;
        this.habilidades = habilidades;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }
}
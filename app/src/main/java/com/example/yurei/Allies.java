package com.example.yurei;

import java.util.List;

public class Allies {
    private String nombre;
    private int PV;
    private int PM;
    private int ATK;
    private int DEF;
    private List<String> habilidades;

    public Allies(String nombre, int PV, int PM, int ATK, int DEF, List<String> habilidades) {
        this.nombre = nombre;
        this.PV = PV;
        this.PM = PM;
        this.ATK = ATK;
        this.DEF = DEF;
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

    public int getPM() {
        return PM;
    }

    public void setPM(int PM) {
        this.PM = PM;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }
}
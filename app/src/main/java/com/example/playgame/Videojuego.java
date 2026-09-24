package com.example.playgame;

import java.util.Date;

public class Videojuego {
    private int id;
    private String titulo;
    private String genero;
    private String desarrollador;
    private Double precio;
    private Date fecha_lanz;
    private String descripcion;

    public Videojuego(){}

    public Videojuego(int id, String titulo, String genero, String desarrollador, Double precio, Date fecha_lanz, String descripcion){
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.desarrollador = desarrollador;
        this.precio = precio;
        this.fecha_lanz = fecha_lanz;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getGenero(){
        return genero;
    }

    public void setGenero(String genero) { this.genero = genero; }

    public String getDesarrollador() { return desarrollador; }

    public void setDesarrollador(String desarrollador) { this.desarrollador = desarrollador;}

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFecha_lanz() {
        return fecha_lanz;
    }

    public void setFecha_lanz(Date fecha_lanz) {
        this.fecha_lanz = fecha_lanz;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

package com.example.proyecto_prueba;

public class Producto {

    Integer id;
    String familia, nombre;
    Float precio;

    public Producto() {
    }

    public Producto(Integer id, String familia, String nombre, Float precio) {
        this.id = id;
        this.familia = familia;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}

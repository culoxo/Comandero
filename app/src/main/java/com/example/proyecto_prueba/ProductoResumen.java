package com.example.proyecto_prueba;

public class ProductoResumen {

    Integer idComanda, idMesa, idProducto, cantidad;
    Float precio;
String nombreDelProducto;
    public ProductoResumen() {

    }

    public ProductoResumen(Integer idComanda,Integer  idMesa, Integer idProducto,String nombreDelProducto, Integer cantidad, Float precio) {
        this.idComanda = idComanda;
        this.idMesa = idMesa;
        this.idProducto = idProducto;
        this.nombreDelProducto = nombreDelProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Integer getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Integer idComanda) {
        this.idComanda = idComanda;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreDelProducto() {
        return nombreDelProducto;
    }

    public void setNombreDelProducto(String nombreDelProducto) {
        this.nombreDelProducto = nombreDelProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}

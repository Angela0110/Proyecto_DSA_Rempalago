package edu.upc.dsa.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Tienda {
    int id;
    static AtomicInteger nextId = new AtomicInteger();
    int precio;
    String nombre;
    int efect_type;         // Tipo de efecto que ejerce
    // Incremento de la vida "0"
    // Incremento del daño "1"
    // Incremento de la velocidad "2"
    // Invisibilidad "3"
    int efect;              // Si el efecto es de tipo invisibilidad siempre será 1
    String description;     // Pequeña descripción del producto

    // Constructores
    public Tienda(){this.id = nextId.incrementAndGet();}
    public Tienda(int precio, String nombre, String description, int efect_type, int efect){
        this();
        this.id = nextId.incrementAndGet();
        this.nombre = nombre;
        this.description = description;
        this.precio = precio;
        this.efect_type = efect_type;
        this.efect = efect;
    }
    public int getId(){return this.id;}
    public int getPrecio(){return this.precio;}
    public void setPrecio(int precio){this.precio = precio;}
    public int getEfectType(){return this.efect_type;}
    public void setEfectType(int efect_type){this.efect_type = efect_type;}
    public int getEfect(){return this.efect;}
    public void setEfect(int efect){this.efect = efect;}
    public String getNombre(){return this.nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}
}

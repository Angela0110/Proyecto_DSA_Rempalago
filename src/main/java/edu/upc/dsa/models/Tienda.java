package edu.upc.dsa.models;

public class Tienda {
    int precio;
    String nombre;
    int type;         // Tipo de efecto que ejerce
    // Incremento de la vida "0"
    // Incremento del daño "1"
    // Incremento de la velocidad "2"
    // Invisibilidad "3"
    int efect;              // Si el efecto es de tipo invisibilidad siempre será 1
    String description;     // Pequeña descripción del producto
    String imagen;


    // Constructores
    public Tienda(){

    }
    public Tienda(int precio, String nombre, String description, int type, int efect, String imagen){
        this.nombre = nombre;
        this.description = description;
        this.precio = precio;
        this.type = type;
        this.efect = efect;
        this.imagen = imagen;
    }
    public int getPrecio(){return this.precio;}
    public void setPrecio(int precio){this.precio = precio;}
    public int getType(){
        return this.type;}

    public void setType(int type) {
        this.type = type;
    }

    public int getEfect(){
        return this.efect;}
    public void setEfect(int efect){this.efect = efect;}
    public String getNombre(){return this.nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

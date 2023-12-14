package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Avatar {
    String jugador;
    String nombre;
    int idArma;
    int health;
    int damg;
    int visible;    // Visible "0" Invisible "1"
    int speed;
   // String jugador;

    // Constructores
    public Avatar(){}
    public Avatar(String jugador, String nombre, int idArma, int health, int damg, int speed){
        this();
        this.jugador = jugador;
        this.nombre = nombre;
        this.idArma = idArma;
        this.health = health;
        this.damg = damg;
        this.speed = speed;
        this.visible = 0;
    }

    public String getJugador() {return jugador;}

    public void setJugador(String jugador) {this.jugador = jugador;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getNombre(){return this.nombre;}
    //public String getJugador(){return this.jugador;}
    //public void setJugador(String jugador){this.jugador = jugador;}
    // Setters y Getters
    public int getIdArma(){return this.idArma;}
    public void setIdArma(int idArma){this.idArma = idArma;}
    public int getHealth(){return this.health;}
    public void setHealth(int life){this.health = life;}
    public int getDamg(){return this.damg;}
    public void setDamg(int damg){this.damg = damg;}
    public int getSpeed(){return this.speed;}
    public void setSpeed(int speed){this.speed = speed;}
    public int getVisible(){return this.visible;}
    public void setVisible(int visible){this.visible = visible;}
}
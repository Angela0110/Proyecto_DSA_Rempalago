package edu.upc.dsa.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Avatar {
    int id;
    static AtomicInteger nextId = new AtomicInteger();
    int idArma;
    int life;
    int damg;
    int visible;    // Visible "0" Invisible "1"
    int speed;

    // Constructores
    public Avatar(){this.id = nextId.incrementAndGet();}
    public Avatar(int idArma, int life, int damg, int speed){
        this();
        this.id = nextId.incrementAndGet();
        this.idArma = idArma;
        this.life = life;
        this.damg = damg;
        this.speed = speed;
        this.visible = 0;
    }
    // Setters y Getters
    public int getId(){return this.id;}
    public int getIdArma(){return this.idArma;}
    public void setIdArma(int idArma){this.idArma = idArma;}
    public int getLife(){return this.life;}
    public void setLife(int life){this.life = life;}
    public int getDamg(){return this.damg;}
    public void SetDamg(int damg){this.damg = damg;}
    public int getSpeed(){return this.speed;}
    public void setSpeed(int speed){this.speed = speed;}
    public int getVisible(){return this.visible;}
    public void setVisible(int visible){this.visible = visible;}
}


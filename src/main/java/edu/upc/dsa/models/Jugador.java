package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Jugador {
    String id;
    String username;
    String pasword;
    String mail;
    int points;   // Puntos en partida

    private Avatar avatarActual;

    // Constructores
    public Jugador(String username, String mail, String pasword){

        this.username = username;
        this.mail = mail;
        this.pasword = pasword;
        this.points = 100;
    }

    public Jugador(){this.id = RandomUtils.getId();}

    // Setters y Getters
    public String getUserId(){return this.id;}
    public String getUserName(){return this.username;}
    public void setUserName(String username){this.username = username;}
    public String getPasword(){return this.pasword;}
    public void setPasword(String pasword){this.pasword = pasword;}
    public String getMail(){return this.mail;}
    public void SetMail(String mail){this.mail = mail;}
    public int getPoints(){return this.points;}
    public void setPoints(int points){this.points = this.points + points;}
    public Avatar getAvatarActual(){return avatarActual;}

    //mirar si son necesarios cambios en otros sitios////
    public void setAvatarActual(Avatar avatarActual) {
        this.avatarActual = avatarActual;
    }
}

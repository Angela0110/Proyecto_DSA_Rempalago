package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Jugador {
    String username;
    String password;
    String mail;
    int points;   // Puntos en partida

    private Avatar avatar;

    // Constructores
    public Jugador(String username, String mail, String password){
        this.setUsername(username);
        this.setMail(mail);
        this.setPassword(password);
        this.setPoints(100);
    }

    public Jugador(){

    }

    // Setters y Getters
    public String getUsername(){return this.username;}
    public void setUsername(String username){this.username = username;}
    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}
    public String getMail(){return this.mail;}
    public void setMail(String mail){this.mail = mail;}
    public int getPoints(){return this.points;}
    public void setPoints(int points){this.points = this.points + points;}

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}

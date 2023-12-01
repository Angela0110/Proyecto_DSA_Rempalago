package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Jugador {
    String username;
    String password;
    String mail;
    int points;   // Puntos en partida, sirven para subir el nivel de los avatares
    int eurillos;   // Divisa del juego

    private Avatar avatar;

    // Constructores
    public Jugador(String username, String mail, String password){
        this.setUsername(username);
        this.setMail(mail);
        this.setPassword(password);
        this.setPoints(100);
        this.eurillos = 50;
    }

    public Jugador(){}

    // Setters y Getters
    public String getUsername(){return this.username;}
    public void setUsername(String username){this.username = username;}
    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}
    public String getMail(){return this.mail;}
    public void setMail(String mail){this.mail = mail;}
    public int getPoints(){return this.points;}
    public void setPoints(int points){this.points = this.points + points;}
    public int getEurillos(){return this.eurillos;}
    public void setEurillos(int e){this.eurillos = e;}

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}

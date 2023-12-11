package edu.upc.dsa.models;

import edu.upc.dsa.util.Fecha;
import edu.upc.dsa.util.RandomUtils;
import java.util.Date;

public class Partida {
    String id;
    int dif;    // Nivel de dificultad de la partida
    // afectará a la intensidad de ataque de los enemigos
    // 3 niveles, Fácil "0" Normal "1"  y Difícil "2"
    int nivl;   // Nivel de la partida en el que se encuentra el jugador
    String player;   // Identificador del jugador que está jugando la partida
    String idMapa;     // Identificador del mapa que se está jugando
    int puntos;
    String fecha;

    // Constructores
    public Partida(){this.id = RandomUtils.getId(); this.idMapa = "";}
    public Partida(int dif, String player, String idMapa){
        this();
        this.id = RandomUtils.getId();
        this.dif = dif;
        this.player = player;
        this.idMapa = idMapa;
        this.nivl = 1;   // Siempre se empieza por el nivel 1
        this.puntos = 0;
        this.fecha = Fecha.getFecha();
    }

    // Setters y Getters

    public String getId(){return this.id;}
    public String getPlayer(){return this.player;}
    public void setPlayer(String player){this.player = player;}
    public String getIdMapa(){return this.idMapa;}
    public void setIdMapa(String idMapa){this.idMapa = idMapa;}
    public int getNivl(){return this.nivl;}
    public void setNivl(int nivel){this.nivl = nivel;}
    public int getDif(){return this.dif;}
    public void setDif(int dif) {
        this.dif = dif;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }
}
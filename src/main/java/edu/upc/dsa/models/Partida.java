package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;
import java.util.Date;

public class Partida {
    String id;
    int dif;    // Nivel de dificultad de la partida
    // afectará a la intensidad de ataque de los enemigos
    // 4 niveles, Fácil "0" Normal "1" Difícil "2" y Muy difícil "3"
    int nivl;   // Nivel de la partida en el que se encuentra el jugador
    String idPlayer;   // Identificador del jugador que está jugando la partida
    String idMapa;     // Identificador del mapa que se está jugando
    int puntos;
    Date fecha;

    // Constructores
    public Partida(){this.id = RandomUtils.getId(); this.idMapa = "";}
    public Partida(int dif, String idPlayer, String idMapa){
        this();
        this.id = RandomUtils.getId();
        this.dif = dif;
        this.idPlayer = idPlayer;
        this.idMapa = idMapa;
        this.nivl = 1;   // Siempre se empieza por el nivel 1
        this.puntos = 0;
        this.fecha = new Date();
    }

    // Setters y Getters

    public String getPartidaId(){return this.id;}
    public String getPlayerId(){return this.idPlayer;}
    public void setPlayerId(String idPlayer){this.idPlayer = idPlayer;}
    public String getMapId(){return this.idMapa;}
    public void setMapaId(String idMapa){this.idMapa = idMapa;}
    public int getNivel(){return this.nivl;}
    public void setNivel(int nivel){this.nivl = nivel;}
    public int getDificultad(){return this.dif;}
    public void setDificultad(int dif) {
        this.dif = dif;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }
}
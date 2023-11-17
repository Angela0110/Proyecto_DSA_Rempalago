package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class Partida {
    String id;
    int dif;    // Nivel de dificultad de la partida
    // afectará a la intensidad de ataque de los enemigos
    // 4 niveles, Fácil "0" Normal "1" Difícil "2" y Muy difícil "3"
    int nivl;   // Nivel de la partida en el que se encuentra el jugador
    String idPlayer;   // Identificador del jugador que está jugando la partida
    int idMapa;     // Identificador del mapa que se está jugando

    // Constructores
    public Partida(){this.id = RandomUtils.getId(); this.idMapa = -1;}
    public Partida(int dif, String idPlayer, int idMapa){
        this();
        this.id = RandomUtils.getId();
        this.dif = dif;
        this.idPlayer = idPlayer;
        this.idMapa = idMapa;
        this.nivl = 1;                  // Siempre se empieza por el nivel 1
    }

    // Setters y Getters

    public String getPartidaId(){return this.id;}
    public String getPlayerId(){return this.idPlayer;}
    public void setPlayerId(String idPlayer){this.idPlayer = idPlayer;}
    public int getMapId(){return this.idMapa;}
    public void setMapaId(int idMapa){this.idMapa = idMapa;}
    public int getNivel(){return this.nivl;}
    public void setNivel(int nivel){this.nivl = nivel;}
    public int getDificultad(){return this.dif;}
    public void setDificultad(int dif){this.dif = dif;}
}
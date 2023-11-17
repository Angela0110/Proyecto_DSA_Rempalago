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
    String idMapa;     // Identificador del mapa que se está jugando

    // Constructores
    public Partida(){this.id = RandomUtils.getId();  this.idMapa = "";}
    public Partida(int dif, String idPlayer, String idMapa){
        this();
        this.dif = dif;
        this.idPlayer = idPlayer;
        this.idMapa = idMapa;
        this.nivl = 1;                  // Siempre se empieza por el nivel 1
    }

    // Setters y Getters

    public String GetPartidaId(){return this.id;}
    public String GetPlayerId(){return this.idPlayer;}
    public void SetPlayerId(String idPlayer){this.idPlayer = idPlayer;}
    public String GetMapId(){return this.idMapa;}
    public void SetMapaId(String idMapa){this.idMapa = idMapa;}
    public int GetNivel(){return this.nivl;}
    public void SetNivel(int nivel){this.nivl = nivel;}
    public int GetDificultad(){return this.dif;}
    public void SetDificultad(int dif){this.dif = dif;}
}
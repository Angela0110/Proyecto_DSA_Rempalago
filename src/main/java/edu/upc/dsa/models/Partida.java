package edu.upc.dsa.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Partida {
    int id;
    AtomicInteger nextID = new AtomicInteger();
    int dif;    // Nivel de dificultad de la partida
    // afectará a la intensidad de ataque de los enemigos
    // 4 niveles, Fácil "0" Normal "1" Difícil "2" y Muy difícil "3"
    int nivl;   // Nivel de la partida en el que se encuentra el jugador
    int idPlayer;   // Identificador del jugador que está jugando la partida
    int idMapa;     // Identificador del mapa que se está jugando

    // Constructores
    public Partida(){this.id = nextID.incrementAndGet(); this.idMapa = -1;}
    public Partida(int dif, int idPlayer, int idMapa){
        this();
        this.id = nextID.incrementAndGet();
        this.dif = dif;
        this.idPlayer = idPlayer;
        this.idMapa = idMapa;
        this.nivl = 1;                  // Siempre se empieza por el nivel 1
    }

    // Setters y Getters

    public int GetPartidaId(){return this.id;}
    public int GetPlayerId(){return this.idPlayer;}
    public void SetPlayerId(int idPlayer){this.idPlayer = idPlayer;}
    public int GetMapId(){return this.idMapa;}
    public void SetMapaId(int idMapa){this.idMapa = idMapa;}
    public int GetNivel(){return this.nivl;}
    public void SetNivel(int nivel){this.nivl = nivel;}
    public int GetDificultad(){return this.dif;}
    public void SetDificultad(int dif){this.dif = dif;}
}
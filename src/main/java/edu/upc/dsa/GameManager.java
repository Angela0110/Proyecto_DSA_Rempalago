package edu.upc.dsa;


import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Tienda;

import java.util.List;

public interface GameManager {

    // Jugador manager
    public Jugador addJugador(String username, String mail, String pasword) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException;
    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException;
    public Jugador getJugador(String id) throws UserNotFoundException;
    // public int getPoints(int id);
    public List<Jugador> findAllJugadores();
    public int JugadoresSize();
    public void logJugador(String username, String password) throws FaltanDatosException, UserNotFoundException;
  /*  public Partida pasarDeNivel(int puntosConseguidos, String id) throws UserNoEnPartidaException, UserNotFoundException;
    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) throws JuegoNotFoundException, UserNotFoundException, UserEnPartidaException;
    public Partida consultarNivelActual(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException;
    public List<Partida> consultarUsuariosPorPuntuacion(String idJuego) throws JuegoNotFoundException;
    public String consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException;
    public String FinalizarPartida(String id) throws UserNotFoundException, UserNoEnPartidaException;
    public String stringToJSON(String args);
    public Juego addJuego(Juego j);
    public Juego addJuego(String id, String descripcion, int niveles) throws NoNivelException, JuegoYaExisteException;
    public int size();
    public List<Partida> consultarPartidas(String id) throws UserNotFoundException;
    public Jugador addJugador(Jugador jugador);
    public Jugador addJugador();*/

    // Tienda manager
    public Tienda addProducto(Tienda producto) throws ProductoYaExisteException, FaltanDatosException;
    public Tienda addProducto(int precio, String nombre, String description, int efect_type, int efect) throws ProductoYaExisteException, FaltanDatosException;
    public Tienda getProducto(String id) throws ProductoNotFoundException;
    public int TiendasSize();
    public List<Tienda> findAllProductos();
}




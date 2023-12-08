package edu.upc.dsa;


import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.CredencialesRespuesta;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Tienda;
import edu.upc.dsa.models.Partida;


import java.util.List;

public interface GameManager {

    // Jugador manager
    public Jugador addJugador(String username, String mail, String pasword) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException;
    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException;
    public Jugador getJugador(String id) throws UserNotFoundException;
    public List<Jugador> findAllJugadores();
    public int JugadoresSize();
    public CredencialesRespuesta updateUsername(String username, String newUsername, String password) throws ErrorCredencialesException;
    public CredencialesRespuesta updatePassword(String user, String newPass, String password) throws ErrorCredencialesException;
    public CredencialesRespuesta deleteUser(String username) throws UserNotFoundException;
    public CredencialesRespuesta logJugador(String username, String password) throws FaltanDatosException, ErrorCredencialesException;

//    public int consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException;
//    public String FinalizarPartida(String id) throws UserNotFoundException, UserNoEnPartidaException;
//    public String stringToJSON(String args);
//    public Juego addJuego(Juego j);
//    public Juego addJuego(String id, String descripcion, int niveles) throws NoNivelException, JuegoYaExisteException;
//    public int size();

    // Partida Manager
    public List<Partida> consultarPartidas(String id) throws UserNotFoundException;
//    public Partida pasarDeNivel(int puntosConseguidos, String id) throws UserNoEnPartidaException, UserNotFoundException;
//    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) throws JuegoNotFoundException, UserNotFoundException, UserEnPartidaException;
//    public Partida consultarNivelActual(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException;
//    public List<Partida> consultarUsuariosPorPuntuacion(String idJuego) throws JuegoNotFoundException;

    // Tienda Manager
    public Tienda addProducto(Tienda producto) throws ProductoYaExisteException, FaltanDatosException;
    public Tienda addProducto(int precio, String nombre, String description, int efect_type, int efect) throws ProductoYaExisteException, FaltanDatosException;
    public Tienda getProducto(String id) throws ProductoNotFoundException;
    public void comprarProducto(String nombre, String usrnm) throws ProductoNotFoundException, CapitalInsuficienteException, UserNotFoundException;
    public List<Tienda> deleteProducto(Tienda producto) throws ProductoNotFoundException, FaltanDatosException;
    public int TiendasSize();
    public List<Tienda> findAllProductos();
    public void increaseDamage(String jugadorUsername, int damage);
    public void increaseHealth(String jugadorUsername, int health);
    public void increaseSpeed(String jugadorUsername, int speed);
    public void invisibility(String jugadorUsername);
}
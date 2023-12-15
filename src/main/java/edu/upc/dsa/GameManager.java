package edu.upc.dsa;


import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;


import java.sql.SQLException;
import java.util.List;

public interface GameManager {

    // Jugador manager
    public Jugador addJugador(String username, String mail, String pasword) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException;
    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException;
    public Jugador getJugador(String id) throws UserNotFoundException;
    public List<Jugador> findAllJugadores();
    public int JugadoresSize();
    public CredencialesRespuesta updateUsername(String username, String newUsername, String password) throws ErrorCredencialesException, JugadorYaExisteException, FaltanDatosException, UserNotFoundException, AvatarNotFound;
    public CredencialesRespuesta updatePassword(String user, String newPass, String password) throws ErrorCredencialesException, FaltanDatosException, UserNotFoundException;
    public void updateJugador(String columna, String user, String newValue);
    public CredencialesRespuesta deleteUser(String username) throws UserNotFoundException, FaltanDatosException;
    public CredencialesRespuesta logJugador(String username, String password) throws FaltanDatosException, ErrorCredencialesException;

    // Avatar Manager

    public Avatar addAvatar(String username, String nombre, int idArma, int health, int damg, int speed) throws AvatarYaExisteException, FaltanDatosException;
    public Avatar addAvatar(Avatar avatar) throws AvatarYaExisteException, FaltanDatosException;
    public Avatar getAvatar(String player, String nombreAvatar) throws AvatarNotFound;
    public void updateAvatar(String newJugadorUsername, String user) throws UserNotFoundException, AvatarNotFound;
    public List<Avatar> findAllAvatares();
    public int AvataresSize();
//    public int consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException;
//    public String FinalizarPartida(String id) throws UserNotFoundException, UserNoEnPartidaException;
//    public String stringToJSON(String args);
//    public Juego addJuego(Juego j);
//    public Juego addJuego(String id, String descripcion, int niveles) throws NoNivelException, JuegoYaExisteException;
//    public int size();

    // Partida Manager
    public Partida addPartida(Partida partida) throws FaltanDatosException, UserNotFoundException;
    public Partida addPartida(int dif, String player, String idMapa) throws FaltanDatosException, UserNotFoundException;
    public List<Partida> consultarPartidas(String username) throws UserNotFoundException;
    public int PartidaSize();
    //public int cambiarDificultad(String player, int newdif) throws PartidaNotFoundException, MismaDificultadException, FaltanDatosException, UserNotFoundException;


//    public Partida pasarDeNivel(int puntosConseguidos, String id) throws UserNoEnPartidaException, UserNotFoundException;
//    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) throws JuegoNotFoundException, UserNotFoundException, UserEnPartidaException;
//    public Partida consultarNivelActual(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException;
//    public List<Partida> consultarUsuariosPorPuntuacion(String idJuego) throws JuegoNotFoundException;

    // Tienda Manager
    public Tienda addProducto(Tienda producto) throws ProductoYaExisteException, FaltanDatosException;
    public Tienda addProducto(String imagen, int precio, String nombre, String description, int efect_type, int efect) throws ProductoYaExisteException, FaltanDatosException;
    public Tienda getProducto(String id) throws ProductoNotFoundException;
    public void comprarProducto(String nombre, String usrnm) throws ProductoNotFoundException, CapitalInsuficienteException, UserNotFoundException, FaltanDatosException, SQLException, AvatarNotFound;
    public List<Tienda> deleteProducto(String nombre) throws ProductoNotFoundException, FaltanDatosException;
    public int TiendasSize();
    public List<Tienda> findAllProductos();
    public void increaseDamage(String jugadorUsername, int damage) throws FaltanDatosException, UserNotFoundException, SQLException, AvatarNotFound;
    public void increaseHealth(String jugadorUsername, int health) throws FaltanDatosException, UserNotFoundException, AvatarNotFound;
    public void increaseSpeed(String jugadorUsername, int speed) throws FaltanDatosException, AvatarNotFound;
    public void invisibility(String jugadorUsername) throws UserNotFoundException, FaltanDatosException, AvatarNotFound;
}
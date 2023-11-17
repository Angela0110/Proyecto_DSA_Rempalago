package edu.upc.dsa;

import java.util.*;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import edu.upc.dsa.util.Verificar;


public class GameManagerImpl implements GameManager {

    private static GameManager instance;
    protected List<Partida> Partidas;
    protected Map<String, Jugador> Jugadores;
    protected List<Partida> Jugadas;
    protected List<Avatar> Avatares;
    protected List<Mapa> Mapas;
    protected List<Tienda> Productos;
    protected List<Credenciales> Credenciales;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    private GameManagerImpl() {
        this.Partidas = new LinkedList<>();
        this.Jugadores = new HashMap<>();
        this.Avatares = new LinkedList<>();
        this.Mapas = new LinkedList<>();
        this.Productos = new LinkedList<>();
        this.Credenciales = new LinkedList<>();

    }

    public static GameManager getInstance() {
        if (instance==null)
            instance = new GameManagerImpl();
        return instance;
    }

    public int PartidaSize() {      // Número de partidas creadas
        int ret = this.Partidas.size();
        logger.info("Game size " + ret);
        return ret;
    }

    public int JuegosSize() {       // Número de partidas jugadas
        int ret = this.Jugadas.size();
        logger.info("Se ha jugado a " + ret + "Juegos");
        return ret;
    }

    public int JugadoresSize() {    // Número de jugadores
        int ret = this.Jugadores.size();
        logger.info("Jugadores size " + ret);
        return ret;
    }

    public Partida addPartida(Partida p) {
        logger.info("Nueva partida " + p);

        this.Partidas.add (p);
        logger.info("Nueva partida añadida");
        return p;
    }
    public Partida addPartida(int dif, String idPlayer, int idMapa) { return this.addPartida(new Partida(dif, idPlayer, idMapa)); }

    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException {
        logger.info("new Jugador " + jugador.getUserName());
        if (jugador.getMail() == null  || jugador.getUserName() == null || jugador.getPasword() == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        for (Jugador j : this.findAllJugadores()){
            if (j.getMail().equals(jugador.getMail()) || j.getUserName().equals(jugador.getUserName())){
                logger.info("Ese jugador ya existe (el email y el usuario tienen que ser únicos)");
                throw new JugadorYaExisteException();
            }
        }
        if (Verificar.esDireccionCorreoValida(jugador.getMail()) == false){
            logger.info("No es un email");
            throw new NotAnEmailException();
        }
        else{
            this.Jugadores.put(jugador.getUserId(), jugador);
            Credenciales c = new Credenciales(jugador.getUserName(), jugador.getPasword());
            this.Credenciales.add(c);
            logger.info("credenciales: " + c.getUsername() + " " + c.getContraseña());
            logger.info("new Jugador added");
            return jugador;
        }
    }

    public Jugador addJugador(String username, String mail, String pasword) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException { return this.addJugador(new Jugador(username, mail, pasword)); }

    public void logJugador(String username, String password) throws FaltanDatosException, UserNotFoundException, WrongPasswordException {
        if(username == null || password == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        for (Jugador j : this.findAllJugadores()){
            if (j.getUserName().equals(username)){
                if (j.getPasword().equals(password)) {
                    logger.info("Login del jugador " + username);
                    return;
                }
                else{
                    throw new WrongPasswordException();
                }
            }
        }
        logger.info("Usuario o contraseña errónea");
        throw new UserNotFoundException();
    }

    public List<Jugador> findAllJugadores(){

        List<Jugador> lista = new ArrayList<Jugador>(Jugadores.values());
        return lista;
    }

    public void updateUsername(String id, String nuevoUser, String password) throws UserNotFoundException, WrongPasswordException {
        Jugador j = Jugadores.get(id);
        logger.info("El usuario " + j.getUserName() +" quiere cambiar su nombre");
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPasword().equals(password)){
                j.setUserName(nuevoUser);
                logger.info("El usuario cambió su username a  "+ nuevoUser);
                return;
            }
            else{
                throw new WrongPasswordException();
            }
        }
    }

    public void updatePassword(String id, String nuevoPass, String password) throws UserNotFoundException, WrongPasswordException {
        Jugador j = Jugadores.get(id);
        logger.info("El usuario " + j.getUserName() +" quiere cambiar su contraseña");
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPasword().equals(password)){
                j.setPasword(nuevoPass);
                logger.info("El usuario cambió su contraseña");
                return;
            }
            else{
                throw new WrongPasswordException();
            }
        }
    }

    public void deleteUser(String id, String password) throws UserNotFoundException, WrongPasswordException {
        Jugador j = Jugadores.get(id);
        logger.info("El usuario " + j.getUserName() +" borrar su perfil");
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPasword().equals(password)){
                this.Jugadores.remove(id);
                logger.info("El usuario cambió su contraseña");
                return;
            }
            else{
                throw new WrongPasswordException();
            }
        }
    }

    public int consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException {
        logger.info("El jugador con id " + identificadorUsuario + " quiere consultar su puntuación");
        Jugador j = Jugadores.get(identificadorUsuario);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            int puntos = j.getPoints();
            logger.info("El usuario tiene "+ puntos + " puntos");
            return puntos;
        }
    }



    public List<Partida> consultarPartidas(String id) throws UserNotFoundException {
        logger.info("El usuario " + id + " quiere consultar una lista con sus partidas");
        List<Partida> par = new LinkedList<Partida>();
        Jugador j = Jugadores.get(id);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            for (Partida p:Partidas){
                if (p.getPlayerId().equals(id)){
                    par.add(p);
                }
            }
            if (par.isEmpty()) {
                logger.info("El usuario aun no jugó ninguna partida");
                return Collections.emptyList();
            }
            else {
                return par;
            }
        }
    }



    // Que pasa si yo ya tengo una partida guardada de antes??
    // Cómo se tendría que inciar?
//    public void startPartida(Partida p, Jugador j, Mapa m, Avatar a) {        // El jugador inicicia una partida
//        if(p.GetMapId() == -1){     // Un id negativo implica que la partida es nueva
//            logger.info(j.getUserName() + " inicia partida en el nivel 1");
//            p.SetNivel(1);
//            p.SetMapaId(m.getId());
//            j.setPoints(50);
//            this.Mapas.add(m);
//            this.Jugadores.add(j);
//        }
//        /* else{
//            logger.info(j.GetUserName() + " inicia partida en el nivel" + p.GetNivel());
//            p.SetNivel(1);
//            p.SetMapaId(m.GetId());
//            j.SetPoints(50);
//            this.Mapas.add(m);
//            this.Jugadores.add(j);
//        } */
//
//    }

    public Jugador getJugador(String id) throws UserNotFoundException {
        logger.info("getUser("+id+")");
        Jugador j = Jugadores.get(id);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            logger.info("getUser("+id+"): "+ j);
            return j;
        }
    }


  /*  private static GameManager instance;
    protected List<Partida> partidas;
    protected Map<String, Jugador> jugadores;
    protected Map<String, Juego> juegos;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.partidas = new LinkedList<>();
        this.jugadores = new HashMap<>();
        this.juegos = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManagerImpl();
        }
        return instance;
    }

    public Partida iniciarPartida(String identificadorJuego, String identificadorUsuario) throws JuegoNotFoundException, UserNotFoundException, UserEnPartidaException {
        logger.info("El usuario " + identificadorUsuario + " quiere iniciar una partida del juego " + identificadorJuego);
        Jugador jugador = jugadores.get(identificadorUsuario);
        if (jugador != null) {
            if (jugador.getPartida() == null) {
                Juego juego = juegos.get(identificadorJuego);
                if (juego != null) {
                    Partida partida = new Partida(juego, identificadorUsuario);
                    jugador.setPartida(partida);
                    partidas.add(partida);
                    logger.info("Partida " + partida + " está iniciada");
                    logger.info("El jugador " + identificadorUsuario + " empieza el juego " + juego.getId());
                    return partida;
                } else {
                    logger.info("El juego no existe");
                    throw new JuegoNotFoundException();
                }
            }
            else {
                logger.info("El usuario ya tiene una partida activa");
                throw new UserEnPartidaException();
            }
        } else {
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
    }

    public Partida pasarDeNivel(int puntosConseguidos, String id) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info("Los puntos conseguidos por el jugador " + id + " en el nivel son " + puntosConseguidos);
        Date fecha = new Date();
        Jugador jugador = jugadores.get(id);
        if (jugador == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (jugador.getPartida() == null){
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
            else{
                Partida p = jugador.getPartida();
                if (Integer.parseInt(p.getNivelActual()) < p.getJuego().getNivel()) {
                    int nivel = Integer.parseInt(p.getNivelActual()) + 1;
                    p.setNivelActual(String.valueOf(nivel));
                    p.setPuntosAcumulados(p.getPuntosAcumulados() + puntosConseguidos);
                    p.setFechaCambioNivel(fecha);
                    logger.info("Pasa al nivel " + p.getNivelActual() + " el día " + fecha + " y tiene en total " + p.getPuntosAcumulados() + " puntos");
                    return p;
                } else {
                    p.setPuntosAcumulados(p.getPuntosAcumulados() + puntosConseguidos + 100);
                    p.setNivelActual("Todos los niveles superados");
                    p.setFechaCambioNivel(fecha);
                    jugador.setPartida(null);
                    logger.info("Acabó el último nivel el " + fecha + " con " + p.getPuntosAcumulados() + " puntos");
                    return p;
                }
            }
        }
    }

    public Partida consultarNivelActual(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info(" El jugador " + identificadorUsuario + " quiere consultar su nivel actual");
        Jugador j = jugadores.get(identificadorUsuario);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else {
            if (j.getPartida() != null) {
                Partida partida = j.getPartida();
                String nivel = partida.getNivelActual();
                logger.info("El jugador está en el nivel " + nivel + " de la partida " + partida);
                return partida;
            }
            else{
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
        }
    }

    public String consultarPuntuacion(String identificadorUsuario) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info("El jugador con id " + identificadorUsuario + " quiere consultar su puntuación");
        Jugador j = jugadores.get(identificadorUsuario);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPartida() != null) {
                Partida partida = j.getPartida();
                int puntos = partida.getPuntosAcumulados();
                logger.info("El usuario tiene "+ puntos + " puntos");
                return "El usuario tiene "+ puntos + " puntos";
            }
            else{
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
        }
    }

    public String FinalizarPartida(String id) throws UserNotFoundException, UserNoEnPartidaException {
        logger.info("El jugador con id = " + id + " quiere acabar la partida");
        Jugador j = jugadores.get(id);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPartida() != null) {
                j.setPartida(null);
                logger.info("El usuario con id " + id + " ha finalizado la partida actual");
                return "El usuario con id " + id + " ha finalizado la partida actual";
            }
            else{
                logger.info("El usuario no tiene una partida en curso");
                throw new UserNoEnPartidaException();
            }
        }
    }

    public List<Partida> consultarPartidas(String id) throws UserNotFoundException {
        logger.info("El usuario " + id + " quiere consultar una lista con sus partidas");
        List<Partida> par = new LinkedList<Partida>();
        Jugador j = jugadores.get(id);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            for (Partida p:partidas){
                if (p.getIdJugador().equals(id)){
                    par.add(p);
                }
            }
            if (par.isEmpty()) {
                logger.info("El usuario aun no jugó ninguna partida");
                return Collections.emptyList();
            }
            else {
                return par;
            }
        }
    }

    public List<Partida> consultarUsuariosPorPuntuacion(String idJuego) throws JuegoNotFoundException {
        logger.info("Se quiere consultar la lista de usuarios ordenada por puntuación descendente que han participado en el juego con id " + idJuego);
        Juego juego = juegos.get(idJuego);
        if (juego == null) {
            logger.info("El juego no existe");
            throw new JuegoNotFoundException();
        }

        List<Partida> partidasJuego = new ArrayList<>();
        for (Partida p : partidas) {
            if (p.getJuego().getId().equals(idJuego)) {
                partidasJuego.add(p);
            }
        }

        if (partidasJuego.isEmpty()) {
            logger.info("Nadie jugó a ese juego por ahora");
            return Collections.emptyList();
        } else {
            // Ordenar por puntuación descendente
            partidasJuego.sort(Comparator.comparingInt(Partida::getPuntosAcumulados).reversed());
            return partidasJuego;
        }
    }

    public Juego addJuego(Juego j) {
        this.juegos.put(j.getId(), j);
        logger.info("Nuevo juego añadido");
        return j;
    }

    public Juego addJuego(String id, String descripcion, int niveles) throws NoNivelException, JuegoYaExisteException {
        logger.info("Crear un nuevo juego con id " + id + " descripcion = " + descripcion + " y " + niveles + " niveles");
        Juego j = juegos.get(id);
        if (j != null){
            logger.info("Ese juego ya existe (el ID tiene que ser único)");
            throw new JuegoYaExisteException();
        }
        else{
            if (niveles > 0){
                return this.addJuego(new Juego(id, descripcion, niveles));
            }
            logger.info("El juego tiene que tener al menos un nivel");
            throw new NoNivelException();
        }
    }

    public Jugador addJugador(Jugador jugador){
        this.jugadores.put(jugador.getId(), jugador);
        logger.info("Nuevo jugador añadido con id: " + jugador.getId());
        return jugador;
    }

    public Jugador addJugador() {
        return this.addJugador(new Jugador());
    }

    public int size() {
        int ret = this.jugadores.size();
        logger.info("size " + ret);

        return ret;
    }

    public String stringToJSON(String args) {
        String json = "{\"Resultado\": \"" + args + "\"}";
        return json;
    }*/
}
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

    public Partida addPartida(int dif, String idPlayer, String idMapa) { return this.addPartida(new Partida(dif, idPlayer, idMapa)); }
    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException {
        logger.info("new Jugador " + jugador.getUserName());
        logger.info(jugador.getUserName() + jugador.getMail() + jugador.getPasword());
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
            Credenciales c = new Credenciales(jugador.getUserName(), jugador.getMail(), jugador.getPasword(), jugador.getUserId());
            this.Credenciales.add(c);
            logger.info("credenciales: " + c.getUsername() + " " + c.getPassword());
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


    public List<Jugador>  findAllJugadores(){
        List<Jugador> lista = new ArrayList<Jugador>(Jugadores.values());
        return lista;
    }

    public List<Tienda> findAllProductos(){return this.Productos;}


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
                this.Credenciales.remove(j);
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


    // Tienda

    public Tienda addProducto(Tienda producto) throws ProductoYaExisteException, FaltanDatosException{
        logger.info("new product " + producto.getNombre());
        for(Tienda p : this.findAllProductos()) {
            if(p.getNombre().equals(producto.getNombre()))
                throw new ProductoYaExisteException();
        }
        if(producto.getNombre() == null || producto.getDescription() == null || producto.getEfect() < 1 || producto.getEfectType() < 0  || producto.getEfectType() > 3 || producto.getPrecio() < 0)
            throw new FaltanDatosException();
        else{
            this.Productos.add(producto);
            logger.info("new producto added");
            return producto;
        }
    }
    public Tienda addProducto(int precio, String nombre, String descripcion, int efect_type, int efect) throws ProductoYaExisteException, FaltanDatosException {return this.addProducto(new Tienda(precio, nombre, descripcion, efect_type,efect));}

    public Tienda getProducto(String id) throws ProductoNotFoundException{
        logger.info("getProducto("+id+")");

        for (Tienda p: this.Productos) {
            if (p.getId().equals(id)) {
                logger.info("getProducto("+id+"): "+p);

                return p;
            }
        }

        logger.error("not found " + id);
        throw new ProductoNotFoundException();
    }
    public List<Tienda> deleteProducto(Tienda producto) throws ProductoNotFoundException, FaltanDatosException {
        if(producto.getId() != null || producto.getNombre() != null || producto.getDescription() != null || producto.getEfect() >= 1 || producto.getEfectType() >= 0 || producto.getEfectType() <= 3){
            logger.info("delete Producto" + producto.getId() + ")");
            int i = 0;
            boolean encontrado = false;
            for (Tienda p : this.Productos) {
                if (p.getId().equals(producto.getId())) {
                    this.Productos.remove(i);
                    return this.Productos;
                }
                i++;
            }
            throw new ProductoNotFoundException();
        }
        else {
            throw new FaltanDatosException();
        }
    }
    public int TiendasSize(){
        int ret = this.Productos.size();
        logger.info("Productos size " + ret);
        return ret;
    }


    // Tienda



    public void increaseDamage(String jugadorUsername){
        Jugador jugador=Jugadores.get(jugadorUsername);
        if(jugador !=null){
            Avatar avatar =jugador.getAvatarActual();
            if(avatar!=null){
                int damage=avatar.getDamg()+20;
                avatar.setDamg(damage);
            }else{
                logger.warn("El jugador"+ jugadorUsername+"no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con ID"+jugadorUsername);
        }
    }

    public void increaseHealth(String jugadorUsername){
        Jugador jugador=Jugadores.get(jugadorUsername);
        if(jugador!=null){
            Avatar avatar=jugador.getAvatarActual();
            if(avatar!=null){
                int health=avatar.getLife()+20;
                avatar.setLife(health);
            }else{
                logger.warn("El jugador "+jugadorUsername+"no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador "+ jugadorUsername);
        }
    }
}
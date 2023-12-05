package edu.upc.dsa;

import java.util.*;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import edu.upc.dsa.util.Verificar;


public class GameManagerImpl implements GameManager {

    private static edu.upc.dsa.GameManager instance;
    protected List<Partida> partidas;
    protected Map<String, Jugador> jugadores;
    protected List<Avatar> avatares;
    protected List<Mapa> mapas;
    protected List<Tienda> productos;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    private GameManagerImpl() {
        this.partidas = new LinkedList<>();
        this.jugadores = new HashMap<>();
        this.avatares = new LinkedList<>();
        this.mapas = new LinkedList<>();
        this.productos = new LinkedList<>();
    }

    public static edu.upc.dsa.GameManager getInstance() {
        if (instance==null)
            instance = new GameManagerImpl();
        return instance;
    }

    public int PartidaSize() {      // Número de partidas creadas
        int ret = this.partidas.size();
        logger.info("Game size " + ret);
        return ret;
    }

    public int JugadoresSize() {    // Número de jugadores
        int ret = this.jugadores.size();
        logger.info("Jugadores size " + ret);
        return ret;
    }

    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException {
        logger.info("new Jugador " + jugador.getUsername());
        logger.info(jugador.getUsername() + jugador.getMail() + jugador.getPassword());
        if (jugador.getMail() == null  || jugador.getUsername() == null || jugador.getPassword() == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        for (Jugador j : this.findAllJugadores()){
            if (j.getMail().equals(jugador.getMail()) || j.getUsername().equals(jugador.getUsername())){
                logger.info("Ese jugador ya existe (el email y el usuario tienen que ser únicos)");
                throw new JugadorYaExisteException();
            }
        }
        if (Verificar.esDireccionCorreoValida(jugador.getMail()) == false){
            logger.info("No es un email");
            throw new NotAnEmailException();
        }
        else{
            this.jugadores.put(jugador.getUsername(), jugador);
            logger.info("new Jugador added");
            return jugador;
        }
    }

    public Jugador addJugador(String username, String mail, String pasword) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException { return this.addJugador(new Jugador(username, mail, pasword)); }

    public CredencialesRespuesta logJugador(String username, String password) throws FaltanDatosException, ErrorCredencialesException {
        CredencialesRespuesta respuesta = new CredencialesRespuesta();
        if(username == null || password == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        Jugador j = jugadores.get(username);
        if (j == null){
            logger.info("El usuario no existe");
            throw new ErrorCredencialesException();
        } else if (j.getPassword().equals(password)){
            logger.info("Login del jugador " + username);
            respuesta.setSuccess(true);
            return respuesta;
        } else{
            logger.info("Usuario o contraseña errónea");
            throw new ErrorCredencialesException();
        }
    }


    public List<Jugador>  findAllJugadores(){
        List<Jugador> lista = new ArrayList<Jugador>(jugadores.values());
        return lista;
    }

    public List<Tienda> findAllProductos(){return this.productos;}


    public CredencialesRespuesta updateUsername(String username, String newUsername, String password) throws ErrorCredencialesException {
        Jugador j = jugadores.get(username);
        CredencialesRespuesta r = new CredencialesRespuesta();

        if (j == null){
            logger.info("El usuario no existe");
            throw new ErrorCredencialesException();
        }
        else if (j.getPassword().equals(password)) {

            j.setUsername(newUsername);
            jugadores.remove(j);
            jugadores.put(newUsername, j);
            logger.info("El usuario " + username + " quiere cambiar su nombre a " + newUsername);
            logger.info("El usuario cambió su username a " + newUsername);
            r.setSuccess(true);
            return r;

        }else{
            logger.info("Contraseña incorrecta");
            throw new ErrorCredencialesException();
        }
    }

    public CredencialesRespuesta updatePassword(String user, String newPass, String password) throws ErrorCredencialesException {
        Jugador j = jugadores.get(user);
        CredencialesRespuesta respuesta = new CredencialesRespuesta();

        if (j == null){
            logger.info("El usuario no existe");
            throw new ErrorCredencialesException();
        }
        else if (j.getPassword().equals(password)) {
            j.setPassword(newPass);
            logger.info("El usuario " + user + " quiere cambiar su contraseña a " + newPass);
            logger.info("El usuario cambió su contraseña");
            respuesta.setSuccess(true);
            return respuesta;

        }else{
            logger.info("Contraseña incorrecta");
            throw new ErrorCredencialesException();
        }
    }


    public CredencialesRespuesta deleteUser(String username) throws UserNotFoundException {
        CredencialesRespuesta respuesta = new CredencialesRespuesta();
        Jugador j = jugadores.get(username);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            this.jugadores.remove(username);
            logger.info("El usuario " + j.getUsername() +" quiere borrar su perfil");
            logger.info("El usuario borró la cuenta");
            respuesta.setSuccess(true);
            return respuesta;
        }
    }


    public void deleteUser(String username, String password) throws UserNotFoundException{
        CredencialesRespuesta respuesta = new CredencialesRespuesta();
        Jugador j = jugadores.get(username);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            if (j.getPassword().equals(password)){
                this.jugadores.remove(username);
                logger.info("El usuario " + j.getUsername() +" quiere borrar su perfil");
                logger.info("El usuario borró la cuenta");
                respuesta.setSuccess(true);
            }
        }
    }



   /* public int consultarPuntuacion(String usuario) throws UserNotFoundException {
>>>>>>> ac24456938c0ebffb28a8f6e00ea413ec9dcd401
        logger.info("El jugador con id " + usuario + " quiere consultar su puntuación");
        Jugador j = jugadores.get(usuario);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            int puntos = j.getPoints();
            logger.info("El usuario tiene "+ puntos + " puntos");
            return puntos;
        }
    }*/



    public List<Partida> consultarPartidas(String username) throws UserNotFoundException {
        logger.info("El usuario " + username + " quiere consultar una lista con sus partidas");
        List<Partida> par = new LinkedList<Partida>();
        Jugador j = jugadores.get(username);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            for (Partida p:partidas){
                if (p.getPlayerId().equals(username)){
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

    public Jugador getJugador(String username) throws UserNotFoundException {
        logger.info("getUser("+username+")");
        Jugador j = jugadores.get(username);
        if (j == null){
            logger.info("El usuario no existe");
            throw new UserNotFoundException();
        }
        else{
            logger.info("getUser("+username+"): "+ j);
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
            this.productos.add(producto);
            logger.info("new producto added");
            return producto;
        }
    }
    public Tienda addProducto(int precio, String nombre, String descripcion, int efect_type, int efect) throws ProductoYaExisteException, FaltanDatosException {return this.addProducto(new Tienda(precio, nombre, descripcion, efect_type,efect));}

    public Tienda getProducto(String nombre) throws ProductoNotFoundException{
        logger.info("getProducto("+nombre+")");

        for (Tienda p: this.productos) {
            if (p.getNombre().equals(nombre)) {
                logger.info("getProducto("+nombre+"): "+p);

                return p;
            }
        }

        logger.error("not found " + nombre);
        throw new ProductoNotFoundException();
    }

    public void comprarProducto(String Pnombre, String usrnm) throws ProductoNotFoundException, CapitalInsuficienteException, UserNotFoundException{
        logger.info("Entramos en la función de comprar");
        try {
            Tienda p = this.getProducto(Pnombre);
            Jugador j = this.getJugador(usrnm);
            int precio = p.getPrecio();
            if(j.getEurillos() < precio) {
                logger.error("Estas tieso hermano, el producto " +p.getNombre()+" cuesta " + p.getPrecio() +" y tu tienes "+ j.getEurillos()+" eurillos");
                throw new CapitalInsuficienteException();
            }
            else{
                logger.info(usrnm + " se ha comprado " + Pnombre);
                j.setEurillos((j.getEurillos() - precio));
                if(p.getEfectType() == 0)
                    logger.info("Se ha incrementado la salud");
                this.increaseHealth(usrnm);
                if(p.getEfectType() == 1)
                    logger.info("Se ha incrementado el daño");
                this.increaseDamage(usrnm);
                if(p.getEfectType() == 2)
                    logger.info("Se ha incrementado la velocidad");
                this.increaseSpeed(usrnm);
                if(p.getEfectType() == 3)
                    logger.info("El jugador se ha hecho invisible...");
                this.invisibility(usrnm);
            }
        }
        catch (UserNotFoundException e) {
            logger.error("User not found");
            throw new RuntimeException(e);
        }
        catch (ProductoNotFoundException e){
            logger.error("Producto no encontrado");
            throw new RuntimeException(e);
        }
    }
    public List<Tienda> deleteProducto(Tienda producto) throws ProductoNotFoundException, FaltanDatosException {
        if(producto.getId() != null || producto.getNombre() != null || producto.getDescription() != null || producto.getEfect() >= 1 || producto.getEfectType() >= 0 || producto.getEfectType() <= 3){
            logger.info("delete Producto" + producto.getId() + ")");
            int i = 0;
            for (Tienda p : this.productos) {
                if (p.getId().equals(producto.getId())) {
                    this.productos.remove(i);
                    return this.productos;
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
        int ret = this.productos.size();
        logger.info("Productos size " + ret);
        return ret;
    }

    public void increaseDamage(String jugadorUsername){
        Jugador jugador=jugadores.get(jugadorUsername);

        if(jugador !=null){
            Avatar avatar =jugador.getAvatar();
            if(avatar!=null){
                int damage=avatar.getDamg()+20;
                avatar.setDamg(damage);
            }else{
                logger.warn("El jugador"+ jugadorUsername+" no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con username "+jugadorUsername);
        }
    }

    public void increaseHealth(String jugadorUsername){
        Jugador jugador=jugadores.get(jugadorUsername);

        if(jugador!=null) {
            Avatar avatar = jugador.getAvatar();
            if (avatar != null) {
                int health = avatar.getHealth() + 20;
                avatar.setHealth(health);
            } else {
                logger.warn("El jugador " + jugadorUsername + " no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
    }
    public void increaseSpeed(String jugadorUsername){
        Jugador jugador=jugadores.get(jugadorUsername);
        if(jugador!=null){
            Avatar avatar=jugador.getAvatar();
            if(avatar!=null){
                int speed=avatar.getSpeed()+20;
                avatar.setSpeed(speed);
            }else{
                logger.warn("El jugador "+jugadorUsername+" no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
    }
    public void invisibility(String jugadorUsername){
        Jugador jugador=jugadores.get(jugadorUsername);
        if(jugador!=null){
            Avatar avatar=jugador.getAvatar();
            if(avatar!=null){
                int invisibility=1;
                avatar.setVisible(invisibility);
            }else{
                logger.warn("El jugador "+jugadorUsername+" no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
    }
    public void armaEscopeta(String jugadorUsername){
        Jugador jugador=jugadores.get(jugadorUsername);
        if(jugador!=null){
            Avatar avatar=jugador.getAvatar();
            if(avatar!=null){
                int speed=avatar.getSpeed()-20;
                avatar.setSpeed(speed);
                int damage=100;
                avatar.setDamg(damage);
            }else{
                logger.warn("El jugador "+jugadorUsername+" no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
    }
    public void armaEspada(String jugadorUsername){

        Jugador jugador=jugadores.get(jugadorUsername);
        if(jugador!=null){
            Avatar avatar=jugador.getAvatar();
            if(avatar!=null){
                int speed=avatar.getSpeed()-10;
                avatar.setSpeed(speed);
                int damage=50;
                avatar.setDamg(damage);
            }else{
                logger.warn("El jugador "+jugadorUsername+" no tiene un avatar actual");
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
    }
}
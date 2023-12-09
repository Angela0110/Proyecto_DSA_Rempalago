package edu.upc.dsa;

import java.sql.SQLException;
import java.util.*;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import edu.upc.dsa.util.Verificar;

import java.sql.SQLIntegrityConstraintViolationException;



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
    public int AvataresSize(){      // Número de avatares disponibles
        int ret = this.avatares.size();
        logger.info("Avatares size " + ret);
        return ret;
    }

    public int JugadoresSize() {    // Número de jugadores
        int ret = this.jugadores.size();
        logger.info("Jugadores size " + ret);
        return ret;
    }

    //          Organización
    //   1        ---> Jugador Manager
    //   2        ---> Tienda Manager
    //   3        ---> Partida Manager
    //   4        ---> Avatar Manager


    // Jugador Manager

    public Jugador addJugador(Jugador jugador) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException {
        logger.info("new Jugador " + jugador.getUsername());
        logger.info(jugador.getUsername() + jugador.getMail() + jugador.getPassword());
        if (jugador.getMail() == null  || jugador.getUsername() == null || jugador.getPassword() == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        if (!Verificar.esDireccionCorreoValida(jugador.getMail())){
            logger.info("No es un email");
            throw new NotAnEmailException();
        }else{
            Session session = null;
            try {
                session = FactorySession.openSession();
                session.save(jugador);
                logger.info("new Jugador added");

            } catch (SQLIntegrityConstraintViolationException e) {
                logger.info("Ese jugador ya existe (el email y el usuario tienen que ser únicos)");
                throw new JugadorYaExisteException();
            } catch (Exception e){
                e.printStackTrace();
            }
            finally {
                session.close();
            }
        }
        return jugador;
    }

    public Jugador addJugador(String username, String mail, String pasword) throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException { return this.addJugador(new Jugador(username, mail, pasword)); }

    public CredencialesRespuesta logJugador(String username, String password) throws FaltanDatosException, ErrorCredencialesException {
        CredencialesRespuesta respuesta = new CredencialesRespuesta();
        if(username == null || password == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        Session session = null;
        Jugador jugador;
        try {
            session = FactorySession.openSession();
            jugador = (Jugador) session.get(Jugador.class, "username", username);
            if (jugador == null) {
                logger.info("El usuario no existe");
                throw new ErrorCredencialesException();

            } else if (!jugador.getPassword().equals(password)){
                logger.info("Usuario o contraseña errónea");
                throw new ErrorCredencialesException();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        logger.info("Login del jugador " + username);
        respuesta.setSuccess(true);
        return respuesta;
    }


    public List<Jugador>  findAllJugadores(){
        Session session = null;
        List<Jugador> jugadores = null;
        try {
            session = FactorySession.openSession();
            jugadores = session.findAll(Jugador.class);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }
        System.out.println(jugadores);
        return jugadores;
    }

    public Jugador getJugador(String username) throws UserNotFoundException {
        logger.info("getUser("+username+")");

        Session session = null;
        Jugador jugador = null;

        try {
            session = FactorySession.openSession();
            jugador = (Jugador)session.get(Jugador.class,"username", username);
            if (jugador == null){
                logger.info("El usuario no existe");
                throw new UserNotFoundException();
            }
            else{
                logger.info("getUser("+username+"): "+ jugador);
            }
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return jugador;
    }


    public CredencialesRespuesta updateUsername(String username, String newUsername, String password) throws ErrorCredencialesException, JugadorYaExisteException, FaltanDatosException {

        logger.info(username + " quiere cambiar su username a " + newUsername);

        if(username == null || password == null || newUsername == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        CredencialesRespuesta r = new CredencialesRespuesta();
        Session session = null;
        Jugador jugador;

        try {
            session = FactorySession.openSession();
            jugador = (Jugador)session.get(Jugador.class,"username", username);
            if (jugador == null || !jugador.getPassword().equals(password)){
                logger.info("No existe el usuario o la contraseña no es correcta");
                throw new ErrorCredencialesException();
            }else{
                session.update("username",username,newUsername);
                r.setSuccess(true);
                logger.info(username + " se cambió el username a " + newUsername);
            }

        } catch (SQLIntegrityConstraintViolationException e){
            logger.info("No se puede hacer el cambio porque ya hay un jugador con ese username");
            throw new JugadorYaExisteException();
        }catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return r;
    }

    public CredencialesRespuesta updatePassword(String user, String newPass, String password) throws ErrorCredencialesException, FaltanDatosException {

        logger.info(user + " quiere cambiar su contraseña a " + password);

        if(user == null || password == null || newPass == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        CredencialesRespuesta r = new CredencialesRespuesta();
        Session session = null;
        Jugador jugador;

        try {
            session = FactorySession.openSession();
            jugador = (Jugador)session.get(Jugador.class,"username", user);
            if (jugador == null || !jugador.getPassword().equals(password)){
                logger.info("No existe el usuario o la contraseña no es correcta");
                throw new ErrorCredencialesException();
            }else{
                session.update("password", user, newPass);
                r.setSuccess(true);
                logger.info(user + " se cambió el password a " + newPass);
            }

        }catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return r;
    }


    public CredencialesRespuesta deleteUser(String username) throws UserNotFoundException, FaltanDatosException {
        CredencialesRespuesta respuesta = new CredencialesRespuesta();
        Session session = null;
        logger.info("El usuario " + username + " quiere borrar su perfil");

        if (username == null) {
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        try {
            session = FactorySession.openSession();
            session.delete(username);
            logger.info("El usuario borró la cuenta");
            respuesta.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoRecordsFoundException e) {
            throw new UserNotFoundException();
        } finally {
            session.close();
        }

        return respuesta;
    }




   /* public int consultarPuntuacion(String usuario) throws UserNotFoundException {
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


    // Tienda Manager

    public Tienda addProducto(Tienda producto) throws ProductoYaExisteException, FaltanDatosException{
        logger.info("new product " + producto.getNombre());
        if(producto.getNombre() == null || producto.getDescription() == null || producto.getEfect() < 1 || producto.getEfect_type() < 0  || producto.getEfect_type() > 3 || producto.getPrecio() < 0){
            throw new FaltanDatosException();
        } else {
            Session session = null;
            try {
                session = FactorySession.openSession();
                session.save(producto);
                logger.info("new producto added");

            } catch (SQLIntegrityConstraintViolationException e) {
                logger.info("Ese producto ya existe");
                throw new ProductoYaExisteException();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return producto;
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

    public List<Tienda> findAllProductos(){return this.productos;}

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
                if(p.getEfect_type() == 0) {
                    logger.info("Se ha incrementado la salud");
                    this.increaseHealth(usrnm, p.getEfect());
                }
                if(p.getEfect_type() == 1) {
                    logger.info("Se ha incrementado el daño");
                    this.increaseDamage(usrnm, p.getEfect());
                }
                if(p.getEfect_type() == 2) {
                    logger.info("Se ha incrementado la velocidad");
                    this.increaseSpeed(usrnm, p.getEfect());
                }
                if(p.getEfect_type() == 3) {
                    logger.info("El jugador se ha hecho invisible...");
                    this.invisibility(usrnm);
                }
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
        if(producto.getNombre() != null || producto.getDescription() != null || producto.getEfect() >= 1 || producto.getEfect_type() >= 0 || producto.getEfect_type() <= 3){
            logger.info("delete Producto" + producto.getNombre() + ")");
            int i = 0;
            for (Tienda p : this.productos) {
                if (p.getNombre().equals(producto.getNombre())) {
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

    public void increaseDamage(String jugadorUsername, int damage){
        Jugador jugador=jugadores.get(jugadorUsername);
        boolean encontrado = false;
        if(jugador !=null){
            String av =jugador.getAvatar();
            for(Avatar avatar : this.avatares){
                if(avatar.getNombre().equals(av)){
                    int damg=avatar.getDamg()+ damage;
                    avatar.setDamg(damg);
                    encontrado = true;
                    logger.info("El jugador " + jugadorUsername + " ha incrementado su daño");
                }
            }
        }else{
            logger.warn("No se encontró al jugador con username "+jugadorUsername);
        }
        if(!encontrado)
            logger.warn("El jugador"+ jugadorUsername+" no tiene un avatar actual");
    }

    public void increaseHealth(String jugadorUsername, int health){
        Jugador jugador=jugadores.get(jugadorUsername);
        boolean encontrado = false;
        if(jugador!=null) {
            String av = jugador.getAvatar();
            for(Avatar avatar : this.avatares){
                if (avatar.getNombre().equals(av)) {
                    int hlth = avatar.getHealth() + health;
                    avatar.setHealth(hlth);
                    logger.info("El jugador " + jugadorUsername + " ha incrementado su vida");
                    encontrado = true;
                }
            }
        }
        else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
        if(!encontrado)
            logger.warn("El jugador " + jugadorUsername + " no tiene un avatar actual");
    }
    public void increaseSpeed(String jugadorUsername, int speed){
        Jugador jugador=jugadores.get(jugadorUsername);
        boolean encontrado = false;
        if(jugador!=null){
            String av=jugador.getAvatar();
            for(Avatar avatar : this.avatares){
                if(avatar.getNombre().equals(av)){
                    int spd=avatar.getSpeed() + speed;
                    avatar.setSpeed(spd);
                    encontrado = true;
                    logger.info("El jugador " + jugadorUsername + " ha incrementado su velocidad");
                }
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
        if(!encontrado)
            logger.warn("El jugador "+jugadorUsername+" no tiene un avatar actual");
    }
    public void invisibility(String jugadorUsername){
        Jugador jugador=jugadores.get(jugadorUsername);
        boolean encontrado = false;
        if(jugador!=null){
            String av = jugador.getAvatar();
            for(Avatar avatar : this.avatares){
                if(avatar.getNombre().equals(av)){
                    avatar.setVisible(1);
                    encontrado = true;
                    logger.info("El jugador " + jugadorUsername + " se ha hecho invisible");
                }
            }
        }else{
            logger.warn("No se encontró al jugador con username " + jugadorUsername);
        }
        if(!encontrado)
            logger.warn("El jugador "+jugadorUsername+" no tiene un avatar actual");
    }


    // Partida Manager

    public Partida addPartida(Partida partida) throws PartidaYaExisteException, FaltanDatosException{
        return partida;
    }

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
                if (p.getPlayer().equals(username)){
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

    public int cambiarDificultad(String player, int newdif) throws PartidaNotFoundException, MismaDificultadException{
        return newdif;
    }

    // Avatar Manager

    public Avatar addAvatar(Avatar avatar) throws AvatarYaExisteException, FaltanDatosException{
        logger.info("new Avatar added " + avatar.getNombre());
        logger.info(avatar.getNombre() + " Salud: " + avatar.getHealth() +" Daño: " +avatar.getDamg() + " Velocidad: " + avatar.getSpeed());
        boolean igual = false;
        if (avatar.getNombre() == null  || avatar.getHealth() == 0 || avatar.getDamg() == 0 || avatar.getSpeed() == 0){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        for (Avatar a : this.findAllAvatares()){
            if (a.getNombre().equals(avatar.getNombre()) ){
                logger.info("Ese jugador ya existe (el email y el usuario tienen que ser únicos)");
                igual = true;
                throw new AvatarYaExisteException();
            }
        }
        this.avatares.add(avatar);
        logger.info("new Avatar added");
        return avatar;
    }

    public Avatar addAvatar(String nombre, int idArma, int health, int damg, int speed) throws AvatarYaExisteException, FaltanDatosException{return this.addAvatar(new Avatar(nombre, idArma, health, damg, speed));}

    public List<Avatar> findAllAvatares(){return this.avatares;}
}
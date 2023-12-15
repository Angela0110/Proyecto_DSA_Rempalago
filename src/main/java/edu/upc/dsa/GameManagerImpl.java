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
    /*protected List<Partida> partidas;
    protected Map<String, Jugador> jugadores;
    protected List<Avatar> avatares;
    protected List<Mapa> mapas;
    protected List<Tienda> productos;*/

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);
    private GameManagerImpl() {
        /*this.partidas = new LinkedList<>();
        this.jugadores = new HashMap<>();
        this.avatares = new LinkedList<>();
        this.mapas = new LinkedList<>();
        this.productos = new LinkedList<>();*/
    }

    public static edu.upc.dsa.GameManager getInstance() {
        if (instance==null)
            instance = new GameManagerImpl();
        return instance;
    }

    public int PartidaSize() {      // Número de partidas creadas
        int ret = 0;
        Session session = null;
        try {
            session = FactorySession.openSession();
            ret = session.size(Partida.class);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        logger.info("Game size " + ret);
        return ret;
    }
    public int AvataresSize(){      // Número de avatares disponibles
        int ret = 0;
        Session session = null;
        try {
            session = FactorySession.openSession();
            ret = session.size(Avatar.class);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        logger.info("Avatar size " + ret);
        return ret;
    }

    public int JugadoresSize() {    // Número de jugadores
        int ret = 0;
        Session session = null;
        try {
            session = FactorySession.openSession();
            ret = session.size(Jugador.class);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        logger.info("Jugadores size " + ret);
        return ret;
    }
    public int TiendasSize(){
        int ret = 0;
        Session session = null;
        try {
            session = FactorySession.openSession();
            ret = session.size(Tienda.class);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        logger.info("Producto size " + ret);
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
                assert session != null;
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
            assert session != null;
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
            assert session != null;
            session.close();
        }
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
            assert session != null;
            session.close();
        }

        return jugador;
    }


    public CredencialesRespuesta updateUsername(String username, String newUsername, String password) throws ErrorCredencialesException, JugadorYaExisteException, FaltanDatosException, UserNotFoundException, AvatarNotFound {

        logger.info(username + " quiere cambiar su username a " + newUsername);

        if(username == null || password == null || newUsername == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        CredencialesRespuesta r = new CredencialesRespuesta();
        Session session = null;
        Jugador jugador;

        jugador = this.getJugador(username);
        if (jugador == null || !jugador.getPassword().equals(password)) {
            logger.info("No existe el usuario o la contraseña no es correcta");
            throw new ErrorCredencialesException();
        }
        try {
            session = FactorySession.openSession();
            session.update("username",username,newUsername);
            r.setSuccess(true);
            logger.info(username + " se cambió el username a " + newUsername);
            this.updateAvatar(newUsername, username);

        } catch (SQLIntegrityConstraintViolationException e){
            logger.info("No se puede hacer el cambio porque ya hay un jugador con ese username");
            throw new JugadorYaExisteException();
        }catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        return r;
    }

    public CredencialesRespuesta updatePassword(String user, String newPass, String password) throws ErrorCredencialesException, FaltanDatosException, UserNotFoundException {

        logger.info(user + " quiere cambiar su contraseña a " + password);

        if(user == null || password == null || newPass == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        CredencialesRespuesta r = new CredencialesRespuesta();
        Session session = null;
        Jugador jugador = this.getJugador(user);
        if (jugador == null || !jugador.getPassword().equals(password)) {
            logger.info("No existe el usuario o la contraseña no es correcta");
            throw new ErrorCredencialesException();
        }

        try {
            session = FactorySession.openSession();
            session.update("password", user, newPass);
            r.setSuccess(true);
            logger.info(user + " se cambió el password a " + newPass);

        }catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        return r;
    }

    public void updateJugador(String columna, String user, String newValue) {


        CredencialesRespuesta r = new CredencialesRespuesta();
        Session session = null;
        Jugador jugador;

        try {
            session = FactorySession.openSession();
            session.update(columna, user, newValue);
            logger.info("update de " + columna + ": pasa a tener " + newValue);

        }catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
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
            session.delete(Jugador.class, "username",username);
            logger.info("El usuario borró la cuenta");
            respuesta.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoRecordsFoundException e) {
            throw new UserNotFoundException();
        } finally {
            assert session != null;
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
        if(producto.getNombre() == null || producto.getDescription() == null || producto.getEfect() < 1 || producto.getType() < 0  || producto.getType() > 3 || producto.getPrecio() < 0){
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
                assert session != null;
                session.close();
            }
        }
        return producto;
    }
    public Tienda addProducto(String imagen, int precio, String nombre, String descripcion, int efect_type, int efect) throws ProductoYaExisteException, FaltanDatosException {return this.addProducto(new Tienda(precio, nombre, descripcion, efect_type,efect, imagen));}

    public Tienda getProducto(String nombre) throws ProductoNotFoundException{
        logger.info("getProducto("+nombre+")");

        Session session = null;
        Tienda tienda = null;

        try {
            session = FactorySession.openSession();
            tienda = (Tienda)session.get(Tienda.class,"nombre", nombre);
            if (tienda == null){
                logger.info("El producto no existe");
                throw new ProductoNotFoundException();
            }
            else{
                logger.info("getProducto("+nombre+"): " + tienda);
            }
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        return tienda;
    }

    public List<Tienda> findAllProductos(){
        Session session = null;
        List<Tienda> productos = null;
        try {
            session = FactorySession.openSession();
            productos = session.findAll(Tienda.class);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            assert session != null;
            session.close();
        }
        return productos;
    }

    public void comprarProducto(String Pnombre, String usrnm) throws CapitalInsuficienteException, FaltanDatosException, SQLException, AvatarNotFound, UserNotFoundException, ProductoNotFoundException {
        logger.info("Entramos en la función de comprar");
        Tienda p = this.getProducto(Pnombre);
        if (p == null) {
            logger.error("Producto no encontrado");
            throw new ProductoNotFoundException();
        }
        Jugador j = this.getJugador(usrnm);
        if (j == null) {
            logger.error("User not found");
            throw new UserNotFoundException();
        }
        logger.info(j.getUsername() + j.getMail() + j.getEurillos());
        int precio = p.getPrecio();
        if(j.getEurillos() < precio) {
            logger.error("Estas tieso hermano, el producto " +p.getNombre()+" cuesta " + p.getPrecio() +" y tu tienes "+ j.getEurillos()+" eurillos");

            throw new CapitalInsuficienteException();
        }
        if(j.getAvatar() == null){
            logger.info("El jugador no tiene avatar");
            throw new AvatarNotFound();
        } else{
            logger.info(usrnm + " se ha comprado " + Pnombre);
            int type = p.getType();
            this.updateJugador("eurillos", j.getUsername(), String.valueOf(j.getEurillos() - precio));

            if(type == 0) {
                logger.info("Se ha incrementado la salud");
                this.increaseHealth(usrnm, p.getEfect());
            }
            else if(type == 1) {
                logger.info("Se ha incrementado el daño");
                this.increaseDamage(usrnm, p.getEfect());
            }
            else if(type == 2) {
                logger.info("Se ha incrementado la velocidad");
                this.increaseSpeed(usrnm, p.getEfect());
            }
            else if(type == 3) {
                logger.info("El jugador se ha hecho invisible...");
                this.invisibility(usrnm);
            }
        }
    }
    public List<Tienda> deleteProducto(String nombre) throws ProductoNotFoundException, FaltanDatosException {
        Session session = null;
        logger.info("delete Producto(" + nombre + ")");
        List<Tienda> lista = null;

        if(nombre == null) {
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        try {
            session = FactorySession.openSession();
            session.delete(Tienda.class, "nombre", nombre);
            logger.info("El usuario borró la cuenta");
            lista = this.findAllProductos();

        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoRecordsFoundException e) {
            logger.info("No existe ese producto");
            throw new ProductoNotFoundException();
        } finally {
            assert session != null;
            session.close();
        }

        return lista;
    }

    public void increaseDamage(String jugadorUsername, int damage) throws FaltanDatosException, UserNotFoundException, SQLException, AvatarNotFound {
        if(jugadorUsername == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        Session session = null;

        try {
            session = FactorySession.openSession();
            Jugador jugador = (Jugador) session.get(Jugador.class, "username", jugadorUsername);
            if (jugador == null) {
                logger.info("No existe el usuario");
                throw new UserNotFoundException();
            }
            if (jugador.getAvatar() == null) {
                logger.warn("El jugador" + jugadorUsername + " no tiene un avatar actual");
            }
            else {
                Avatar avatar = this.getAvatar(jugadorUsername, jugador.getAvatar());
                int damg = avatar.getDamg() + damage;
                session.updateAvatar("damg",jugadorUsername, jugador.getAvatar(), String.valueOf(damg));
                logger.info("Se imcrementó el daño");

            }
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }

    }

    public void increaseHealth(String jugadorUsername, int health) throws FaltanDatosException, UserNotFoundException, AvatarNotFound {
        if(jugadorUsername == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        Session session = null;

        try {
            session = FactorySession.openSession();
            Jugador jugador = (Jugador) session.get(Jugador.class, "username", jugadorUsername);
            if (jugador == null) {
                logger.info("No existe el usuario");
                throw new UserNotFoundException();
            }
            if (jugador.getAvatar() == null) {
                logger.warn("El jugador" + jugadorUsername + " no tiene un avatar actual");
            }
            else {
                Avatar avatar = this.getAvatar(jugadorUsername, jugador.getAvatar());
                int hlth = avatar.getHealth() + health;
                session.updateAvatar("health",jugadorUsername, jugador.getAvatar(), String.valueOf(hlth));
                logger.info("El jugador " + jugadorUsername + " ha incrementado su vida");
            }
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }

    }


    public void increaseSpeed(String jugadorUsername, int speed) throws FaltanDatosException, AvatarNotFound {
        if(jugadorUsername == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        Session session = null;

        try {
            session = FactorySession.openSession();
            Jugador jugador = (Jugador) session.get(Jugador.class, "username", jugadorUsername);
            if (jugador == null) {
                logger.info("No existe el usuario");
                throw new UserNotFoundException();
            }
            if (jugador.getAvatar() == null) {
                logger.warn("El jugador" + jugadorUsername + " no tiene un avatar actual");
            }
            else {
                Avatar avatar = this.getAvatar(jugadorUsername, jugador.getAvatar());
                int spd=avatar.getSpeed() + speed;
                session.updateAvatar("speed",jugadorUsername, jugador.getAvatar(), String.valueOf(spd));
                logger.info("El jugador " + jugadorUsername + " ha incrementado su velocidad");
            }
        }
        catch (Exception | UserNotFoundException e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
    }

    public void invisibility(String jugadorUsername) throws UserNotFoundException, FaltanDatosException, AvatarNotFound {
        if(jugadorUsername == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }

        Session session = null;

        try {
            session = FactorySession.openSession();
            Jugador jugador = (Jugador) session.get(Jugador.class, "username", jugadorUsername);
            if (jugador == null) {
                logger.info("No existe el usuario");
                throw new UserNotFoundException();
            }
            if (jugador.getAvatar() == null) {
                logger.warn("El jugador" + jugadorUsername + " no tiene un avatar actual");
            }
            else {
                session.updateAvatar("visible",jugadorUsername, jugador.getAvatar(), String.valueOf(1));
                logger.info("El jugador " + jugadorUsername + " se ha hecho invisible");
            }
        }
        catch (Exception | UserNotFoundException e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
    }


    // Partida Manager

    public Partida addPartida(Partida partida) throws FaltanDatosException, UserNotFoundException {
        logger.info("new Partida del jugador " + partida.getPlayer());
        if (partida.getPlayer() == null  || partida.getDif() > 2 || partida.getIdMapa() == null){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        else{
            Session session = null;
            try {
                session = FactorySession.openSession();
                session.save(partida);
                logger.info("new partida added");

            } catch (SQLIntegrityConstraintViolationException e) {
                logger.info("no existe ningun jugador con ese username o no existe ese mapa");
                throw new UserNotFoundException();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                assert session != null;
                session.close();
            }
        }
        return partida;
    }

    public Partida addPartida(int dif, String player, String idMapa) throws FaltanDatosException, UserNotFoundException {
        return this.addPartida(new Partida(dif, player, idMapa));
    }

   /* public int cambiarDificultad(String player, int newdif) throws PartidaNotFoundException, MismaDificultadException{
        logger.info("El jugador "+player+" quiere cambiar la dificultad de su partida");
        boolean encontrado = false;
        for(Partida p : this.partidas){
            if(p.getPlayer().equals(player)) {
                if(p.getDif() == newdif) {
                    logger.warn("La dificultad seleccionada es la misma que se está jugando");
                    throw new MismaDificultadException();
                }else {
                    logger.info("Dificultad cambiada correctamente");
                    p.setDif(newdif);
                    encontrado = true;
                }
            }
        }
        if(!encontrado)
            return 1;
        else{
            logger.warn("El jugador " +player+ " no está en ninguna partida");
            throw new PartidaNotFoundException();
        }
    }*/

    public List<Partida> consultarPartidas(String username) throws UserNotFoundException {
        logger.info("El usuario " + username + " quiere consultar una lista con sus partidas");
        Session session = null;
        List<Partida> par = null;
        try {
            session = FactorySession.openSession();
            session = FactorySession.openSession();
            Jugador jugador = (Jugador) session.get(Jugador.class, "username", username);
            if (jugador == null) {
                logger.info("No existe el usuario");
                throw new UserNotFoundException();
            }else{
                par = session.findAllPartidas(Partida.class, username);
            }
        }
        catch (Exception e) {
            // LOG
        } finally {
            assert session != null;
            session.close();
        }
        return par;
    }

    // Avatar Manager

    public Avatar addAvatar(Avatar avatar) throws AvatarYaExisteException, FaltanDatosException{
        logger.info("new Avatar added " + avatar.getNombre());
        logger.info(avatar.getNombre() + " Salud: " + avatar.getHealth() +" Daño: " +avatar.getDamg() + " Velocidad: " + avatar.getSpeed());
        if (avatar.getNombre() == null  || avatar.getHealth() == 0 || avatar.getDamg() == 0 || avatar.getSpeed() == 0){
            logger.info("Faltan datos");
            throw new FaltanDatosException();
        }
        else{
            Session session = null;
            try {
                session = FactorySession.openSession();
                session.save(avatar);
                this.updateJugador("avatar", avatar.getJugador(), avatar.getNombre());
                logger.info("new avatar added");

            } catch (SQLIntegrityConstraintViolationException e) {
                logger.info("ya existe un avatar con ese nombre");
                throw new AvatarYaExisteException();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                assert session != null;
                session.close();
            }
        }

        logger.info("new Avatar added");
        return avatar;
    }


    public Avatar addAvatar(String username, String nombre, int idArma, int health, int damg, int speed) throws AvatarYaExisteException, FaltanDatosException{return this.addAvatar(new Avatar(username, nombre, idArma, health, damg, speed));}

    public Avatar getAvatar(String player, String nombreAvatar) throws AvatarNotFound {
        logger.info("getAvatar("+player+")");

        Session session = null;
        Avatar avatar = null;

        try {
            session = FactorySession.openSession();
            avatar = (Avatar) session.getAvatar(player,nombreAvatar);
            if (avatar == null){
                logger.info("El avatar no existe");
                throw new AvatarNotFound();
            }
            else{
                logger.info("getAvatar("+player+"): " + nombreAvatar);
            }
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }

        return avatar;
    }

    public void updateAvatar(String newJugadorUsername, String user) throws UserNotFoundException, AvatarNotFound {

        Session session = null;
        try {
            session = FactorySession.openSession();
            Jugador jugador = (Jugador) session.get(Jugador.class, "username", newJugadorUsername);
            if (jugador == null) {
                logger.info("No existe el usuario");
                throw new UserNotFoundException();
            }
            if (jugador.getAvatar() == null) {
                logger.warn("El jugador" + newJugadorUsername + " no tiene un avatar actual");
            }
            else {
                session.updateAvatar("jugador", user, jugador.getAvatar(), newJugadorUsername);
                logger.info("Se actualiza el nombre del jugador del avatar a " + newJugadorUsername);
            }
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public List<Avatar> findAllAvatares(){
        logger.info("Lista de avatares");
        Session session = null;
        List<Avatar> avatares = new LinkedList<Avatar>();
        try {
            session = FactorySession.openSession();
            avatares = session.findAll(Avatar.class);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            assert session != null;
            session.close();
        }
        return avatares;
    }
}
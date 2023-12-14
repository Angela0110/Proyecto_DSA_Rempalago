package edu.upc.dsa;

import edu.upc.dsa.models.*;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.exceptions.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ServiceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
        this.jm = GameManagerImpl.getInstance();

    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }



    private GameManager jm;
    private static Partida p;
    private static String idJ;
    private static String idJ2;


   /* @Before
    public void setUp() throws JugadorYaExisteException, NotAnEmailException, FaltanDatosException, AvatarYaExisteException {

        this.jm = GameManagerImpl.getInstance();
        if (this.jm.JugadoresSize() == 0) {
            this.jm.addJugador("Antonio","Fernanditox_47@gmail.com","SweetP2");
            this.jm.addJugador("Lobi","malasia.2010@gmail.com","Perico23");
            this.jm.addJugador("Fernando33","brasil.2005@gmail.com","33?");
            this.jm.addAvatar("Lobi","AX-21",23,100,10,50);
            this.jm.addAvatar("Antonio","ZE-32",25,150,20,25);
            this.jm.addAvatar("Fernando33", "DC-25",29,110,50,100);
        }
    } */

    @Test
    public void TestLogin() throws UserNotFoundException, FaltanDatosException, ErrorCredencialesException {
        Credenciales credenciales = new Credenciales("Antonio","SweetP2");
        CredencialesRespuesta r = new CredencialesRespuesta();

        r = this.jm.logJugador(credenciales.getUsername(), credenciales.getPassword());
        boolean answer = true;
        assertEquals(answer,r.getSuccess());
    }

    @Test
    public void TestRegister() throws JugadorYaExisteException, NotAnEmailException, FaltanDatosException {
        CredencialesRegistro credenciales = new CredencialesRegistro("Manuel","1234","antonio@hotmail.com");
        Jugador j;

        j = this.jm.addJugador(credenciales.getUsername(), credenciales.getEmail(), credenciales.getPassword());
        Jugador answer = new Jugador("Manuel","1234","antonio@hotmail.com");
        assertEquals(answer.getUsername(),j.getUsername());
    }

    @Test
    public void TestComprar() throws UserNotFoundException, FaltanDatosException, ProductoNotFoundException, SQLException, AvatarNotFound, CapitalInsuficienteException {
        String pnombre = "Espada";
        String nombre = "Lobi";
        Jugador j = this.jm.getJugador(nombre);
        Tienda p = this.jm.getProducto(pnombre);
        int r = j.getEurillos() - p.getPrecio();
        this.jm.comprarProducto(pnombre,nombre);
        j = this.jm.getJugador(nombre);
        assertEquals(r,j.getEurillos());
    }
    
   /* @Test
    public void TestFinalizarPartida() throws UserNotFoundException, UserNoEnPartidaException, UserEnPartidaException, JuegoNotFoundException {
        Partida partida = this.jm.iniciarPartida("Pokemon", idJ2);
        String mensaje = jm.FinalizarPartida(idJ2);
        Assert.assertEquals("El usuario con id " + idJ2 + " ha finalizado la partida actual", mensaje);

        try {
            jm.consultarNivelActual("sda");
            Assert.fail("Se esperaba que lanzara UserNotFoundException");
        } catch (UserNotFoundException e) {
            Assert.assertEquals("El usuario no existe", e.getMessage());
        }

        try {
            jm.consultarNivelActual(idJ2);
            Assert.fail("Se esperaba que lanzara UserNoEnPartidaException");
        } catch (UserNoEnPartidaException ex) {
            Assert.assertEquals("El usuario no tiene una partida en curso", ex.getMessage());
        }
    }

    @Test
    public void TestConsultarPuntuaciones() throws UserNotFoundException, UserNoEnPartidaException {
        String msg1 = jm.consultarPuntuacion(idJ);
        Assert.assertEquals("El usuario tiene " + 50 + " puntos", msg1);

        try {
            jm.consultarPuntuacion("sda");
            Assert.fail("Se esperaba que lanzara UserNotFoundException");
        } catch (UserNotFoundException e) {
            Assert.assertEquals("El usuario no existe", e.getMessage());
        }

        try {
            jm.consultarPuntuacion(idJ2);
            Assert.fail("Se esperaba que lanzara UserNoEnPartidaException");
        } catch (UserNoEnPartidaException ex) {
            Assert.assertEquals("El usuario no tiene una partida en curso", ex.getMessage());
        }
    }

    @Test
    public void TestConsultarNivelActual() throws UserNotFoundException, UserNoEnPartidaException {
        Partida partida = jm.consultarNivelActual(idJ);
        Assert.assertEquals(p, partida);

        try {
            jm.consultarNivelActual("sda");
            Assert.fail("Se esperaba que lanzara UserNotFoundException");
        } catch (UserNotFoundException e) {
            Assert.assertEquals("El usuario no existe", e.getMessage());
        }

        try {
            jm.consultarNivelActual(idJ2);
            Assert.fail("Se esperaba que lanzara UserNoEnPartidaException");
        } catch (UserNoEnPartidaException ex) {
            Assert.assertEquals("El usuario no tiene una partida en curso", ex.getMessage());
        }
    }
    @Test
    public void TestConsultarPartidas() throws UserNotFoundException, UserNoEnPartidaException, UserEnPartidaException, JuegoNotFoundException {
        List<Partida> lista= new LinkedList<Partida>();
        lista.add(p);
        String mensaje = jm.FinalizarPartida(idJ);
        p = this.jm.iniciarPartida("Pokemon", idJ);
        lista.add(p);
        List<Partida> par = jm.consultarPartidas(idJ);
        Assert.assertEquals(lista, par);

        try {
            jm.consultarPartidas("sda");
            Assert.fail("Se esperaba que lanzara UserNotFoundException");
        } catch (UserNotFoundException e) {
            Assert.assertEquals("El usuario no existe", e.getMessage());
        }

        Jugador jugador = new Jugador();
        this.jm.addJugador(jugador);
        String idJugador = jugador.getId();

        List<Partida> part = jm.consultarPartidas(idJugador);
        List<Partida> emptyList = new LinkedList<Partida>(); // Crear una lista vacía
        Assert.assertEquals(emptyList,part);
    }*/
}

package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Credenciales;
import edu.upc.dsa.models.Jugador;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;



@Api(value = "/jugadores", description = "Endpoint to Juego Service")
@Path("/jugadores")
public class JugadorService {

    private GameManager gm;

    public JugadorService() throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException {
        this.gm = GameManagerImpl.getInstance();
        if (gm.JugadoresSize()==0) {
            this.gm.addJugador("Antonio","Fernanditox_47@gmail.com","SweetP2");
            this.gm.addJugador("Lobi","malasia.2010@gmail.com","Perico23");
            this.gm.addJugador("Fernando33","brasil.2005@gmail.com","33?");
        }
    }

    @GET
    @ApiOperation(value = "get all Jugadores")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Jugador.class, responseContainer="List"),
    })

    @Path("/allJugadores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJugadores() {

        List<Jugador> Jugadores = this.gm.findAllJugadores();

        GenericEntity<List<Jugador>> entity = new GenericEntity<List<Jugador>>(Jugadores) {};
        return Response.status(201).entity(entity).build();
    }


    @GET
    @ApiOperation(value = "get a Jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Jugador.class),
            @ApiResponse(code = 404, message = "Error")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJugador(@PathParam("id") String id) {

        try {
            Jugador j = this.gm.getJugador(id);
            return Response.status(201).entity(j).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

    }
    /*   @PUT
       @ApiOperation(value = "More points for jugador", notes = "asdasd")
       @ApiResponses(value = {
               @ApiResponse(code = 201, message = "Successful"),
               @ApiResponse(code = 404, message = "User not found")
       })
       @Path("/")
       public Response nextlevelUser(int id, int points, String date) {

           Jugador j = this.gm.addPointsJugador(id, points, date);

           if (j == null) return Response.status(404).build();
           return Response.status(201).build();
       }*/
//
    @PUT
    @ApiOperation(value = "update nombre de un Jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Error")
    })
    @Path("/updateUsername")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsername(Credenciales credenciales) {
        try {
            this.gm.updateUsername(credenciales.getId(), credenciales.getUsername(), credenciales.getPassword());
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (WrongPasswordException e){
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @ApiOperation(value = "update password de un Jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Error")
    })
    @Path("/updatePassword")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(Credenciales credenciales) {
        try {
            this.gm.updatePassword(credenciales.getId(), credenciales.getNewPassword(), credenciales.getPassword());
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (WrongPasswordException e){
            return Response.status(400).entity(e.getMessage()).build();
        }

    }

    @DELETE
    @ApiOperation(value = "Borrar perfil de un Jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Error")
    })
    @Path("/borrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteJugador(Credenciales credenciales) {
        try {
            this.gm.deleteUser(credenciales.getId(), credenciales.getPassword());
            return Response.status(201).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (WrongPasswordException e){
            return Response.status(400).entity(e.getMessage()).build();
        }

    }




    @POST
    @ApiOperation(value = "register a new Jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Jugador.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code=400,message="Bad request"),
            @ApiResponse(code=404, message = "Not found"),
            @ApiResponse(code=409,message="Conflict")
    })
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response regJugador(Credenciales credenciales) throws FaltanDatosException, NotAnEmailException, JugadorYaExisteException {
        try {
            Jugador jugador = this.gm.addJugador(credenciales.getUsername(), credenciales.getEmail(), credenciales.getPassword());
            return Response.status(201).entity(jugador).build();
        } catch (FaltanDatosException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (NotAnEmailException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (JugadorYaExisteException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @POST
    @ApiOperation(value = "login a jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesful"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Validation Error")

    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginJugador(Credenciales credenciales) throws FaltanDatosException {
        try {
            this.gm.logJugador(credenciales.getUsername(), credenciales.getPassword());
            return Response.status(201).build();
        } catch (FaltanDatosException e){
            return Response.status(500).entity(e.getMessage()).build();
        } catch (UserNotFoundException e){
            return Response.status(400).entity(e.getMessage()).build();
        } catch (WrongPasswordException e){
            return Response.status(400).entity(e.getMessage()).build();
        }
    }




    //public GameService() throws UserNotFoundException, UserEnPartidaException, JuegoNotFoundException, NoNivelException {
//        this.jm = GameManagerImpl.getInstance();
//        if (jm.size()==0) {
//            this.jm.addJugador();
//            this.jm.addJugador();
//        }
//    }
//
//    @POST
//    @ApiOperation(value = "Crear juego")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 400, message = "Error")
//    })
//    @Path("/{id}/{descripcion}/{niveles}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response crearJuegos(@PathParam("id") String id, @PathParam("descripcion") String descripcion, @PathParam("niveles") int niveles){
//        try {
//            Juego j = this.jm.addJuego(id, descripcion, niveles);
//            return Response.status(201).entity(j.toString()).build();
//        } catch (NoNivelException e) {
//            return Response.status(400).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (JuegoYaExisteException e){
//            return Response.status(400).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Puntuaciones de un usuario")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/puntuaciones/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getPuntuaciones(@PathParam("id") String id){
//        try {
//            String r = this.jm.consultarPuntuacion(id);
//            String respuesta = this.jm.stringToJSON(r);
//            return Response.status(201).entity(respuesta).build();
//        } catch (UserNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (UserNoEnPartidaException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Nivel de un usuario")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/nivel/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getNivel(@PathParam("id") String id){
//        try {
//            Partida partida = this.jm.consultarNivelActual(id);
//            return Response.status(201).entity(partida.toString()).build();
//        } catch (UserNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (UserNoEnPartidaException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Inicio de partida")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/iniciar/{idJuego}/{idUsuario}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response inicioPartida(@PathParam("idJuego")String idJuego, @PathParam("idUsuario") String idUsuario){
//        try {
//            Partida partida = this.jm.iniciarPartida(idJuego,idUsuario);
//            return Response.status(201).entity(partida.toString()).build();
//        } catch (UserNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (UserEnPartidaException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (JuegoNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Consultar partidas de un jugador")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/partidas/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getPartidas(@PathParam("id") String id){
//        try {
//            List<Partida> p = this.jm.consultarPartidas(id);
//            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(p) {};
//            return Response.status(201).entity(entity.toString()).build()  ;
//        } catch (UserNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Pasar de nivel")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/nivel/{idUsuario}/{Puntos}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response pasarNivel(@PathParam("idUsuario") String idUsuario, @PathParam("Puntos") int puntos){
//        try {
//            Partida partida = this.jm.pasarDeNivel(puntos,idUsuario);
//            return Response.status(201).entity(partida.toString()).build();
//        } catch (UserNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (UserNoEnPartidaException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Finalizar partida de un jugador")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/finalizar/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response finalizarPartida(@PathParam("id") String id){
//        try {
//            String resultado = this.jm.FinalizarPartida(id);
//            return Response.status(201).entity(this.jm.stringToJSON(resultado)).build();
//        } catch (UserNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        } catch (UserNoEnPartidaException e){
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
//
//
//    @GET
//    @ApiOperation(value = "Lista de jugadores de un juego")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful", response = Jugador.class, responseContainer="List"),
//            @ApiResponse(code = 404, message = "Error")
//    })
//    @Path("/jugadores/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUsuariosPorPuntos(@PathParam("id") String id){
//        try {
//            List<Partida> j = this.jm.consultarUsuariosPorPuntuacion(id);
//            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(j) {};
//            return Response.status(201).entity(entity.toString()).build()  ;
//        } catch (JuegoNotFoundException e) {
//            return Response.status(404).entity(this.jm.stringToJSON(e.getMessage())).build();
//        }
//    }
}
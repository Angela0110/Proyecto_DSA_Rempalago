package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.exceptions.*;

import edu.upc.dsa.models.*;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;
import java.util.List;

@Api(value = "/partidas", description = "Endpoint to Partida Service")
@Path("/partidas")
public class PartidaService {
    private GameManager gm;

    public PartidaService() throws FaltanDatosException, UserNotFoundException {
        this.gm = GameManagerImpl.getInstance();
        if (gm.PartidaSize() == 0) {

        }
    }
    @POST
    @ApiOperation(value = "register a new Partida")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class),
            @ApiResponse(code=400,message="Bad request"),
            @ApiResponse(code=409,message="Conflict")
    })
    @Path("/addPartida")
    @Consumes(MediaType.APPLICATION_JSON)

    public Response addPartida(Partida partida){

        try {
            this.gm.addPartida(partida.getDif(), partida.getPlayer(), partida.getIdMapa());
            return Response.status(201).build();
        } catch (FaltanDatosException e) {
            return Response.status(400).build();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @ApiOperation(value = "historial de partidas de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Partida.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Not found"),
    })

    @Path("/historialPartidas/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidas(@PathParam("username") String username) {
        List<Partida> partidas = null;
        try {
            partidas = this.gm.consultarPartidas(username);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {};
            return Response.status(201).entity(entity).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

    }
}

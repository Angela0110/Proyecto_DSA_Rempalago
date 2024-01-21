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


@Api(value = "/avatares", description = "Endpoint to Avatar Service")
@Path("/avatares")
public class AvatarService {

    private GameManager gm;

    public AvatarService() {
        this.gm = GameManagerImpl.getInstance();

    }
    @GET
    @ApiOperation(value = "avatar de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Avatar.class),
            @ApiResponse(code = 404, message = "Not found"),
    })

    @Path("/{username}/{avatar}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatar(@PathParam("username") String username, @PathParam("avatar") String a) {
        try {
            Avatar avatar = this.gm.getAvatar(username,a);
            return Response.status(201).entity(avatar).build();
        }  catch (AvatarNotFound e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

    }

    @GET
    @ApiOperation(value = "avatares de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Avatar.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Not found"),
    })

    @Path("/listaAvatares/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatares(@PathParam("username") String username) {
        List<Avatar> avatars = null;
        try {
            avatars = this.gm.consultarAvatares(username);
            GenericEntity<List<Avatar>> entity = new GenericEntity<List<Avatar>>(avatars) {};
            return Response.status(201).entity(entity).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }

    }
}

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

@Api(value="/mensajes",description="Endpoint to Mensajes Service")
@Path("/mensajes")
public class MensajesService {
    private GameManager gm;

    public MensajesService(){
        this.gm=GameManagerImpl.getInstance();
        if(gm.mensajesSize()==0){
            try{
                Mensaje mensaje = new Mensaje("Nueva arma en la tienda");
                this.gm.addMensaje(mensaje);
                Mensaje mensaje2 = new Mensaje("Solución de errores 3.5");
                this.gm.addMensaje(mensaje2);
                Mensaje mensaje3 = new Mensaje("Nuevo evento de carnavales");
                this.gm.addMensaje(mensaje3);
                Mensaje mensaje4 = new Mensaje("Regalo de bienvenida");
                this.gm.addMensaje(mensaje4);
                Mensaje mensaje5 = new Mensaje("Nueva competición, apúntate!");
                this.gm.addMensaje(mensaje5);
                Mensaje mensaje6 = new Mensaje("Nuevo nivel disponible");
                this.gm.addMensaje(mensaje6);
                Mensaje mensaje7 = new Mensaje("Solución de errores 3.4.5");
                this.gm.addMensaje(mensaje7);


            }catch (Throwable e){
                throw new RuntimeException(e);
            }
        }
    }

    @GET
    @ApiOperation(value= "get all mensajes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Mensaje.class,responseContainer = "List"),
    })
    @Path("/todosmen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMensajes() {
        List<Mensaje> mensajes=this.gm.getAllMensajes();
        GenericEntity<List<Mensaje>> entity = new GenericEntity<List<Mensaje>>(mensajes) {};

        return Response.status(201).entity(entity).build();
    }
}
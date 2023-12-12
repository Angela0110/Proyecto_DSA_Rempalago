package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Tienda;
import edu.upc.dsa.models.Avatar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


@Api(value = "/tienda", description = "Endpoint to Tienda Service")

@Path("/tienda")
public class TiendaService {

    private GameManager gm;

    public TiendaService() throws ProductoYaExisteException, FaltanDatosException {
        this.gm = GameManagerImpl.getInstance();
        /*if (gm.TiendasSize() == 0) {
            this.gm.addProducto(150, "Espada", "Espada pesada de combate,+50 de daño,-10 de velocidad", 1, 50);
            this.gm.addProducto(200, "Mejora de vida", "Mejora en la salud del avatar", 0, 200);
            this.gm.addProducto(250, "Unobtanium", "Confiere invisivilidad a la persona que la use", 3, 1);
            this.gm.addProducto(100,"Mejora de daño", "+20 de daño", 1,20);
            this.gm.addProducto(100,"Mejora de velocidad","+20 de vida",2,20);
            this.gm.addProducto(200,"Escopeta","+100 de daño,-20 de velocidad",1,100);
        }*/
    }


        @GET
        @ApiOperation(value = "get all productos")
        @ApiResponses(value = {
                @ApiResponse(code = 201, message = "Successful", response = Tienda.class, responseContainer="List"),
        })
        @Path("/todos")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getProductos() {
            List<Tienda> productos = this.gm.findAllProductos();
            GenericEntity<List<Tienda>> entity = new GenericEntity<List<Tienda>>(productos) {};
            return Response.status(201).entity(entity).build();
        }

    @GET
    @ApiOperation(value = "get a Producto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Tienda.class),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducto(@PathParam("id") String id) {

        try {
            Tienda p = this.gm.getProducto(id);
            return Response.status(201).entity(p).build();
        } catch (ProductoNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @ApiOperation(value = "buy a Producto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Jugador.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Capital insufieciente")
    })
    @Path("/comprar/{pnombre}/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprarProducto(@PathParam("pnombre")  String pnombre, @PathParam("username")  String usrnm) {
        try {
            this.gm.comprarProducto(pnombre, usrnm);
            Jugador j = this.gm.getJugador(usrnm);
            return Response.status(201).entity(j).build();
        }
        catch (ProductoNotFoundException e) {
            return Response.status(404).build();
        } catch (CapitalInsuficienteException e) {
            return Response.status(400).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).build();
        } catch (FaltanDatosException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @ApiOperation(value = "new producto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Tienda.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code= 409,message="Conflict")
    })
    @Path("/addproducto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProducto(Tienda producto) {
        try {
            this.gm.addProducto(producto);
            return Response.status(201).entity(producto).build();
        } catch (FaltanDatosException e) {
            return Response.status(500).entity(e.getMessage()).build();
        } catch (ProductoYaExisteException e){
            return Response.status(409).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @ApiOperation(value = "delete producto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Succesful", response = Tienda.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/delete/{id}")
    public Response deleteProducto(@PathParam("id")String id) {
        try {
            this.gm.deleteProducto(id);
            List<Tienda> p = this.gm.findAllProductos();
            GenericEntity<List<Tienda>> entity = new GenericEntity<List<Tienda>>(p) {};
            return Response.status(201).entity(entity).build();
        }
        catch(ProductoNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
        catch(FaltanDatosException e){
            return Response.status(500).entity(e.getMessage()).build();
        }
    }
}

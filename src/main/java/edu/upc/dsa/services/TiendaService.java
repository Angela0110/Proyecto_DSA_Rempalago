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
        if (gm.TiendasSize() == 0) {
            this.gm.addProducto("https://img1.freepng.es/20180317/kce/kisspng-the-legend-of-zelda-the-wind-waker-the-legend-of-master-sword-cliparts-5aacb91e87cbb4.6204607315212690225562.jpg",150, "Espada", "Espada pesada de combate, +2 de daño", 1, 2);
            this.gm.addProducto("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCSLDNmYWkEM8wHLMdN5mfncTKoGxHCXr3mzcOZHXwYw&s",200, "Mejora de vida", "Mejora en la salud del avatar", 0, 2);
            this.gm.addProducto("https://wow.zamimg.com/modelviewer/live/webthumbs/npc/114/101746.png", 250, "Unobtanium", "Confiere invisivilidad a la persona que la use", 3, 1);
            this.gm.addProducto("https://i.etsystatic.com/22467704/r/il/359084/2613034126/il_570xN.2613034126_ehhd.jpg", 100,"Mejora de daño", "+1 de daño", 1,1);
            this.gm.addProducto("https://cdn-icons-png.flaticon.com/512/84/84570.png", 100,"Mejora de velocidad","+1 de vida",2,1);
            this.gm.addProducto("https://cdn-icons-png.flaticon.com/512/6753/6753736.png", 200,"Escopeta","+1 de daño",1,3);

        }
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
        catch (ProductoNotFoundException | AvatarNotFound | UserNotFoundException | SQLException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (CapitalInsuficienteException | FaltanDatosException | MaximoException e) {
            return Response.status(400).entity(e.getMessage()).build();
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

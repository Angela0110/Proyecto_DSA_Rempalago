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

    public AvatarService() { //throws NotAnEmailException, FaltanDatosException, JugadorYaExisteException, UserNotFoundException {
        this.gm = GameManagerImpl.getInstance();
        if (gm.JugadoresSize()==0) {
            try {
                this.gm.addJugador("Antonio","Fernanditox_47@gmail.com","SweetP2");
                this.gm.addJugador("Lobi","malasia.2010@gmail.com","Perico23");
                this.gm.addJugador("Fernando33","brasil.2005@gmail.com","33?");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }


}

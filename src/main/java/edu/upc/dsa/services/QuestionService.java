package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.Question;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/question", description = "Endpoint to Question Service")
@Path("/question")
public class QuestionService {

    private GameManager gm;

    public QuestionService()  {
        this.gm = GameManagerImpl.getInstance();
    }

    //Minimo 2

    @POST
    @ApiOperation(value = "add a question")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Question.class),
            @ApiResponse(code=400,message="Bad request"),
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)

    public Response addQuestion(Question question) {

        try {
            Question question1 = this.gm.addQuestion(question.getTitle(), question.getMessage(), question.getSender());
            return Response.status(201).entity(question1).build();
        } catch (Exception e) {
            return Response.status(400).entity("Error").build();
        }
    }

    @GET
    @ApiOperation(value = "all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Question.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Not found"),
    })

    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allQuestions() {
        List<Question> questions = null;
        try {
            questions = this.gm.findAllQuestions();
            GenericEntity<List<Question>> entity = new GenericEntity<List<Question>>(questions) {};
            return Response.status(201).entity(entity).build();
        } catch (Exception e) {
            return Response.status(404).entity("Error").build();
        }

    }
}

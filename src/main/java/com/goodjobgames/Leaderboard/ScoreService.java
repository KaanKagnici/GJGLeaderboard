package com.goodjobgames.Leaderboard;

import com.goodjobgames.Leaderboard.model.DBManager;
import com.goodjobgames.Leaderboard.model.domain.ScoreSubmission;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/score")
public class ScoreService {

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitScore(ScoreSubmission scoreSubmission) {


        DBManager dbManager = new DBManager();

        if (dbManager.submitScore(scoreSubmission)) {
            return Response.status(Response.Status.OK).entity("Score is submitted!").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User does not exist!").build();
        }


    }


}

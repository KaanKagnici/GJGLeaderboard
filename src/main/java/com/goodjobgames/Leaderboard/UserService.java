package com.goodjobgames.Leaderboard;

import com.goodjobgames.Leaderboard.model.DBManager;
import com.goodjobgames.Leaderboard.model.domain.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/user")
public class UserService {


    @GET
    @Path("/profile/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayer(@PathParam("guid") String id) {
        DBManager dbManager = new DBManager();


        Player player = dbManager.getPlayer(id);


        if (player != null) {
            return Response.status(Response.Status.OK).entity(player).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User does not exist!").build();
        }


    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlayer(Player player) {


        DBManager dbManager = new DBManager();
        boolean result = dbManager.createPlayer(player);

        return Response.status(Response.Status.OK).entity(result ? "User has been added!" : "User already exists.").build();


    }



    @POST
    @Path("/populate/{amount}")
    public Response populate(@PathParam("amount")int amount){
        DBManager dbManager = new DBManager();
        dbManager.populate(amount);
        return Response.ok().entity(amount + " players have been created!").build();
    }

    @DELETE
    @Path("/flush")
    public Response flush(){
        DBManager dbManager = new DBManager();
        dbManager.flushDB();
        return Response.ok().entity("All data is gone!").build();
    }


}

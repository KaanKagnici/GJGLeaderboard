package com.goodjobgames.Leaderboard;

import com.goodjobgames.Leaderboard.model.DBManager;
import com.goodjobgames.Leaderboard.model.domain.Player;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class LeaderboardService {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/leaderboard")
    public List<Player> getLeaderboard(@QueryParam("page") int page) {
        DBManager dbManager = new DBManager();


        return dbManager.getLeaderboard(page);

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leaderboard/{country_iso_code}")
    public List<Player> getLocalLeaderboard(@PathParam("country_iso_code") String country,@QueryParam("page") int page) {
        DBManager dbManager = new DBManager();


        return dbManager.getLocalLeaderboard(country,page);

    }



}

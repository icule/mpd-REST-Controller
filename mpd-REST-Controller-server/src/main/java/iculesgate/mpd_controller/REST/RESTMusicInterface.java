package iculesgate.mpd_controller.REST;

import iculesgate.mpd_controller.data.TaggedMusicInfo;
import iculesgate.mpd_controller.database.DatabaseOperationImpossible;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class RESTMusicInterface extends RESTAbstractInterface{

    @Path("{authToken}/music/statistic")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void postTag(@PathParam("authToken") String token) throws AuthenticationException, DatabaseOperationImpossible {
        checkToken(token);

    }
}

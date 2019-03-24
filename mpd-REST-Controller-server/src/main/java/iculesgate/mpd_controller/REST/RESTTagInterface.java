package iculesgate.mpd_controller.REST;

/**
 * Created by icule on 17/07/17.
 */

import iculesgate.mpd_controller.data.Tag;
import iculesgate.mpd_controller.database.DatabaseOperationImpossible;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("tag")
public class RESTTagInterface extends RESTAbstractInterface{

    @Path("{authToken}/tag")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void postTag(@PathParam("authToken") String token, String body) throws AuthenticationException, DatabaseOperationImpossible {
        checkToken(token);

        Tag tag = Tag.valueOf(body);
        getCore().addTagToCurrentMusic(tag);
    }
}

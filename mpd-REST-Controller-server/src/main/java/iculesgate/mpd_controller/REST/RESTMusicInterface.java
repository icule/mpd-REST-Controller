package iculesgate.mpd_controller.REST;

import iculesgate.mpd_controller.data.TaggedMusicInfo;
import iculesgate.mpd_controller.database.DatabaseOperationImpossible;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("music")
public class RESTMusicInterface extends RESTAbstractInterface{

    @Path("{authToken}/statistic")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String postTag(@PathParam("authToken") String token) throws AuthenticationException, DatabaseOperationImpossible {
        checkToken(token);

        List<TaggedMusicInfo> res = getDatabaseManager().getAllMusic();
        return getGson().toJson(res);
    }
}

package iculesgate.mpd_controller.REST;

import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Root resource (exposed at "player" path)
 */
@Path("player")
public class RESTPlayerInterface extends RESTAbstractInterface{

    @Path("{authToken}/music")
    @GET
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.TEXT_PLAIN)
    public String getMusic(@PathParam("authToken") String token) throws IOException, InterruptedException, MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            return getMPDClient().getInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/next")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putNext(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().next();
            return getMPDClient().getInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/play")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putPlay(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().play();
            return getMPDClient().getInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/pause")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putPause(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().pause();
            return getMPDClient().getInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/stop")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putStop(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().stop();
            String res = getMPDClient().getInfo();
            return res;
        }
        throw new ForbiddenException("Bad token");
    }
}

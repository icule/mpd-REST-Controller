package server;

import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Root resource (exposed at "player" path)
 */
@Path("player")
public class RESTPlayerInterface {
    @Inject
    private Application application;

    private String getAuthToken(){
        return (String)application.getProperties().get("authToken");
    }

    private MPDClient getMPDClient() {
        return (MPDClient)application.getProperties().get("mpdClient");
    }


    @Path("{authToken}/music")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMusic(@PathParam("authToken") String token) throws IOException, InterruptedException, MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            return getMPDClient().getSongInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/next")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putNext(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().next();
            return getMPDClient().getSongInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/play")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putPlay(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().play();
            return getMPDClient().getSongInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/pause")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putPause(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().pause();
            return getMPDClient().getSongInfo();
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/stop")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putStop(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException {
        if(token.equals(getAuthToken())){
            getMPDClient().stop();
            return getMPDClient().getSongInfo();
        }
        throw new ForbiddenException("Bad token");
    }
}

package iculesgate.mpd_controller.REST;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "player" path)
 */
@Path("player")
public class RESTPlayerInterface extends RESTAbstractInterface{

    @Path("{authToken}/music")
    @GET
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.TEXT_PLAIN)
    public String getMusic(@PathParam("authToken") String token) throws AuthenticationException {
        checkToken(token);

        return getMPDClient().getInfo();
    }

    @Path("{authToken}/next")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putNext(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().next();
        return getMPDClient().getInfo();
    }

    @Path("{authToken}/play")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putPlay(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().play();
        return getMPDClient().getInfo();
    }

    @Path("{authToken}/pause")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putPause(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().pause();
        return getMPDClient().getInfo();
    }

    @Path("{authToken}/stop")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String putStop(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().stop();
        return getMPDClient().getInfo();
    }
}

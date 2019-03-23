package iculesgate.mpd_controller.REST;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "player" path)
 */
@Path("player")
public class RESTPlayerInterface extends RESTAbstractInterface{

    private String getInfo() {
        return getGson().toJson(getMPDClient().getInfo());
    }

    @Path("{authToken}/music")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMusic(@PathParam("authToken") String token) throws AuthenticationException {
        checkToken(token);
        return getInfo();
    }

    @Path("{authToken}/next")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String putNext(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().next();
        return getInfo();
    }

    @Path("{authToken}/play")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String putPlay(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().play();
        return getInfo();
    }

    @Path("{authToken}/pause")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String putPause(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().pause();
        return getInfo();
    }

    @Path("{authToken}/stop")
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String putStop(@PathParam("authToken") String token, String body) throws AuthenticationException {
        checkToken(token);

        getMPDClient().stop();
        return getInfo();
    }
}

package server;

import common.ConfigurationManager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Root resource (exposed at "player" path)
 */
@Path("player")
public class RESTInterface {
    @Inject
    private Application application;

    private String getAuthToken(){
        return (String)application.getProperties().get("authToken");
    }


    @Path("{authToken}/music")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMusic(@PathParam("authToken") String token) throws IOException, InterruptedException {
        if(token.equals(getAuthToken())){
            return Executor.executeCommand("mpc");
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/next")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putNext(@PathParam("authToken") String token, String body) throws IOException, InterruptedException {
        if(token.equals(getAuthToken())){
            return Executor.executeCommand("mpc next");
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/play")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putPlay(@PathParam("authToken") String token, String body) throws IOException, InterruptedException {
        if(token.equals(getAuthToken())){
            return Executor.executeCommand("mpc play");
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/pause")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putPause(@PathParam("authToken") String token, String body) throws IOException, InterruptedException {
        if(token.equals(getAuthToken())){
            return Executor.executeCommand("mpc pause");
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("{authToken}/stop")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public String putStop(@PathParam("authToken") String token, String body) throws IOException, InterruptedException {
        if(token.equals(getAuthToken())){
            return Executor.executeCommand("mpc stop");
        }
        throw new ForbiddenException("Bad token");
    }
}

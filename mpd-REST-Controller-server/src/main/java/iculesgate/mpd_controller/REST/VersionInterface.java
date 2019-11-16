package iculesgate.mpd_controller.REST;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class VersionInterface {

    @Path("version")
    @GET
    public String getVersion() {
        return "Version";
    }
}

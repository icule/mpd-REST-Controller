package iculesgate.mpd_controller.REST;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionHandler implements ExceptionMapper<AuthenticationException> {
    @Override
    public Response toResponse(AuthenticationException e) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).type("text/plain").build();
    }
}

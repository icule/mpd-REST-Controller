package iculesgate.mpd_controller.REST;

/**
 * Created by icule on 17/07/17.
 */

import com.google.inject.Inject;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.objects.MPDSong;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("tag")
public class RESTTagInterface extends RESTAbstractInterface{
    @Inject
    private Application application;


    @Path("{authToken}/tag")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String postTag(@PathParam("authToken") String token, String body) throws MPDPlayerException, MPDDatabaseException, SQLException {
        System.out.println("tag attack " + body);
        if(token.equals(getAuthToken())){
            MPDSong mpdSong = getMPDClient().getMusicInfo();
            Tag tag = Tag.valueOf(body);
            MusicInfo info = new MusicInfo(mpdSong.getFile(), mpdSong.getTitle(), mpdSong.getArtistName(), tag);
            System.out.println("lala");
            getDatabaseManager().registerTag(info);
        }
        throw new ForbiddenException("Bad token");
    }
}

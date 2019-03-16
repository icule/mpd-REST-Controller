package iculesgate.mpd_controller.REST;

/**
 * Created by icule on 17/07/17.
 */

import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.objects.MPDSong;
import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("tag")
public class RESTTagInterface extends RESTAbstractInterface{

    @Path("{authToken}/tag")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String postTag(@PathParam("authToken") String token, String body) throws MPDPlayerException, SQLException {
        if(token.equals(getAuthToken())){
            MPDSong mpdSong = getMPDClient().getMusicInfo();
            Tag tag = Tag.valueOf(body);
            MusicInfo info = new MusicInfo(mpdSong.getFile(), mpdSong.getTitle(), mpdSong.getArtistName(), tag);
            getDatabaseManager().registerTag(info);
        }
        throw new ForbiddenException("Bad token");
    }

    @Path("list")
    @GET
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.TEXT_PLAIN)
    public String generateTagPage() throws SQLException {
        List<MusicInfo> musicList = getDatabaseManager().getTaggedMusic();
        return musicList.toString();
    }

}

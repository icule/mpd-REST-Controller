package iculesgate.mpd_controller.REST;

/**
 * Created by icule on 17/07/17.
 */

import iculesgate.mpd_controller.data.MusicInfo;
import iculesgate.mpd_controller.data.Tag;
import org.bff.javampd.song.MPDSong;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("tag")
public class RESTTagInterface extends RESTAbstractInterface{

    @Path("{authToken}/tag")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void postTag(@PathParam("authToken") String token, String body) throws SQLException, AuthenticationException {
        checkToken(token);

        MPDSong mpdSong = getMPDClient().getMusicInfo();
        Tag tag = Tag.valueOf(body);
        MusicInfo info = new MusicInfo(mpdSong.getFile(), mpdSong.getTitle(), mpdSong.getArtistName(), null);
        getDatabaseManager().registerTag(info);
    }

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generateTagPage() throws SQLException {
        List<MusicInfo> musicList = getDatabaseManager().getTaggedMusic();
        return getGson().toJson(musicList);
    }

}

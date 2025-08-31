import java.io.Serializable;

public class PlaylistNode implements Serializable {
    Playlist playlist;
    PlaylistNode next;
    PlaylistNode prev;
    public PlaylistNode(Playlist playlist){
        this.playlist = playlist;
        this.next = null;
        this.prev = null;
    }
}

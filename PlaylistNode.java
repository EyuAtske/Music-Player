public class PlaylistNode {
    Playlist playlist;
    PlaylistNode next;
    PlaylistNode prev;
    public PlaylistNode(Playlist playlist){
        this.playlist = playlist;
        this.next = null;
        this.prev = null;
    }
}

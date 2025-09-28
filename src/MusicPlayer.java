package src;

public class MusicPlayer {
    public static void main(String[] args) {
        Collection collection = Collection.loadCollection();
        new MediaPlayer(collection);
    }
}

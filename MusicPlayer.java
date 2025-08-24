public class MusicPlayer {
    public static void main(String[] args) {
        Collection collection = new Collection();
        collection.loadCollection();
        new MediaPlayer(collection);
    }
}

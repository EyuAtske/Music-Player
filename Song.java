public class Song {
    private String title, artist, songPath;
    private double duration;
    public Song(){
        this.title = "Unknown";
        this.artist = "Unknown";
        this.songPath = "Unknown";
    }
    public Song(String title, String artist, String songPath){
        this.title = title;
        this.artist = artist;
        this.songPath = songPath;
    }
    public String getSongPathString(){
        return songPath;
    }
    public String getTitleString(){
        return title;
    }
    public String getArtisString(){
        return artist;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }

}

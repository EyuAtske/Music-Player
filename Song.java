import java.io.Serializable;

public class Song implements Serializable {
    private String title, artist, songPath;
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
    public String getArtistString(){
        return artist;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }

    @Override
    public String toString(){
        return title + " - " + artist;
    }

}

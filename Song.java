public class Song {
    private String title, artist, songPath;
    private double duration;
    public Song(){
        this.title = "Unknown";
        this.artist = "Unknown";
        this.songPath = "Unknown";
        this. duration = 0.00;
    }
    public Song(String title, String artist, String songPath, double duration){
        this.title = title;
        this.artist = artist;
        this.songPath = songPath;
        this. duration = duration;
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
    public double getduration(){
        return duration;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public void setDuration(double duration){
        this.duration = duration;
    }

}

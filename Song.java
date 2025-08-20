public class Song {
    private String title, artist;
    private double duration;
    public Song(){
        this.title = "Unknown";
        this.artist = "Unknown";
        this. duration = 0.00;
    }
    public Song(String title, String artist, double duration){
        this.title = title;
        this.artist = artist;
        this. duration = duration;
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

}

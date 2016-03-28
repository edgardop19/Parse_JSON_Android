package inducesmile.com.parsejsoninandroid;

public class ItemObject {

    private String songName;
    private String songYear;
    private String songAuthor;

    public ItemObject(String songName, String songYear, String songAuthor) {
        this.songName = songName;
        this.songYear = songYear;
        this.songAuthor = songAuthor;
    }
    public String getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }
    public String getSongYear() {
        return songYear;
    }
    public void setSongYear(String songYear) {
        this.songYear = songYear;
    }
    public String getSongAuthor() {
        return songAuthor;
    }
    public void setSongAuthor(String songAuthor) {
        this.songAuthor = songAuthor;
    }
}

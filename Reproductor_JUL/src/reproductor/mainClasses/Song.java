package reproductor.mainClasses;

public class Song {
	private String name;
	private String artist;
	private String album;
	private String track;
	private String path;
	private String genre;
	private String year;
	
	public Song(String name, String artist, String album, String track, String path, String genre, String year) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.track = track;
		this.path = path;
		this.genre = genre;
		this.year = year;
	}

	public Song() {
		super();
		this.name = "";
		this.artist = "";
		this.album = "";
		this.track = "";
		this.path="";
		this.year = "";
		this.genre = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getGenre() {
		return genre;
	}
	
	
	
	
	@Override
    public boolean equals(Object o) {
        if (!(o instanceof Song))
            return false;

        Song s = (Song) o;
        return s.path.equals(this.path);
    }
	
}

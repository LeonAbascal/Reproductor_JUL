package reproductor.mainClasses;

public class Song {
	private String name;
	private String artist;
	private String album;
	private String track;
	private String duration;
	private String path;
	
	public Song(String name, String artist, String album, String track, String duration, String path) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.track = track;
		this.duration = duration;
		this.path=path;
	}

	public Song() {
		super();
		this.name = "";
		this.artist = "";
		this.album = "";
		this.track = "";
		this.duration = "";
		this.path="";
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	@Override
    public boolean equals(Object o) {
        if (!(o instanceof Song))
            return false;

        Song s = (Song) o;
        return s.name.equals(this.name);
    }
	
}

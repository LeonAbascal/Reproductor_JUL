package reproductor.mainClasses;

public class Song {
	private String name;
	private String artist;
	private String album;
	private int track;
	private String duration;
	
	public Song(String name, String artist, String album, int track, String duration) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.track = track;
		this.duration = duration;
	}

	public Song() {
		super();
		this.name = "";
		this.artist = "";
		this.album = "";
		this.track = 0;
		this.duration = "";
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

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}

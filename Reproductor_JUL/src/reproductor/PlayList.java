package reproductor;

import java.util.ArrayList;

public class PlayList {
	private ArrayList<Song> songs = new ArrayList<Song>();
	private String title;
	
	public PlayList(ArrayList<Song> songs, String title) {
		this.songs = songs;
		this.title = title;
	}
	
	public PlayList() {
		this.songs = null;
		this.title = "";
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "PlayList [title=" + title + "]";
	}
	
}

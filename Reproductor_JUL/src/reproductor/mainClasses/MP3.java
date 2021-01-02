package reproductor.mainClasses;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Logger;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import database.DBManager;
import javazoom.jl.player.Player;


public class MP3 {
	private String filename;
	private Player player; 
	static Logger logger = Logger.getLogger(DBManager.class.getName());

	// constructor that takes the name of an MP3 file
	public MP3(String filename) {
		this.filename = filename;
	}

	public void close() { if (player != null) player.close(); }

	// play the MP3 file to the sound card
	public void play() {
		try {
			FileInputStream fis     = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
		}
		catch (Exception e) {
			System.out.println("Problem playing file " + filename);
			System.out.println(e);
		}

		// run in new thread to play in background
		new Thread() {
			public void run() {
				try { player.play(); }
				catch (Exception e) { System.out.println(e); }
			}
		}.start();




	}


	// test client
	public static void main(String[] args) {
		File f = new File("MusicFiles\\Arctic Monkeys - Do I Wanna Know (Official Video).mp3");
		System.out.println(MP3.getTrackNoTag(f));
		try {
			Mp3File mp3 = new Mp3File(f);
			System.out.println("ID3v1: " + mp3.hasId3v1Tag());
			System.out.println("ID3v2: " + mp3.hasId3v2Tag());
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				System.out.println(tag.getTitle());
			}
		} catch (Exception e) { System.err.println(e);}
		/*
        String filename = "MusicFiles\\Beave - Too Much Ft. Bethany Lamb (Torin Dundas Remix).mp3";
        MP3 mp3 = new MP3(filename);
        mp3.play();

        mp3.close();

        // play from the beginning
        mp3 = new MP3(filename);
        mp3.play();
		 */


	}


	public static String getTitleTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getTitle();
			}

			else if (mp3.hasId3v1Tag()) { 
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getTitle();
			}

		} catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return "";
	}

	public static String getArtistTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getArtist();
			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getArtist();
			}

		} catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return "";
	}

	public static String getAlbumTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getAlbum();
			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getAlbum();
			}

		} catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return "";
	}

	public static String getTrackNoTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getTrack();
			} 
			
			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getTrack();
			}

		} catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return "";
	}

	public static String getGenreTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getGenreDescription();
			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getGenreDescription();
			}

		} catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return "";
	}

	public static String getYearTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getYear();
			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getYear();
			}

		} catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return "";
	}


	/**
	 * @param f
	 * @return Returns a String array with the content of the tags on the following order: title, artist, album, trackNo, genre, year 
	 */
	public static String[] getAllTags(File f) {
		String[] tags = new String[6];

		try {

			Mp3File mp3 = new Mp3File(f);
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tags[0] = tag.getTitle();
				tags[1] = tag.getArtist();
				tags[2] =  tag.getAlbum();
				tags[3] =  tag.getTrack();
				tags[4] =  tag.getGenreDescription();
				tags[5] =  tag.getYear();
			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tags[0] = tag.getTitle();
				tags[1] = tag.getArtist();
				tags[2] =  tag.getAlbum();
				tags[3] =  tag.getTrack();
				tags[4] =  tag.getGenreDescription();
				tags[5] =  tag.getYear();
			} 



		}  catch (Exception e) { logger.warning("Exception trying to read title tag of mp3 file (" + f.getAbsolutePath() + ")"); }

		return tags;
	}

	public static void setTitleTag(File f, String title) {

		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setTitle(title);

			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tag.setTitle(title);
			}

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

	public static void setArtistTag(File f, String artist) {
		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setArtist(artist);

			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tag.setArtist(artist);
			}

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

	public static void setAlbumTag(File f, String album) {
		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setAlbum(album);

			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tag.setAlbum(album);
			}

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

	public static void setTrackNoTag(File f, String trackNo) {
		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setTrack(trackNo);

			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tag.setTrack(trackNo);
			}

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

	public static void setYearTag(File f, String year) {
		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setYear(year);

			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tag.setYear(year);
			}

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

	public static void setStandardTags(File f, String title, String artist, String album) {
		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setTitle(title);
				tag.setArtist(artist);
				tag.setAlbum(album);


			}

			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				tag.setTitle(title);
				tag.setArtist(artist);
				tag.setAlbum(album);
			}

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

}
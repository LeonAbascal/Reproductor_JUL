package reproductor.mainClasses;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import com.mpatric.mp3agic.ID3Wrapper;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Genres;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;

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


	
	public static void main(String[] args) {
		File f = new File("MusicFiles\\Arctic Monkeys - Do I Wanna Know (Official Video).mp3");
		File f2 = new File("MusicFiles\\Outrun Project.mp3"); // ID 3v1
		System.out.println(MP3.getGenreTag(f));
		try {
			Mp3File mp3 = new Mp3File(f2);
			System.out.println("ID3v1: " + mp3.hasId3v1Tag());
			System.out.println("ID3v2: " + mp3.hasId3v2Tag());
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				System.out.println(tag.getGenre());
			}
			
			else if (mp3.hasId3v1Tag()) {
				ID3v1 tag = mp3.getId3v1Tag();
				System.out.println(tag.getArtist());
			}
			
		} catch (Exception e) { System.err.println(e);}
		


	}
	

	public static String getTitleTag(File f) {
		try {
			Mp3File mp3 = new Mp3File(f);
			//System.out.println(mp3.hasId3v2Tag());
			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				return tag.getTitle();
			}

			else if (mp3.hasId3v1Tag()) { 
				ID3v1 tag = mp3.getId3v1Tag();
				return tag.getTitle();
			}
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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

			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}

	/** Will only work with ID3v2
	 * 
	 * @param f File to write
	 * @param genreDescription Genre name
	 */
	public static void setGenreTag(File f, String genreDescription) {
		
		try {
			Mp3File mp3 = new Mp3File(f);

			if (mp3.hasId3v2Tag()) {
				ID3v2 tag = mp3.getId3v2Tag();
				tag.setGenreDescription(genreDescription);

			}

			else if (mp3.hasId3v1Tag()) {
				int index = ID3v1Genres.matchGenreDescription(genreDescription);
				
				if (index >= 0) {
					ID3v1 tag = mp3.getId3v1Tag();
					tag.setGenre(index);					
				} else {
					logger.info("Not valid genre name");
				}
			}
			
			else {
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

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
			
			else {
				System.err.println("Not supported tag");
				logger.info("File (" + f + ") is using an unsupported tagging format");
			}
			
			// WRITE CHANGES TO FILE
			updateTags(f, mp3);

		} catch (Exception e) { logger.warning("Exception trying to write tag of mp3 file (" + f.getAbsolutePath() + ") \n" + e); }
	}
	
	
	private static void updateTags(File f, Mp3File mp3){
		File tempFile = new File("MusicFiles\\tmp\\tmp.mp3");
		try {
			mp3.save(tempFile.getAbsolutePath());
			//f.delete();
			mp3.save(f.getAbsolutePath());
		} catch (NotSupportedException | IOException e) { System.err.println(e);}
	}

}
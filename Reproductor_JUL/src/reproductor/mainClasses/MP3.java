package reproductor.mainClasses;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import database.DBManager;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import reproductor.windows.MainWindow;


// parte del código para la reproducción de archivos mp3 viene de: https://thiscouldbebetter.wordpress.com/2011/06/14/playing-an-mp3-from-java-using-jlayer/
public class MP3 extends PlaybackListener implements Runnable {
	private String filename;
	//private Player player; 
	static Logger logger = Logger.getLogger(DBManager.class.getName());
	private AdvancedPlayer player;
	private Thread playerThread;
	boolean playing;

	/** MP3 class that allows mp3 files playback
	 * 
	 * @param filename file name must always be absolute path
	 */
	public MP3(String filename) {
		this.filename = filename;
		playing = false;
	}

	public void close() { if (player != null) player.close(); }

	// PLAYBACK METHODS
	public void play() {
		try {
			String urlAsString = "file:///" + filename;

			this.player = new AdvancedPlayer(new URL(urlAsString).openStream(),
					javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

			this.player.setPlayBackListener(this);

			this.playerThread = new Thread(this, "AudioPlayerThread");

			this.playerThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void stop() {
		this.player.stop();
	}
	
	public void playbackStarted(PlaybackEvent playbackEvent) {
		this.playing = true;
	}

	public void playbackFinished(PlaybackEvent playbackEvent) {
		MainWindow.nextSong();
		this.playing = false;
	}
	
	public void run() {
		try {
			this.player.play();
		} catch (javazoom.jl.decoder.JavaLayerException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	
	// TAGGING METHODS (STATIC)

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

}
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import reproductor.mainClasses.PlayList;
import reproductor.mainClasses.Song;
import reproductor.mainClasses.User;


public class DBManager {
	
	private static String databasePath = "database/JUL_database.db";
	private static Connection conn;
	static Logger logger = Logger.getLogger(DBManager.class.getName());

	private static boolean loadDriver() {
		boolean opSuccess;
		try { 
			Class.forName("org.sqlite.JDBC"); 
			logger.info("Database drivers were loaded successfully.");
			
			opSuccess = true;

		} catch (ClassNotFoundException e) { 
			logger.severe("Database drivers could not be loaded.");
			opSuccess = false;
		}

		return opSuccess;

	}

	private static boolean connect() {
		boolean opSuccess;
		
		if (loadDriver()) {
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
				opSuccess = true;
			} catch (SQLException e) {
				logger.severe("Error connecting to the database due to a SQL exception: ");
				logger.info(e.toString());
				opSuccess = false;
			}
		} else {
			opSuccess = false;
			logger.severe("Database driver could not be loaded, thus, the connection can not be opened.");
		}
		
		
		return opSuccess;
	}
	
	private static boolean disconnect() {
		boolean opSuccess;
		try {
			conn.close();
			logger.info("Connection successfully closed.");
			opSuccess = true;
		} catch (SQLException e) {
			logger.severe("Error closing the connection.");
			opSuccess = false;
		}
		
		return opSuccess;
	}
	
	
	public static String getDatabaseFilePath() {
		return DBManager.databasePath;
	}


	public static List<User> getAllUsers() {
		connect();

		List<User> users = new ArrayList<User>();

		try (Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery("SELECT * FROM user_t");

			while(rs.next()) {
				User user = new User();
				user.setName(rs.getString("username"));
				user.setPassword(rs.getString("pass"));
				users.add(user);
			}
			
			logger.info("All users were obtained succesfully");

		} catch (SQLException e) {
			logger.severe("Error retrieving all users from database.");
			logger.info(e.toString());
		}
		
		disconnect();
		return users;
	}
	public static void store(User user) {
		connect();
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_t (username,pass) VALUES (?, ?)")){

			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPassword());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.severe("Could not store the user into the database");
			logger.info(e.toString());
		}
		disconnect();
	}
	public static void storePlaylist(PlayList playlist,String username) {
		connect();
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO playlist (name_p,belong_to_user) VALUES (?, ?)")){

			stmt.setString(1, playlist.getTitle());
			stmt.setString(2, username);

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.severe("Could not store the playlist into the database");
			logger.info(e.toString());
		}
		disconnect();
	}
	public static void storeSong(Song song, String playlistName) {
		connect();
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO  belongs (name_p, song_path) VALUES (?, ?)")){

			stmt.setString(1, playlistName);
			stmt.setString(2, song.getPath());

			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.severe("Could not store the song into the database");
			logger.info(e.toString());
		}
		try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO song (name, artist, album, track, genre, yearS, song_path) VALUES (?, ?, ?, ?, ?, ?, ?)")){

			stmt2.setString(1, song.getName());
			stmt2.setString(2, song.getArtist());
			stmt2.setString(3, song.getAlbum());
			stmt2.setString(4, song.getTrack());
			stmt2.setString(5, song.getGenre());
			stmt2.setString(5, song.getYear());
			stmt2.setString(7, song.getPath());

			stmt2.executeUpdate();

		} catch (SQLException e) {
			logger.severe("Could not store the song into the database");
			logger.info(e.toString());
		}
		disconnect();
	}
	public static List<String> getAllPlaylist(String user) {
		connect();

		List<String> playlists = new ArrayList<String>();

		try (PreparedStatement stmt = conn.prepareStatement("SELECT name_p FROM playlist WHERE belong_to_user=?")) {
			
			stmt.setString(1, user);
			
			
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				playlists.add(rs.getString("name_p"));
			}
			
			logger.info("All users were obtained succesfully");

		} catch (SQLException e) {
			logger.severe("Error retrieving all playlists from database.");
			logger.info(e.toString());
		}
		
		disconnect();
		return playlists;
	}
	public static List<Song> getSongs(String playlist, String user) {
		connect();

		List<Song> songs = new ArrayList<Song>();

		try (PreparedStatement stmt = conn.prepareStatement("SELECT s.name, s.artist, s.album, s.track, s.genre, s.yearS, s.song_path FROM song s,belongs b,playlist p WHERE p.belong_to_user=? AND p.name_p=? AND p.name_p=b.name_p AND s.song_path=b.song_path ")) {
			
			stmt.setString(1, user);
			stmt.setString(2, playlist);
			
			
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {
				Song song = new Song();
				song.setName(rs.getString("name"));
				song.setArtist(rs.getString("artist"));
				song.setAlbum(rs.getString("album"));
				song.setTrack(rs.getString("track"));
				song.setGenre(rs.getString("genre"));
				song.setYear(rs.getString("yearS"));
				song.setPath(rs.getString("song_path"));
				songs.add(song);
			}
			
			logger.info("All users were obtained succesfully");

		} catch (SQLException e) {
			logger.severe("Error retrieving all songs from database.");
			logger.info(e.toString());
		}
		
		disconnect();
		return songs;
	}
	public static Map<String, Integer> getAllUsersPlaylistsCount() {
		connect();

		Map<String, Integer> usersPlaylistCount=new HashMap<String, Integer>();

		try (Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery("Select Distinct belong_to_user as Usuario ,count(belong_to_user) as TotalPlaylist from playlist group by belong_to_user;");

			while(rs.next()) {
				usersPlaylistCount.put(rs.getString("Usuario"),rs.getInt("TotalPlaylist"));
			}
			
			logger.info("All users were obtained succesfully");

		} catch (SQLException e) {
			logger.severe("Error retrieving all users from database.");
			logger.info(e.toString());
		}
		
		disconnect();
		return usersPlaylistCount;
	}
	public static void delete(String playlist, String user) {
		connect();

		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM playlist WHERE  belong_to_user=? AND name_p=?;")) {
			
			stmt.setString(1, user);
			stmt.setString(2, playlist);
			
			stmt.executeUpdate();
			logger.info("Playlist deleted succesfully");

		} catch (SQLException e) {
			logger.severe("Error deleting playlist");
			logger.info(e.toString());
		}
		
		disconnect();
	}

}

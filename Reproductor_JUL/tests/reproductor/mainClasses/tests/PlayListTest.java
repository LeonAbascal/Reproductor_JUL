package reproductor.mainClasses.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.PlayList;
import reproductor.mainClasses.Song;

public class PlayListTest {
	
	private ArrayList<Song> songs;
	private PlayList p;
	private PlayList emptyP;
	
	@Before
	public void setUp() {
		songs = new ArrayList<Song>();
		p = new PlayList(songs, "Lista");
		emptyP = new PlayList();
	}

	@Test
	public void testGetSongs() {
		assertEquals(songs, p.getSongs());
	}
	
	@Test
	public void testSetSongs() {
		emptyP.setSongs(songs);
		assertEquals(songs, emptyP.getSongs());
	}

	@Test
	public void testGetTitle() {
		assertEquals("Lista", p.getTitle());
	}
	
	@Test
	public void testSetTitle() {
		emptyP.setTitle("tituloSet");
		assertEquals("tituloSet", emptyP.getTitle());
	}
	
	@Test
	public void testToString() {
		assertEquals("PlayList [title=Lista]", p.toString());
	}
	
}

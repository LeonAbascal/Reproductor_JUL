package reproductor.mainClasses.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.Song;

public class SongTest {

	private Song s;
	private Song emptyS;

	@Before
	public void setUp() {

		emptyS = new Song();
		s = new Song("Fire", "Pedro", "New", "2", "path", "Classic Rock", "1960");

	}

	@Test
	public void testGetName() {
		assertEquals("Fire", s.getName());
	}

	@Test
	public void testSetName() {
		s.setName("Firee");
		assertEquals("Firee", s.getName());
	}

	@Test
	public void testGetArtist() {
		assertEquals("Pedro", s.getArtist());
	}

	@Test
	public void testSetArtist() {
		s.setArtist("Pedroo");
		assertEquals("Pedroo", s.getArtist());
	}

	@Test
	public void testGetAlbum() {
		assertEquals("New", s.getAlbum());
	}

	@Test
	public void testSetAlbum() {
		s.setAlbum("Neww");
		assertEquals("Neww", s.getAlbum());
	}

	@Test
	public void testGetTrack() {
		assertEquals("2", s.getTrack());
	}

	@Test
	public void testSetTrack() {
		s.setTrack("3");
		assertEquals("3", s.getTrack());
	}

	@Test
	public void testGetPath() {
		assertEquals("path", s.getPath());
	}

	@Test
	public void testSetPath() {
		emptyS.setPath("examplepath");
		assertEquals("examplepath", emptyS.getPath());
	}
	
	@Test
	public void testGetYear() {
		assertEquals("1960", s.getYear());
	}
	
	@Test
	public void testSetYear() {
		emptyS.setYear("2020");
		assertEquals("2020", emptyS.getYear());
	}
	
	@Test
	public void testGetGenre() {
		assertEquals("Classic Rock", s.getGenre());
	}
	
	@Test
	public void testSetGenre() {
		emptyS.setGenre("Rock");
		assertEquals("Rock", emptyS.getGenre());
	}
	
	@Test
	public void testEquals() {
		assertEquals(true, s.equals(new Song("Fire", "Pedro", "New", "2", "path", "Classic Rock", "1960")));
		assertEquals(false, s.equals(new File(s.getPath())));
	}

}

package reproductor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SongTest {

	private Song s;
	
	@Before
	public void setUp() {
		
		s = new Song("Fire", "Pedro","New", 2, "2:00");

	}
	@Test
	public void testGetName() {
		assertEquals("Fire",s.getName());
	}
	
	@Test
	public void testSetName() {
		s.setName("Firee");
		assertEquals("Firee",s.getName());
	}
	
	@Test
	public void  testGetArtist() {
		assertEquals("Pedro",s.getArtist());
	}
	@Test
	public void testSetArtist() {
		s.setArtist("Pedroo");
		assertEquals("Pedroo",s.getArtist());
	}
	@Test
	public void testGetAlbum() {
		assertEquals("New",s.getAlbum());
	}
	@Test
	public void testSetAlbum() {
		s.setAlbum("Neww");
		assertEquals("Neww",s.getAlbum());
	}
	@Test
	public void testGetTrack() {
		assertEquals(2,s.getTrack());
	}
	@Test
	public void testSetTrack() {
		s.setTrack(3);
		assertEquals(3,s.getTrack());
	}
	@Test
	public void testGetDuration() {
		assertEquals("2:00",s.getDuration());
	}
	@Test
	public void testSetDuration() {
		s.setDuration("3:00");
		assertEquals("3:00",s.getDuration());
	}
	

}



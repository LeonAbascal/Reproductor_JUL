package reproductor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SongTest {
		
	private Song s;
	@Before
	public void setUp() {
			
			
	// creamos un objeto nuevo antes de cada @Test
	s = new Song("Fire", "Pedro","New", 2, "2:00");
			
	}
	@Test
	public void testGetName() {
		assertEquals("Fire",s.getName());
	}
	@Test
	public void testSetName(String name) {
		assertEquals(name,s.getName());
	}
	@Test
	public void  testGetArtist() {
		assertEquals("Pedro",s.getArtist());
	}
	@Test
	public void testSetArtist(String artist) {
		assertEquals(artist,s.getArtist());
	}
	@Test
	public void testGetAlbum() {
		assertEquals("New",s.getAlbum());
	}
	@Test
	public void testSetAlbum(String album) {
		assertEquals(album,s.getAlbum());
	}
	@Test
	public void testGetTrack() {
		assertEquals(2,s.getTrack());
	}
	@Test
	public void testSetTrack(int track) {
		assertEquals(track,s.getTrack());
	}
	@Test
	public void testGetDuration() {
		assertEquals("2:00",s.getDuration());
	}
	@Test
	public void testSetDuration(String duration) {
		assertEquals(duration,s.getDuration());
	}
	

}



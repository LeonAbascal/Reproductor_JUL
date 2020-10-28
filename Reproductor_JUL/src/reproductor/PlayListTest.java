package reproductor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class PlayListTest {
	private PlayList p;

	@Before
	public void setUp() {
		p = new PlayList(null, "Lista");
	}

	@Test
	public void testGetSongs() {
		assertEquals(null, p.getSongs());
	}

	@Test
	public void testGetTitle() {
		assertEquals("Lista", p.getTitle());
	}
}

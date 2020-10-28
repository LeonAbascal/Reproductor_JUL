package reproductor;

import org.junit.Assert.assertEquals;

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

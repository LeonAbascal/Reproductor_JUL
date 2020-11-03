package reproductor.mainClasses.tests;



import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.User;

public class UserTest {
	private User u;

	@Before
	public void setUp() {
		u = new User("Yulen", "12345", 3, null);
	}

	@Test
	public void testGetName() {
		assertEquals("Yulen", u.getName());
	}

	@Test
	public void testGetPassword() {
		assertEquals("12345", u.getPassword());
	}

	@Test
	public void testGetEdad() {
		assertEquals(3, u.getEdad());
	}

	@Test
	public void testGetPlaylist() {
		assertEquals(null, u.getPlaylist());
	}

}

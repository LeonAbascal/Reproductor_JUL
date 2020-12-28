package reproductor.mainClasses.tests;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.PlayList;
import reproductor.mainClasses.User;

public class UserTest {
	private User u;
	private User emptyU;

	@Before
	public void setUp() {
		emptyU = new User();
		u = new User("Yulen", "12345",  null);
	}

	@Test
	public void testGetName() {
		assertEquals("Yulen", u.getName());
	}
	
	@Test
	public void testSetName() {
		emptyU.setName("Test");
		assertEquals("Test",emptyU.getName());
	}

	@Test
	public void testGetPassword() {
		assertEquals("12345", u.getPassword());
	}

	@Test
	public void testSetPassword() {
		emptyU.setPassword("TestP");
		assertEquals("TestP", emptyU.getPassword());
	}
	
	@Test
	public void testGetPlaylist() {
		assertEquals(null, u.getPlaylist());
	}
	
	@Test
	public void testSetPlaylist() {
		ArrayList<PlayList> p = new ArrayList<PlayList>();
		p.add(new PlayList());
		emptyU.setPlaylist(p);
		assertEquals(p, emptyU.getPlaylist());
	}
	
	@Test
	public void testToString() {
		assertEquals("User [name=Yulen, password=12345]",u.toString());
	}

}

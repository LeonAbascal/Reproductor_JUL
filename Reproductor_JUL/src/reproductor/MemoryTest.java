package reproductor;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MemoryTest {


	
private Song s;
private PlayList p;
private User u;
private User u2;
private Memory m;
private ArrayList<Song> songs;
private ArrayList<User> users;

	@Before
	public void setUp() {
		s= new Song("Fire", "Pedro","New", 2, "2:00");
		songs= new ArrayList<Song>();
		songs.add(s);
		p = new PlayList(songs,"First");
		u = new User("Julen" ,"12345", 20, p);
		u2 = new User("Unai" ,"12345", 20, p);
		users= new ArrayList<User>();
		users.add(u);
		m = new Memory(users);

	}
	@Test
	public void  TestGetUsers() {
		for (User user : m.getUsers()) {
			assertEquals(u,user);
		}
	}

}

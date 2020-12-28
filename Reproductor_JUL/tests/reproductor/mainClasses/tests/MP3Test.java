package reproductor.mainClasses.tests;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.MP3;

public class MP3Test {

	private MP3 mp3;
	
	@Before
	public void setUp() {
		mp3 = new MP3("TEST");
	}
	
	@Test
	public void testPlay() {
		mp3.play();
	}
	
	@Test
	public void testClose() {
		mp3.close();
	}

}

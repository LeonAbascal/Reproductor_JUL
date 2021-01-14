package reproductor.mainClasses.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.MP3;

public class MP3Test {

	private File f;
	private File f2;
	private File f3;
	private File f4;
	
	@Before
	public void setUp() {
		f = new File("MusicFiles\\Arctic Monkeys - Do I Wanna Know (Official Video).mp3"); // ID 3v2 GETTERS
		f2 = new File("MusicFiles\\Outrun Project.mp3"); // ID 3v1 GETTERS
		f3 = new File("MusicFiles\\Beave - Too Much Ft. Bethany Lamb (Torin Dundas Remix).mp3"); // ID 3v2 SETTERS
		f4 = new File("MusicFiles\\Pink Floyd - Have a Cigar.mp3"); // ID 3v1 SETTERS
	}
	
	@Test
	public void testGetTitleTag() {
		String titleTag = MP3.getTitleTag(f);
		String titleTag2 = MP3.getTitleTag(f2);

		assertEquals("Do I Wanna Know", titleTag);
		assertEquals("Outrun Oddyssey", titleTag2);
	}
	
	@Test
	public void testGetArtistTag() {
		String artistTag = MP3.getArtistTag(f);
		String artistTag2 = MP3.getArtistTag(f2);

		assertEquals("Arctic Monkeys", artistTag);
		assertEquals("", artistTag2);
	}
	
	@Test
	public void testGetAlbumTag() {
		String albumTag = MP3.getAlbumTag(f);
		String albumTag2 = MP3.getAlbumTag(f2);

		assertEquals("AM", albumTag);
		assertEquals("Crepuscle", albumTag2);
	}
	
	@Test
	public void testGetTrackNo() {
		String trackNoTag = MP3.getTrackNoTag(f);
		String trackNoTag2 = MP3.getTrackNoTag(f2);

		assertEquals("1/12", trackNoTag);
		assertEquals("1", trackNoTag2);
	}
	
	@Test
	public void testGetGenreTag() {
		String genreTag = MP3.getGenreTag(f);
		String genreTag2 = MP3.getGenreTag(f2);
		
		assertEquals(null, genreTag);
		assertEquals("Unknown", genreTag2);
	}
	
	@Test
	public void testGetYearTag() {
		String yearTag = MP3.getYearTag(f);
		String yearTag2 = MP3.getYearTag(f2);
		
		assertEquals("2013", yearTag);
		assertEquals("2020", yearTag2);
	}
	
	@Test
	public void testGetAllTags() {
		String[] expectedTags = {"Do I Wanna Know", "Arctic Monkeys", "AM", "1/12", null, "2013"};
		String[] actualTags = MP3.getAllTags(f);
		assertEquals(expectedTags[0], actualTags[0]);
		assertEquals(expectedTags[1], actualTags[1]);
		assertEquals(expectedTags[2], actualTags[2]);
		assertEquals(expectedTags[3], actualTags[3]);
		assertEquals(expectedTags[4], actualTags[4]);
		assertEquals(expectedTags[5], actualTags[5]);
		
		String[] expectedTags2 = {"Outrun Oddyssey", "", "Crepuscle", "1", "Unknown", "2020"};
		String[] actualTags2 = MP3.getAllTags(f2);
		assertEquals(expectedTags2[0], actualTags2[0]);
		assertEquals(expectedTags2[1], actualTags2[1]);
		assertEquals(expectedTags2[2], actualTags2[2]);
		assertEquals(expectedTags2[3], actualTags2[3]);
		assertEquals(expectedTags2[4], actualTags2[4]);
		assertEquals(expectedTags2[5], actualTags2[5]);
	}
	
	@Test
	public void testSetTitleTag() {
		// SET
		MP3.setTitleTag(f3, "Too Much Ft. Bethany Lamb (Torin Dundas Remix)");
		MP3.setTitleTag(f4, "Have a Cigar");
		
		// GET & CHECK
		String titleTag = MP3.getTitleTag(f3);
		String titleTag2 = MP3.getTitleTag(f4);
		assertEquals("Too Much Ft. Bethany Lamb (Torin Dundas Remix)", titleTag);
		assertEquals("Have a Cigar", titleTag2);
	}
	
	@Test
	public void testSetArtistTag() {
		// SET
		MP3.setArtistTag(f3, "Beave");
		MP3.setArtistTag(f4, "Pink Floyd");
		
		// GET & CHECK
		String artistTag = MP3.getTitleTag(f3);
		String artistTag2 = MP3.getTitleTag(f4);
		assertEquals("Beave", artistTag);
		assertEquals("Pink Floyd", artistTag2);
	}
	
	@Test
	public void testSetAlbumTag() {
		// SET
		MP3.setAlbumTag(f3, "Unknown");
		MP3.setAlbumTag(f4, "Wish You Were Here");
		
		// GET & CHECK
		String albumTag = MP3.getTitleTag(f3);
		String albumTag2 = MP3.getTitleTag(f4);
		assertEquals("Unknown", albumTag);
		assertEquals("Wish You Were Here", albumTag2);
	}
	
	@Test
	public void testSetTrackNoTag() {
		// SET
		MP3.setTrackNoTag(f3, "1");
		MP3.setTrackNoTag(f4, "3");
		
		// GET & CHECK
		String trackNoTag = MP3.getTitleTag(f3);
		String trackNoTag2 = MP3.getTitleTag(f4);
		assertEquals("1", trackNoTag );
		assertEquals("3", trackNoTag2);
	}
	
	@Test
	public void testSetYearTag() {
		// SET
		MP3.setYearTag(f3, "2016");
		MP3.setYearTag(f4, "1984");
		
		// GET & CHECK
		String yearTag = MP3.getTitleTag(f3);
		String yearTag2 = MP3.getTitleTag(f4);
		assertEquals("2016", yearTag);
		assertEquals("1984", yearTag2);
	}
	/*
	@Test
	public void testSetGenreTag() {
		// SET
		MP3.setGenreTag(f3, "Future House");
		
		// GET & CHECK
		String genreTag = MP3.getTitleTag(f3);
		assertEquals("Future House", genreTag);
	}*/

}

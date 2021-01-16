package reproductor.mainClasses.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import reproductor.mainClasses.Counter;

public class CounterTest {

	private Counter counter;
	private Counter counter1;
	
	@Before
	public void setUp() {
		counter = new Counter();
		counter1 = new Counter(1);
	}
	
	@Test
	public void testGet() {
		assertEquals(0, counter.get());
		assertEquals(1, counter1.get());
	}
	
	@Test
	public void testInc() {
		counter.inc();
		assertEquals(1, counter.get());
		counter1.inc(2);
		assertEquals(3, counter1.get());
	}
	
	@Test
	public void testDec() {
		counter.dec();
		assertEquals(-1, counter.get());
		counter1.dec(3);
		assertEquals(-2, counter1.get());
	}
	
	@Test
	public void testReset() {
		counter1.reset();
		assertEquals(0, counter.get());
	}
	
	@Test
	public void testToString() {
		assertEquals("1",counter1.toString());
	}
}

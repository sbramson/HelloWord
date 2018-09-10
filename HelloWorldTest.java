import static org.junit.Assert.*;

import org.junit.*;

public class HelloWorldTest {
	
	// Asserts that HelloWorld class main method outputs "Hello, world!"
	@Test
	public void testHelloWorldOutput() {
		assertEquals("Hello, world!", AbstractMainTests.executeMain("HelloWorld"));
	}
}
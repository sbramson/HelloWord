import static org.junit.Assert.*;

import org.junit.*;

public class HelloWorldTest {
	
	// Asserts that HelloWorld class main method outputs "Hello, world!"
	// adapted from http://gilmation.com/articles/junit-with-standard-and-error-output-in-main-methods/
	@Test
	public void testHelloWorldOutput(String[] args) throws Throwable {
		private static final PrintStream OUT = System.out;
		private static final PrintStream ERR = System.err;
		
		ByteArrayOutputStream bosOut = new ByteArrayOutputStream();
		ByteArrayOutputStream bosErr = new ByteArrayOutputStream();
		PrintStream tempOutput = new PrintStream(bosOut, true);
		PrintStream tempError = new PrintStream(bosErr, true);
		System.setOut(tempOutput);
		System.setErr(tempError);
		
		List<String> result = new ArrayList<String>();

		Class c = c.forName("HelloWorld");
		Object o = c.newInstance();
		Method m = o.getClass().getMethod("main", (new String[0]).getClass());
		
		if ((m.getReturnType() != Void.TYPE) ||
			(!Modifier.isStatic(m.getModifiers()))) {
				throw new RuntimeException("main method prototype not static and/or void");
		}
		Object[] param = { args };
		m.invoke(o, param);
		
		BufferedReader outReader = new BufferedReader(new StringReader(bosOut.toString()));
		BufferedReader errReader = new BufferedReader(new StringReader(bosErr.toString()));
		String outLine = outReader.readLine();
		String errLine = errReader.readLine();
		while (outLine != null || errLine != null) {
			if (outLine != null) {
				result.add("stdout: " + outLine);
				outLine = outReader.readLine();
			}
			if (errLine != null) {
				result.add("stderr: " + errLine);
				errLine = errReader.readLine();
			}
		}
		
		System.err.flush();
		System.out.flush();
		System.setOut(OUT);
		System.setErr(ERR);
		
		bosOut.close();
		bosErr.close();		
		
		assertArrayEquals(new String[]{ "Hello, world!" }, result.toArray(new String[0]));
	}
}
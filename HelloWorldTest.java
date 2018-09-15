import static org.junit.Assert.*;

import org.junit.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class HelloWorldTest {
	
	// Asserts that HelloWorld class main method outputs "Hello, world!"
	// adapted from http://gilmation.com/articles/junit-with-standard-and-error-output-in-main-methods/
	@Test
	public void testHelloWorldOutput() throws Throwable {
		PrintStream OUT = System.out;
		PrintStream ERR = System.err;
		
		ByteArrayOutputStream bosOut = new ByteArrayOutputStream();
		ByteArrayOutputStream bosErr = new ByteArrayOutputStream();
		PrintStream tempOutput = new PrintStream(bosOut, true);
		PrintStream tempError = new PrintStream(bosErr, true);
		System.setOut(tempOutput);
		System.setErr(tempError);
		
		List<String> result = new ArrayList<String>();

		Class c = Class.forName("HelloWorld");
		Object o = c.newInstance();
		Method m = o.getClass().getMethod("main", (new String[0]).getClass());
		
		if ((m.getReturnType() != Void.TYPE) ||
			(!Modifier.isStatic(m.getModifiers()))) {
				throw new RuntimeException("main method prototype not static and/or void");
		}
		Object[] param = { null };
		m.invoke(o, param);
		
		BufferedReader outReader = new BufferedReader(new StringReader(bosOut.toString()));
		BufferedReader errReader = new BufferedReader(new StringReader(bosErr.toString()));
		String outLine = outReader.readLine();
		String errLine = errReader.readLine();
		while (outLine != null || errLine != null) {
			if (outLine != null) {
				StringBuilder sb = new StringBuilder("stdout: ");
				sb.append(outLine);
				result.add(sb.toString());
				outLine = outReader.readLine();
			}
			if (errLine != null) {
				StringBuilder sb = new StringBuilder("stderr: ");
				sb.append(errLine);
				result.add(sb.toString());
				errLine = errReader.readLine();
			}
		}
		
		System.err.flush();
		System.out.flush();
		System.setOut(OUT);
		System.setErr(ERR);
		
		bosOut.close();
		bosErr.close();		
		
		assertArrayEquals(new String[]{ "stdout: Hello, world!" }, result.toArray(new String[0]));
	}
}
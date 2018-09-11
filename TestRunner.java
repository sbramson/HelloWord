

public class TestRunner {
	public static void main(String[] args) {
		Result r = JUnitCore.runClasses(HelloWorldTest.class);
		for (Failure f : r.getFailures()) {
			System.out.println(f.toString());
		}
		if (!r.wasSuccessful()) System.out.println("At least one failure, see above.");
		else System.out.println("All tests passed!");
	}
}
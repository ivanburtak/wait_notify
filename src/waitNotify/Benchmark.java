package waitNotify;

public class Benchmark {
	private static long start = -1;

	public static void begin() {
		start = System.nanoTime();
	}
	
	public static long end() {
		if (!hasStarted()) {
			return 0;
		}
		
		long end = System.nanoTime();
		long delta = end - start;
		start = -1;
		
		return delta;
	}
	
	public static boolean hasStarted() {
		return start != -1;
	}
}
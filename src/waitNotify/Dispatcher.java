package waitNotify;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutionException;

public class Dispatcher {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String[] filenames = {"file1", "file2", "file3"};
		
		
		Benchmark.begin();
		
		Pipeline<FileData> pipeline = new Pipeline<>();

		Thread counterThread = new Thread(new SpaceCounter(filenames, pipeline));
		Thread processorThread = new Thread(new LetterCapitalizer(pipeline));

		counterThread.start();
		processorThread.start();

		counterThread.join();
		processorThread.join();
		System.out.printf("%-25s %d ns%n", "Pipeline time: ", Benchmark.end());
		
		
		Benchmark.begin();
		
		List<Thread> threads = new ArrayList<>();
		Thread thread;
		for (String filename : filenames) {
			thread = new Thread(() -> {
				long spaceCount = FileController.countSpaces(filename);
				if (spaceCount == -1) {
					System.err.println("Skipping file '" + filename + "' due to an error.");
					return;
				}
				
				switch (FileController.getCapitalizeMode(spaceCount)) {
				case LAST_LETTER:
					FileController.capitalizeLastLetters(filename);
					break;
				case FIRST_LETTER:
					FileController.capitalizeFirstLetters(filename);
					break;
				} 
			});
			threads.add(thread);
			thread.start();
		}

		for (Thread t : threads) {
			t.join();
		}
		
		System.out.printf("%-25s %d ns%n", "Classic Parallel time: ", Benchmark.end());
	}
}


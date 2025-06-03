package waitNotify;

public class SpaceCounter implements Runnable {
	private String[] filenames;
	private Pipeline<FileData> outPipeline;
	SpaceCounter(String[] filenames, Pipeline<FileData> outPipeline) {
		this.filenames = filenames;
		this.outPipeline = outPipeline;
	}
	public void run() {
		long spaceCount;
		for (String filename : filenames) {
			spaceCount = FileController.countSpaces(filename);
			if (spaceCount == -1) {
				System.err.println("Skipping file '" + filename + "' due to an error.");
				continue;
			}
			
			outPipeline.put(new FileData(filename, spaceCount));
		}
		outPipeline.close();
	}
}
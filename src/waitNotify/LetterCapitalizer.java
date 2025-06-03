package waitNotify;

public class LetterCapitalizer implements Runnable {
	private Pipeline<FileData> inPipeline;
	LetterCapitalizer(Pipeline<FileData> inPipeline) {
		this.inPipeline = inPipeline;
	}
	public void run() {
		FileData fileData;
		while (true) {
			fileData = inPipeline.get();
			if (fileData == null) {
				break;
			}
			switch (fileData.capitalizeMode) {
			case LAST_LETTER:
				FileController.capitalizeLastLetters(fileData.filename);
				break;
			case FIRST_LETTER:
				FileController.capitalizeFirstLetters(fileData.filename);
				break;
			}
		}
	}
}
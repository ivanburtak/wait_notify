package waitNotify;

public class FileData {
	final String filename;
	final CapitalizeMode capitalizeMode;

	FileData(String filename, long spaces) {
		this.filename = filename;
		this.capitalizeMode = FileController.getCapitalizeMode(spaces);
	}
}

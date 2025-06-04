package waitNotify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

enum CapitalizeMode {
	LAST_LETTER,
	FIRST_LETTER
}

public class FileController {
	public static long countSpaces(String filename) {
		long spaceCount = 0, ch;
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			while ((ch = reader.read()) != -1) {
				if (ch == ' ') {
					spaceCount++;
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading file '" + filename + "': " + e.getMessage());
			return -1;
		}
		return spaceCount;
	}
	
	public static CapitalizeMode getCapitalizeMode(long spaceCount) {
		return spaceCount % 2 == 0 ? CapitalizeMode.LAST_LETTER : CapitalizeMode.FIRST_LETTER;
	}
	
	public static void capitalizeFirstLetters(final String inputFilename) {
		final String outputFilename = "output_" + inputFilename;
		try (
			final BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
			final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))
		) {
			String line;
			boolean delimiterSeen;
			char[] charArray;
			char ch;
			while ((line = reader.readLine()) != null) {
				charArray = line.toCharArray();
				// Щоб завжди збільшувати перший символ нового рядка(рахуємо початок рядку за роздільник)
				delimiterSeen = true;
				for (int i = 0; i < charArray.length; i++) {
					ch = charArray[i];
					if (delimiterSeen) {
						if (Character.isAlphabetic(ch)) {
							charArray[i] = Character.toUpperCase(ch);
							delimiterSeen = false;
						}
						continue;
					}
					
					if (!Character.isAlphabetic(ch) && !Character.isDigit(ch)) {
						delimiterSeen = true;
					}
				}
				writer.write(charArray);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error processing file '" + inputFilename + "': " + e.getMessage());
		}
	}
	
	public static void capitalizeLastLetters(final String inputFilename) {
		final String outputFilename = "output_" + inputFilename;
		try (
			final BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
			final BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))
		) {
			String line;
			char[] charArray;
			char ch;
			char lastCh;
			while ((line = reader.readLine()) != null) {
				charArray = line.toCharArray();
				if (charArray.length == 0) {
					writer.newLine();
					continue;
				}
				// Починаємо з 1 щоб не вийти за допустимі межі при отриманні останнього символу
				for (int i = 1; i < charArray.length; i++) {
					ch = charArray[i];
					lastCh = charArray[i - 1];
					if (!Character.isAlphabetic(ch) && !Character.isDigit(ch) && Character.isAlphabetic(lastCh)) {
						charArray[i - 1] = Character.toUpperCase(lastCh);
					}
				}
				// Якщо рядок закінчився літерою(рахуємо кінець рядку за роздільник)
				ch = charArray[charArray.length - 1];
				if (Character.isAlphabetic(ch)) {
					charArray[charArray.length - 1] = Character.toUpperCase(ch);
				}
				
				writer.write(charArray);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error processing file '" + inputFilename + "': " + e.getMessage());
		}
	}
}
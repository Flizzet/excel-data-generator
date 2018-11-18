package com.flizzet.creation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.flizzet.input.PossibleInput;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class FileCreator {

	private static WritableWorkbook workbook;
	private static String fileName;
	private static String directory;
	private static boolean userCreatedDirectory;

	public FileCreator() {

	}

	public static void createFile() {

		System.out.println("File creator started");

		/*
		 * User input to create file directory
		 */
		System.out.println("Do you want a specific directory?");
		Scanner scanner = new Scanner(System.in);
		String sd = scanner.nextLine();
		// Checking to see if user said yes
		for (String s : PossibleInput.yesResponses) {
			if (sd.toLowerCase().equals(s.toLowerCase())) {
				userCreatedDirectory = true;
			}
		}
		// Checking to see if user said no
		for (String s : PossibleInput.noResponses) {
			if (sd.toLowerCase().equals(s.toLowerCase())) {
				userCreatedDirectory = false;
			}
		}
		/*
		 * // Show a directory chooser if they answered yes
		 * (userCreatedDirectory) { fileChooser = new JFileChooser();
		 * //fileChooser.showOpenDialog(this); }
		 */
		if (userCreatedDirectory) {
			System.out.println("Please type out your directory in a c/folder/folder/ format");
			directory = scanner.nextLine();
		} else {
			directory = "Generated Excel Files/";
		}
		// Checking if the directory exists, otherwise creating it
		Path path = Paths.get(directory);
		if (Files.exists(path)) {
			System.out.println("Excel file directory found");
		} else {
			try {
				Files.createDirectories(path);
				System.out.println("Excel file directory not found. Created one for you");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * User input to create file
		 */
		System.out.println("What would you like your file to be called?");
		fileName = scanner.nextLine();
		scanner.close();
		// Adding .xls to fileName
		if (fileName.contains(".xls")) {
			System.out.println("File extension identified");
		} else {
			System.out.println("File extension not identified, adding it for you");
			fileName = fileName + ".xls";
		}

		/*
		 * Creating file
		 */
		try {
			System.out.println("Creating file...");
			workbook = Workbook.createWorkbook(new File(directory + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("File created");
		
		/*
		 * Adding sheet
		 */
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);

		/*
		 * Opening file writer
		 */
		FileWriter.writeNewFile(sheet, workbook);

	}

}
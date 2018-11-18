package com.flizzet.creation;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class FileWriter {

	private static int nRows = 31;
	private static int nColumns = 145;
	private static Label[] topLabels;
	private static Label[][] dataLabels;
	private static Label[] genderLabels;
	private static int maxMales = 15;
	private static int maxFemales = 15;

	public FileWriter() {

		System.out.println("File writer added");

	}

	public static void writeNewFile(WritableSheet sheet, WritableWorkbook workbook) {

		System.out.println("File writer started");

		/*
		 * Current algorithm used for current project, wouldnt be in final work
		 */
		buildIaGenders();
		buildIaLabels();

		// Adding top rows labels
		try {
			System.out.println("Adding top cells to workbook...");
			for (int i = 1; i < topLabels.length; i++) {
				sheet.addCell(topLabels[i]);
			}
			System.out.println("Successfully added cells to workbook");
		} catch (WriteException e) {
			System.err.println("Failed to build top cells");
		}

		// Adding all first column genders
		try {
			System.out.println("Adding genders to the workbook...");
			for (int i = 1; i < genderLabels.length; i++) {
				sheet.addCell(genderLabels[i]);
			}
		} catch (WriteException e) {
			System.err.println("Failed to build gender cells");
		}

		// Adding all data
		try {
			System.out.println("Adding all data to workbook...");
			// Loop to create full cells
			for (int i = 1; i < nColumns; i++) {
				for (int j = 1; j < nRows; j++) {
					sheet.addCell(dataLabels[i][j]);
				}
			}
		} catch (WriteException e) {
			System.err.println("Failed to build data cells");
		}

		/*
		 * Writing workbook
		 */
		try {
			System.out.println("Writing to workbook...");
			workbook.write();
		} catch (IOException e) {
			System.err.println("Failed to write to workbook");
		} finally {
			try {
				workbook.close();
			} catch (WriteException | IOException e) {
				System.err.println("Failed to close the workbook");
			}
		}

		System.out.println("Successfully built, wrote and completed workbook");

	}

	// IA
	private static void buildIaLabels() {

		topLabels = new Label[nColumns];

		// Loop to create top columns (tc) ie 3*12
		int tc = 1;
		for (int i = 1; i < 13; i++) {
			for (int j = 1; j < 13; j++) {
				Label newLabel = new Label(tc, 0, (i + " * " + j));
				topLabels[tc] = newLabel;
				tc++;
			}
		}

		buildIaBaseNums();

	}

	// IA
	private static void buildIaBaseNums() {

		System.out.println("Building IA Data");

		double[] baseNumbers = new double[12];

		// Base numbers. Starts at 0, so +1 for all
		baseNumbers[0] = 1.5;
		baseNumbers[1] = 1.75;
		baseNumbers[2] = 2;
		baseNumbers[3] = 2.1;
		baseNumbers[4] = 1.5;
		baseNumbers[5] = 2.3;
		baseNumbers[6] = 3.5;
		baseNumbers[7] = 3.6;
		baseNumbers[8] = 3.8;
		baseNumbers[9] = 2;
		baseNumbers[10] = 2.3;
		baseNumbers[11] = 3;

		double skillLevel;
		Random r = new Random();

		// Creating labels
		skillLevel = 1 + r.nextFloat();
		dataLabels = new Label[nColumns][nRows];

		int currentNumber = 0;
		for (int i = 0; i < nColumns; i++) {
			for (int j = 1; j < nRows; j++) {

				currentNumber++;

				if (currentNumber == 12) {
					currentNumber = 0;
				}

				// Making dataNumber baseNumber
				double dataNumber = baseNumbers[currentNumber];
				// Building data multiplication equation
				double dataMultiplier = 0;
				skillLevel = r.nextInt(10);

				//dataMultiplier = (dataNumber / (r.nextFloat() * 2)) + 1 * (r.nextFloat()) + skillLevel;
				int mistake = 0;
				int mistakeNum = 5;
				if(currentNumber > 7) {
					if(r.nextInt(20) == mistakeNum) {
						mistake = r.nextInt(10);
					}
				} else
				{
					mistake = 0;
				}
				dataMultiplier = (1 / (1 + r.nextFloat()) * skillLevel) + (r.nextInt(3) + 7) + (skillLevel * 2) + mistake;

				// Applying data multiplication equation
				dataNumber = dataNumber * dataMultiplier;
				dataNumber = dataNumber / 10;
				DecimalFormat df = new DecimalFormat("#.##");
				dataNumber = Double.valueOf(df.format(dataNumber));

				/*if (dataNumber > 90) {
					// Making dataNumber baseNumber
					dataNumber = baseNumbers[currentNumber];
					// Building data multiplication equation
					dataMultiplier = 0;
					skillLevel = r.nextDouble();

					dataMultiplier = (dataNumber / (r.nextFloat())) + 1 * (r.nextFloat()) + skillLevel;

					// Applying data multiplication equation
					dataNumber = dataNumber * dataMultiplier;
					dataNumber = dataNumber / 10;
					dataNumber = Double.valueOf(df.format(dataNumber));
				}*/

				// Checking data, making sure it's not too high

				// Putting data in array
				dataLabels[i][j] = new Label(i, j, "" + dataNumber);

			}
		}

	}

	// IA
	private static void buildIaGenders() {

		System.out.println("Generating genders");

		genderLabels = new Label[nRows];

		Random r = new Random();
		// Gender 1 = male 0 = female
		int gender = 1;

		int totalMales = 0;
		int totalFemales = 0;
		String strGender = "Male";

		for (int i = 1; i < nRows; i++) {
			gender = r.nextInt(2);

			if (gender == 1) {
				if (totalMales < maxMales) {
					totalMales++;
					strGender = "Male";
				} else {
					totalFemales++;
					strGender = "Female";
				}
			} else if (gender == 0) {
				if (totalFemales < maxFemales) {
					totalFemales++;
					strGender = "Female";
				} else {
					totalMales++;
					strGender = "Male";
				}
			} else {
				gender = 1;
			}

			genderLabels[i] = new Label(0, i, strGender);
		}

	}
}
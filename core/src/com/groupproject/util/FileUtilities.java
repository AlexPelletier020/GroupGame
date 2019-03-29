package com.groupproject.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * @author Mohammed Al-Safwan
 * @date Feb 26, 2018
 */
public class FileUtilities {

	public static char[][] readLeaderBoardFile(String filePath) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method : public ArrayList<char[][]> readLeaderBoardFile(String filePath)
		//
		// Method parameters : filePath - the path to the puzzles
		//
		// Method return : ArrayList<char[][]> - a list of puzzles
		//
		// Synopsis : Read The leader board file and copy the lines in an array list of
		// char[][].
		//
		//
		// Modifications :
		// Date Developer Notes
		// ---- --------- -----
		// Sep18th, 2018 Mohammed Al-Safwan Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		char[][] cells = new char[37][21];

		FileHandle handle = Gdx.files.internal(filePath); // initialize BufferReader

		String line = ""; // initialize an empty string to hold each line

		if (null != (line = handle.readString())) { // Read a line and check if it's not null

			String[] cell = line.split("\\r?\\n"); // Split the line using "," to an array
			String[] completeLine;
			for (int height = cell.length - 1; height >= 0; height--) {

				completeLine = (cell[height].split(" "));

				for (int width = completeLine.length - 1; width >= 0; width--) { // Add the chars to the cells
					System.out.println(width);
					System.out.println(height);
					cells[height][width] = completeLine[width].charAt(0);
					System.out.print(cells[height][width] + " ");
				}
				System.out.println();
				System.out.println("=======================");
			}
		}

		return cells; // return the ArrayList of the puzzles
	}
}

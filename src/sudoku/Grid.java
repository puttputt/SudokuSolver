package sudoku;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This is the main class of the game. It initialises, solves and also displays
 * the results in the console. Even though we have already switched to the GUI
 * for the display. It contains a Linked List of nodes which keeps the
 * information of each node in the game.
 * 
 * @author Pooya Sami & Kyle Smyth
 */
public class Grid {
	private LinkedList<Node> grid;
	private int count;

	/**
	 * Default Constructor It creates the whole grid with 0's inserted and also
	 * creates the empty possibilities for each node.
	 */
	public Grid() {
		grid = new LinkedList<Node>();
		count = 0;
		LinkedList<Integer> empty = new LinkedList<Integer>();
		for (int i = 1; i < 10; i++) {
			empty.push(i);
		}
		for (int i = 0; i < 81; i++) {
			grid.add(new Node(new LinkedList<Integer>(empty), false, 0));
		}
	}

	/**
	 * Copy Constructor It creates a copy of a grid
	 */
	public Grid(Grid original) {
		grid = new LinkedList<Node>();
		count = 0;

		for (int i = 0; i < 81; i++) {
			LinkedList<Integer> empty = new LinkedList<Integer>();
			empty = original.getGrid().get(i).getPossibilities();
			grid.add(new Node(new LinkedList<Integer>(empty), original
					.getGrid().get(i).isPermanent(), original.getGrid().get(i)
					.getValue()));
		}

	}

	/**
	 * Returns the Grid number according to the following grid map 1|2|3 -+-+-
	 * 4|5|6 -+-+- 7|8|9
	 * 
	 * @param index
	 *            index of the node (0-80)
	 * @return Grid Number and -1 if the index is out of bounds
	 */
	public int FindGridIndex(int index) {
		int row = index / 9 + 1;
		int col = index % 9 + 1;
		if (row <= 3) {
			if (col <= 3) {
				return 1;
			} else if (col >= 4 && col <= 6) {
				return 2;
			} else if (col >= 7 && col <= 9) {
				return 3;
			}
		} else if (row >= 4 && row <= 6) {
			if (col <= 3) {
				return 4;
			} else if (col >= 4 && col <= 6) {
				return 5;
			} else if (col >= 7 && col <= 9) {
				return 6;
			}
		} else if (row >= 7 && row <= 9) {
			if (col <= 3) {
				return 7;
			} else if (col >= 4 && col <= 6) {
				return 8;
			} else if (col >= 7 && col <= 9) {
				return 9;
			}
		}
		return -1;
	}

	/**
	 * Gets the filename that contains the puzzle and imports it to the game.
	 * 
	 * @param filename
	 *            name of the file
	 */
	public void Import(String filename) {
		File file = new File(filename);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			String inputString;
			int value;

			while (bis.available() != 0) {
				inputString = dis.readLine();
				for (int i = 0; i < 81; i++) {
					value = Integer.parseInt(inputString.substring(i, i + 1));
					if (!CheckGrid(i, value) && !checkCol(i, value)
							&& !checkRow(i, value)) {
						grid.get(i).setValue(value);
						grid.get(i).setPermanent(true);
						grid.get(i).getPossibilities().clear();
						Update(i);
						count++;
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Prints the grid to the console
	 */
	void printGrid() {
		int j = 0;
		for (int i = 0; i < 81; i++) {
			System.out.print(grid.get(i).getValue());
			++j;
			if (j == 9) {
				j = 0;
				System.out.println();
			}

		}
	}

	/**
	 * Finds the first index of the sub-grid
	 * 
	 * @param index
	 *            of the Node
	 * @return returns the first index of the sub-grid
	 */
	public int FindGridFirstIndex(int index) {
		int gridIndex = FindGridIndex(index);

		switch (gridIndex) {
		case 1:
			index = 0;
			break;
		case 2:
			index = 3;
			break;
		case 3:
			index = 6;
			break;
		case 4:
			index = 27;
			break;
		case 5:
			index = 30;
			break;
		case 6:
			index = 33;
			break;
		case 7:
			index = 54;
			break;
		case 8:
			index = 57;
			break;
		case 9:
			index = 60;
			break;
		}
		return index;
	}

	/**
	 * Checks is the number exists in the grid of the Node we want
	 * 
	 * @param index
	 *            index of the Node
	 * @param number
	 *            the number we want to search for it
	 * @return true if it exists
	 */
	public boolean CheckGrid(int index, int number) {
		index = FindGridFirstIndex(index);
		boolean answer = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid.get(index + j).getValue() == number)
					answer = true;
			}
			index = index + 9;
		}
		return answer;
	}

	/**
	 * Returns the first index of the row
	 * 
	 * @param index
	 *            index of the Node
	 * @return first index of the row
	 */
	public int FindRowFirstIndex(int index) {
		if (index < 9)
			return 0;
		else if (index >= 9 && index < 18)
			return 9;
		else if (index >= 18 && index < 27)
			return 18;
		else if (index >= 27 && index < 36)
			return 27;
		else if (index >= 36 && index < 45)
			return 36;
		else if (index >= 45 && index < 54)
			return 45;
		else if (index >= 54 && index < 63)
			return 54;
		else if (index >= 63 && index < 72)
			return 63;
		else if (index >= 72 && index < 81)
			return 72;
		else
			return -1;
	}

	/**
	 * Checks the row of the given index for the number we need
	 * 
	 * @param index
	 *            index of the Node
	 * @param number
	 *            the value
	 * @return true if the number exists in the row
	 */
	public boolean checkRow(int index, int number) {
		index = FindRowFirstIndex(index);
		boolean answer = false;
		int i = index;
		do {
			if (grid.get(i).getValue() == number)
				answer = true;
			++i;
		} while (i < (index + 9));
		return answer;
	}

	/**
	 * finds the first index of the column
	 * 
	 * @param index
	 *            index of the Node
	 * @return first index of the column
	 */
	public int FindColFirstIndex(int index) {
		while (index >= 9) {
			index = index - 9;
		}
		return index;
	}

	/**
	 * checks the column of the given index for the value
	 * 
	 * @param index
	 *            index of the Node
	 * @param number
	 *            the number we're searching for
	 * @return true if the number exists in the column
	 */
	public boolean checkCol(int index, int number) {
		index = this.FindColFirstIndex(index);

		boolean answer = false;
		int i = index;
		do {
			if (grid.get(i).getValue() == number)
				answer = true;
			i = i + 9;
		} while (i < (81));
		return answer;

	}

	/**
	 * Updates the possibilities according to the value of the index given
	 * 
	 * @param index
	 *            the reference index
	 */
	public void Update(int index) {
		UpdateRow(index);
		UpdateColumn(index);
		UpdateSubGrid(index);
	}

	/**
	 * Updates the possibilities of the row according to the value of the index
	 * given (checks the value of the given node and updates according to that).
	 * 
	 * @param index
	 *            the reference index
	 */
	public void UpdateRow(int index) {

		int rowFirstIndex = FindRowFirstIndex(index);
		for (int i = rowFirstIndex; i < rowFirstIndex + 9; i++) {
			if (grid.get(i).getPossibilities().contains(
					grid.get(index).getValue())) {
				grid.get(i).getPossibilities().remove(
						grid.get(index).getValue());
			}
		}

	}

	/**
	 * Updates the possibilities of the column according to the value of the
	 * index given (checks the value of the given node and updates according to
	 * that).
	 * 
	 * @param index
	 *            the reference index
	 */
	public void UpdateColumn(int index) {
		int columnFirstIndex = FindColFirstIndex(index);
		while (columnFirstIndex < 81) {
			if (grid.get(columnFirstIndex).getPossibilities().contains(
					grid.get(index).getValue())) {
				grid.get(columnFirstIndex).getPossibilities().remove(
						grid.get(index).getValue());
			}
			columnFirstIndex = columnFirstIndex + 9;
		}
	}

	/**
	 * Updates the possibilities of the sub grid according to the value of the
	 * index given. (checks the value of the given node and updates according to
	 * that)
	 * 
	 * @param index
	 *            the reference index
	 */
	public void UpdateSubGrid(int index) {
		int subGridFirstIndex = FindGridFirstIndex(index);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (grid.get(subGridFirstIndex + j).getPossibilities()
						.contains(grid.get(index).getValue())) {
					grid.get(subGridFirstIndex + j).getPossibilities().remove(
							grid.get(index).getValue());
				}
			}
			subGridFirstIndex = subGridFirstIndex + 9;
		}
	}

	/**
	 * a recursive method for solving simple puzzles. it looks for the nodes
	 * with only one possibility and continues until there aren't any
	 * "one possibility" node left.
	 */
	public void SimpleFillValuesRecursive() {
		boolean moved = false;
		for (int j = 0; j < 81; j++) {
			if (grid.get(j).getPossibilities().size() == 1) {
				grid.get(j).setValue(
						grid.get(j).getPossibilities().removeFirst());
				grid.get(j).setPermanent(true);
				Update(j);
				count++;
				moved = true;
				YouWin();
			}
		}
		if (moved) {
			SimpleFillValuesRecursive();
		}
	}

	/**
	 * Used by the brute force method, it gathers the empty cells together.
	 * @return returns the linked list of empty cells
	 */
	public LinkedList<Integer> getEmptyCells() {
		LinkedList<Integer> emptyCells = new LinkedList<Integer>();
		int index = 0;
		for (int i = 0; i < 81; i++) {
			if (grid.get(i).getValue() == 0) {
				emptyCells.add(i);
				index++;
			}

		}
		return emptyCells;
	}

	/**
	 * A brute force, backtracking method to solve advanced puzzles: It checks
	 * the values in the emptyCells, if it's equal to 9 it goes to the node
	 * before that and increases its value by 1. and keeps doing this until the
	 * game is finished. It also checks if the inserted value is consistent with
	 * the rest of the table, then proceeds to the next empty cell.
	 * @return true when all the values are filled
	 */
	public boolean bruteSolve() {
		LinkedList<Integer> emptyCells = getEmptyCells();
		int index = 0;
		while (index < emptyCells.size()) {
			int i = emptyCells.get(index);
			int value = grid.get(i).getValue();

			if (value == 9) {
				grid.get(i).setValue(0);
				index--;
				if (index < 0) {
					return true;
				}
			} else {
				grid.get(i).setValue(value + 1);
				System.out.println("--");
				this.printGrid();
				System.out.println("--");
				if (rowConsistent(i) && colConsistent(i) && gridConsistent(i)) {
					index++;
				}
			}
		}
		return true;
	}

	/**
	 * Checks to see if the row is consistent, meaning that there are no
	 * repeating integers
	 * 
	 * @param index
	 *            the reference index
	 * @return returns true if the value of the index does not conflict with the
	 *         row rule of the game
	 */
	public boolean rowConsistent(int index) {
		LinkedList<Integer> values = new LinkedList<Integer>();
		int i = this.FindRowFirstIndex(index);
		index = i;
		do {
			if (!values.contains(grid.get(i).getValue())) {
				if (!(grid.get(i).getValue() == 0))
					values.add(grid.get(i).getValue());

			} else {
				return false;
			}
			++i;
		} while (i < (index + 9));
		return true;
	}

	/**
	 * Checks to see if the column is consistent, meaning that there are no
	 * repeating integers
	 * 
	 * @param index
	 *            the reference index
	 * @return returns true if the value of the index does not conflict with the
	 *         column rule of the game
	 */
	public boolean colConsistent(int index) {
		LinkedList<Integer> values = new LinkedList<Integer>();
		int i = this.FindColFirstIndex(index);
		do {
			if (!values.contains(grid.get(i).getValue())) {
				if (!(grid.get(i).getValue() == 0))
					values.add(grid.get(i).getValue());
			} else {
				return false;
			}
			i = i + 9;
		} while (i < (81));
		return true;

	}

	/**
	 * Checks to see if the sub-grid is consistent, meaning that there are no
	 * repeating integers
	 * 
	 * @param index
	 *            the reference index
	 * @return returns true if the value of the index does not conflict with the
	 *         grid rule of the game
	 */
	public boolean gridConsistent(int index) {
		LinkedList<Integer> values = new LinkedList<Integer>();
		index = FindGridFirstIndex(index);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!values.contains(grid.get(index + j).getValue())) {
					if (!(grid.get(index + j).getValue() == 0))
						values.add(grid.get(index + j).getValue());
				} else {
					return false;
				}
			}
			index = index + 9;
		}
		return true;
	}

	/**
	 * It prints out a YouWin message in the console when all the nodes are full
	 * 
	 * @return returns true if the game is finished.
	 */
	public boolean YouWin() {
		if (count == 81) {
			System.out.println("You Win!");
			return true;
		}
		return false;
	}

	/**
	 * Getter method for the "grid".
	 * 
	 * @return the grid
	 */
	public LinkedList<Node> getGrid() {
		return grid;
	}

	/**
	 * Getter method for the "count".
	 * 
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Setter method for the count
	 * 
	 * @param count
	 *            the new value of the count
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
